package practicams.clienteservice.repositories;

import org.springframework.data.repository.Repository;
import practicams.proyectoentidadessql.domain.Cliente;


import java.util.List;

public interface ClienteRepository extends Repository<Cliente, String> {

    List<Cliente> findAll();
}
