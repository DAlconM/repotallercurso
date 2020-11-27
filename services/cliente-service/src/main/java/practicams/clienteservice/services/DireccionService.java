package practicams.clienteservice.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practicams.clienteservice.repositories.DireccionRepository;

@Service
public class DireccionService {

    @Autowired
    DireccionRepository direccionRepository;


}
