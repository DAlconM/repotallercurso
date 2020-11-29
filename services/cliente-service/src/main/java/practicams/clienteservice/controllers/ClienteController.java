package practicams.clienteservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import practicams.clienteservice.services.ClienteService;
import practicams.clienteservice.services.DireccionService;
import practicams.proyectoentidadesdto.domain.ClienteDTO;
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

    /*
        Métodos práctica en microservicio
     */

    //
    // Mostrar clientes por nombre
    //
    @GetMapping("/clientes/bynombre/{nombre}")
    public Map<String, List<Direccion>> getClientesByNombre(@PathVariable("nombre") String nombre){
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


    //
    // Mostrar clientes por provincia
    //
    @GetMapping("/clientes/byprovincia/{provincia}")
    public Map<String, List<Direccion>> getClientesByProvincia(@PathVariable("provincia") String provincia){
        Map<String, List<Direccion>> resultado = new HashMap<>();

        // Sacamos los clientes con esa provincia
        List<Cliente> clientes = clienteService.getClientesByProvincia(provincia);

        // Sacamos las direcciones de cada cliente y rellenamos el resultado
        for(Cliente c : clientes){
            List<Direccion> direccionesCliente = direccionService.getAllDirectionsByClienteId(c.getId());
            resultado.put(c.getNombre() + " " + c.getApellido() + " || Estado: " + c.getEstado(), direccionesCliente);
        }

        if(resultado.isEmpty()){
            resultado.put("No hay clientes con esa provincia", null);
        }

        return resultado;
    }

    /*
        Métodos práctica para llamadas entre microservicios
     */

    //
    // Método que devuelve los clientes por estado
    //
    @GetMapping("/callcliente/byclienteestado/{estado}")
    public List<ClienteDTO> callGetClientesByEstado(@PathVariable("estado") String estado){
        List<Cliente> clientes = clienteService.getClientesByEstado(estado);

        // Mapeamos los clientes a dto para su transferencia
        List<ClienteDTO> resultado = mapClienteToClienteDTO(clientes);

        // Devolvemos los clientes dto
        return resultado;
    }


    /*
        Métodos extra
     */

    // Obtener todos los clientes de la base de datos
    @GetMapping("/clientes/all")
    public List<Cliente> getAllClientes(){
        return clienteService.getAllClientes();
    }

    // Obtener todos los clientes de la base de datos con sus direcciones
    @GetMapping("/clientes/allanddir")
    public Map<String, List<Direccion>> getAllClienteAndDireccion(){
        Map<String, List<Direccion>> resultado = new HashMap<>();

        List<Cliente> clientes = clienteService.getAllClientes();

        for(Cliente c : clientes){
            List<Direccion> direccionesCliente = direccionService.getAllDirectionsByClienteId(c.getId());
            resultado.put(c.getNombre() + " " + c.getApellido() + " || Estado: " + c.getEstado(), direccionesCliente);
        }

        if(resultado.isEmpty()){
            resultado.put("No hay clientes en la base de datos", null);
        }

        return resultado;
    }

    // Sacar el Cliente y sus direcciones por id
    @GetMapping("/clientes/getDir/{id}")
    public Map<String, List<Direccion>> getClienteDirId(@PathVariable("id") Integer id){
        Map<String, List<Direccion>> resultado = new HashMap<>();

        // Sacamos el id
        Cliente cliente = clienteService.getClienteById(id);

        if(cliente == null){
            resultado.put("No existe cliente con ese id", null);
            return resultado;
        }

        // Sacamos sus direcciones
        List<Direccion> direcciones = direccionService.getAllDirectionsByClienteId(id);

        resultado.put(cliente.getNombre() + " " + cliente.getApellido() + " || Estado: " + cliente.getEstado(), direcciones);
        return resultado;
    }

    // Insertar un cliente nuevo en la bd

    // Modificar un cliente existente en la bd

    // Eliminar un cliente


    // Método que mapea una lista de clientes a clientes dto
    private List<ClienteDTO> mapClienteToClienteDTO(List<Cliente> clientes) {
        List<ClienteDTO> resultado = new ArrayList<>();

        for(Cliente c : clientes){
            ClienteDTO cdto = new ClienteDTO();
            cdto.setId(c.getId());
            cdto.setNombre(c.getNombre());
            cdto.setApellido(c.getApellido());
            cdto.setEstado(c.getEstado());

            resultado.add(cdto);
        }

        return resultado;

    }
}
