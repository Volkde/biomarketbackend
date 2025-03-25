package de.aittr.bio_marketplace.controller;


import de.aittr.bio_marketplace.domain.entity.User;
import de.aittr.bio_marketplace.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User controller", description = "Controller for various operations with Users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }


    @PostMapping
    public User save(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Instance of a User")
            User user
    ) {
        return service.saveCustomer(user);
    }


    @GetMapping()
    @Operation(
            summary = "Get all users",
            description = "Getting all users that exist in the database"
    )
    public List<User> getAll() {
        return service.getAllActiveUsers();
    }


    @GetMapping("/{id}")
    public User getById(@PathVariable
                               @Parameter(description = "User unique identifier")
                               Long id
    ) {
        return service.getById(id);
    }

    @PutMapping
    public void update(@RequestBody User user) {
        service.update(user);

    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @DeleteMapping("/by-username/{username}")
    public void deleteByTitle(@PathVariable String username) {
        service.deleteByUsername(username);
    }
}
