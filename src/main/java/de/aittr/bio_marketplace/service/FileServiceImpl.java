package de.aittr.bio_marketplace.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import de.aittr.bio_marketplace.service.interfaces.FileService;
import de.aittr.bio_marketplace.service.interfaces.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private final AmazonS3 client;
    private final ProductService productService;

    public FileServiceImpl(AmazonS3 client, ProductService productService) {
        this.client = client;
        this.productService = productService;
    }

    @Override
    public String upload(MultipartFile file, String productTitle) {
        try {
            String uniqueName = generateUniqueFileName(file);


            ObjectMetadata metaData = new ObjectMetadata();
            metaData.setContentType(file.getContentType());

            PutObjectRequest request = new PutObjectRequest(
                    "farmvibe-bucket", uniqueName, file.getInputStream(), metaData
            ).withCannedAcl(CannedAccessControlList.PublicRead);

            client.putObject(request);

            String url = client.getUrl("farmvibe-bucket", uniqueName).toString();

            productService.attachImage(url, productTitle);

            return url;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String generateUniqueFileName(MultipartFile file) {

        String sourceFileName = file.getOriginalFilename();
        int dotIndex = sourceFileName.lastIndexOf(".");
        String fileName = sourceFileName.substring(0, dotIndex);
        String extension = sourceFileName.substring(dotIndex);

        return String.format("%s-%s%s", fileName, UUID.randomUUID(), extension);
    }
}