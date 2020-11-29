package practicams.clienteservice.repositories;

import org.springframework.data.repository.Repository;
import practicams.proyectoentidadessql.domain.Cliente;

import java.util.List;

public interface ClienteRepository extends Repository<Cliente, Integer> {

    List<Cliente> findAll();

    Cliente findById(Integer id);

    List<Cliente> findAllByNombre(String nombre);

    List<Cliente> findAllByEstado(String estado);

}
