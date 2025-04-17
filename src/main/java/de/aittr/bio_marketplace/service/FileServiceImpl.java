package de.aittr.bio_marketplace.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import de.aittr.bio_marketplace.domain.entity.Product;
import de.aittr.bio_marketplace.domain.entity.ProductImage;
import de.aittr.bio_marketplace.domain.entity.Seller;
import de.aittr.bio_marketplace.exception_handling.exceptions.ProductNotFoundException;
import de.aittr.bio_marketplace.repository.ProductImageRepository;
import de.aittr.bio_marketplace.repository.ProductRepository;
import de.aittr.bio_marketplace.repository.SellerRepository;
import de.aittr.bio_marketplace.service.interfaces.FileService;
import de.aittr.bio_marketplace.service.interfaces.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    // --- FIELDS ---

    private final AmazonS3 client;
    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private static final String BUCKET_NAME = "farmvibe-bucket";
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    // --- CONSTRUCTOR ---

    public FileServiceImpl(AmazonS3 client, ProductImageRepository productImageRepository, ProductRepository productRepository, SellerRepository sellerRepository) {
        this.client = client;
        this.productImageRepository = productImageRepository;
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
    }

    // --- METHODS ---

    // --- Create ---

    @Override
    public ProductImage uploadProductImage(MultipartFile file, Long sellerId) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file cannot be null or empty");
        }

        try {
            String uniqueFileName = generateUniqueFileName(file);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            PutObjectRequest request = new PutObjectRequest(
                    BUCKET_NAME,
                    uniqueFileName,
                    file.getInputStream(),
                    metadata
            ).withCannedAcl(CannedAccessControlList.PublicRead);

            client.putObject(request);

            String url = client.getUrl(BUCKET_NAME, uniqueFileName).toString();

            Seller seller = sellerRepository.findById(sellerId)
                    .orElseThrow(() -> new RuntimeException("Seller with ID " + sellerId + " not found"));

            ProductImage productImage = new ProductImage();
            productImage.setUrl(url);
            productImage.setSeller(seller);
            productImage.setProduct(null);

            return productImageRepository.save(productImage);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to Digital Ocean bucket", e);
        }
    }

    private String generateUniqueFileName(MultipartFile file) {
        String sourceFileName = file.getOriginalFilename();
        if (sourceFileName == null || sourceFileName.isEmpty()) {
            sourceFileName = "unnamed_file";
        }
        int dotIndex = sourceFileName.lastIndexOf(".");
        String fileName = dotIndex > 0 ? sourceFileName.substring(0, dotIndex) : sourceFileName;
        String extension = dotIndex > 0 ? sourceFileName.substring(dotIndex) : "";

        return String.format("product_images/%s-%s%s", fileName, UUID.randomUUID(), extension);
    }

    // --- Update ---

    @Override
    public ProductImage attachImageToProduct(Long imageId, Long productId, Long sellerId) {
        logger.info("Attaching image with ID {} to product with ID {} for seller with ID {}", imageId, productId, sellerId);

        ProductImage productImage = productImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("ProductImage with ID " + imageId + " not found"));

        if (!productImage.getSeller().getId().equals(sellerId)) {
            logger.warn("Image with ID {} does not belong to seller with ID {}", imageId, sellerId);
            throw new IllegalStateException("Image with ID " + imageId + " does not belong to seller with ID " + sellerId);
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if (!product.getSeller().getId().equals(sellerId)) {
            logger.warn("Product with ID {} does not belong to seller with ID {}", productId, sellerId);
            throw new IllegalStateException("Product with ID " + productId + " does not belong to seller with ID " + sellerId);
        }

        productImage.setProduct(product);
        ProductImage updatedImage = productImageRepository.save(productImage);
        logger.info("Successfully attached image with ID {} to product with ID {}", imageId, productId);

        return updatedImage;
    }

}