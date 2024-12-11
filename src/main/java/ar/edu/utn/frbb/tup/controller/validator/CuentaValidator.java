package ar.edu.utn.frbb.tup.controller.validator;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import org.springframework.stereotype.Component;

@Component
public class CuentaValidator {

    public void validate(CuentaDto cuentaDto) {
        if (!"CC".equals(cuentaDto.getTipoCuenta()) && !"CA".equals(cuentaDto.getTipoCuenta())) {
            throw new IllegalArgumentException("El tipo de cuenta no es correcto");
        }
        if (!"P".equals(cuentaDto.getTipoMoneda()) && !"D".equals(cuentaDto.getTipoMoneda())) {
            throw new IllegalArgumentException("El tipo de moneda no es correcto");
        }
    }
}
