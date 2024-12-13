package ar.edu.utn.frbb.tup.model.enums;

import java.util.List;

import ar.edu.utn.frbb.tup.model.PlanPago;

public enum PrestamoResultado {
    APROBADO("A"),
    RECHAZADO("R"),
    PENDIENTE("P");

    


    private final String code;

    PrestamoResultado(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}