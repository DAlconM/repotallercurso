package practicams.proyectoentidadesdto.domain;

import java.util.List;

// Pojo Factura Data Transfer Object
public class FacturaDTO {

    private String id;

    private String estado;

    private double importetotal;

    private Integer formapago;

    private List<VisitaDTO> lineasfactura;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getImportetotal() {
        return importetotal;
    }

    public void setImportetotal(double importetotal) {
        this.importetotal = importetotal;
    }

    public Integer getFormapago() {
        return formapago;
    }

    public void setFormapago(Integer formapago) {
        this.formapago = formapago;
    }

    public List<VisitaDTO> getLineasfactura() {
        return lineasfactura;
    }

    public void setLineasfactura(List<VisitaDTO> lineasfactura) {
        this.lineasfactura = lineasfactura;
    }
}
