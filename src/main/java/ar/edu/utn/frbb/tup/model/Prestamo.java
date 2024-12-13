package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.model.enums.TipoMoneda;

import java.util.concurrent.atomic.AtomicLong;

public class Prestamo {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);

    private long id;
    private long numeroCliente;
    private int plazoMeses;
    private double monto;
    private double montoConIntereses;
    private double saldoRestante;
    private double valorCuota;
    private int cuotasPagas;
    private TipoMoneda moneda;

    public Prestamo() {
        this.id = ID_GENERATOR.getAndIncrement();
    }

    public Prestamo(PrestamoDto prestamoDto) {
        this();
        this.numeroCliente = prestamoDto.getNumeroCliente();
        this.plazoMeses = prestamoDto.getPlazoMeses();
        this.monto = prestamoDto.getMonto();
        this.moneda = TipoMoneda.fromString(prestamoDto.getMoneda());
        this.montoConIntereses = calcularMontoConIntereses();
        this.saldoRestante = this.montoConIntereses;
        this.valorCuota = calcularValorCuota();
        this.cuotasPagas = 0;
    }

    private double calcularMontoConIntereses() {
        // Implementar lógica para calcular el monto con intereses
        return this.monto * 1.1; // Ejemplo: 10% de interés
    }

    private double calcularValorCuota() {
        // Implementar lógica para calcular el valor de la cuota
        return this.montoConIntereses / this.plazoMeses;
    }

    // Getters y setters
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

    public double getMontoConIntereses() {
        return montoConIntereses;
    }

    public void setMontoConIntereses(double montoConIntereses) {
        this.montoConIntereses = montoConIntereses;
    }

    public double getSaldoRestante() {
        return saldoRestante;
    }

    public void setSaldoRestante(double saldoRestante) {
        this.saldoRestante = saldoRestante;
    }

    public double getValorCuota() {
        return valorCuota;
    }

    public void setValorCuota(double valorCuota) {
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