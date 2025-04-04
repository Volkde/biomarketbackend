package de.aittr.bio_marketplace.controller;


import de.aittr.bio_marketplace.controller.responses.AuthResponse;
import de.aittr.bio_marketplace.domain.dto.auth.LoginRequestDto;
import de.aittr.bio_marketplace.domain.dto.auth.RegisterUserDto;
import de.aittr.bio_marketplace.domain.dto.auth.RegisterUserResponseDto;
import de.aittr.bio_marketplace.exceptions.AuthenticationException;
import de.aittr.bio_marketplace.security.service.JwtTokenService;
import de.aittr.bio_marketplace.service.CookieService;
import de.aittr.bio_marketplace.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth controller", description = "Controller for various operations with Auth")
public class AuthController {

    private final UserService service;
    private final CookieService cookieService;
    private final JwtTokenService jwtTokenService;

    public AuthController(UserService service, CookieService cookieService, JwtTokenService jwtTokenService) {
        this.service = service;
        this.cookieService = cookieService;
        this.jwtTokenService = jwtTokenService;
    }

    @Operation(
            summary = "Register new user",
            description = "Registration new user with given parameters"
    )
    @PostMapping("/register")
    public AuthResponse register(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Instance of a User")
            RegisterUserDto registerUserDto
    ) {
        RegisterUserResponseDto responseDto = service.registerUser(registerUserDto);
        return new AuthResponse(responseDto);
    }

    @Operation(
            summary = "Login user",
            description = "Login user with given parameters"
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginDto) {
        try {
            RegisterUserResponseDto responseDto = service.loginUser(loginDto.email(), loginDto.password());

            ResponseCookie accessTokenCookie = cookieService.generateAccessTokenCookie(loginDto.email());
            ResponseCookie refreshTokenCookie = cookieService.generateRefreshTokenCookie(loginDto.email());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
            headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

            return new ResponseEntity<>(new AuthResponse(responseDto), headers, HttpStatus.OK);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @Operation(
            summary = "Refresh user token",
            description = "Refreshing user token"
    )
    @GetMapping("/refresh")
    public ResponseEntity<Void> refreshToken(HttpServletRequest request) {
        String refreshToken = cookieService.getJwtRefreshFromCookies(request);
        if (refreshToken == null) {
            return ResponseEntity.status(401).build();
        }
        //2 Проверить валидность
        boolean valid = jwtTokenService.parseRefreshToken(refreshToken) != null;
        if (!valid) {
            //неправильный/просроченный
            return ResponseEntity.status(401).build();
        }

        //3 Получить email из refresh
        String email = jwtTokenService.getEmailFromRefreshToken(refreshToken);

        //4 Сгенерировать новый Access
        ResponseCookie newAccessCookie = cookieService.generateAccessTokenCookie(email);

        //5 Записать в ответ
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, newAccessCookie.toString())
                .build();
    }

    @Operation(
            summary = "Logout user",
            description = "Logout user"
    )
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        //1 Берём пустые куки
        ResponseCookie cleanAccess = cookieService.getCleanAtJwtCookie();
        ResponseCookie cleanRefresh = cookieService.getCleanRtJwtCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cleanAccess.toString())
                .header(HttpHeaders.SET_COOKIE, cleanRefresh.toString())
                .build();
    }

    @Operation(
            summary = "Get current user",
            description = "Get current registered user"
    )
    @GetMapping("/profile")
    public RegisterUserResponseDto profile() {
         return service.getCurrentUser();
    }


}
