package ar.edu.utn.frbb.tup.controller.validator;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CuentaValidatorTest {

    private CuentaValidator cuentaValidator;

    @BeforeEach
    public void setUp() {
        cuentaValidator = new CuentaValidator();
    }

    @Test
    public void testValidateTipoCuentaInvalido() {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setTipoCuenta("XX");
        cuentaDto.setTipoMoneda("P");
        cuentaDto.setBalance(1000);
        cuentaDto.setDniTitular(12345678);

        assertThrows(IllegalArgumentException.class, () -> cuentaValidator.validate(cuentaDto), "El tipo de cuenta no es correcto");
    }

    @Test
    public void testValidateTipoMonedaInvalido() {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setTipoCuenta("CC");
        cuentaDto.setTipoMoneda("X");
        cuentaDto.setBalance(1000);
        cuentaDto.setDniTitular(12345678);

        assertThrows(IllegalArgumentException.class, () -> cuentaValidator.validate(cuentaDto), "El tipo de moneda no es correcto");
    }

    @Test
    public void testValidateBalanceNegativo() {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setTipoCuenta("CC");
        cuentaDto.setTipoMoneda("P");
        cuentaDto.setBalance(-1000);
        cuentaDto.setDniTitular(12345678);

        assertThrows(IllegalArgumentException.class, () -> cuentaValidator.validate(cuentaDto), "El balance no puede ser negativo");
    }

    @Test
    public void testValidateDniTitularInvalido() {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setTipoCuenta("CC");
        cuentaDto.setTipoMoneda("P");
        cuentaDto.setBalance(1000);
        cuentaDto.setDniTitular(-1);

        assertThrows(IllegalArgumentException.class, () -> cuentaValidator.validate(cuentaDto), "El DNI del titular no es válido");
    }

    @Test
    public void testValidateCuentaValida() {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setTipoCuenta("CC");
        cuentaDto.setTipoMoneda("P");
        cuentaDto.setBalance(1000);
        cuentaDto.setDniTitular(12345678);

        cuentaValidator.validate(cuentaDto);
    }
}