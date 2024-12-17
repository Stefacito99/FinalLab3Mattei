package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.controller.validator.CuentaValidator;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.CuentaNotFoundException;
import ar.edu.utn.frbb.tup.model.exception.DatosIncorrectosException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.ClienteNotFoundException;
import ar.edu.utn.frbb.tup.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cuenta")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private CuentaValidator cuentaValidator;

    @PostMapping
    public ResponseEntity<?> crearCuenta(@RequestBody CuentaDto cuentaDto) throws CuentaAlreadyExistsException, DatosIncorrectosException, ClienteNotFoundException, TipoCuentaAlreadyExistsException {
        cuentaValidator.validate(cuentaDto);
        Cuenta cuenta = cuentaService.darDeAltaCuenta(cuentaDto);
        return new ResponseEntity<>(cuenta, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCuenta(@PathVariable long id) throws CuentaNotFoundException {
        Cuenta cuenta = cuentaService.find(id);
        return new ResponseEntity<>(cuenta, HttpStatus.OK);
    }
}