package ar.edu.utn.frbb.tup.controller.validator;

import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.model.exception.DatosIncorrectosException;
import ar.edu.utn.frbb.tup.model.exception.TipoMonedaNoSoportada;

@Component
public class PrestamoValidator {

    public void validar(PrestamoDto prestamoDto) throws TipoMonedaNoSoportada, DatosIncorrectosException{
    validarMoneda(prestamoDto);
    validateCampos(prestamoDto);
    }

    public void validarMoneda(PrestamoDto prestamoDto) throws TipoMonedaNoSoportada{
        if(!prestamoDto.getMoneda().equals("P") && !prestamoDto.getMoneda().equals("D")){
        throw new TipoMonedaNoSoportada("Tipo de moneda no soportada");
        }
    }

    public void validateCampos(PrestamoDto prestamoDto) throws DatosIncorrectosException {
        if(prestamoDto.getMonto() <= 0){
            throw new IllegalArgumentException("El monto debe ser mayor a 0");
        }
        if(prestamoDto.getPlazoMeses() <= 0){
            throw new IllegalArgumentException("El plazo en meses debe ser mayor a 0");
        }
        if(prestamoDto.getNumeroCliente() <= 0){
            throw new IllegalArgumentException("El numero de cliente debe ser mayor a 0");
        }
    }

}