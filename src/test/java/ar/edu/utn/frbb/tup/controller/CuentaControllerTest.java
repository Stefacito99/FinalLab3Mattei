package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.enums.TipoCuenta;
import ar.edu.utn.frbb.tup.model.enums.TipoMoneda;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.CuentaNotFoundException;
import ar.edu.utn.frbb.tup.model.exception.DatosIncorrectosException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.ClienteNotFoundException;
import ar.edu.utn.frbb.tup.service.CuentaService;
import ar.edu.utn.frbb.tup.controller.validator.CuentaValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CuentaController.class)
public class CuentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CuentaService cuentaService;

    @MockBean
    private CuentaValidator cuentaValidator;

    @Autowired
    private ObjectMapper objectMapper;

    private CuentaDto cuentaDto;

    @BeforeEach
    public void setUp() {
        cuentaDto = new CuentaDto();
        cuentaDto.setTipoCuenta("CC");
        cuentaDto.setTipoMoneda("P");
        cuentaDto.setBalance(1000);
        cuentaDto.setDniTitular(12345678);
    }

    @Test
    public void testCrearCuentaExitoso() throws Exception {
        Cuenta cuenta = new Cuenta();
        cuenta.setTipoCuenta(TipoCuenta.CUENTA_CORRIENTE);
        cuenta.setMoneda(TipoMoneda.PESOS);
        cuenta.setBalance(1000);
        cuenta.setDniTitular(12345678);

        when(cuentaService.darDeAltaCuenta(any(CuentaDto.class))).thenReturn(cuenta);

        mockMvc.perform(post("/cuenta")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuentaDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipoCuenta").value("CUENTA_CORRIENTE"))
                .andExpect(jsonPath("$.moneda").value("PESOS"))
                .andExpect(jsonPath("$.balance").value(cuenta.getBalance()))
                .andExpect(jsonPath("$.dniTitular").value(cuenta.getDniTitular()));
    }

    @Test
    public void testCrearCuentaDatosIncorrectos() throws Exception {
        doThrow(new DatosIncorrectosException("Datos incorrectos")).when(cuentaValidator).validate(any(CuentaDto.class));

        mockMvc.perform(post("/cuenta")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuentaDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Datos incorrectos"));
    }

    @Test
    public void testCrearCuentaYaExiste() throws Exception {
        doThrow(new CuentaAlreadyExistsException("Cuenta ya existe")).when(cuentaService).darDeAltaCuenta(any(CuentaDto.class));

        mockMvc.perform(post("/cuenta")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuentaDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorMessage").value("Cuenta ya existe"));
    }

    @Test
    public void testCrearCuentaTipoCuentaYaExiste() throws Exception {
        doThrow(new TipoCuentaAlreadyExistsException("Tipo de cuenta ya existe")).when(cuentaService).darDeAltaCuenta(any(CuentaDto.class));

        mockMvc.perform(post("/cuenta")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuentaDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorMessage").value("Tipo de cuenta ya existe"));
    }

    @Test
    public void testCrearCuentaClienteNoEncontrado() throws Exception {
        doThrow(new ClienteNotFoundException("Cliente no encontrado")).when(cuentaService).darDeAltaCuenta(any(CuentaDto.class));

        mockMvc.perform(post("/cuenta")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuentaDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Cliente no encontrado"));
    }

    @Test
    public void testObtenerCuentaExitoso() throws Exception {
        Cuenta cuenta = new Cuenta();
        cuenta.setTipoCuenta(TipoCuenta.CUENTA_CORRIENTE);
        cuenta.setMoneda(TipoMoneda.PESOS);
        cuenta.setBalance(1000);
        cuenta.setDniTitular(12345678);

        when(cuentaService.find(1L)).thenReturn(cuenta);

        mockMvc.perform(get("/cuenta/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoCuenta").value("CUENTA_CORRIENTE"))
                .andExpect(jsonPath("$.moneda").value("PESOS"))
                .andExpect(jsonPath("$.balance").value(cuenta.getBalance()))
                .andExpect(jsonPath("$.dniTitular").value(cuenta.getDniTitular()));
    }

    @Test
    public void testObtenerCuentaNoEncontrada() throws Exception {
        when(cuentaService.find(1L)).thenThrow(new CuentaNotFoundException("Cuenta no encontrada"));

        mockMvc.perform(get("/cuenta/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Cuenta no encontrada"))
                .andExpect(jsonPath("$.errorCode").value(404));
    }
}