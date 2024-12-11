package ar.edu.utn.frbb.tup.controller.dto;

public class PrestamoDto {

    private Long id;
    private Double monto;
    private Integer cantidadCuotas;
    private Double tasaInteres;
    private ClienteDto cliente;

    public PrestamoDto() {
    }

    public PrestamoDto(Double monto, Integer cantidadCuotas, Double tasaInteres, ClienteDto cliente) {
        this.monto = monto;
        this.cantidadCuotas = cantidadCuotas;
        this.tasaInteres = tasaInteres;
        this.cliente = cliente;
    }

    public PrestamoDto(Long id, Double monto, Integer cantidadCuotas, Double tasaInteres, ClienteDto cliente) {
        this.id = id;
        this.monto = monto;
        this.cantidadCuotas = cantidadCuotas;
        this.tasaInteres = tasaInteres;
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Integer getCantidadCuotas() {
        return cantidadCuotas;
    }

    public void setCantidadCuotas(Integer cantidadCuotas) {
        this.cantidadCuotas = cantidadCuotas;
    }

    public Double getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(Double tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public ClienteDto getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDto cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "PrestamoDto{" +
                "id=" + id +
                ", monto=" + monto +
                ", cantidadCuotas=" + cantidadCuotas +
                ", tasaInteres=" + tasaInteres +
                ", cliente=" + cliente +
                '}';
    }
    
}
