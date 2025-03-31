package de.aittr.bio_marketplace.controller;


import de.aittr.bio_marketplace.domain.dto.auth.LoginRequestDto;
import de.aittr.bio_marketplace.domain.dto.auth.RegisterUserResponseDto;
import de.aittr.bio_marketplace.domain.entity.User;
import de.aittr.bio_marketplace.security.service.JwtTokenService;
import de.aittr.bio_marketplace.service.CookieService;
import de.aittr.bio_marketplace.domain.dto.auth.RegisterUserDto;
import de.aittr.bio_marketplace.domain.dto.UserDto;
import de.aittr.bio_marketplace.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
@Tag(name = "User controller", description = "Controller for various operations with Users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/auth/refresh")
    public ResponseEntity<Void> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = cookieService.getJwtRefreshFromCookies(request);
        if (refreshToken == null) {
            return ResponseEntity.status(401).build(); // нет куки
        }

        //2 Проверить валидность
        boolean valid = /* jwtTokenService.validateRefreshToken(refreshToken) */
                jwtTokenService.parseRefreshToken(refreshToken) != null;
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

    /**
     * Логаут — чистим оба куки (Access/Refresh)
     */
    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        //1 Берём пустые куки
        ResponseCookie cleanAccess = cookieService.getCleanAtJwtCookie();
        ResponseCookie cleanRefresh = cookieService.getCleanRtJwtCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cleanAccess.toString())
                .header(HttpHeaders.SET_COOKIE, cleanRefresh.toString())
                .build();
    }

    @GetMapping
    @Operation(
            summary = "Get all users",
            description = "Getting all users that exist in the database"
    )
    public List<UserDto> getAll() {
        return service.getAllActiveUsers();
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable
                           @Parameter(description = "User unique identifier")
                           Long id
    ) {
        return service.getById(id);
    }

    @PutMapping
    public void update(@RequestBody UserDto user) {
        service.update(user);
    }

    @GetMapping("/quantity")
    public long getUserQuantity() {
        return service.getAllActiveUsersCount();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @DeleteMapping("/by-username/{username}")
    public void deleteByUsername(@PathVariable String username) {
        service.deleteByUsername(username);
    }

    @PutMapping("/{id}/product/{productId}")
    public void addProduct(@PathVariable Long id, @PathVariable Long productId) {
        service.addProductToUserCart(id, productId);
    }

    @GetMapping("/total-cost/{userId}")
    public BigDecimal getUserCartTotalCost(Long userId) {
        return service.getUsersCartTotalCost(userId);
    }

    @DeleteMapping("/remove-user/{userId}/product/{id}")
    public void removeProductFromUserCart(Long userId, Long productId) {
        service.removeProductFromUserCart(userId, productId);
    }

    @DeleteMapping("/clear-cart")
    public void clearUserCart(Long userId) {
        service.clearUserCart(userId);
    }

    @GetMapping("/product-average-price")
    public BigDecimal getUserProductsAveragePrice(Long userId) {
        return service.getUserProductsAveragePrice(userId);
    }
}
