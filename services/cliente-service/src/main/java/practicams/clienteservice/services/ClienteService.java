package practicams.clienteservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practicams.clienteservice.repositories.ClienteRepository;
import practicams.proyectoentidadessql.domain.Cliente;
import practicams.proyectoentidadessql.domain.Direccion;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    DireccionService direccionService;

    // Devolver todos los clientes
    public List<Cliente> getAllClientes(){
        return clienteRepository.findAll();
    }

    // Devolver un cliente por el id
    public Cliente getClienteById(Integer id){
        return clienteRepository.findById(id);
    }

    // Devolver una lista de clientes según el nombre del cliente
    public List<Cliente> getClientesByNombre(String nombre){
        return clienteRepository.findAllByNombre(nombre);
    }

    // Devolver una lista de clientes según la provincia
    public List<Cliente> getClientesByProvincia(String provincia) {

        // Primero sacamos las direcciones con esa provincia
        List<Direccion> direcciones = direccionService.getAllDireccionByProvincia(provincia);

        List<Cliente> clientes = new ArrayList<>();

        // Sacamos los clientes de cada direccion con el id de cliente
        for(Direccion d : direcciones){
            clientes.add(clienteRepository.findById(d.getCliente()));
        }

        // Devolvemos la lista de clientes que aparecen en direcciones con dicha provincia
        return clientes;

    }

    // Devolver los clientes por el estado
    public List<Cliente> getClientesByEstado(String estado){
        return clienteRepository.findAllByEstado(estado);
    }
}
