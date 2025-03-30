package de.aittr.bio_marketplace.controller;


import de.aittr.bio_marketplace.domain.dto.auth.LoginRequestDto;
import de.aittr.bio_marketplace.domain.dto.auth.RegisterUserDto;
import de.aittr.bio_marketplace.domain.dto.auth.RegisterUserResponseDto;
import de.aittr.bio_marketplace.service.CookieService;
import de.aittr.bio_marketplace.service.interfaces.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class RegistrationController {

    private final UserService service;
    private final CookieService cookieService;

    public RegistrationController(UserService service, CookieService cookieService) {
        this.service = service;
        this.cookieService = cookieService;
    }

    @PostMapping("/register")
    public RegisterUserResponseDto register(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Instance of a User")
            RegisterUserDto registerUserDto
    ) {
        return service.registerUser(registerUserDto);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequestDto loginDto, HttpServletResponse response) {
        service.loginUser(loginDto.email(), loginDto.password());

        ResponseCookie accessTokenCookie = cookieService.generateAccessTokenCookie(loginDto.email());
        ResponseCookie refreshTokenCookie = cookieService.generateRefreshTokenCookie(loginDto.email());

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .build();
    }

}
