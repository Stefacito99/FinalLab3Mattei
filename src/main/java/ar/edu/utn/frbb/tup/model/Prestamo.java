package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.model.enums.TipoMoneda;

import java.time.LocalDateTime;

public class Prestamo {
    private static long contadorPrestamos = 0;

    private long numeroPrestamo;
    private LocalDateTime fechaCreacion;
    private double monto;
    private TipoMoneda moneda;
    private long dniTitular;
    private int plazoMeses;
    private int cuotasPagadas;
    private int montoTotal;
    private int cuotaMensual;
    private int montoFaltanteAPagar;

    public Prestamo() {
        this.numeroPrestamo = ++contadorPrestamos;
        this.fechaCreacion = LocalDateTime.now();
        this.cuotasPagadas = 0;
    }

    public Prestamo(PrestamoDto prestamoDto) {
        this.numeroPrestamo = ++contadorPrestamos;
        this.fechaCreacion = LocalDateTime.now();
        this.monto = prestamoDto.getMonto();
        this.moneda = TipoMoneda.fromString(prestamoDto.getMoneda());
        this.dniTitular = prestamoDto.getDniTitular();
        this.plazoMeses = prestamoDto.getPlazoMeses();
        this.cuotasPagadas = 0;
        calcularPlanDePagos();
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

    public static long getContadorPrestamos() {
        return contadorPrestamos;
    }

    public static void setContadorPrestamos(long contadorPrestamos) {
        Prestamo.contadorPrestamos = contadorPrestamos;
    }

    public void setMontoTotal(int montoTotal) {
        this.montoTotal = montoTotal;
    }

    public void setCuotaMensual(int cuotaMensual) {
        this.cuotaMensual = cuotaMensual;
    }

    public void setMontoFaltanteAPagar(int montoFaltanteAPagar) {
        this.montoFaltanteAPagar = montoFaltanteAPagar;
    }

    public int getCuotaMensual() {
        return cuotaMensual;
    }

    public int getMontoFaltanteAPagar() {
        return montoFaltanteAPagar;
    }

    private void calcularPlanDePagos() {
        double tasaInteresAnual = 0.05;
        double tasaInteresMensual = tasaInteresAnual / 12;
        this.montoTotal = (int) Math.round(this.monto * Math.pow(1 + tasaInteresMensual, this.plazoMeses));
        this.cuotaMensual = (int) Math.round((double) this.montoTotal / this.plazoMeses);
        this.montoFaltanteAPagar = this.montoTotal;
    }

    public void actualizarMontoFaltanteAPagar() {
        this.montoFaltanteAPagar = this.montoTotal - (this.cuotasPagadas * this.cuotaMensual);
    }
}