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
        TipoMoneda tipoMoneda = prestamoDto.getMoneda();

        // Imprimir detalles del cliente y sus cuentas
        System.out.println("Cliente: " + cliente.getDni());
        System.out.println("Cuentas del cliente:" + tipoMoneda);        

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

    public Prestamo buscarPrestamoPorId(long id) {
        return prestamoDao.find(id);
    }
}