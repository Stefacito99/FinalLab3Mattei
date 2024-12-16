package ar.edu.utn.frbb.tup.controller.validator;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClienteValidatorTest {

    private ClienteValidator clienteValidator;

    @BeforeEach
    public void setUp() {
        clienteValidator = new ClienteValidator();
    }

    @Test
    public void testValidateTipoPersonaInvalido() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setTipoPersona("X");
        clienteDto.setFechaNacimiento("2000-01-01");
        clienteDto.setNombre("Pepe");
        clienteDto.setApellido("Rino");
        clienteDto.setDni(12345678);
        clienteDto.setBanco("Banco");

        assertThrows(IllegalArgumentException.class, () -> clienteValidator.validate(clienteDto), "El tipo de persona no es correcto");
    }

    @Test
    public void testValidateFechaNacimientoInvalida() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setTipoPersona("F");
        clienteDto.setFechaNacimiento("fecha-invalida");
        clienteDto.setNombre("Pepe");
        clienteDto.setApellido("Rino");
        clienteDto.setDni(12345678);
        clienteDto.setBanco("Banco");

        assertThrows(IllegalArgumentException.class, () -> clienteValidator.validate(clienteDto), "Error en el formato de fecha");
    }

    @Test
    public void testValidateNombreVacio() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setTipoPersona("F");
        clienteDto.setFechaNacimiento("2000-01-01");
        clienteDto.setNombre("");
        clienteDto.setApellido("Rino");
        clienteDto.setDni(12345678);
        clienteDto.setBanco("Banco");

        assertThrows(IllegalArgumentException.class, () -> clienteValidator.validate(clienteDto), "El nombre no puede estar vacío");
    }

    @Test
    public void testValidateApellidoVacio() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setTipoPersona("F");
        clienteDto.setFechaNacimiento("2000-01-01");
        clienteDto.setNombre("Pepe");
        clienteDto.setApellido("");
        clienteDto.setDni(12345678);
        clienteDto.setBanco("Banco");

        assertThrows(IllegalArgumentException.class, () -> clienteValidator.validate(clienteDto), "El apellido no puede estar vacío");
    }

    @Test
    public void testValidateDniInvalido() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setTipoPersona("F");
        clienteDto.setFechaNacimiento("2000-01-01");
        clienteDto.setNombre("Pepe");
        clienteDto.setApellido("Rino");
        clienteDto.setDni(-1);
        clienteDto.setBanco("Banco");

        assertThrows(IllegalArgumentException.class, () -> clienteValidator.validate(clienteDto), "El DNI no es válido");
    }

    @Test
    public void testValidateBancoVacio() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setTipoPersona("F");
        clienteDto.setFechaNacimiento("2000-01-01");
        clienteDto.setNombre("Pepe");
        clienteDto.setApellido("Rino");
        clienteDto.setDni(12345678);
        clienteDto.setBanco("");

        assertThrows(IllegalArgumentException.class, () -> clienteValidator.validate(clienteDto), "El banco no puede estar vacío");
    }

    @Test
    public void testValidateClienteValido() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setTipoPersona("F");
        clienteDto.setFechaNacimiento("2000-01-01");
        clienteDto.setNombre("Pepe");
        clienteDto.setApellido("Rino");
        clienteDto.setDni(12345678);
        clienteDto.setBanco("Banco");

        clienteValidator.validate(clienteDto);
    }
}