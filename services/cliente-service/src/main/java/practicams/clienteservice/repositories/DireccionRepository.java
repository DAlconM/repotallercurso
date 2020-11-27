package practicams.clienteservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import practicams.proyectoentidadessql.domain.Direccion;

import java.util.List;

public interface DireccionRepository extends JpaRepository<Direccion, Integer> {

    List<Direccion> findAll();
}
