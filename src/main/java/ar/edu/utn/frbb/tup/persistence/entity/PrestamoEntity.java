package ar.edu.utn.frbb.tup.persistence.entity;

import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.model.enums.TipoMoneda;

public class PrestamoEntity {
    private long id;
    private long numeroCliente;
    private int plazoMeses;
    private double monto;
    private long montoConIntereses;
    private long saldoRestante;
    private long valorCuota;
    private int cuotasPagas;
    private TipoMoneda moneda;

    public PrestamoEntity() {
    }

    public PrestamoEntity(Prestamo prestamo) {
        this.id = prestamo.getId();
        this.numeroCliente = prestamo.getNumeroCliente();
        this.plazoMeses = prestamo.getPlazoMeses();
        this.monto = prestamo.getMonto();
        this.montoConIntereses = prestamo.getMontoConIntereses();
        this.saldoRestante = prestamo.getSaldoRestante();
        this.valorCuota = prestamo.getValorCuota();
        this.cuotasPagas = prestamo.getCuotasPagas();
        this.moneda = prestamo.getMoneda();
    }

    public Prestamo toPrestamo() {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(this.id);
        prestamo.setNumeroCliente(this.numeroCliente);
        prestamo.setPlazoMeses(this.plazoMeses);
        prestamo.setMonto(this.monto);
        prestamo.setMontoConIntereses(this.montoConIntereses);
        prestamo.setSaldoRestante(this.saldoRestante);
        prestamo.setValorCuota(this.valorCuota);
        prestamo.setCuotasPagas(this.cuotasPagas);
        prestamo.setMoneda(this.moneda);
        return prestamo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNumeroCliente() {
        return numeroCliente;
    }

    public void setNumeroCliente(long numeroCliente) {
        this.numeroCliente = numeroCliente;
    }

    public int getPlazoMeses() {
        return plazoMeses;
    }

    public void setPlazoMeses(int plazoMeses) {
        this.plazoMeses = plazoMeses;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public long getMontoConIntereses() {
        return montoConIntereses;
    }

    public void setMontoConIntereses(long montoConIntereses) {
        this.montoConIntereses = montoConIntereses;
    }

    public long getSaldoRestante() {
        return saldoRestante;
    }

    public void setSaldoRestante(long saldoRestante) {
        this.saldoRestante = saldoRestante;
    }

    public long getValorCuota() {
        return valorCuota;
    }

    public void setValorCuota(long valorCuota) {
        this.valorCuota = valorCuota;
    }

    public int getCuotasPagas() {
        return cuotasPagas;
    }

    public void setCuotasPagas(int cuotasPagas) {
        this.cuotasPagas = cuotasPagas;
    }

    public TipoMoneda getMoneda() {
        return moneda;
    }

    public void setMoneda(TipoMoneda moneda) {
        this.moneda = moneda;
    }

    
}