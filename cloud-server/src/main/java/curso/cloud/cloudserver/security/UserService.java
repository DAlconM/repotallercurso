package curso.cloud.cloudserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User createUser(String username, String password, String domain, String rol) {
        // Comprobamos si exite el usuario en la bd con ese username
        Mono<UserDetails> fromDB = userRepository.findByUsername(username);
        UserDetails user = fromDB.block();

        // Si el usuario existe indicamos que no se puede crear
        if(user != null){
            User error = new User();
            error.setUsername("Nombre de usuario en uso");
            return error;
        }
        else{
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(passwordEncoder.encode(password));
            newUser.setDomain(domain);

            // Creamos su rol y lo asignamos
            Set<SimpleGrantedAuthority> roles = new HashSet<SimpleGrantedAuthority>();
            SimpleGrantedAuthority rolMade = new SimpleGrantedAuthority(rol);

            roles.add(rolMade);
            newUser.setRoles(roles);

            Mono<User> saved = userRepository.save(newUser);
            return saved.block();
        }
    }
}
