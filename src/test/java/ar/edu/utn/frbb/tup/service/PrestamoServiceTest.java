package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.model.enums.TipoMoneda;
import ar.edu.utn.frbb.tup.model.exception.*;
import ar.edu.utn.frbb.tup.persistence.PrestamoDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PrestamoServiceTest {

    @Mock
    private PrestamoDao prestamoDao;

    @Mock
    private ClienteService clienteService;

    @Mock
    private CuentaService cuentaService;

    @Mock
    private ServicioExternoClasificacionCredito servicioExternoClasificacionCredito;

    @InjectMocks
    private PrestamoService prestamoService;

    private PrestamoDto prestamoDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        prestamoDto = new PrestamoDto();
        prestamoDto.setMoneda("P");
        prestamoDto.setMonto(1000);
        prestamoDto.setPlazoMeses(12);
        prestamoDto.setDniTitular(12345678);
    }

    @Test
    public void testDarDeAltaPrestamoExitoso() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        Cuenta cuenta = new Cuenta();
        cuenta.setMoneda(TipoMoneda.PESOS);
        cliente.setCuentas(Collections.singleton(cuenta));

        when(clienteService.buscarClientePorDni(12345678)).thenReturn(cliente);
        when(servicioExternoClasificacionCredito.tieneBuenaCalificacion(12345678)).thenReturn(true);
        doNothing().when(prestamoDao).save(any(Prestamo.class));
        doNothing().when(cuentaService).actualizarCuenta(any(Cuenta.class));

        Prestamo prestamo = prestamoService.darDeAltaPrestamo(prestamoDto);

        assertNotNull(prestamo);
        assertEquals(1000, prestamo.getMonto());
        assertEquals(TipoMoneda.PESOS, prestamo.getMoneda());
        assertEquals(12345678, prestamo.getDniTitular());
        verify(prestamoDao, times(1)).save(any(Prestamo.class));
        verify(cuentaService, times(1)).actualizarCuenta(any(Cuenta.class));
    }

    @Test
    public void testDarDeAltaPrestamoClienteNoEncontrado() throws ClienteNotFoundException {
        when(clienteService.buscarClientePorDni(12345678)).thenReturn(null);

        assertThrows(ClienteNotFoundException.class, () -> prestamoService.darDeAltaPrestamo(prestamoDto));
    }

    @Test
    public void testDarDeAltaPrestamoCuentaNoEncontrada() throws ClienteNotFoundException {
        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        cliente.setCuentas(Collections.emptySet());

        when(clienteService.buscarClientePorDni(12345678)).thenReturn(cliente);

        assertThrows(DatosIncorrectosException.class, () -> prestamoService.darDeAltaPrestamo(prestamoDto));
    }

    @Test
    public void testDarDeAltaPrestamoCalificacionRechazada() throws ClienteNotFoundException {
        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        Cuenta cuenta = new Cuenta();
        cuenta.setMoneda(TipoMoneda.PESOS);
        cliente.setCuentas(Collections.singleton(cuenta));

        when(clienteService.buscarClientePorDni(12345678)).thenReturn(cliente);
        when(servicioExternoClasificacionCredito.tieneBuenaCalificacion(12345678)).thenReturn(false);

        assertThrows(CreditoRechazadoException.class, () -> prestamoService.darDeAltaPrestamo(prestamoDto));
    }

    @Test
    public void testFindPrestamoExitoso() throws PrestamoNotFoundException {
        Prestamo prestamo = new Prestamo();
        prestamo.setNumeroPrestamo(1L);
        when(prestamoDao.find(1L)).thenReturn(prestamo);

        Prestamo foundPrestamo = prestamoService.find(1L);

        assertNotNull(foundPrestamo);
        assertEquals(1L, foundPrestamo.getNumeroPrestamo());
    }

    @Test
    public void testFindPrestamoNoEncontrado() {
        when(prestamoDao.find(1L)).thenReturn(null);

        assertThrows(PrestamoNotFoundException.class, () -> prestamoService.find(1L));
    }

    @Test
    public void testPagarCuotaExitoso() throws Exception {
        Prestamo prestamo = new Prestamo();
        prestamo.setNumeroPrestamo(1L);
        prestamo.setCuotaMensual(88);
        prestamo.setDniTitular(12345678);
        Cuenta cuenta = new Cuenta();
        cuenta.setMoneda(TipoMoneda.PESOS);
        cuenta.setBalance(1000);
        cuenta.setNumeroCuenta(1L); // Aseguramos que la cuenta tenga un número de cuenta
        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        cliente.setCuentas(Collections.singleton(cuenta)); // Usamos singleton para crear un Set

        when(prestamoDao.find(1L)).thenReturn(prestamo);
        when(clienteService.buscarClientePorDni(12345678)).thenReturn(cliente);
        doNothing().when(cuentaService).actualizarCuenta(any(Cuenta.class));
        doNothing().when(prestamoDao).save(any(Prestamo.class));

        Prestamo updatedPrestamo = prestamoService.pagarCuota(1L, 88);

        assertNotNull(updatedPrestamo);
        assertEquals(1, updatedPrestamo.getCuotasPagadas());
        verify(cuentaService, times(1)).actualizarCuenta(any(Cuenta.class));
        verify(prestamoDao, times(1)).save(any(Prestamo.class));
    }

    @Test
    public void testPagarCuotaPrestamoNoEncontrado() {
        when(prestamoDao.find(1L)).thenReturn(null);

        assertThrows(PrestamoNotFoundException.class, () -> prestamoService.pagarCuota(1L, 88));
    }

    @Test
    public void testPagarCuotaCuentaNoEncontrada() throws ClienteNotFoundException {
        Prestamo prestamo = new Prestamo();
        prestamo.setNumeroPrestamo(1L);
        prestamo.setDniTitular(12345678);
        when(prestamoDao.find(1L)).thenReturn(prestamo);
        when(clienteService.buscarClientePorDni(12345678)).thenReturn(new Cliente());

        assertThrows(CuentaNotFoundException.class, () -> prestamoService.pagarCuota(1L, 88));
    }

    /*@Test
    public void testPagarCuotaMontoIncorrecto() throws ClienteNotFoundException {
        Prestamo prestamo = new Prestamo();
        prestamo.setNumeroPrestamo(1L);
        prestamo.setCuotaMensual(88);
        prestamo.setDniTitular(12345678);
        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        Cuenta cuenta = new Cuenta();
        cuenta.setMoneda(TipoMoneda.PESOS);
        cliente.setCuentas(Collections.singletonList(cuenta));

        when(prestamoDao.find(1L)).thenReturn(prestamo);
        when(clienteService.buscarClientePorDni(12345678)).thenReturn(cliente);

        assertThrows(DatosIncorrectosException.class, () -> prestamoService.pagarCuota(1L, 50));
    }*/

    /*@Test
    public void testPagarCuotaSaldoInsuficiente() throws ClienteNotFoundException {
        Prestamo prestamo = new Prestamo();
        prestamo.setNumeroPrestamo(1L);
        prestamo.setCuotaMensual(88);
        prestamo.setDniTitular(12345678);
        Cuenta cuenta = new Cuenta();
        cuenta.setMoneda(TipoMoneda.PESOS);
        cuenta.setBalance(50);
        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        cliente.setCuentas(Collections.singleton(cuenta));

        when(prestamoDao.find(1L)).thenReturn(prestamo);
        when(clienteService.buscarClientePorDni(12345678)).thenReturn(cliente);

        assertThrows(NoAlcanzaException.class, () -> prestamoService.pagarCuota(1L, 88));
    }*/

    @Test
    public void testObtenerPrestamosPorClienteExitoso() throws ClienteNotFoundException {
        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        Prestamo prestamo = new Prestamo();
        prestamo.setNumeroPrestamo(1L);
        prestamo.setDniTitular(12345678);

        when(clienteService.buscarClientePorDni(12345678)).thenReturn(cliente);
        when(prestamoDao.findAll()).thenReturn(List.of(prestamo));

        List<Prestamo> prestamos = prestamoService.obtenerPrestamosPorCliente(12345678);

        assertNotNull(prestamos);
        assertFalse(prestamos.isEmpty());
        assertEquals(1L, prestamos.get(0).getNumeroPrestamo());
    }

    @Test
    public void testObtenerPrestamosPorClienteNoEncontrado() throws ClienteNotFoundException {
        when(clienteService.buscarClientePorDni(12345678)).thenReturn(null);

        assertThrows(ClienteNotFoundException.class, () -> prestamoService.obtenerPrestamosPorCliente(12345678));
    }
}