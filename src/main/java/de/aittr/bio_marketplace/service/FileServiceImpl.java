package de.aittr.bio_marketplace.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import de.aittr.bio_marketplace.domain.entity.ProductImage;
import de.aittr.bio_marketplace.domain.entity.Seller;
import de.aittr.bio_marketplace.repository.ProductImageRepository;
import de.aittr.bio_marketplace.repository.SellerRepository;
import de.aittr.bio_marketplace.service.interfaces.FileService;
import de.aittr.bio_marketplace.service.interfaces.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    // --- FIELDS ---

    private final AmazonS3 client;
    private final ProductImageRepository productImageRepository;
    private final SellerRepository sellerRepository;
    private static final String BUCKET_NAME = "farmvibe-bucket";

    // --- CONSTRUCTOR ---

    public FileServiceImpl(AmazonS3 client, ProductImageRepository productImageRepository, SellerRepository sellerRepository) {
        this.client = client;
        this.productImageRepository = productImageRepository;
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
            productImage.setSeller(seller); // Используем существующего Seller
            productImage.setProduct(null); // productId = null as per requirement

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
}