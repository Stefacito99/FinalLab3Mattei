package ar.edu.utn.frbb.tup.controller.validator;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.exception.DatosIncorrectosException;
import org.springframework.stereotype.Component;

@Component
public class CuentaValidator {

    public void validate(CuentaDto cuentaDto) throws DatosIncorrectosException {
        if (!"CC".equals(cuentaDto.getTipoCuenta()) && !"CA".equals(cuentaDto.getTipoCuenta())) {
            throw new DatosIncorrectosException("El tipo de cuenta no es correcto");
        }
        if (!"P".equals(cuentaDto.getTipoMoneda()) && !"D".equals(cuentaDto.getTipoMoneda())) {
            throw new DatosIncorrectosException("El tipo de moneda no es correcto");
        }
        if (cuentaDto.getBalance() < 0) {
            throw new DatosIncorrectosException("El balance no puede ser negativo");
        }
        if (cuentaDto.getDniTitular() <= 0) {
            throw new DatosIncorrectosException("El DNI del titular no es válido");
        }
    }
}