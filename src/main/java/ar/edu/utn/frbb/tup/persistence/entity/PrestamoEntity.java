package ar.edu.utn.frbb.tup.persistence.entity;

import ar.edu.utn.frbb.tup.model.Prestamo;
import ar.edu.utn.frbb.tup.model.enums.TipoMoneda;

import java.util.concurrent.atomic.AtomicLong;

public class PrestamoEntity extends BaseEntity {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);

    private long numeroCliente;
    private int plazoMeses;
    private double monto;
    private double montoConIntereses;
    private double saldoRestante;
    private double valorCuota;
    private int cuotasPagas;
    private TipoMoneda moneda;

    public PrestamoEntity() {
        super(ID_GENERATOR.getAndIncrement());
    }

    public PrestamoEntity(Prestamo prestamo) {
        super(prestamo.getId() > 0 ? prestamo.getId() : ID_GENERATOR.getAndIncrement());
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
        prestamo.setId(this.getId());
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

    // Getters y setters
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