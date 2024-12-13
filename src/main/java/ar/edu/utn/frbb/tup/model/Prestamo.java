package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.model.enums.TipoMoneda;

public class Prestamo {
    private long id;
    private long numeroCliente;
    private int plazoMeses;
    private double monto;
    private long montoConIntereses;
    private long saldoRestante; // Saldo deudor a pagar
    private long valorCuota;
    private int cuotasPagas;
    private TipoMoneda moneda;

    public Prestamo() {
    }

    public Prestamo(long numeroCliente, int plazoMeses, double monto, TipoMoneda moneda) {
        this.numeroCliente = numeroCliente;
        this.plazoMeses = plazoMeses;
        this.monto = monto;
        this.montoConIntereses = (long) (monto * 1.1); // interés fijo del 10% con conversión a long para trabajar con valores enteros
        this.valorCuota = (long) (this.montoConIntereses / this.plazoMeses); // Valor de la cuota fija
        this.cuotasPagas = 0;
        this.saldoRestante = this.montoConIntereses;
        this.moneda = moneda;
    }

    public Prestamo(PrestamoDto prestamoDto) {
        this(prestamoDto.getNumeroCliente(), prestamoDto.getPlazoMeses(), prestamoDto.getMonto(), prestamoDto.getMoneda());
        this.montoConIntereses = (long) (prestamoDto.getMonto() * 1.05);
        this.saldoRestante = this.montoConIntereses;
        this.valorCuota = (long) (this.montoConIntereses / this.plazoMeses);
        this.cuotasPagas = 0;
    }

    public void pagarCuota() {
        this.saldoRestante = this.saldoRestante - this.valorCuota;
        this.cuotasPagas++;
    }

    public boolean estaPagado() {
        return cuotasPagas == plazoMeses || saldoRestante == 0;
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