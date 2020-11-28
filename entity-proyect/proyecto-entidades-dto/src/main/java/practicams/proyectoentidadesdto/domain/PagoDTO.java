package practicams.proyectoentidadesdto.domain;

// Pojo Pago Data Transfer Object
public class PagoDTO {

    private String id;

    private String factura;

    private double importepago;

    private String estado;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public double getImporte() {
        return importepago;
    }

    public void setImporte(double importepago) {
        this.importepago = importepago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
