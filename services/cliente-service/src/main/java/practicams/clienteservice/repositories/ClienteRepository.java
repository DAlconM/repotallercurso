package practicams.clienteservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import practicams.proyectoentidadessql.domain.Cliente;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    List<Cliente> findAll();
}
