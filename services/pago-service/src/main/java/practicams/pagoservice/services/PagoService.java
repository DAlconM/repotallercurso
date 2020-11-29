package practicams.pagoservice.services;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import practicams.pagoservice.repositories.PagoRepository;
import practicams.proyectoentidadesdto.domain.ClienteDTO;
import practicams.proyectoentidadesdto.domain.FacturaDTO;
import practicams.proyectoentidadesmongo.domain.Pago;

import java.util.*;

@Service
public class PagoService {

    @Autowired
    PagoRepository pagoRepository;

    // Bean de Eureka
    @Qualifier("eurekaClient")
    @Autowired
    private EurekaClient eurekaClient;

    // Devolver todos los pagos de la BD
    public List<Pago> getAllPagos() {
        return pagoRepository.findAll();
    }

    // Devolver un pago por el id
    public Pago getPagoById(String id) {
        Optional<Pago> pago = pagoRepository.findById(id);
        if(pago.isPresent()){
            return pago.get();
        }
        else{
            Pago p = new Pago();
            p.setEstado("No existe pago con ese id");
            return p;
        }
    }

    // Modificar un pago por JSON
    public Pago modifyPago(Pago pago) {
        return pagoRepository.save(pago);
    }

    // Insertar un pago vacío por JSON
    public Pago insertEmptyPago(Pago pago) {
        return pagoRepository.insert(pago);
    }

    // Devolver los pagos por el id de factura
    public List<Pago> getPagosByFactura(String facturaid) {
        return pagoRepository.findAllByFactura(facturaid);
    }

    // Devolver los pagos por el estado del pago
    public List<Pago> getPagosByEstado(String estado) {
        return pagoRepository.findAllByEstado(estado);
    }

    // Devolver los pagos por el id de un cliente
    // Este método necesita llamar al factura-service
    // Resultado será una coleccion clave valor
    // Siendo clave un string con el id del cliente y nombre
    // Y la lista de sus pagos que obtenemos de las facturas de ese cliente
    public Map<String, List<Pago>> getPagosByClientId(Integer clientId){
        Map<String, List<Pago>> resultado = new HashMap<>();

        // Primero sacamos las facturas con ese id de cliente llamando al servicio de facturas

        ///////////////////////////////////////////
        // Llamada usando RestTemplate
        // Implementar LoadBalancer
        ///////////////////////////////////////////
        Application app = eurekaClient.getApplication("FACTURA-SERVICE");
        List<InstanceInfo> infoApp = app.getInstances();
        String url = infoApp.get(0).getHomePageUrl();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<FacturaDTO[]> response = restTemplate.getForEntity(url + "/callfactura/byclientid/" + clientId, FacturaDTO[].class);

        FacturaDTO[] facturas = response.getBody();

        // Si no hay facturas no devolvemos nada mas
        if(facturas == null || facturas.length == 0){
            resultado.put("No hay facturas de ese cliente", null);
            return resultado;
        }

        // Recorremos las facturas sacando los pagos de cada una y añadiendolo al mapa
        for(FacturaDTO f : facturas){
            resultado.put("Pagos cliente ID: " + clientId, pagoRepository.findAllByFactura(f.getId()));
        }

        return resultado;
    }

    // Devolver los pagos por el estado de las facturas
    // Este método necesita llamar al factura-service
    // Resultado será una coleccion clave valor
    // Siendo clave un string con la factura y su estado
    // Y la lista de sus pagos que obtenemos de las facturas de con ese estado
    public Map<String, List<Pago>> getPagosByFacturaEstado(String estado){
        Map<String, List<Pago>> resultado = new HashMap<>();

        // Primero sacamos las facturas con ese estado llamando al servicio de facturas

        ///////////////////////////////////////////
        // Llamada usando RestTemplate
        // Implementar LoadBalancer
        ///////////////////////////////////////////
        Application app = eurekaClient.getApplication("FACTURA-SERVICE");
        List<InstanceInfo> infoApp = app.getInstances();
        String url = infoApp.get(0).getHomePageUrl();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<FacturaDTO[]> response = restTemplate.getForEntity(url + "/callfactura/byfacturaestado/" + estado, FacturaDTO[].class);

        FacturaDTO[] facturas = response.getBody();

        // Si no hay facturas no devolvemos nada mas
        if(facturas == null || facturas.length == 0){
            resultado.put("No hay facturas con ese estado", null);
            return resultado;
        }

        // Recorremos las facturas sacando los pagos de cada una y añadiendolo al mapa
        for(FacturaDTO f : facturas){
            resultado.put("Factura ID: " + f.getId() + "Estado: " + f.getEstado(), pagoRepository.findAllByFactura(f.getId()));
        }

        return resultado;
    }

    // Devolver los pagos de los clientes por estado
    // Este método llama al ms de clientes para saber los clientes con ese estado
    // Cuando tiene los clientes va recorriendo clientes para sacar los pagos de cada uno
    // El resultado es un mapa con cliente, su estado y sus pagos
    public Map<String, Map<String, List<Pago>>> getPagosByClienteEstado(String estado) {
        Map<String, Map<String, List<Pago>>> resultado = new HashMap<>();

        // Primero obtenemos los clientes con ese estado
        ///////////////////////////////////////////
        // Llamada usando RestTemplate
        // Implementar LoadBalancer
        ///////////////////////////////////////////
        Application app = eurekaClient.getApplication("CLIENTE-SERVICE");
        List<InstanceInfo> infoApp = app.getInstances();
        String url = infoApp.get(0).getHomePageUrl();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ClienteDTO[]> response = restTemplate.getForEntity(url + "/callcliente/byclienteestado/" + estado, ClienteDTO[].class);

        ClienteDTO[] clientes = response.getBody();

        // Si no hay clientes no hacemos mas
        if (clientes == null || clientes.length == 0) {
            resultado.put("No hay clientes con ese estado", null);
            return resultado;
        }

        // Ahora que tenemos los clientes, debemos sacar las facturas de cada uno y los pagos de estas
        // Para ello recorremos la lista de clientes sacando sus facturas y pagos
        for (ClienteDTO c : clientes) {
            Map<String, List<Pago>> pagosCliente = getPagosByClientId(c.getId());
            resultado.put("Cliente: " + c.getNombre() + " " + c.getApellido() + " || Estado: " + c.getEstado(), pagosCliente);
        }

        // Aquí resultado es un mapa de clave String y de valor otro mapa de string lista pagos

        return resultado;
    }



}
