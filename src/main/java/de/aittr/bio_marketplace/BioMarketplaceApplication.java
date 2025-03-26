package de.aittr.bio_marketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BioMarketplaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BioMarketplaceApplication.class, args);
	}

}