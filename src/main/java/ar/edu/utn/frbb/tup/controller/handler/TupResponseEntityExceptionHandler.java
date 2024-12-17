package ar.edu.utn.frbb.tup.controller.handler;

import ar.edu.utn.frbb.tup.model.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class TupResponseEntityExceptionHandler {

    @ExceptionHandler(CuentaAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleCuentaAlreadyExistsException(CuentaAlreadyExistsException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("errorCode", 409);
        response.put("errorMessage", ex.getMessage());
        return buildResponseEntity(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DatosIncorrectosException.class)
    public ResponseEntity<Map<String, Object>> handleDatosIncorrectosException(DatosIncorrectosException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("errorCode", 400);
        response.put("errorMessage", ex.getMessage());
        return buildResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TipoCuentaAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleTipoCuentaAlreadyExistsException(TipoCuentaAlreadyExistsException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("errorCode", 409);
        response.put("errorMessage", ex.getMessage());
        return buildResponseEntity(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleClienteNotFoundException(ClienteNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("errorCode", 404);
        response.put("errorMessage", ex.getMessage());
        return buildResponseEntity(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CuentaNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCuentaNotFoundException(CuentaNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("errorCode", 404);
        response.put("errorMessage", ex.getMessage());
        return buildResponseEntity(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TipoMonedaNoSoportada.class)
    public ResponseEntity<Map<String, Object>> handleTipoMonedaNoSoportada(TipoMonedaNoSoportada ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("errorCode", 400);
        response.put("errorMessage", ex.getMessage());
        return buildResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClienteAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleClienteAlreadyExistsException(ClienteAlreadyExistsException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("errorCode", 409);
        response.put("errorMessage", ex.getMessage());
        return buildResponseEntity(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PrestamoYaExisteException.class)
    public ResponseEntity<Map<String, Object>> handlePrestamoYaExisteException(PrestamoYaExisteException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("errorCode", 409);
        response.put("errorMessage", ex.getMessage());
        return buildResponseEntity(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CreditoRechazadoException.class)
    public ResponseEntity<Map<String, Object>> handleCreditoRechazadoException(CreditoRechazadoException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("errorCode", 400);
        response.put("errorMessage", ex.getMessage());
        return buildResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CantidadNegativaException.class)
    public ResponseEntity<Map<String, Object>> handleCantidadNegativaException(CantidadNegativaException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("errorCode", 400);
        response.put("errorMessage", ex.getMessage());
        return buildResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoAlcanzaException.class)
    public ResponseEntity<Map<String, Object>> handleNoAlcanzaException(NoAlcanzaException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("errorCode", 400);
        response.put("errorMessage", ex.getMessage());
        return buildResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PrestamoNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePrestamoNotFoundException(PrestamoNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("errorCode", 404);
        response.put("errorMessage", ex.getMessage());
        return buildResponseEntity(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PrestamoPagadoException.class)
    public ResponseEntity<Map<String, Object>> handlePrestamoPagadoException(PrestamoPagadoException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("errorCode", 400);
        response.put("errorMessage", ex.getMessage());
        return buildResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("errorCode", 500);
        response.put("errorMessage", "Internal Server Error");
        return buildResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Map<String, Object>> buildResponseEntity(Map<String, Object> response, HttpStatus status) {
        return new ResponseEntity<>(response, status);
    }
}