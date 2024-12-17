package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.enums.TipoCuenta;
import ar.edu.utn.frbb.tup.model.enums.TipoMoneda;

import java.time.LocalDateTime;

public class Cuenta {
    private static long contadorCuentas = 0;

    private long numeroCuenta;
    private LocalDateTime fechaCreacion;
    private double balance;
    private TipoCuenta tipoCuenta;
    private TipoMoneda moneda;
    private long dniTitular;

    public Cuenta() {
        this.numeroCuenta = ++contadorCuentas;
        this.fechaCreacion = LocalDateTime.now();
    }

    public Cuenta(CuentaDto cuentaDto) {
        this.numeroCuenta = ++contadorCuentas;
        this.fechaCreacion = LocalDateTime.now();
        this.balance = cuentaDto.getBalance();
        this.tipoCuenta = TipoCuenta.fromString(cuentaDto.getTipoCuenta());
        this.moneda = TipoMoneda.fromString(cuentaDto.getTipoMoneda());
        this.dniTitular = cuentaDto.getDniTitular();
    }

    // Getters y setters
    public long getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
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

    public static long getContadorCuentas() {
        return contadorCuentas;
    }

    public static void setContadorCuentas(long contadorCuentas) {
        Cuenta.contadorCuentas = contadorCuentas;
    }

    
}