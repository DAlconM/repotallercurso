package practicams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableEurekaClient
@EnableScheduling
@EnableCircuitBreaker
@SpringBootApplication
public class VisitaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisitaServiceApplication.class, args);
	}

}
