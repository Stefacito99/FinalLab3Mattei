package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.enums.TipoCuenta;
import ar.edu.utn.frbb.tup.model.enums.TipoMoneda;
import ar.edu.utn.frbb.tup.model.enums.TipoPersona;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.ClienteNotFoundException;
import ar.edu.utn.frbb.tup.model.exception.DatosIncorrectosException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    @Mock
    private ClienteDao clienteDao;

    @InjectMocks
    private ClienteService clienteService;

    private ClienteDto clienteDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        clienteDto = new ClienteDto();
        clienteDto.setNombre("Pepe");
        clienteDto.setApellido("Rino");
        clienteDto.setDni(44958360);
        clienteDto.setFechaNacimiento("2003-08-24");
        clienteDto.setTipoPersona("F");
        clienteDto.setBanco("Revolut");
    }

    @Test
    public void testCrearCliente() throws ClienteAlreadyExistsException, DatosIncorrectosException {
        Cliente cliente = new Cliente(clienteDto);

        when(clienteDao.find(cliente.getDni())).thenReturn(null);
        doNothing().when(clienteDao).save(any(Cliente.class));

        clienteService.darDeAltaCliente(clienteDto);

        verify(clienteDao, times(1)).save(any(Cliente.class));
    }

    @Test
    public void testCrearClienteAlreadyExistsException() throws ClienteAlreadyExistsException, DatosIncorrectosException {
        Cliente cliente = new Cliente(clienteDto);

        when(clienteDao.find(cliente.getDni())).thenReturn(cliente);

        assertThrows(ClienteAlreadyExistsException.class, () -> clienteService.darDeAltaCliente(clienteDto));
    }

    @Test
    public void testCrearClienteConDniDistinto() throws ClienteAlreadyExistsException, DatosIncorrectosException {
        ClienteDto otroClienteDto = new ClienteDto();
        otroClienteDto.setNombre("Paul");
        otroClienteDto.setApellido("Turner");
        otroClienteDto.setDni(12345678);
        otroClienteDto.setFechaNacimiento("1990-01-01");
        otroClienteDto.setTipoPersona("F");
        otroClienteDto.setBanco("Santander");

        Cliente otroCliente = new Cliente(otroClienteDto);

        when(clienteDao.find(otroCliente.getDni())).thenReturn(null);
        doNothing().when(clienteDao).save(any(Cliente.class));

        clienteService.darDeAltaCliente(otroClienteDto);

        ArgumentCaptor<Cliente> clienteCaptor = ArgumentCaptor.forClass(Cliente.class);
        verify(clienteDao, times(1)).save(clienteCaptor.capture());

        Cliente clienteGuardado = clienteCaptor.getValue();
        assertEquals(otroClienteDto.getDni(), clienteGuardado.getDni());
        assertEquals(otroClienteDto.getNombre(), clienteGuardado.getNombre());
        assertEquals(otroClienteDto.getApellido(), clienteGuardado.getApellido());
        assertEquals(LocalDate.parse(otroClienteDto.getFechaNacimiento()), clienteGuardado.getFechaNacimiento());
        assertEquals(TipoPersona.PERSONA_FISICA, clienteGuardado.getTipoPersona());
        assertEquals(otroClienteDto.getBanco(), clienteGuardado.getBanco());
    }

    @Test
    public void testAgregarCuentaAClienteSuccess() throws TipoCuentaAlreadyExistsException, DatosIncorrectosException, ClienteNotFoundException {
        Cliente pepeRino = new Cliente();
        pepeRino.setDni(26456439);
        pepeRino.setNombre("Pepe");
        pepeRino.setApellido("Rino");
        pepeRino.setFechaNacimiento(LocalDate.of(1978, 3, 25));
        pepeRino.setTipoPersona(TipoPersona.PERSONA_FISICA);

        Cuenta cuenta = new Cuenta();
        cuenta.setMoneda(TipoMoneda.PESOS);
        cuenta.setBalance(500000);
        cuenta.setTipoCuenta(TipoCuenta.CAJA_AHORRO);

        when(clienteDao.find(26456439)).thenReturn(pepeRino);

        clienteService.agregarCuenta(cuenta, pepeRino.getDni());

        verify(clienteDao, times(1)).save(pepeRino);
        assertEquals(1, pepeRino.getCuentas().size());
        assertEquals(pepeRino.getDni(), cuenta.getDniTitular());
    }

    @Test
    public void testAgregarCuentaAClienteDuplicada() throws TipoCuentaAlreadyExistsException, DatosIncorrectosException, ClienteNotFoundException {
        Cliente luciano = new Cliente();
        luciano.setDni(26456439);
        luciano.setNombre("Pepe");
        luciano.setApellido("Rino");
        luciano.setFechaNacimiento(LocalDate.of(1978, 3, 25));
        luciano.setTipoPersona(TipoPersona.PERSONA_FISICA);

        Cuenta cuenta = new Cuenta();
        cuenta.setMoneda(TipoMoneda.PESOS);
        cuenta.setBalance(500000);
        cuenta.setTipoCuenta(TipoCuenta.CAJA_AHORRO);

        when(clienteDao.find(26456439)).thenReturn(luciano);

        clienteService.agregarCuenta(cuenta, luciano.getDni());

        Cuenta cuenta2 = new Cuenta();
        cuenta2.setMoneda(TipoMoneda.PESOS);
        cuenta2.setBalance(500000);
        cuenta2.setTipoCuenta(TipoCuenta.CAJA_AHORRO);

        assertThrows(TipoCuentaAlreadyExistsException.class, () -> clienteService.agregarCuenta(cuenta2, luciano.getDni()));
        verify(clienteDao, times(1)).save(luciano);
        assertEquals(1, luciano.getCuentas().size());
        assertEquals(luciano.getDni(), cuenta.getDniTitular());
    }
}