package ar.edu.utn.frbb.tup.persistence.entity;

import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.enums.TipoCuenta;
import ar.edu.utn.frbb.tup.model.enums.TipoMoneda;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class CuentaEntity extends BaseEntity {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);

    private LocalDateTime fechaCreacion;
    private double balance;
    private String tipoCuenta;
    private Long dniTitular;
    private long numeroCuenta;
    private TipoMoneda tipoMoneda;

    public CuentaEntity() {
        super(ID_GENERATOR.getAndIncrement());
    }

    public CuentaEntity(Cuenta cuenta) {
        super(cuenta.getNumeroCuenta() > 0 ? cuenta.getNumeroCuenta() : ID_GENERATOR.getAndIncrement());
        this.numeroCuenta = cuenta.getNumeroCuenta();
        this.balance = cuenta.getBalance();
        this.tipoCuenta = cuenta.getTipoCuenta().toString();
        this.dniTitular = cuenta.getDniTitular();
        this.fechaCreacion = cuenta.getFechaCreacion();
        this.tipoMoneda = cuenta.getMoneda();
    }

    public Cuenta toCuenta() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(this.numeroCuenta);
        cuenta.setBalance(this.balance);
        cuenta.setTipoCuenta(TipoCuenta.valueOf(this.tipoCuenta));
        cuenta.setFechaCreacion(this.fechaCreacion);
        cuenta.setDniTitular(this.dniTitular);
        cuenta.setMoneda(this.tipoMoneda);
        return cuenta;
    }

    // Getters y setters
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

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Long getDniTitular() {
        return dniTitular;
    }

    public void setDniTitular(Long dniTitular) {
        this.dniTitular = dniTitular;
    }

    public long getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public TipoMoneda getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(TipoMoneda tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }
}