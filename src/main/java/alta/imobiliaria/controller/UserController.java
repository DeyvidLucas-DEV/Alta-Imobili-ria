package alta.imobiliaria.controller;

import alta.imobiliaria.domain.User;
import alta.imobiliaria.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        // Passwords are stored as provided since no security configuration is present
        User saved = repository.save(user);
        return ResponseEntity.created(URI.create("/users/" + saved.getId())).body(saved);
    }
}
