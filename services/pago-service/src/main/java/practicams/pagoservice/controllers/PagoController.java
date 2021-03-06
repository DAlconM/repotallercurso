package practicams.pagoservice.controllers;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import practicams.pagoservice.services.PagoService;
import practicams.proyectoentidadesdto.domain.PagoDTO;
import practicams.proyectoentidadesmongo.domain.Factura;
import practicams.proyectoentidadesmongo.domain.Pago;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PagoController {

    @Autowired
    PagoService pagoService;

    // Balanceador de carga para las llamadas entre microservicios
    @LoadBalanced
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    /*
        Métodos práctica en microservicio
     */
    // Mostrar pagos por el id de la factura
    @HystrixCommand
    @GetMapping("/pagos/bypagoid/{id}")
    public List<Pago> getPagosById(@PathVariable("id") String pagoid){
        List<Pago> resultado = pagoService.getPagosByFactura(pagoid);

        if(resultado.isEmpty()){
            Pago p = new Pago();
            p.setEstado("No hay pagos de esa factura");
            resultado.add(p);
            return resultado;
        }

        return resultado;
    }

    // Mostrar pagos por el estado del pago
    @HystrixCommand
    @GetMapping("/pagos/byestado")
    public List<Pago> getPagosByEstado(@RequestParam String estado){
        List<Pago> resultado = new ArrayList<>();

        if(!estado.equals("Pendiente") && !estado.equals("Pagado") && !estado.equals("Impagado")){
            Pago p = new Pago();
            p.setEstado("Estado de pago inválido");
            resultado.add(p);
            return resultado;
        }

        resultado = pagoService.getPagosByEstado(estado);

        if(resultado.isEmpty()){
            Pago p = new Pago();
            p.setEstado("No hay pagos con ese estado");
            resultado.add(p);
            return resultado;
        }

        return resultado;
    }


    // Mostrar pagos por cliente
    @HystrixCommand
    @GetMapping("/pagos/byclientid/{id}")
    public Map<String, List<Pago>> getPagosByClientId(@PathVariable("id") Integer clienteId){
        return pagoService.getPagosByClientId(clienteId);
    }


    // Mostrar pagos por estado de factura
    @HystrixCommand
    @GetMapping("/pagos/byfacturaestado")
    public Map<String, List<Pago>> getPagosByFacturaEstado(@RequestParam String estado){
        Map<String, List<Pago>> resultado = new HashMap<>();
        // Comprobamos la validez del estado
        // Pendiente pago, Pagada parcialmente, Pagada, Impagada

        if(!estado.equals("Pendiente pago") && !estado.equals("Pagada parcialmente") && !estado.equals("Pagada") && !estado.equals("Impagada") ){
            resultado.put("Estado de factura inválido", null);
            return resultado;
        }

        resultado = pagoService.getPagosByFacturaEstado(estado);

        return resultado;
    }

    // Mostrar pagos por estado de cliente
    @HystrixCommand
    @GetMapping("/pagos/byclienteestado")
    public Map<String, Map<String, List<Pago>>> getPagosByClienteEstado(@RequestParam String estado){
        Map<String, Map<String, List<Pago>>> resultado = new HashMap<>();
        // Comprobamos la validez del estado
        // Con facturas pendientes, Sin facturas pendientes
        if(!estado.equals("Sin facturas pendientes") && !estado.equals("Factura pendiente pago") && !estado.equals("Impagado")){
            resultado.put("Estado de cliente inválido", null);
            return resultado;
        }

        resultado = pagoService.getPagosByClienteEstado(estado);

        return resultado;
    }

    /*
        Métodos práctica para llamadas entre microservicios
     */
    @HystrixCommand
    @GetMapping("/callpago/getpagosbyfactura/{id}")
    public List<PagoDTO> callGetPagosByFactura(@PathVariable("id") String facturaid){
        List<Pago> pagos = pagoService.getPagosByFactura(facturaid);

        List<PagoDTO> resultado = mapPagoToPagoDTO(pagos);

        return resultado;
    }


    /*
        Métodos extra
     */

    // Mostrar todos los pagos
    @HystrixCommand
    @GetMapping("/pagos/all")
    public List<Pago> getAllPagos(){
        return pagoService.getAllPagos();
    }

    // Mostrar un pago por id
    @HystrixCommand
    @GetMapping("/pagos/byid/{id}")
    public Pago getPagoById(@PathVariable("id") String id){
        return pagoService.getPagoById(id);
    }

    // Modificar uno pago por JSON
    @HystrixCommand
    @PutMapping("/pagos/modify")
    public Pago modifyPago(@RequestBody Pago pago){
        return pagoService.modifyPago(pago);
    }

    // Insertar un pago vací por JSON
    @HystrixCommand
    @PostMapping("/pagos/insertempty")
    public Pago insertEmptyPago(@RequestBody Pago pago){
        return pagoService.insertEmptyPago(pago);
    }

    // Método que mapea una lista de pagos a pagos dto
    private List<PagoDTO> mapPagoToPagoDTO(List<Pago> pagos){
        List<PagoDTO> resultado = new ArrayList<>();

        for(Pago p : pagos){
            PagoDTO pagoDTO = new PagoDTO();
            pagoDTO.setId(p.getId());
            pagoDTO.setFactura(p.getFactura());
            pagoDTO.setImporte(p.getImporte());
            pagoDTO.setEstado(p.getEstado());

            resultado.add(pagoDTO);
        }

        return resultado;
    }

}
