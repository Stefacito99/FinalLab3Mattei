package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.controller.validator.PrestamoValidator;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.model.enums.TipoMoneda;
import ar.edu.utn.frbb.tup.persistence.PrestamoDao;
import ar.edu.utn.frbb.tup.model.exception.ClienteNotFoundException;
import ar.edu.utn.frbb.tup.model.exception.DatosIncorrectosException;
import ar.edu.utn.frbb.tup.model.exception.CreditoRechazadoException;
import ar.edu.utn.frbb.tup.model.exception.PrestamoYaExisteException;
import ar.edu.utn.frbb.tup.model.exception.TipoMonedaNoSoportada;
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

    public Prestamo darDeAltaPrestamo(PrestamoDto prestamoDto) throws TipoMonedaNoSoportada, DatosIncorrectosException, IllegalArgumentException, PrestamoYaExisteException, ClienteNotFoundException, CreditoRechazadoException {
        PrestamoValidator prestamoValidator = new PrestamoValidator();
        prestamoValidator.validar(prestamoDto);

        // Verificar que el cliente exista
        Cliente cliente = clienteService.buscarClientePorDni(prestamoDto.getNumeroCliente());
        if (cliente == null) {
            throw new ClienteNotFoundException("Cliente no encontrado.");
        }

        // Verificar que el cliente posea una cuenta en la moneda solicitada
        TipoMoneda tipoMoneda = TipoMoneda.fromString(prestamoDto.getMoneda());

        // Buscar la cuenta directamente en el DAO de cuentas
        Cuenta cuenta = cuentaService.getCuentasByCliente(prestamoDto.getNumeroCliente()).stream()
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

    public Prestamo buscarPrestamoPorId(long id) {
        return prestamoDao.find(id);
    }

    public List<Prestamo> consultarPrestamosPorCliente(long numeroCliente) throws ClienteNotFoundException {
        Cliente cliente = clienteService.buscarClientePorDni(numeroCliente);
        if (cliente == null) {
            throw new ClienteNotFoundException("Cliente no encontrado.");
        }
        return prestamoDao.findAll().stream()
                .filter(prestamo -> prestamo.getNumeroCliente() == numeroCliente)
                .collect(Collectors.toList());
    }

    public Prestamo realizarPago(long id, double monto) throws DatosIncorrectosException {
        Prestamo prestamo = prestamoDao.find(id);
        if (prestamo == null) {
            throw new DatosIncorrectosException("Préstamo no encontrado.");
        }
        if (monto <= 0) {
            throw new DatosIncorrectosException("El monto del pago debe ser mayor a cero.");
        }
        if (monto > prestamo.getSaldoRestante()) {
            throw new DatosIncorrectosException("El monto del pago excede el saldo restante del préstamo.");
        }

        // Actualizar el saldo restante del préstamo y las cuotas pagas
        prestamo.setSaldoRestante(prestamo.getSaldoRestante() - monto);
        prestamo.setCuotasPagas(prestamo.getCuotasPagas() + 1);
        prestamoDao.save(prestamo);

        // Actualizar el balance de la cuenta asociada
        Cuenta cuenta = cuentaService.getCuentasByCliente(prestamo.getNumeroCliente()).stream()
                .filter(c -> c.getMoneda().equals(prestamo.getMoneda()))
                .findFirst()
                .orElseThrow(() -> new DatosIncorrectosException("Cuenta asociada no encontrada."));
        cuenta.setBalance(cuenta.getBalance() - monto);
        cuentaService.actualizarCuenta(cuenta);

        return prestamo;
    }
}