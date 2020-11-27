package practicams.clienteservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practicams.clienteservice.repositories.ClienteRepository;
import practicams.proyectoentidadessql.domain.Cliente;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    DireccionService direccionService;

    // Devolver todos los clientes
    public List<Cliente> getAllClients() {
        return clienteRepository.findAll();
    }

}
