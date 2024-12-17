package ar.edu.utn.frbb.tup.controller.validator;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.model.exception.DatosIncorrectosException;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ClienteValidator {

    public void validate(ClienteDto clienteDto) throws DatosIncorrectosException {
        if (!"F".equals(clienteDto.getTipoPersona()) && !"J".equals(clienteDto.getTipoPersona())) {
            throw new DatosIncorrectosException("El tipo de persona no es correcto");
        }
        try {
            LocalDate.parse(clienteDto.getFechaNacimiento());
        } catch (Exception e) {
            throw new DatosIncorrectosException("Error en el formato de fecha");
        }
        if (clienteDto.getNombre() == null || clienteDto.getNombre().isEmpty()) {
            throw new DatosIncorrectosException("El nombre no puede estar vacío");
        }
        if (clienteDto.getApellido() == null || clienteDto.getApellido().isEmpty()) {
            throw new DatosIncorrectosException("El apellido no puede estar vacío");
        }
        if (clienteDto.getDni() <= 0) {
            throw new DatosIncorrectosException("El DNI no es válido");
        }
        if (clienteDto.getBanco() == null || clienteDto.getBanco().isEmpty()) {
            throw new DatosIncorrectosException("El banco no puede estar vacío");
        }
    }
}