package practicams.clienteservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import practicams.proyectoentidadessql.domain.Cliente;

@EnableEurekaClient
@SpringBootApplication
public class ClienteServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClienteServiceApplication.class, args);
	}

	public void pruebaSQl(){
		Cliente cliente;
	}
}

