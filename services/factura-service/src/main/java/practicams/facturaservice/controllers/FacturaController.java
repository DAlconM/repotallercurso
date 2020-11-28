package practicams.facturaservice.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import practicams.facturaservice.services.FacturaService;
import practicams.proyectoentidadesmongo.domain.Factura;
import practicams.proyectoentidadesmongo.domain.LineaFactura;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FacturaController {

    @Autowired
    FacturaService facturaService;

    /*
        Métodos práctica en microservicio
     */

    //
    // Obtener una factura por el id de cliente
    //
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


    /*
        Métodos extra
     */

    // Mostrar todas las facturas
    @GetMapping("/facturas/all")
    public List<Factura> getAllFacturas(){
        return facturaService.getAllFacturas();
    }

    // Mostrar una factura por id de factura
    @GetMapping("/facturas/byid/{id}")
    public Factura getFacturaById(@PathVariable("id") String id){
        return facturaService.getFacturaById(id);
    }

    // Modificar una factura por JSON
    @PutMapping("/facturas/modify")
    public Factura modifyFactura(@RequestBody Factura factura){
        return facturaService.modifyFactura(factura);
    }

    // Insertar una factura vacía por JSON
    @PostMapping("/facturas/insertempty")
    public Factura insertEmptyFactura(@RequestBody Factura factura){
        return facturaService.insertEmptyFactura(factura);
    }

}
