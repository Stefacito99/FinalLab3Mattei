package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.controller.validator.CuentaValidator;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.DatosIncorrectosException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cuenta")
public class CuentaController {
    @Autowired
    private CuentaService cuentaService;
    @Autowired
    private CuentaValidator cuentaValidator;

    @PostMapping
    public Cuenta crearCuenta(@RequestBody CuentaDto cuentaDto) throws CuentaAlreadyExistsException, TipoCuentaAlreadyExistsException, DatosIncorrectosException {
        cuentaValidator.validate(cuentaDto);

        Cuenta cuenta = cuentaService.darDeAltaCuenta(cuentaDto);        
        
        return cuenta;
    }
}