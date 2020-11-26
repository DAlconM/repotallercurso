package practicams.facturaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class FacturaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacturaServiceApplication.class, args);
	}

}
