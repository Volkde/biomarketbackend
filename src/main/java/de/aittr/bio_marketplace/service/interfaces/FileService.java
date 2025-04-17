package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.domain.entity.ProductImage;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    // --- Create ---
    ProductImage uploadProductImage(MultipartFile file, Long sellerId);

    // --- Update ---
    ProductImage attachImageToProduct(Long imageId, Long productId, Long sellerId);

}
