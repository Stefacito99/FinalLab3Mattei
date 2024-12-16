
package ar.edu.utn.frbb.tup.model.exception;

public class CantidadNegativaException extends Exception {
    public CantidadNegativaException() {
        super("Cantidad negativa no permitida");
    }
    public CantidadNegativaException(String message) {
        super(message);
    }
}
