package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.enums.TipoPersona;
import ar.edu.utn.frbb.tup.model.exception.ClienteNotFoundException;
import ar.edu.utn.frbb.tup.model.exception.DatosIncorrectosException;
import ar.edu.utn.frbb.tup.service.ClienteService;
import ar.edu.utn.frbb.tup.controller.validator.ClienteValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
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
        clienteDto = new ClienteDto();
        clienteDto.setTipoPersona("F");
        clienteDto.setFechaNacimiento("2000-01-01");
        clienteDto.setNombre("Pepe");
        clienteDto.setApellido("Rino");
        clienteDto.setDni(12345678);
        clienteDto.setBanco("Banco");
    }

    @Test
    public void testCrearClienteExitoso() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setTipoPersona(TipoPersona.PERSONA_FISICA);
        cliente.setFechaNacimiento(LocalDate.parse("2000-01-01"));
        cliente.setNombre("Pepe");
        cliente.setApellido("Rino");
        cliente.setDni(12345678);
        cliente.setBanco("Banco");

        when(clienteService.darDeAltaCliente(any(ClienteDto.class))).thenReturn(cliente);

        mockMvc.perform(post("/cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dni").value(cliente.getDni()))
                .andExpect(jsonPath("$.nombre").value(cliente.getNombre()))
                .andExpect(jsonPath("$.apellido").value(cliente.getApellido()));
    }

    @Test
    public void testCrearClienteDatosIncorrectos() throws Exception {
        doThrow(new DatosIncorrectosException("Datos incorrectos")).when(clienteValidator).validate(any(ClienteDto.class));

        mockMvc.perform(post("/cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Datos incorrectos"));
    }

    @Test
    public void testCrearClienteYaExiste() throws Exception {
        doThrow(new ClienteAlreadyExistsException("Cliente ya existe")).when(clienteService).darDeAltaCliente(any(ClienteDto.class));

        mockMvc.perform(post("/cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorMessage").value("Cliente ya existe"));
    }

    @Test
    public void testBuscarClientePorDniExitoso() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setTipoPersona(TipoPersona.PERSONA_FISICA);
        cliente.setFechaNacimiento(LocalDate.parse("2000-01-01"));
        cliente.setNombre("Pepe");
        cliente.setApellido("Rino");
        cliente.setDni(12345678);
        cliente.setBanco("Banco");

        when(clienteService.buscarClientePorDni(12345678)).thenReturn(cliente);

        mockMvc.perform(get("/cliente/12345678")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dni").value(cliente.getDni()))
                .andExpect(jsonPath("$.nombre").value(cliente.getNombre()))
                .andExpect(jsonPath("$.apellido").value(cliente.getApellido()));
    }

    @Test
    public void testBuscarClientePorDniNoEncontrado() throws Exception {
        when(clienteService.buscarClientePorDni(12345678)).thenThrow(new ClienteNotFoundException("Cliente no encontrado"));

        mockMvc.perform(get("/cliente/12345678")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Cliente no encontrado"));
    }
}