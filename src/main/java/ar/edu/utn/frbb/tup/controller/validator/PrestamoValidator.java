package ar.edu.utn.frbb.tup.controller.validator;

import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.model.exception.DatosIncorrectosException;
import org.springframework.stereotype.Component;

@Component
public class PrestamoValidator {

    public void validar(PrestamoDto prestamoDto) throws DatosIncorrectosException {
        if (prestamoDto.getMonto() <= 0) {
            throw new DatosIncorrectosException("El monto del préstamo debe ser mayor a cero");
        }
        if (!"P".equals(prestamoDto.getMoneda()) && !"D".equals(prestamoDto.getMoneda())) {
            throw new DatosIncorrectosException("El tipo de moneda no es correcto");
        }
        if (prestamoDto.getDniTitular() <= 0) {
            throw new DatosIncorrectosException("El DNI del titular no es válido");
        }
    }
}