package de.aittr.bio_marketplace.controller;

import de.aittr.bio_marketplace.controller.responses.ProductImageResponse;
import de.aittr.bio_marketplace.domain.dto.ProductImageDto;
import de.aittr.bio_marketplace.domain.dto.UserDto;
import de.aittr.bio_marketplace.domain.entity.ProductImage;
import de.aittr.bio_marketplace.domain.entity.Seller;
import de.aittr.bio_marketplace.domain.entity.User;
import de.aittr.bio_marketplace.exception_handling.Response;
import de.aittr.bio_marketplace.exception_handling.exceptions.UserNotSellerException;
import de.aittr.bio_marketplace.service.interfaces.FileService;
import de.aittr.bio_marketplace.service.interfaces.UserService;
import de.aittr.bio_marketplace.service.mapping.ProductImageMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
@Tag(name = "File controller", description = "Controller for file operations")
public class FileController {

    // --- FIELDS ---

    private final FileService fileService;
    private final UserService userService;
    private final ProductImageMapper productImageMapper;


    // --- CONSTRUCTOR ---

    public FileController(FileService fileService, UserService userService, ProductImageMapper productImageMapper) {
        this.fileService = fileService;
        this.userService = userService;
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

}