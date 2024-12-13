package ar.edu.utn.frbb.tup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.model.exception.ClienteNotFoundException;
import ar.edu.utn.frbb.tup.model.exception.CreditoRechazadoException;
import ar.edu.utn.frbb.tup.model.exception.DatosIncorrectosException;
import ar.edu.utn.frbb.tup.model.exception.PrestamoYaExisteException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoMonedaNoSoportada;
import ar.edu.utn.frbb.tup.service.PrestamoService;

@RestController
@RequestMapping("/prestamo")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @PostMapping
    public Prestamo crearPrestamo(@RequestBody PrestamoDto prestamoDto) throws TipoCuentaAlreadyExistsException, TipoMonedaNoSoportada, DatosIncorrectosException, PrestamoYaExisteException, ClienteNotFoundException, CreditoRechazadoException {
        return prestamoService.darDeAltaPrestamo(prestamoDto);
    }

    /*@GetMapping("/{id}")
    public List<PlanPago> buscarPrestamoPorId(@PathVariable long id) {
        Prestamo prestamo = prestamoService.buscarPrestamoPorId(id);
        if (prestamo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Prestamo no encontrado");
        }
        return prestamo.getPlanPagos();
    }*/
}