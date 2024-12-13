package ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Random;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.enums.TipoCuenta;
import ar.edu.utn.frbb.tup.model.enums.TipoMoneda;
import ar.edu.utn.frbb.tup.model.exception.CantidadNegativaException;
import ar.edu.utn.frbb.tup.model.exception.NoAlcanzaException;

public class Cuenta {
    private long numeroCuenta;
    private static long contadorCuentas = 1;
    private LocalDateTime fechaCreacion;
    private double balance;
    private TipoCuenta tipoCuenta;
    private TipoMoneda moneda;
    private long dniTitular;

    public Cuenta() {
        this.numeroCuenta = new Random().nextLong();
        this.balance = 0;
        this.fechaCreacion = LocalDateTime.now();
    }

    public Cuenta(CuentaDto cuentaDto) {
        this.tipoCuenta = TipoCuenta.fromString(cuentaDto.getTipoCuenta());
        this.moneda = TipoMoneda.fromString(cuentaDto.getTipoMoneda());
        this.fechaCreacion = LocalDateTime.now();
        this.balance = cuentaDto.getBalance();
        this.numeroCuenta = contadorCuentas++;
        this.moneda = TipoMoneda.fromString(cuentaDto.getTipoMoneda());
        this.dniTitular = cuentaDto.getDniTitular();
    }

    

    public long getDniTitular() {
        return dniTitular;
    }

    public void setDniTitular(long dniTitular) {
        this.dniTitular = dniTitular;
    }

    public long getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public Cuenta setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public double getBalance() {
        return balance;
    }

    public Cuenta setBalance(double balance) {
        this.balance = balance;
        return this;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public Cuenta setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
        return this;
    }

    public TipoMoneda getMoneda() {
        return moneda;
    }

    public Cuenta setMoneda(TipoMoneda moneda) {
        this.moneda = moneda;
        return this;
    }

    public void debitarDeCuenta(int cantidadADebitar) throws NoAlcanzaException, CantidadNegativaException {
        if (cantidadADebitar < 0) {
            throw new CantidadNegativaException();
        }

        if (balance < cantidadADebitar) {
            throw new NoAlcanzaException();
        }
        this.balance = this.balance - cantidadADebitar;
    }

    public void forzaDebitoDeCuenta(int cantidadADebitar) {
        this.balance = this.balance - cantidadADebitar;
    }

    public static long getContadorCuentas() {
        return contadorCuentas;
    }

    public static void setContadorCuentas(long contadorCuentas) {
        Cuenta.contadorCuentas = contadorCuentas;
    }
}