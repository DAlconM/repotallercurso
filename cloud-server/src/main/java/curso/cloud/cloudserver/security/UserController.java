package curso.cloud.cloudserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/createuser/{username}/{password}/{domain}/{rol}")
    public User createUser(@PathVariable("username") String username,
                                  @PathVariable("password") String password,
                                  @PathVariable("domain") String domain,
                                  @PathVariable("rol") String rol){

        return userService.createUser(username, password, domain, rol);
    }
}
