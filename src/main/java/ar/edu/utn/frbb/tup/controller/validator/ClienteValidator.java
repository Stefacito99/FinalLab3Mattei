package ar.edu.utn.frbb.tup.controller.validator;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ClienteValidator {

    public void validate(ClienteDto clienteDto) {
        if (!"F".equals(clienteDto.getTipoPersona()) && !"J".equals(clienteDto.getTipoPersona())) {
            throw new IllegalArgumentException("El tipo de persona no es correcto");
        }
        try {
            LocalDate.parse(clienteDto.getFechaNacimiento());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error en el formato de fecha");
        }
        if (clienteDto.getNombre() == null || clienteDto.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (clienteDto.getApellido() == null || clienteDto.getApellido().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío");
        }
        if (clienteDto.getDni() <= 0) {
            throw new IllegalArgumentException("El DNI no es válido");
        }
        if (clienteDto.getBanco() == null || clienteDto.getBanco().isEmpty()) {
            throw new IllegalArgumentException("El banco no puede estar vacío");
        }
    }
}