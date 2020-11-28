package practicams.proyectoentidadesmongo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("Factura")
public class Factura {

    private @Id String id;

    private String estado;

    private Integer cliente;

    private double importetotal;

    private Integer formapago;

    private List<LineaFactura> lineasfactura;

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

    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
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

    public List<LineaFactura> getLineasfactura() {
        return lineasfactura;
    }

    public void setLineasfactura(List<LineaFactura> lineasfactura) {
        this.lineasfactura = lineasfactura;
    }
}
