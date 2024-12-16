package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.service.PrestamoService;
import ar.edu.utn.frbb.tup.model.exception.ClienteNotFoundException;
import ar.edu.utn.frbb.tup.model.exception.DatosIncorrectosException;
import ar.edu.utn.frbb.tup.model.exception.CreditoRechazadoException;
import ar.edu.utn.frbb.tup.model.exception.PrestamoYaExisteException;
import ar.edu.utn.frbb.tup.model.exception.TipoMonedaNoSoportada;
import ar.edu.utn.frbb.tup.controller.validator.PrestamoValidator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/prestamo")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private PrestamoValidator prestamoValidator;

    @PostMapping
    public Prestamo crearPrestamo(@RequestBody PrestamoDto prestamoDto) throws TipoMonedaNoSoportada, DatosIncorrectosException, IllegalArgumentException, PrestamoYaExisteException, ClienteNotFoundException, CreditoRechazadoException {
        try {
            prestamoValidator.validar(prestamoDto);
            return prestamoService.darDeAltaPrestamo(prestamoDto);
        } catch (TipoMonedaNoSoportada e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (DatosIncorrectosException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (PrestamoYaExisteException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (ClienteNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (CreditoRechazadoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/{id}")
    public Prestamo buscarPrestamoPorId(@PathVariable long id) {
        return prestamoService.buscarPrestamoPorId(id);
    }

    @GetMapping("/cliente/{numeroCliente}")
    public List<Prestamo> consultarPrestamosPorCliente(@PathVariable long numeroCliente) throws ClienteNotFoundException {
        return prestamoService.consultarPrestamosPorCliente(numeroCliente);
    }

    @PostMapping("/pago/{id}")
    public Prestamo realizarPago(@PathVariable long id, @RequestParam double monto) throws DatosIncorrectosException {
        return prestamoService.realizarPago(id, monto);
    }
}