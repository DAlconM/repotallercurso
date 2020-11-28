package practicams.clienteservice.repositories;

import org.springframework.data.repository.Repository;
import practicams.proyectoentidadessql.domain.Direccion;

import java.util.List;

public interface DireccionRepository extends Repository<Direccion, Integer> {

    List<Direccion> findAll();

    Direccion findById(Integer id);

    List<Direccion> findAllByCliente(Integer id);

    Direccion findByCliente(Integer id);

    List<Direccion> findAllByProvincia(String provincia);
}
