package de.aittr.bio_marketplace.controller;


import de.aittr.bio_marketplace.domain.dto.auth.LoginRequestDto;
import de.aittr.bio_marketplace.domain.dto.auth.RegisterUserResponseDto;
import de.aittr.bio_marketplace.domain.entity.User;
import de.aittr.bio_marketplace.service.CookieService;
import de.aittr.bio_marketplace.domain.dto.auth.RegisterUserDto;
import de.aittr.bio_marketplace.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User controller", description = "Controller for various operations with Users")
public class UserController {

    private final UserService service;
    private final CookieService cookieService;

    public UserController(UserService service, CookieService cookieService) {
        this.service = service;
        this.cookieService = cookieService;
    }

    @PostMapping("/auth/register")
    public RegisterUserResponseDto register(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Instance of a User")
            RegisterUserDto registerUserDto
    ) {
        return service.registerUser(registerUserDto);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequestDto loginDto, HttpServletResponse response) {
        service.loginUser(loginDto.email(), loginDto.password());

        ResponseCookie accessTokenCookie = cookieService.generateAccessTokenCookie(loginDto.email());
        ResponseCookie refreshTokenCookie = cookieService.generateRefreshTokenCookie(loginDto.email());

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .build();
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
