package ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.model.enums.TipoCuenta;
import ar.edu.utn.frbb.tup.model.enums.TipoMoneda;
import ar.edu.utn.frbb.tup.model.enums.TipoPersona;

public class Cliente {
    private long dni;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private TipoPersona tipoPersona;
    private String banco;
    private LocalDate fechaAlta;
    private Set<Cuenta> cuentas = new HashSet<>();

    public Cliente() {
        this.fechaAlta = LocalDate.now();
    }

    public Cliente(ClienteDto clienteDto) {
        this.dni = clienteDto.getDni();
        this.nombre = clienteDto.getNombre();
        this.apellido = clienteDto.getApellido();
        this.fechaNacimiento = LocalDate.parse(clienteDto.getFechaNacimiento());
        this.tipoPersona = TipoPersona.fromString(clienteDto.getTipoPersona());
        this.banco = clienteDto.getBanco();
        this.fechaAlta = LocalDate.now();
    }

    public long getDni() {
        return dni;
    }

    public void setDni(long dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public TipoPersona getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Set<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(Set<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public void addCuenta(Cuenta cuenta) {
        this.cuentas.add(cuenta);
    }

    public boolean tieneCuenta(TipoCuenta tipoCuenta, TipoMoneda moneda) {
        return cuentas.stream()
                .anyMatch(cuenta -> tipoCuenta.equals(cuenta.getTipoCuenta()) && moneda.equals(cuenta.getMoneda()));
    }

    public int getEdad() {
        return Period.between(this.fechaNacimiento, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "dni=" + dni +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", tipoPersona=" + tipoPersona +
                ", banco='" + banco + '\'' +
                ", fechaAlta=" + fechaAlta +
                ", cuentas=" + cuentas +
                '}';
    }
}