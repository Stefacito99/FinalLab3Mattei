package ar.edu.utn.frbb.tup.controller.validator;

import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.model.exception.DatosIncorrectosException;
import ar.edu.utn.frbb.tup.model.exception.TipoMonedaNoSoportada;
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
        prestamoDto.setNumeroCliente(12345678);

        assertThrows(TipoMonedaNoSoportada.class, () -> prestamoValidator.validar(prestamoDto), "Tipo de moneda no soportada");
    }

    @Test
    public void testValidarMontoInvalido() {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setMoneda("P");
        prestamoDto.setMonto(0);
        prestamoDto.setPlazoMeses(12);
        prestamoDto.setNumeroCliente(12345678);

        assertThrows(IllegalArgumentException.class, () -> prestamoValidator.validar(prestamoDto), "El monto debe ser mayor a 0");
    }

    @Test
    public void testValidarPlazoMesesInvalido() {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setMoneda("P");
        prestamoDto.setMonto(1000);
        prestamoDto.setPlazoMeses(0);
        prestamoDto.setNumeroCliente(12345678);

        assertThrows(IllegalArgumentException.class, () -> prestamoValidator.validar(prestamoDto), "El plazo en meses debe ser mayor a 0");
    }

    @Test
    public void testValidarNumeroClienteInvalido() {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setMoneda("P");
        prestamoDto.setMonto(1000);
        prestamoDto.setPlazoMeses(12);
        prestamoDto.setNumeroCliente(0);

        assertThrows(IllegalArgumentException.class, () -> prestamoValidator.validar(prestamoDto), "El numero de cliente debe ser mayor a 0");
    }

    @Test
    public void testValidarPrestamoValido() throws DatosIncorrectosException, TipoMonedaNoSoportada {
        PrestamoDto prestamoDto = new PrestamoDto();
        prestamoDto.setMoneda("P");
        prestamoDto.setMonto(1000);
        prestamoDto.setPlazoMeses(12);
        prestamoDto.setNumeroCliente(12345678);

        prestamoValidator.validar(prestamoDto);
    }
}