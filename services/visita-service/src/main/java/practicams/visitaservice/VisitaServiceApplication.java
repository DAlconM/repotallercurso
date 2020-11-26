package practicams.visitaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class VisitaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisitaServiceApplication.class, args);
	}

}
