package ar.edu.utn.frbb.tup.model;

import java.util.ArrayList;
import java.util.List;

public class Prestamo {

    private Long id;
    private Cliente cliente;
    private Double monto;
    private String moneda;
    private Integer plazoMeses;
    private Double tasaInteres;
    private Integer pagosRealizados;
    private Double saldoRestante;
    private List<PlanPago> planPagos = new ArrayList<>();

    public Prestamo() {
    }

    public Prestamo(Cliente cliente, Double monto, String moneda, Integer plazoMeses, Double tasaInteres) {
        this.cliente = cliente;
        this.monto = monto;
        this.moneda = moneda;
        this.plazoMeses = plazoMeses;
        this.tasaInteres = tasaInteres;
        this.pagosRealizados = 0;
        this.saldoRestante = monto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Integer getPlazoMeses() {
        return plazoMeses;
    }

    public void setPlazoMeses(Integer plazoMeses) {
        this.plazoMeses = plazoMeses;
    }

    public Double getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(Double tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public Integer getPagosRealizados() {
        return pagosRealizados;
    }

    public void setPagosRealizados(Integer pagosRealizados) {
        this.pagosRealizados = pagosRealizados;
    }

    public Double getSaldoRestante() {
        return saldoRestante;
    }

    public void setSaldoRestante(Double saldoRestante) {
        this.saldoRestante = saldoRestante;
    }

    public List<PlanPago> getPlanPagos() {
        return planPagos;
    }

    public void addPlanPago(PlanPago planPago) {
        this.planPagos.add(planPago);
        planPago.setPrestamo(this);
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", monto=" + monto +
                ", moneda='" + moneda + '\'' +
                ", plazoMeses=" + plazoMeses +
                ", tasaInteres=" + tasaInteres +
                ", pagosRealizados=" + pagosRealizados +
                ", saldoRestante=" + saldoRestante +
                '}';
    }
}