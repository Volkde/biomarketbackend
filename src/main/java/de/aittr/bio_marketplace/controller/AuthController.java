package de.aittr.bio_marketplace.controller;


import de.aittr.bio_marketplace.domain.dto.auth.LoginRequestDto;
import de.aittr.bio_marketplace.domain.dto.auth.RegisterUserDto;
import de.aittr.bio_marketplace.domain.dto.auth.RegisterUserResponseDto;
import de.aittr.bio_marketplace.security.service.JwtTokenService;
import de.aittr.bio_marketplace.service.CookieService;
import de.aittr.bio_marketplace.service.interfaces.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService service;
    private final CookieService cookieService;
    private final JwtTokenService jwtTokenService;

    public AuthController(UserService service, CookieService cookieService, JwtTokenService jwtTokenService) {
        this.service = service;
        this.cookieService = cookieService;
        this.jwtTokenService = jwtTokenService;
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

    @GetMapping("/refresh")
    public ResponseEntity<Void> refreshToken(HttpServletRequest request, HttpServletResponse response) {
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
}
