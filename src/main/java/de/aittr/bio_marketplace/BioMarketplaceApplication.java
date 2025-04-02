package de.aittr.bio_marketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan({"de.aittr.bio_marketplace.security"})
public class BioMarketplaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BioMarketplaceApplication.class, args);
    }

}
