package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.enums.TipoCuenta;
import ar.edu.utn.frbb.tup.model.enums.TipoMoneda;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.CuentaNotFoundException;
import ar.edu.utn.frbb.tup.model.exception.DatosIncorrectosException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.ClienteNotFoundException;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CuentaServiceTest {

    @Mock
    private CuentaDao cuentaDao;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private CuentaService cuentaService;

    private CuentaDto cuentaDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cuentaDto = new CuentaDto();
        cuentaDto.setTipoCuenta("CC");
        cuentaDto.setTipoMoneda("P");
        cuentaDto.setBalance(1000);
        cuentaDto.setDniTitular(12345678);
    }

    @Test
    public void testDarDeAltaCuentaExitoso() throws CuentaAlreadyExistsException, TipoCuentaAlreadyExistsException, DatosIncorrectosException, ClienteNotFoundException {
        when(cuentaDao.find(anyLong())).thenReturn(null);
        doNothing().when(clienteService).agregarCuenta(any(Cuenta.class), anyLong());

        Cuenta cuenta = cuentaService.darDeAltaCuenta(cuentaDto);

        assertNotNull(cuenta);
        assertEquals(TipoCuenta.CUENTA_CORRIENTE, cuenta.getTipoCuenta());
        assertEquals(TipoMoneda.PESOS, cuenta.getMoneda());
        assertEquals(1000, cuenta.getBalance());
        assertEquals(12345678, cuenta.getDniTitular());
        verify(cuentaDao, times(1)).save(any(Cuenta.class));
    }

    @Test
    public void testDarDeAltaCuentaYaExiste() {
        when(cuentaDao.find(anyLong())).thenReturn(new Cuenta());

        assertThrows(CuentaAlreadyExistsException.class, () -> cuentaService.darDeAltaCuenta(cuentaDto));
    }

    @Test
    public void testFindCuentaExitoso() throws CuentaNotFoundException {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(1L);
        when(cuentaDao.find(1L)).thenReturn(cuenta);

        Cuenta foundCuenta = cuentaService.find(1L);

        assertNotNull(foundCuenta);
        assertEquals(1L, foundCuenta.getNumeroCuenta());
    }

    @Test
    public void testFindCuentaNoEncontrada() {
        when(cuentaDao.find(1L)).thenReturn(null);

        assertThrows(CuentaNotFoundException.class, () -> cuentaService.find(1L));
    }

    @Test
    public void testActualizarCuentaExitoso() throws CuentaNotFoundException {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(1L);
        when(cuentaDao.find(1L)).thenReturn(cuenta);

        cuentaService.actualizarCuenta(cuenta);

        verify(cuentaDao, times(1)).save(cuenta);
    }

    @Test
    public void testActualizarCuentaNoEncontrada() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(1L);
        when(cuentaDao.find(1L)).thenReturn(null);

        assertThrows(CuentaNotFoundException.class, () -> cuentaService.actualizarCuenta(cuenta));
    }
}