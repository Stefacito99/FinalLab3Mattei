package ar.edu.utn.frbb.tup.model;

public class PlanPago {

    private Long id;
    private Prestamo prestamo;
    private Integer cuotaNro;
    private Double monto;

    public PlanPago() {
    }

    public PlanPago(Integer cuotaNro, Double monto) {
        this.cuotaNro = cuotaNro;
        this.monto = monto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public Integer getCuotaNro() {
        return cuotaNro;
    }

    public void setCuotaNro(Integer cuotaNro) {
        this.cuotaNro = cuotaNro;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    @Override
    public String toString() {
        return "PlanPago{" +
                "id=" + id +
                ", cuotaNro=" + cuotaNro +
                ", monto=" + monto +
                '}';
    }
}
