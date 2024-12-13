package ar.edu.utn.frbb.tup.controller.dto;

import ar.edu.utn.frbb.tup.model.enums.TipoMoneda;

public class PrestamoDto {
    private double monto;
    private int plazoMeses;
    private long numeroCliente;
    private TipoMoneda moneda;
    
    public PrestamoDto(double monto, int plazoMeses, long numeroCliente, TipoMoneda moneda) {
        this.monto = monto;
        this.plazoMeses = plazoMeses;
        this.numeroCliente = numeroCliente;
        this.moneda = moneda;
    }
    public PrestamoDto() {
    }
    public double getMonto() {
        return monto;
    }
    public void setMonto(double monto) {
        this.monto = monto;
    }
    public int getPlazoMeses() {
        return plazoMeses;
    }
    public void setPlazoMeses(int plazoMeses) {
        this.plazoMeses = plazoMeses;
    }
    public long getNumeroCliente() {
        return numeroCliente;
    }
    public void setNumeroCliente(long numeroCliente) {
        this.numeroCliente = numeroCliente;
    }
    public TipoMoneda getMoneda() {
        return moneda;
    }
    public void setMoneda(TipoMoneda moneda) {
        this.moneda = moneda;
    }
}

