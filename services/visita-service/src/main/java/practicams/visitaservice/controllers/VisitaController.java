package practicams.visitaservice.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import practicams.proyectoentidadessql.domain.Visita;
import practicams.visitaservice.services.VisitaService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class VisitaController {

    @Autowired
    VisitaService visitaService;

    /*
        Métodos práctica en microservicio
     */

    //
    // Mostrar visitas por cliente ID
    //
    @GetMapping("/visitas/byclienteid/{id}")
    public List<Visita> getVisitasByCliente(@PathVariable("id") Integer clienteId){
        return visitaService.getAllVisitasByCliente(clienteId);
    }

    //
    // Mostrar visitas por estado
    //
    @GetMapping("/visitas/bystate")
    public List<Visita> getVisitasByEstado(@RequestParam String estado){
        List<Visita> resultado = new ArrayList<>();

        // Comprobamos la validez del estado
        if(!estado.equals("Agendada") && !estado.equals("Facturada") && !estado.equals("Sin facturar") ){
            Visita v = new Visita();
            v.setId(null);
            v.setEstado("Estado inválido");
            resultado.add(v);
            return resultado;
        }

        return visitaService.getAllVisitasByEstado(estado);

    }

    //
    // Devolver visitas por un cliente Y estado (deben coincidir)
    //
    @GetMapping("/visitas/byclienteandestado/{clienteid}")
    public List<Visita> getVisitasByClienteAndEstado(@PathVariable("clienteid") Integer clienteId,
                                                    @RequestParam String estado){
        List<Visita> resultado = new ArrayList<>();

        // Comprobamos la validez del estado
        if(!estado.equals("Agendada") && !estado.equals("Facturada") && !estado.equals("Sin facturar") ){
            Visita v = new Visita();
            v.setId(null);
            v.setEstado("Estado inválido");
            resultado.add(v);
            return resultado;
        }

        return visitaService.getAllVisitasByClienteAndEstado(clienteId, estado);

    }

    //
    // Modificar una visita si está agendada
    //
    @PutMapping("/visitas/modifyvisita")
    public Visita modifyVisita(@RequestBody Visita visita){

        return visitaService.modifyVisita(visita);
    }

    /*
        Métodos práctica para llamadas entre microservicios
     */


    /*
        Métodos extra
     */

    // Obtener una visita por su id
    @GetMapping("/visitas/all")
    public List<Visita> getAllVisitas(){
        return visitaService.getAllVisitas();
    }

    // Obtener todas las visitas de la base de datos
    @GetMapping("/visitas/byid/{id}")
    public Visita getVisitaById(@PathVariable("id") Integer id){

        if(visitaService.getVisiitaById(id) == null){
            Visita v = new Visita();
            v.setId(id);
            v.setEstado("No existe esa visita");
            return v;
        }

        return visitaService.getVisiitaById(id);
    }


    // Insertar una visita nueva en la bd

    // Eliminar una visita

}
