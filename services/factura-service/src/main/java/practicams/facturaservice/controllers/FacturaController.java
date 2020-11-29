package practicams.facturaservice.controllers;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import practicams.facturaservice.services.FacturaService;
import practicams.proyectoentidadesdto.domain.FacturaDTO;
import practicams.proyectoentidadesdto.domain.VisitaDTO;
import practicams.proyectoentidadesmongo.domain.Factura;
import practicams.proyectoentidadesmongo.domain.LineaFactura;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FacturaController {

    @Autowired
    FacturaService facturaService;

    // Balanceador de carga para las llamadas entre microservicios
    @LoadBalanced
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    /*
        Métodos práctica en microservicio
     */

    //
    // Obtener una factura por el id de cliente
    //
    @HystrixCommand
    @GetMapping("/facturas/byclienteid/{id}")
    public List<Factura> getFacturasByClienteId(@PathVariable("id") Integer clienteId){
        List<Factura> resultado = facturaService.getFacturasByClienteId(clienteId);

        // Si el cliente no tiene facturas
        if(resultado.isEmpty()){
            Factura f = new Factura();
            f.setEstado("El cliente no tiene facturas");
            resultado.add(f);
            return resultado;
        }

        return resultado;
    }

    //
    // Obtener facturas por el estado
    //
    @HystrixCommand
    @GetMapping("/facturas/byestado")
    public List<Factura> getFacturasByEstado(@RequestParam String estado){
        List<Factura> resultado = new ArrayList<>();

        // Comprobamos la validez del estado
        // Pendiente pago, Pagada parcialmente, Pagada, Impagada
        if(!estado.equals("Pendiente pago") && !estado.equals("Pagada parcialmente") && !estado.equals("Pagada") && !estado.equals("Impagada") ){
            Factura f = new Factura();
            f.setEstado("Estado de factura inválido");
            resultado.add(f);
            return resultado;
        }

        // Sacamos las facturas por el estado
        resultado = facturaService.getFacturasByEstado(estado);

        // Si no hay facturas con ese estado
        if(resultado.isEmpty()){
            Factura f = new Factura();
            f.setEstado("No hay facturas con ese estado");
            resultado.add(f);
            return resultado;
        }

        return resultado;
    }

    //
    // Sacar facturas por importe
    //
    @HystrixCommand
    @GetMapping("/facturas/byimporte")
    public List<Factura> getFacturasByImportetotal(@RequestParam double importe){
        // Sacamos las facturas por el importe
        List<Factura> resultado = facturaService.getFacturasByImportetotal(importe);

        // Si no hay facturas con ese importe
        if(resultado.isEmpty()){
            Factura f = new Factura();
            f.setEstado("No hay facturas con ese importe");
            resultado.add(f);
            return resultado;
        }

        return resultado;
    }

    /*
        Métodos práctica para llamadas entre microservicios
     */

    //
    // Método remoto que devuelve las facturas por id de cliente
    //
    @HystrixCommand
    @GetMapping("/callfactura/byclientid/{id}")
    public List<FacturaDTO> callGetFacturasByCliente(@PathVariable("id") Integer clienteid){
        List<Factura> facturas =  facturaService.getFacturasByClienteId(clienteid);

        // Mapeamos las facturas a dto para su transferencia
        List<FacturaDTO> resultado = mapFacturaToFacturaDTO(facturas);

        // Devolvemos la lista de facturas dto
        return resultado;

    }

    //
    // Método remoto que devuelve las facturas por estado de la factura
    //
    @HystrixCommand
    @GetMapping("/callfactura/byfacturaestado/{estado}")
    public List<FacturaDTO> callGetFacturasByEstado(@PathVariable("estado") String estado){
        List<Factura> facturas =  facturaService.getFacturasByEstado(estado);

        // Mapeamos las facturas a dto para su transferencia
        List<FacturaDTO> resultado = mapFacturaToFacturaDTO(facturas);

        // Devolvemos la lista de facturas dto
        return resultado;

    }


    /*
        Métodos extra
     */

    // Mostrar todas las facturas
    @HystrixCommand
    @GetMapping("/facturas/all")
    public List<Factura> getAllFacturas(){
        return facturaService.getAllFacturas();
    }

    // Mostrar una factura por id de factura
    @HystrixCommand
    @GetMapping("/facturas/byid/{id}")
    public Factura getFacturaById(@PathVariable("id") String id){
        return facturaService.getFacturaById(id);
    }

    // Modificar una factura por JSON
    @HystrixCommand
    @PutMapping("/facturas/modify")
    public Factura modifyFactura(@RequestBody Factura factura){
        return facturaService.modifyFactura(factura);
    }

    // Insertar una factura vacía por JSON
    @HystrixCommand
    @PostMapping("/facturas/insertempty")
    public Factura insertEmptyFactura(@RequestBody Factura factura){
        return facturaService.insertEmptyFactura(factura);
    }

    // Método que mapea una lista de facturas a facturas dto
    private List<FacturaDTO> mapFacturaToFacturaDTO(List<Factura> facturas){
        List<FacturaDTO> resultado = new ArrayList<>();

        // Mapeamos las facturas obtenidas a facturas dto
        for(Factura f : facturas){
            FacturaDTO fdto = new FacturaDTO();
            fdto.setId(f.getId());
            fdto.setEstado(f.getEstado());
            fdto.setCliente(f.getCliente());
            fdto.setImportetotal(f.getImportetotal());
            fdto.setFormapago(f.getFormapago());

            // Mapeamos las lineas de factura de la factura a visitas DTO
            List<VisitaDTO> visitasDTO = new ArrayList<>();
            for(LineaFactura lf : f.getLineasfactura()){
                VisitaDTO vdto = new VisitaDTO();
                vdto.setId(lf.getId());
                vdto.setEstado(lf.getEstado());
                vdto.setCliente(lf.getCliente());
                vdto.setImporte(lf.getImporte());
                vdto.setFactura(lf.getFactura());

                visitasDTO.add(vdto);
            }
            // Seteamos la lista de lineas de factura DTO
            fdto.setLineasfactura(visitasDTO);

            // Añadimos la FacturaDTO a la lista de DTOs
            resultado.add(fdto);
        }

        return resultado;
    }

}
