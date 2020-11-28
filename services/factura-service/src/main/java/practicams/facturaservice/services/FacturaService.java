package practicams.facturaservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practicams.facturaservice.repositories.FacturaRepository;
import practicams.proyectoentidadesmongo.domain.Factura;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {

    @Autowired
    FacturaRepository facturaRepository;

    // Devolver todas las facturas de la BD
    public List<Factura> getAllFacturas(){
        return facturaRepository.findAll();
    }

    // Modificar una factura por JSON en la BD
    public Factura modifyFactura(Factura factura) {
        return facturaRepository.save(factura);
    }

    // Insertar una factura vacía por JSON
    public Factura insertEmptyFactura(Factura factura) {
        return facturaRepository.insert(factura);
    }

    // Obtener una factura por el id
    public Factura getFacturaById(String id) {
        Optional<Factura> factura = facturaRepository.findById(id);
        if(factura.isPresent()){
            return factura.get();
        }
        else{
            Factura f = new Factura();
            f.setEstado("No existe factura con ese id");
            return f;
        }
    }

    // Obtener facturas por el id del cliente
    public List<Factura> getFacturasByClienteId(Integer clienteId) {
        return facturaRepository.findAllByCliente(clienteId);
    }

    // Obtener facturas por el estado
    public List<Factura> getFacturasByEstado(String estado) {
        return facturaRepository.findAllByEstado(estado);
    }

    public List<Factura> getFacturasByImportetotal(double importe) {
        return facturaRepository.findAllByImportetotal(importe);
    }
}
