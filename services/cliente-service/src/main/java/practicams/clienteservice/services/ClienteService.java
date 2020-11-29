package practicams.clienteservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import practicams.clienteservice.repositories.ClienteRepository;
import practicams.proyectoentidadesdto.domain.FacturaDTO;
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

    @Autowired
    RestTemplate balancedCall;

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

    //
    // Métodos Schedule de ejecución periódica
    //

    // Método que se encarga de comprobar el estado de los clientes cada 10 segundos
    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    @HystrixCommand(fallbackMethod = "facturaServiceDown")
    public void checkClienteEstado(){

        // Sacamos los clientes
        List<Cliente> clientes = clienteRepository.findAll();

        // Recorremos la lista de clientes y vamos pidiendo sus facturas para ver los estados de estas
        for(Cliente cliente : clientes){
            // Pedimos al servicio de facturas las facturas con el id del cliente
            // Llamada con Balancer
            ResponseEntity<FacturaDTO[]> response = balancedCall.getForEntity("http://factura-service/callfactura/byclientid/" + cliente.getId(), FacturaDTO[].class);

            FacturaDTO[] facturas = response.getBody();

            //System.out.println("Cliente: " + cliente.getId());

            if(facturas != null && facturas.length != 0){

                Boolean pendientes = false;

                // Recorremos las facturas comprobando los estados
                for(FacturaDTO factura : facturas){

                    //System.out.println("\tFactura: " + factura.getId() + " || Estado: " + factura.getEstado());

                    // Si el estado de la factura es pendiente de pago o pagada parcialmente establecemos activamos el flag
                    if(factura.getEstado().equals("Pendiente pago") || factura.getEstado().equals("Pagada parcialmente")){
                        //System.out.println("GUARDAR CLIENTE ESTADO CAMBIADO EN BD");
                        pendientes = true;
                    }
                }

                // Si tenia alguna pendiente modificamos
                if(pendientes){
                    cliente.setEstado("Factura pendiente pago");
                    clienteRepository.save(cliente);
                }
                else{
                    cliente.setEstado("Sin facturas pendientes");
                    clienteRepository.save(cliente);
                }
            }
        }
    }

    // Metodo fallback
    public void facturaServiceDown(){
        System.out.println("ERROR: SERVICIO FACTURAS NO DISPONIBLE");
    }

}
