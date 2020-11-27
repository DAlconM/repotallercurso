package practicams.clienteservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import practicams.clienteservice.services.ClienteService;
import practicams.proyectoentidadessql.domain.Cliente;

import java.util.List;


@RestController
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    // Obtener todos los clientes de la base de datos
    @GetMapping(name = "/clientes/all")
    public List<Cliente> getAllClients(){
        return clienteService.getAllClients();
    }




}
