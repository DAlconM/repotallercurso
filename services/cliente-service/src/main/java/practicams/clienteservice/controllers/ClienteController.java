package practicams.clienteservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import practicams.clienteservice.services.ClienteService;
import practicams.clienteservice.services.DireccionService;
import practicams.proyectoentidadessql.domain.Cliente;
import practicams.proyectoentidadessql.domain.Direccion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @Autowired
    DireccionService direccionService;

    // Obtener todos los clientes de la base de datos
    @GetMapping("/clientes/all")
    public List<Cliente> getAllClients(){
        return clienteService.getAllClients();
    }

    // Mostrar clientes por nombre
    @GetMapping("/clientes/bynombre/{nombre}")
    public Map<String, List<Direccion>> getClienteByNombre(@PathVariable("nombre") String nombre){
        Map<String, List<Direccion>> resultado = new HashMap<>();

        // Sacamos los clientes con ese nombre
        List<Cliente> clientes = clienteService.getClientesByNombre(nombre);
        //List<Cliente> clientes = clienteService.getAllClients();

        // Sacamos las direcciones de cada cliente y rellenamos el resultado
        for(Cliente c : clientes){
            List<Direccion> direccionesCliente = direccionService.getAllDirectionsByClienteId(c.getId());
            resultado.put(c.getNombre() + " " + c.getApellido() + " || Estado: " + c.getEstado(), direccionesCliente);
        }

        if(resultado.isEmpty()){
            resultado.put("No hay clientes con ese nombre", null);
        }

        return resultado;

    }


    // Mostrar clientes por provincia





    // Sacar el Cliente y sus direcciones por id
    @GetMapping("/cliente/getDir/{id}")
    public Map<String, List<Direccion>> getClienteDirId(@PathVariable("id") Integer id){
        Map<String, List<Direccion>> resultado = new HashMap<>();

        // Sacamos el id
        Cliente cliente = clienteService.getClienteById(id);

        // Sacamos sus direcciones
        List<Direccion> direcciones = direccionService.getAllDirectionsByClienteId(id);

        resultado.put(cliente.getNombre() + " " + cliente.getApellido() + " || Estado: " + cliente.getEstado(), direcciones);
        return resultado;
    }


}
