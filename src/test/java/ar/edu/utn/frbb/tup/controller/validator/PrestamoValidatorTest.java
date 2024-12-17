package ar.edu.utn.frbb.tup.controller.validator;

import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.model.exception.DatosIncorrectosException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PrestamoValidatorTest {

    private PrestamoValidator prestamoValidator;

    @BeforeEach
    public void setUp() {
        prestamoValidator = new PrestamoValidator();
    }

    @Test
    public void testValidarMonedaInvalida() {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setMoneda("X");
        prestamoDto.setMonto(1000);
        prestamoDto.setPlazoMeses(12);
        prestamoDto.setDniTitular(12345678);

        assertThrows(DatosIncorrectosException.class, () -> prestamoValidator.validar(prestamoDto), "El tipo de moneda no es correcto");
    }

    @Test
    public void testValidarMontoInvalido() {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setMoneda("P");
        prestamoDto.setMonto(0);
        prestamoDto.setPlazoMeses(12);
        prestamoDto.setDniTitular(12345678);

        assertThrows(DatosIncorrectosException.class, () -> prestamoValidator.validar(prestamoDto), "El monto del préstamo debe ser mayor a cero");
    }

    @Test
    public void testValidarDniTitularInvalido() {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setMoneda("P");
        prestamoDto.setMonto(1000);
        prestamoDto.setPlazoMeses(12);
        prestamoDto.setDniTitular(0);

        assertThrows(DatosIncorrectosException.class, () -> prestamoValidator.validar(prestamoDto), "El DNI del titular no es válido");
    }

    @Test
    public void testValidarPrestamoValido() throws DatosIncorrectosException {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setMoneda("P");
        prestamoDto.setMonto(1000);
        prestamoDto.setPlazoMeses(12);
        prestamoDto.setDniTitular(12345678);

        prestamoValidator.validar(prestamoDto);
    }
}