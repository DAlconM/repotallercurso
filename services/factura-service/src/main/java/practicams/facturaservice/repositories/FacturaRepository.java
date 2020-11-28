package practicams.facturaservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import practicams.proyectoentidadesmongo.domain.Factura;

import java.util.List;

public interface FacturaRepository extends MongoRepository<Factura, String> {

    List<Factura> findAllByCliente(Integer cliente);

    List<Factura> findAllByEstado(String estado);

    List<Factura> findAllByImportetotal(double importetotal);
}
