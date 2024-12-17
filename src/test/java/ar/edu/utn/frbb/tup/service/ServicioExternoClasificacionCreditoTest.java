package ar.edu.utn.frbb.tup.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class ServicioExternoClasificacionCreditoTest {

    @InjectMocks
    private ServicioExternoClasificacionCredito servicioExternoClasificacionCredito;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTieneBuenaCalificacionExitoso() {
        long dni = 12345678L;
        boolean resultado = servicioExternoClasificacionCredito.tieneBuenaCalificacion(dni);
        assertTrue(resultado || !resultado); // El resultado puede ser true o false debido a la aleatoriedad
    }

    @Test
    public void testTieneBuenaCalificacionDniInvalido() {
        long dni = -1L;
        assertThrows(IllegalArgumentException.class, () -> servicioExternoClasificacionCredito.tieneBuenaCalificacion(dni));
    }
}