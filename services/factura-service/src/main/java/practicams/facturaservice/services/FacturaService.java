package practicams.facturaservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import practicams.facturaservice.repositories.FacturaRepository;
import practicams.proyectoentidadesdto.domain.FacturaDTO;
import practicams.proyectoentidadesdto.domain.PagoDTO;
import practicams.proyectoentidadesmongo.domain.Factura;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {

    @Autowired
    FacturaRepository facturaRepository;

    @Autowired
    RestTemplate balancedCall;

    // Devolver todas las facturas de la BD
    public List<Factura> getAllFacturas(){
        return facturaRepository.findAll();
    }

    // Modificar una factura por JSON en la BD
    public Factura modifyFactura(Factura factura) {
        return facturaRepository.save(factura);
    }

    // Insertar una factura vacía por JSON
    public Factura insertEmptyFactura(Factura factura) {
        return facturaRepository.insert(factura);
    }

    // Obtener una factura por el id
    public Factura getFacturaById(String id) {
        Optional<Factura> factura = facturaRepository.findById(id);
        if(factura.isPresent()){
            return factura.get();
        }
        else{
            Factura f = new Factura();
            f.setEstado("No existe factura con ese id");
            return f;
        }
    }

    // Obtener facturas por el id del cliente
    public List<Factura> getFacturasByClienteId(Integer clienteId) {
        return facturaRepository.findAllByCliente(clienteId);
    }

    // Obtener facturas por el estado
    public List<Factura> getFacturasByEstado(String estado) {
        return facturaRepository.findAllByEstado(estado);
    }

    public List<Factura> getFacturasByImportetotal(double importe) {
        return facturaRepository.findAllByImportetotal(importe);
    }

    //
    // Métodos Schedule de ejecución periódica
    //


    // Método que se encarga de comprobar el estado de las facturas
    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    @HystrixCommand(fallbackMethod = "pagoServiceDown")
    public void checkFacturaEstado(){

        // Sacamos las facturas de la bd
        List<Factura> facturas = facturaRepository.findAll();

        // Recorremos la lista de facturas
        for(Factura f : facturas){

            ResponseEntity<PagoDTO[]> pagos = balancedCall.getForEntity("http://pago-service/callpago/getpagosbyfactura/" + f.getId(), PagoDTO[].class);

            PagoDTO[] pagosDTO = pagos.getBody();

            double importeTotal = f.getImportetotal();
            double importePagado = 0.0;

            boolean impagada = false;

            // Recorremos los pagos
            // Si un pago está Pagado, cogemos su importe y lo sumamos al importePagado
            // Si está pendiente o impagado no cogemos su importe
            for(PagoDTO p : pagosDTO){
                if(p.getEstado().equals("Pagado")){
                    importePagado += p.getImporte();
                }
                if(p.getEstado().equals("Impagado")){
                    impagada = true;
                }
            }

            //System.out.println("Factura: " + f.getId() + "\nImporte Total: " + importeTotal + "\nImporte Pagado: " + importePagado);
            //"Pendiente pago" "Pagada parcialmente" "Pagada" "Impagada"

            // Si no se ha pagado nada
            if(importePagado == 0.0){
                f.setEstado("Pendiente pago");
            }
            else if(importePagado>0.0 && importePagado<importeTotal){
                f.setEstado("Pagada parcialmente");
            }
            else {
                f.setEstado("Pagada");
            }

            if(impagada){
                f.setEstado("Impagada");
            }

            // Actualizamos la información en la base de datos
            facturaRepository.save(f);
        }
    }


    // Método fallback por si el servicio de pagos cae
    public void pagoServiceDown(){
        System.out.println("SERVICIO DE PAGOS NO DISPONIBLE");
    }


}
