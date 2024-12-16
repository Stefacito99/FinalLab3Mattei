package ar.edu.utn.frbb.tup.controller.handler;

import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.ClienteNotFoundException;
import ar.edu.utn.frbb.tup.model.exception.DatosIncorrectosException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class TupResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ClienteAlreadyExistsException.class})
    protected ResponseEntity<Object> handleClienteAlreadyExists(Exception ex, WebRequest request) {
        CustomApiError error = new CustomApiError(HttpStatus.CONFLICT, ex.getMessage());
        return new ResponseEntity<>(error, new HttpHeaders(), error.getErrorCode());
    }

    @ExceptionHandler({ClienteNotFoundException.class})
    protected ResponseEntity<Object> handleClienteNotFound(Exception ex, WebRequest request) {
        CustomApiError error = new CustomApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(error, new HttpHeaders(), error.getErrorCode());
    }

    @ExceptionHandler({DatosIncorrectosException.class})
    protected ResponseEntity<Object> handleDatosIncorrectos(Exception ex, WebRequest request) {
        CustomApiError error = new CustomApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(error, new HttpHeaders(), error.getErrorCode());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<Object> handleIllegalArgumentException(Exception ex, WebRequest request) {
        CustomApiError error = new CustomApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(error, new HttpHeaders(), error.getErrorCode());
    }

    private ResponseEntity<Object> buildResponseEntity(CustomApiError error) {
        return new ResponseEntity<>(error, new HttpHeaders(), error.getErrorCode());
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (body == null) {
            body = new CustomApiError(HttpStatus.valueOf(status.value()), ex.getMessage());
        } else if (body instanceof String) {
            body = new CustomApiError(HttpStatus.valueOf(status.value()), (String) body);
        }
        return new ResponseEntity(body, headers, status);
    }
}