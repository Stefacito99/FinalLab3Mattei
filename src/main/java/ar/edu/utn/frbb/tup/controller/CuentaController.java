package ar.edu.utn.frbb.tup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.controller.validator.CuentaValidator;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.ClienteNotFoundException;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.DatosIncorrectosException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.service.CuentaService;

@RestController
@RequestMapping("/cuenta")
public class CuentaController {
    @Autowired
    private CuentaService cuentaService;
    @Autowired
    private CuentaValidator cuentaValidator;

    @PostMapping
    public Cuenta crearCuenta(@RequestBody CuentaDto cuentaDto) throws CuentaAlreadyExistsException, TipoCuentaAlreadyExistsException, DatosIncorrectosException, ClienteNotFoundException {
        cuentaValidator.validate(cuentaDto);

        Cuenta cuenta = cuentaService.darDeAltaCuenta(cuentaDto);        
        
        return cuenta;
    }

    @GetMapping("/{id}")
    public Cuenta buscarCuentaPorId(@PathVariable long id) {
        Cuenta cuenta = cuentaService.find(id);
        if (cuenta == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada");
        }
        return cuenta;
    }
}