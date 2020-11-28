package practicams.facturaservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import practicams.proyectoentidadesmongo.domain.Factura;

public interface FacturaRepository extends MongoRepository<Factura, String> {
}
