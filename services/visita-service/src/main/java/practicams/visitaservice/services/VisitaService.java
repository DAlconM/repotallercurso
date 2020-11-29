package practicams.visitaservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practicams.proyectoentidadessql.domain.Visita;
import practicams.visitaservice.repositories.VisitaRepository;

import java.util.List;

@Service
public class VisitaService {

    @Autowired
    VisitaRepository visitaRepository;

    // Devolver todas las visitas
    public List<Visita> getAllVisitas() {
        return visitaRepository.findAll();
    }

    // Devolver una visita por id
    public Visita getVisiitaById(Integer id) {
        return visitaRepository.findById(id);
    }

    // Devolver las visitas por id cliente
    public List<Visita> getAllVisitasByCliente(Integer id) {
        return visitaRepository.findAllByCliente(id);
    }

    // Devolver las visitas por estado
    public List<Visita> getAllVisitasByEstado(String estado) {
        return visitaRepository.findAllByEstado(estado);
    }

    // Devolver las visitas por cliente y estado
    public List<Visita> getAllVisitasByClienteAndEstado(Integer clienteId, String estado) {

        List<Visita> visitas = visitaRepository.findAllByClienteAndEstado(clienteId, estado);

        if(visitas.isEmpty()){
            Visita v = new Visita();
            v.setEstado("No hay visitas con ese estado para este cliente");
            visitas.add(v);
            return visitas;
        }

        return visitas;
    }

    // Modificar una visita
    public Visita modifyVisita(Visita visita){
        // Primero comprobamos si la visia existe en la base de datos
        Visita fromDB = visitaRepository.findById(visita.getId());

        // Si no existe indicamos que no existe la visita
        if(fromDB == null){
            visita.setImporte(0.0);
            visita.setCliente(null);
            visita.setFactura(null);
            visita.setEstado("La visita no existe");
            return visita;
        }

        // Comprobamos su estado actual no es agendada
        if(!fromDB.getEstado().equals("Agendada")){
            visita.setImporte(0.0);
            visita.setCliente(null);
            visita.setFactura(null);
            visita.setEstado("La visita no está Agendada");
            return visita;
        }

        // Si está agendada podemos modificarla
        return visitaRepository.save(visita);

    }

}
