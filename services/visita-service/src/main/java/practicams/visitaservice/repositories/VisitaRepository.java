package practicams.visitaservice.repositories;

import org.springframework.data.repository.Repository;
import practicams.proyectoentidadessql.domain.Visita;

import java.util.List;

public interface VisitaRepository extends Repository<Visita, Integer> {

    List<Visita> findAll();

    Visita findById(Integer id);

    List<Visita> findAllByCliente(Integer cliente);

    List<Visita> findAllByEstado(String estado);

    List<Visita> findAllByClienteAndEstado(Integer cliente, String estado);

    Visita save(Visita visita);

}

