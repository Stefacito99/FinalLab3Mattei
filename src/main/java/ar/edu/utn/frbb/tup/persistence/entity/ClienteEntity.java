package ar.edu.utn.frbb.tup.persistence.entity;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.enums.TipoPersona;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClienteEntity extends BaseEntity {

    private final String tipoPersona;
    private final String nombre;
    private final String apellido;
    private final LocalDate fechaAlta;
    private final LocalDate fechaNacimiento;
    private String banco;
    private List<Long> cuentas;

    public ClienteEntity(Cliente cliente) {
        super(cliente.getDni());
        this.tipoPersona = cliente.getTipoPersona() != null ? cliente.getTipoPersona().getDescripcion() : null;
        this.nombre = cliente.getNombre();
        this.apellido = cliente.getApellido();
        this.fechaAlta = cliente.getFechaAlta();
        this.fechaNacimiento = cliente.getFechaNacimiento();
        this.banco = cliente.getBanco();
        this.cuentas = new ArrayList<>();
        if (cliente.getCuentas() != null && !cliente.getCuentas().isEmpty()) {
            for (Cuenta c: cliente.getCuentas()) {
                cuentas.add(c.getNumeroCuenta());
            }
        }
    }

    public Cliente toCliente(Set<Cuenta> cuentasSet) {
        Cliente cliente = new Cliente();
        cliente.setDni(this.getId());
        cliente.setNombre(this.nombre);
        cliente.setApellido(this.apellido);
        cliente.setTipoPersona(TipoPersona.fromString(this.tipoPersona));
        cliente.setFechaAlta(this.fechaAlta);
        cliente.setFechaNacimiento(this.fechaNacimiento);
        cliente.setBanco(this.banco);
        cliente.setCuentas(cuentasSet);
        return cliente;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public List<Long> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Long> cuentas) {
        this.cuentas = cuentas;
    }
}