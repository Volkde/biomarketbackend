package de.aittr.bio_marketplace.controller;


import de.aittr.bio_marketplace.domain.dto.ProductDto;
import de.aittr.bio_marketplace.domain.dto.UserDto;
import de.aittr.bio_marketplace.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User controller", description = "Controller for various operations with Users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
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
    public BigDecimal getUserCartTotalCost(@PathVariable Long userId) {
        return service.getUsersCartTotalCost(userId);
    }

    @GetMapping("/all-products-by-user-id/{id}")
    public List<ProductDto> getAllProductsByUserId(@PathVariable Long id){
        return service.getAllProductsByUserId(id);
    }

    @DeleteMapping("/remove-user/{userId}/product/{productId}")
    public void removeProductFromUserCart(@PathVariable Long userId,@PathVariable  Long productId) {
        service.removeProductFromUserCart(userId, productId);
    }

    @DeleteMapping("/clear-cart/{id}")
    public void clearUserCart(@PathVariable Long id) {
        service.clearUserCart(id);
    }

    @GetMapping("/product-average-price/{id}")
    public BigDecimal getUserProductsAveragePrice(@PathVariable Long id) {
        return service.getUserProductsAveragePrice(id);
    }
}
