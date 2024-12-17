package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.controller.validator.PrestamoValidator;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.model.enums.TipoMoneda;
import ar.edu.utn.frbb.tup.model.exception.*;
import ar.edu.utn.frbb.tup.persistence.PrestamoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoDao prestamoDao;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private ServicioExternoClasificacionCredito servicioExternoClasificacionCredito;

    public Prestamo darDeAltaPrestamo(PrestamoDto prestamoDto) throws TipoMonedaNoSoportada, DatosIncorrectosException, PrestamoYaExisteException, ClienteNotFoundException, CreditoRechazadoException, CuentaNotFoundException {
        // Validar el prestamoDto
        PrestamoValidator prestamoValidator = new PrestamoValidator();
        prestamoValidator.validar(prestamoDto);

        // Verificar que el cliente exista
        Cliente cliente = clienteService.buscarClientePorDni(prestamoDto.getDniTitular());
        if (cliente == null) {
            throw new ClienteNotFoundException("Cliente no encontrado.");
        }

        // Verificar que el cliente posea una cuenta en la moneda solicitada
        TipoMoneda tipoMoneda = TipoMoneda.fromString(prestamoDto.getMoneda());
        Cuenta cuenta = cliente.getCuentas().stream()
                .filter(c -> c.getMoneda().equals(tipoMoneda))
                .findFirst()
                .orElseThrow(() -> new DatosIncorrectosException("El cliente no posee una cuenta en la moneda solicitada."));

        // Verificar la calificación crediticia del cliente
        if (!servicioExternoClasificacionCredito.tieneBuenaCalificacion(cliente.getDni())) {
            throw new CreditoRechazadoException("El cliente no tiene una buena calificación crediticia.");
        }

        // Crear y guardar el préstamo
        Prestamo prestamo = new Prestamo(prestamoDto);
        prestamoDao.save(prestamo);

        // Actualizar el saldo de la cuenta del cliente
        cuenta.setBalance(cuenta.getBalance() + prestamoDto.getMonto());
        cuentaService.actualizarCuenta(cuenta);

        return prestamo;
    }

    public Prestamo find(long id) throws PrestamoNotFoundException {
        Prestamo prestamo = prestamoDao.find(id);
        if (prestamo == null) {
            throw new PrestamoNotFoundException("Préstamo no encontrado.");
        }
        return prestamo;
    }

    public Prestamo pagarCuota(long numeroPrestamo, double monto) throws PrestamoNotFoundException, CuentaNotFoundException, NoAlcanzaException, DatosIncorrectosException, ClienteNotFoundException, PrestamoPagadoException {
        Prestamo prestamo = prestamoDao.find(numeroPrestamo);
        if (prestamo == null) {
            throw new PrestamoNotFoundException("Préstamo no encontrado.");
        }
    
        if (prestamo.getMontoFaltanteAPagar() <= 0) {
            throw new PrestamoPagadoException("El préstamo ya ha sido completamente pagado.");
        }
    
        Cliente cliente = clienteService.buscarClientePorDni(prestamo.getDniTitular());
        Cuenta cuenta = cliente.getCuentas().stream()
                .filter(c -> c.getMoneda().equals(prestamo.getMoneda()))
                .findFirst()
                .orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrada."));
    
        int cuotaMensual = prestamo.getCuotaMensual();
        if (monto != cuotaMensual) {
            throw new DatosIncorrectosException("El monto de la cuota no es correcto.");
        }
        if (cuenta.getBalance() < monto) {
            throw new NoAlcanzaException("No hay suficiente saldo en la cuenta para pagar la cuota.");
        }
    
        // Solo se realizan modificaciones si hay suficiente saldo
        cuenta.setBalance(cuenta.getBalance() - monto);
        cuentaService.actualizarCuenta(cuenta);
    
        prestamo.setCuotasPagadas(prestamo.getCuotasPagadas() + 1);
        prestamo.actualizarMontoFaltanteAPagar();
        prestamoDao.actualizar(prestamo);
    
        return prestamo;
    }

    public List<Prestamo> obtenerPrestamosPorCliente(long dniTitular) throws ClienteNotFoundException {
        Cliente cliente = clienteService.buscarClientePorDni(dniTitular);
        if (cliente == null) {
            throw new ClienteNotFoundException("Cliente no encontrado.");
        }
        return prestamoDao.findAll().stream()
                .filter(prestamo -> prestamo.getDniTitular() == dniTitular)
                .collect(Collectors.toList());
    }
}