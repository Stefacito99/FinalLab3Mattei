package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.model.enums.TipoMoneda;
import ar.edu.utn.frbb.tup.model.exception.*;
import ar.edu.utn.frbb.tup.service.PrestamoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PrestamoController.class)
public class PrestamoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrestamoService prestamoService;

    @Autowired
    private ObjectMapper objectMapper;

    private PrestamoDto prestamoDto;

    @BeforeEach
    public void setUp() {
        prestamoDto = new PrestamoDto();
        prestamoDto.setMoneda("P");
        prestamoDto.setMonto(1000);
        prestamoDto.setPlazoMeses(12);
        prestamoDto.setDniTitular(12345678);
    }

    @Test
    public void testCrearPrestamoExitoso() throws Exception {
        Prestamo prestamo = new Prestamo();
        prestamo.setNumeroPrestamo(1L);
        prestamo.setFechaCreacion(LocalDateTime.now());
        prestamo.setMonto(1000);
        prestamo.setMoneda(TipoMoneda.PESOS);
        prestamo.setDniTitular(12345678);
        prestamo.setPlazoMeses(12);
        prestamo.setCuotasPagadas(0);
        prestamo.setMontoTotal(1050);
        prestamo.setCuotaMensual(88);

        when(prestamoService.darDeAltaPrestamo(any(PrestamoDto.class))).thenReturn(prestamo);

        mockMvc.perform(post("/prestamo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(prestamoDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroPrestamo").value(prestamo.getNumeroPrestamo()))
                .andExpect(jsonPath("$.monto").value(prestamo.getMonto()))
                .andExpect(jsonPath("$.moneda").value(prestamo.getMoneda().name()))
                .andExpect(jsonPath("$.dniTitular").value(prestamo.getDniTitular()))
                .andExpect(jsonPath("$.plazoMeses").value(prestamo.getPlazoMeses()))
                .andExpect(jsonPath("$.cuotasPagadas").value(prestamo.getCuotasPagadas()))
                .andExpect(jsonPath("$.montoTotal").value(prestamo.getMontoTotal()))
                .andExpect(jsonPath("$.cuotaMensual").value(prestamo.getCuotaMensual()));
    }

    @Test
    public void testCrearPrestamoDatosIncorrectos() throws Exception {
        doThrow(new DatosIncorrectosException("Datos incorrectos")).when(prestamoService).darDeAltaPrestamo(any(PrestamoDto.class));

        mockMvc.perform(post("/prestamo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(prestamoDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Datos incorrectos"));
    }

    @Test
    public void testCrearPrestamoClienteNoEncontrado() throws Exception {
        doThrow(new ClienteNotFoundException("Cliente no encontrado")).when(prestamoService).darDeAltaPrestamo(any(PrestamoDto.class));

        mockMvc.perform(post("/prestamo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(prestamoDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Cliente no encontrado"));
    }

    @Test
    public void testObtenerPrestamoExitoso() throws Exception {
        Prestamo prestamo = new Prestamo();
        prestamo.setNumeroPrestamo(1L);
        prestamo.setFechaCreacion(LocalDateTime.now());
        prestamo.setMonto(1000);
        prestamo.setMoneda(TipoMoneda.PESOS);
        prestamo.setDniTitular(12345678);
        prestamo.setPlazoMeses(12);
        prestamo.setCuotasPagadas(0);
        prestamo.setMontoTotal(1050);
        prestamo.setCuotaMensual(88);

        when(prestamoService.find(1L)).thenReturn(prestamo);

        mockMvc.perform(get("/prestamo/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroPrestamo").value(prestamo.getNumeroPrestamo()))
                .andExpect(jsonPath("$.monto").value(prestamo.getMonto()))
                .andExpect(jsonPath("$.moneda").value(prestamo.getMoneda().name()))
                .andExpect(jsonPath("$.dniTitular").value(prestamo.getDniTitular()))
                .andExpect(jsonPath("$.plazoMeses").value(prestamo.getPlazoMeses()))
                .andExpect(jsonPath("$.cuotasPagadas").value(prestamo.getCuotasPagadas()))
                .andExpect(jsonPath("$.montoTotal").value(prestamo.getMontoTotal()))
                .andExpect(jsonPath("$.cuotaMensual").value(prestamo.getCuotaMensual()));
    }

    @Test
    public void testObtenerPrestamoNoEncontrado() throws Exception {
        when(prestamoService.find(1L)).thenThrow(new PrestamoNotFoundException("Préstamo no encontrado"));

        mockMvc.perform(get("/prestamo/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Préstamo no encontrado"))
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    public void testPagarCuotaExitoso() throws Exception {
        Prestamo prestamo = new Prestamo();
        prestamo.setNumeroPrestamo(1L);
        prestamo.setFechaCreacion(LocalDateTime.now());
        prestamo.setMonto(1000);
        prestamo.setMoneda(TipoMoneda.PESOS);
        prestamo.setDniTitular(12345678);
        prestamo.setPlazoMeses(12);
        prestamo.setCuotasPagadas(1);
        prestamo.setMontoTotal(1050);
        prestamo.setCuotaMensual(88);

        when(prestamoService.pagarCuota(1L, 88)).thenReturn(prestamo);

        mockMvc.perform(post("/prestamo/pago/1")
                .param("monto", "88")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroPrestamo").value(prestamo.getNumeroPrestamo()))
                .andExpect(jsonPath("$.monto").value(prestamo.getMonto()))
                .andExpect(jsonPath("$.moneda").value(prestamo.getMoneda().name()))
                .andExpect(jsonPath("$.dniTitular").value(prestamo.getDniTitular()))
                .andExpect(jsonPath("$.plazoMeses").value(prestamo.getPlazoMeses()))
                .andExpect(jsonPath("$.cuotasPagadas").value(prestamo.getCuotasPagadas()))
                .andExpect(jsonPath("$.montoTotal").value(prestamo.getMontoTotal()))
                .andExpect(jsonPath("$.cuotaMensual").value(prestamo.getCuotaMensual()));
    }

    @Test
    public void testPagarCuotaPrestamoNoEncontrado() throws Exception {
        doThrow(new PrestamoNotFoundException("Préstamo no encontrado")).when(prestamoService).pagarCuota(1L, 88);

        mockMvc.perform(post("/prestamo/pago/1")
                .param("monto", "88")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Préstamo no encontrado"))
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    public void testPagarCuotaMontoIncorrecto() throws Exception {
        doThrow(new DatosIncorrectosException("El monto de la cuota no es correcto")).when(prestamoService).pagarCuota(1L, 50);

        mockMvc.perform(post("/prestamo/pago/1")
                .param("monto", "50")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("El monto de la cuota no es correcto"))
                .andExpect(jsonPath("$.errorCode").value(400))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    public void testObtenerPrestamosPorClienteExitoso() throws Exception {
        Prestamo prestamo = new Prestamo();
        prestamo.setNumeroPrestamo(1L);
        prestamo.setFechaCreacion(LocalDateTime.now());
        prestamo.setMonto(1000);
        prestamo.setMoneda(TipoMoneda.PESOS);
        prestamo.setDniTitular(12345678);
        prestamo.setPlazoMeses(12);
        prestamo.setCuotasPagadas(0);
        prestamo.setMontoTotal(1050);
        prestamo.setCuotaMensual(88);

        when(prestamoService.obtenerPrestamosPorCliente(12345678)).thenReturn(List.of(prestamo));

        mockMvc.perform(get("/prestamo/cliente/12345678")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numeroPrestamo").value(prestamo.getNumeroPrestamo()))
                .andExpect(jsonPath("$[0].monto").value(prestamo.getMonto()))
                .andExpect(jsonPath("$[0].moneda").value(prestamo.getMoneda().name()))
                .andExpect(jsonPath("$[0].dniTitular").value(prestamo.getDniTitular()))
                .andExpect(jsonPath("$[0].plazoMeses").value(prestamo.getPlazoMeses()))
                .andExpect(jsonPath("$[0].cuotasPagadas").value(prestamo.getCuotasPagadas()))
                .andExpect(jsonPath("$[0].montoTotal").value(prestamo.getMontoTotal()))
                .andExpect(jsonPath("$[0].cuotaMensual").value(prestamo.getCuotaMensual()));
    }

    @Test
    public void testObtenerPrestamosPorClienteNoEncontrado() throws Exception {
        doThrow(new ClienteNotFoundException("Cliente no encontrado")).when(prestamoService).obtenerPrestamosPorCliente(12345678);

        mockMvc.perform(get("/prestamo/cliente/12345678")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Cliente no encontrado"))
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}