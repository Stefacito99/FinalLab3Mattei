package ar.edu.utn.frbb.tup.persistence.entity;

import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.model.enums.TipoMoneda;

import java.time.LocalDateTime;

public class PrestamoEntity extends BaseEntity {
    private long numeroPrestamo;
    private LocalDateTime fechaCreacion;
    private double monto;
    private TipoMoneda moneda;
    private long dniTitular;
    private int plazoMeses;
    private int cuotasPagadas;
    private int montoTotal;
    private int cuotaMensual;

    public PrestamoEntity(Prestamo prestamo) {
        super(prestamo.getNumeroPrestamo());
        this.fechaCreacion = prestamo.getFechaCreacion();
        this.monto = prestamo.getMonto();
        this.moneda = prestamo.getMoneda();
        this.dniTitular = prestamo.getDniTitular();
        this.plazoMeses = prestamo.getPlazoMeses();
        this.cuotasPagadas = prestamo.getCuotasPagadas();
        this.montoTotal = prestamo.getMontoTotal();
        this.cuotaMensual = prestamo.getCuotaMensual();
    }

    public Prestamo toPrestamo() {
        Prestamo prestamo = new Prestamo();
        prestamo.setNumeroPrestamo(this.getId());
        prestamo.setFechaCreacion(this.fechaCreacion);
        prestamo.setMonto(this.monto);
        prestamo.setMoneda(this.moneda);
        prestamo.setDniTitular(this.dniTitular);
        prestamo.setPlazoMeses(this.plazoMeses);
        prestamo.setCuotasPagadas(this.cuotasPagadas);
        prestamo.setMontoTotal(this.montoTotal);
        prestamo.setCuotaMensual(this.cuotaMensual);
        return prestamo;
    }

    // Getters y setters
    public long getNumeroPrestamo() {
        return numeroPrestamo;
    }

    public void setNumeroPrestamo(long numeroPrestamo) {
        this.numeroPrestamo = numeroPrestamo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public TipoMoneda getMoneda() {
        return moneda;
    }

    public void setMoneda(TipoMoneda moneda) {
        this.moneda = moneda;
    }

    public long getDniTitular() {
        return dniTitular;
    }

    public void setDniTitular(long dniTitular) {
        this.dniTitular = dniTitular;
    }

    public int getPlazoMeses() {
        return plazoMeses;
    }

    public void setPlazoMeses(int plazoMeses) {
        this.plazoMeses = plazoMeses;
    }

    public int getCuotasPagadas() {
        return cuotasPagadas;
    }

    public void setCuotasPagadas(int cuotasPagadas) {
        this.cuotasPagadas = cuotasPagadas;
    }

    public int getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(int montoTotal) {
        this.montoTotal = montoTotal;
    }

    public int getCuotaMensual() {
        return cuotaMensual;
    }

    public void setCuotaMensual(int cuotaMensual) {
        this.cuotaMensual = cuotaMensual;
    }
}