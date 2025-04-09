package de.aittr.bio_marketplace.controller;


import de.aittr.bio_marketplace.controller.responses.AuthResponse;
import de.aittr.bio_marketplace.controller.responses.UserResponse;
import de.aittr.bio_marketplace.domain.dto.CartItemDto;
import de.aittr.bio_marketplace.domain.dto.ProductDto;
import de.aittr.bio_marketplace.domain.dto.SellerDto;
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

    @Operation(
            summary = "Get user by id",
            description = "Getting user from database by id"
    )
    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable
                           @Parameter(description = "User unique identifier")
                           Long id
    ) {
        return new UserResponse (service.getById(id));
    }

    @Operation(
            summary = "Update user by id",
            description = "Updating user from database by id"
    )
    @PutMapping
    public UserResponse update(@RequestBody UserDto user) {
        return new UserResponse(service.update(user));
    }

    @Operation(
            summary = "Get user quantity",
            description = "Getting user quantity from database"
    )
    @GetMapping("/quantity")
    public long getUserQuantity() {
        return service.getAllActiveUsersCount();
    }

    @Operation(
            summary = "Activate user by id",
            description = "Activating user from database by id"
    )
    @PutMapping("/activate/{id}")
    public UserResponse activateUserById(@PathVariable Long id) {
        return new UserResponse(service.activateUser(id));
    }

    @Operation(
            summary = "Deactivate user by id",
            description = "Deactivating user from database by id"
    )
    @PutMapping("/deactivate/{id}")
    public UserResponse deactivateUserById(@PathVariable Long id) {
        return new UserResponse(service.deactivateUserById(id));
    }

    @Operation(
            summary = "Delete user by id",
            description = "Deletion user from database by id"
    )
    @DeleteMapping("/{id}")
    public UserResponse deleteById(@PathVariable Long id) {
        return new UserResponse(service.deleteById(id));
    }

    @Operation(
            summary = "Delete user by username",
            description = "Deletion user from database by username"
    )
    @DeleteMapping("/by-username/{username}")
    public UserResponse deleteByUsername(@PathVariable String username) {
        return new UserResponse(service.deleteByUsername(username));
    }

    @Operation(
            summary = "Save product to user cart",
            description = "Saving product to user cart with given parameters"
    )
    @PutMapping("/{id}/product/{productId}")
    public void addProduct(@PathVariable Long userId, @PathVariable Long productId, BigDecimal quantity) {
        service.addProductToUserCart(userId, productId, quantity);
    }

    @Operation(
            summary = "Get total cost by user id",
            description = "Getting the total cost of the cart from database by user id"
    )
    @GetMapping("/total-cost/{userId}")
    public BigDecimal getUserCartTotalCost(@PathVariable Long userId) {
        return service.getUsersCartTotalCost(userId);
    }

    @Operation(
            summary = "Get all products by user id",
            description = "Getting all products from the cart from database by user id"
    )
    @GetMapping("/all-products-by-user-id/{id}")
    public List<CartItemDto> getAllProductsByUserId(@PathVariable Long id){
//        return service.getAllProductsByUserId(id);
        return null;
        // TODO: fix it later
    }

    @Operation(
            summary = "Get all sellers by user id",
            description = "Getting all sellers from database by user id"
    )
    @GetMapping("/all-sellers-by-user-id/{id}")
    public List<SellerDto> getAllSellers(@PathVariable Long id){
        return service.getAllSellers(id);
    }

    @Operation(
            summary = "Delete the product from user cart by id",
            description = "Deletion the product from user cart from database by user and cart id"
    )
    @DeleteMapping("/remove-user/{userId}/product/{productId}")
    public void removeProductFromUserCart(@PathVariable Long userId,@PathVariable  Long productId) {
        service.removeProductFromUserCart(userId, productId);
    }

    @Operation(
            summary = "Delete all products from user cart by id",
            description = "Deletion all products from user cart from database by user id"
    )
    @DeleteMapping("/clear-cart/{id}")
    public void clearUserCart(@PathVariable Long id) {
        service.clearUserCart(id);
    }

    @Operation(
            summary = "Get average price by user id",
            description = "Getting the average price of the cart from database by user id"
    )
    @GetMapping("/product-average-price/{id}")
    public BigDecimal getUserProductsAveragePrice(@PathVariable Long id) {
        return service.getUserProductsAveragePrice(id);
    }

    @Operation(
            summary = "Change user to admin by id",
            description = "Changing user to admin in database by id"
    )
    @PutMapping("/give-admin/{id}")
    public AuthResponse giveAdmin(@PathVariable Long id) {
        return new AuthResponse(service.giveAdmin(id));
    }


    @Operation(
            summary = "Change admin to user by id",
            description = "Changing admin to user in database by id"
    )
    @PutMapping("/remove-admin/{id}")
    public AuthResponse removeAdmin(@PathVariable Long id) {
        return new AuthResponse(service.removeAdmin(id));
    }

  @Operation(
            summary = "Change password by user id",
            description = "Changing password in database by user id"
    )
    @PutMapping("/change-password/{password}/by-id/{id}")
    public UserResponse changePassword(@PathVariable String password,@PathVariable Long id) {
        return new UserResponse(service.changePassword(password, id));
    }


}
