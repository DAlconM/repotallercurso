package curso.cloud.cloudserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class WebfluxSecurityConfig {

    @Autowired
    UserRepository userRepository;

    // Implementación de la seguridad en el gateway
    @Bean
    public ReactiveUserDetailsService userDetailsService(){
        return (username) -> userRepository.findByUsername(username);
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){

        ServerHttpSecurity.AuthorizeExchangeSpec authorizeExchangeSpec = http.authorizeExchange();

        // Permitimos la creación del usuario y el actuator solo a los administradores
        authorizeExchangeSpec.pathMatchers("/createuser/**").hasRole("ADMIN");
        authorizeExchangeSpec.pathMatchers("/actuator/**").hasRole("ADMIN");
        authorizeExchangeSpec.pathMatchers("/allusers/**").hasRole("ADMIN");

        // Con esto permitimos a cualquier autenticado el acceso
        //authorizeExchangeSpec.pathMatchers("/**").authenticated();

        /*
            Controlando roles de los microservicios
         */
        // Para acceder a facturas, solo se puede acceder si el rol del usuario es facturas, por ejemplo
        authorizeExchangeSpec.pathMatchers("/cliente-service/**").hasAnyRole("ADMIN", "CLIENTE");
        authorizeExchangeSpec.pathMatchers("/visita-service/**").hasAnyRole("ADMIN", "VISITA");
        authorizeExchangeSpec.pathMatchers("/factura-service/**").hasAnyRole("ADMIN", "FACTURA");
        authorizeExchangeSpec.pathMatchers("/pago-service/**").hasAnyRole("ADMIN", "PAGO");

        authorizeExchangeSpec.and().csrf().disable().httpBasic().and().formLogin().loginPage("/login");
        return http.build();

    }


}
