package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.model.exception.*;
import ar.edu.utn.frbb.tup.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestamo")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @PostMapping
    public ResponseEntity<?> crearPrestamo(@RequestBody PrestamoDto prestamoDto) throws TipoMonedaNoSoportada, DatosIncorrectosException, PrestamoYaExisteException, ClienteNotFoundException, CreditoRechazadoException, CuentaNotFoundException {
        Prestamo prestamo = prestamoService.darDeAltaPrestamo(prestamoDto);
        return new ResponseEntity<>(prestamo, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPrestamo(@PathVariable long id) throws PrestamoNotFoundException {
        Prestamo prestamo = prestamoService.find(id);
        return new ResponseEntity<>(prestamo, HttpStatus.OK);
    }

    @PostMapping("/pago/{id}")
    public ResponseEntity<?> pagarCuota(@PathVariable long id, @RequestParam double monto) throws PrestamoNotFoundException, CuentaNotFoundException, NoAlcanzaException, DatosIncorrectosException, ClienteNotFoundException, PrestamoPagadoException {
        Prestamo prestamo = prestamoService.pagarCuota(id, monto);
        return new ResponseEntity<>(prestamo, HttpStatus.OK);
    }

    @GetMapping("/cliente/{dniTitular}")
    public ResponseEntity<?> obtenerPrestamosPorCliente(@PathVariable long dniTitular) throws ClienteNotFoundException {
        List<Prestamo> prestamos = prestamoService.obtenerPrestamosPorCliente(dniTitular);
        return new ResponseEntity<>(prestamos, HttpStatus.OK);
    }
}