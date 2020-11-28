package practicams.pagoservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import practicams.proyectoentidadesmongo.domain.Pago;

import java.util.List;

public interface PagoRepository extends MongoRepository<Pago, String> {

    List<Pago> findAllByFactura(String factura);

    List<Pago> findAllByEstado(String estado);

}
