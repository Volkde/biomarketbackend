package de.aittr.bio_marketplace.controller;

import de.aittr.bio_marketplace.controller.requests.AttachImageToProductRequest;
import de.aittr.bio_marketplace.controller.responses.ProductImageResponse;
import de.aittr.bio_marketplace.domain.dto.ProductImageDto;
import de.aittr.bio_marketplace.domain.dto.UserDto;
import de.aittr.bio_marketplace.domain.entity.Product;
import de.aittr.bio_marketplace.domain.entity.ProductImage;
import de.aittr.bio_marketplace.domain.entity.Seller;
import de.aittr.bio_marketplace.domain.entity.User;
import de.aittr.bio_marketplace.exception_handling.Response;
import de.aittr.bio_marketplace.exception_handling.exceptions.UserNotSellerException;
import de.aittr.bio_marketplace.service.interfaces.FileService;
import de.aittr.bio_marketplace.service.interfaces.ProductService;
import de.aittr.bio_marketplace.service.interfaces.UserService;
import de.aittr.bio_marketplace.service.mapping.ProductImageMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
@Tag(name = "File controller", description = "Controller for file operations")
public class FileController {

    // --- FIELDS ---

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private final FileService fileService;
    private final UserService userService;
    private final ProductService productService;
    private final ProductImageMapper productImageMapper;


    // --- CONSTRUCTOR ---

    public FileController(FileService fileService, UserService userService, ProductService productService, ProductImageMapper productImageMapper) {
        this.fileService = fileService;
        this.userService = userService;
        this.productService = productService;
        this.productImageMapper = productImageMapper;
    }

    // --- METHODS ---

    // --- Create ---

    @Operation(
            summary = "Upload product image",
            description = "Uploads an image to Digital Ocean bucket for a seller. Creates a ProductImage entity with sellerId and null productId."
    )
    @PostMapping("/product_images")
    public ProductImageResponse uploadProductImage(@RequestParam("file") MultipartFile file) {
        UserDto userDto = userService.getCurrentUserAsDto();
        Long userId = userDto.getId();
        User user = userService.getActiveUserEntityById(userId);

        Seller seller = user.getSeller();
        if (seller == null) {
            throw new UserNotSellerException("User with ID " + userId + " is not a seller");
        }

        ProductImage productImage = fileService.uploadProductImage(file, seller.getId());
        ProductImageDto productImageDto = productImageMapper.toDto(productImage);
        return new ProductImageResponse(productImageDto);
    }

    // --- Update ---

    @Operation(
            summary = "Attach image to product",
            description = "Attaches an existing product image to a product if the current user is a seller and owns the product."
    )
    @PutMapping("/product_images")
    @ResponseStatus(HttpStatus.OK)
    public ProductImageResponse attachImageToProduct(@Valid @RequestBody AttachImageToProductRequest request) {

        UserDto userDto = userService.getCurrentUserAsDto();
        Long userId = userDto.getId();
        User user = userService.getActiveUserEntityById(userId);

        Seller seller = user.getSeller();
        if (seller == null) {
            throw new UserNotSellerException("User with ID " + userId + " is not a seller");
        }

        Product product = productService.getActiveProductEntityById(request.getProductId());
        if (product.getSeller() == null) {
            throw new IllegalStateException("Product with ID " + request.getProductId() + " does not have a seller");
        }

        Hibernate.initialize(product.getSeller());
        Long productSellerId = product.getSeller().getId();
        Long userSellerId = seller.getId();
        logger.info("Current user's sellerId: {}, Product's sellerId: {}, User ID: {}, Product ID: {}", userSellerId, productSellerId, userId, request.getProductId());
        if (!productSellerId.equals(userSellerId)) {
            throw new IllegalStateException("User with ID " + userId + " is not the owner of the product with ID " + request.getProductId());
        }

        ProductImage productImage = fileService.attachImageToProduct(request.getImageId(), request.getProductId(), userSellerId);
        ProductImageDto productImageDto = productImageMapper.toDto(productImage);
        return new ProductImageResponse(productImageDto);
    }

}