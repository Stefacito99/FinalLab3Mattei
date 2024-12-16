package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.controller.validator.ClienteValidator;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.enums.TipoPersona;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.ClienteNotFoundException;
import ar.edu.utn.frbb.tup.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @MockBean
    private ClienteValidator clienteValidator;

    @Autowired
    private ObjectMapper objectMapper;

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
    public void testCrearCliente() throws Exception, ClienteAlreadyExistsException {
        Cliente cliente = new Cliente(clienteDto);

        when(clienteService.darDeAltaCliente(any(ClienteDto.class))).thenReturn(cliente);

        mockMvc.perform(post("/cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Pepe"))
                .andExpect(jsonPath("$.apellido").value("Rino"))
                .andExpect(jsonPath("$.dni").value(44958360))
                .andExpect(jsonPath("$.fechaNacimiento").value("2003-08-24"))
                .andExpect(jsonPath("$.tipoPersona").value(TipoPersona.PERSONA_FISICA.name()))
                .andExpect(jsonPath("$.banco").value("Revolut"));
    }

    @Test
    public void testCrearClienteAlreadyExistsException() throws Exception, ClienteAlreadyExistsException {
        when(clienteService.darDeAltaCliente(any(ClienteDto.class))).thenThrow(new ClienteAlreadyExistsException("Ya existe un cliente con DNI 44958360"));

        mockMvc.perform(post("/cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorCode").value(409))
                .andExpect(jsonPath("$.errorMessage").value("Ya existe un cliente con DNI 44958360"));
    }

    @Test
    public void testBuscarClientePorDni() throws Exception {
        Cliente cliente = new Cliente(clienteDto);

        when(clienteService.buscarClientePorDni(44958360L)).thenReturn(cliente);

        mockMvc.perform(get("/cliente/44958360")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Pepe"))
                .andExpect(jsonPath("$.apellido").value("Rino"))
                .andExpect(jsonPath("$.dni").value(44958360))
                .andExpect(jsonPath("$.fechaNacimiento").value("2003-08-24"))
                .andExpect(jsonPath("$.tipoPersona").value(TipoPersona.PERSONA_FISICA.name()))
                .andExpect(jsonPath("$.banco").value("Revolut"));
    }

    @Test
    public void testBuscarClientePorDniNotFoundException() throws Exception {
        when(clienteService.buscarClientePorDni(44958360L)).thenThrow(new ClienteNotFoundException("El cliente no existe"));

        mockMvc.perform(get("/cliente/44958360")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.errorMessage").value("El cliente no existe"));
    }
}