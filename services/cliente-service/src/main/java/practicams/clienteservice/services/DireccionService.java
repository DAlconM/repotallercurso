package practicams.clienteservice.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practicams.clienteservice.repositories.DireccionRepository;
import practicams.proyectoentidadessql.domain.Direccion;

import java.util.List;

@Service
public class DireccionService {

    @Autowired
    DireccionRepository direccionRepository;

    // Obtener todas las direcciones
    public List<Direccion> getAllDirectionsByClienteId(Integer id){
        return direccionRepository.findAllByCliente(id);
    }
    
    // Obtener una dirección por id
    public Direccion getDireccionById(Integer id){
        return direccionRepository.findById(id);
    }

}
