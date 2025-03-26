package de.aittr.bio_marketplace.service.interfaces;

import de.aittr.bio_marketplace.domain.entity.Role;
import de.aittr.bio_marketplace.domain.entity.Seller;
import de.aittr.bio_marketplace.domain.entity.User;

import java.util.List;

public interface SellerService {

    Seller saveSeller(Seller seller);

    List<Seller> getAllActiveSellers();

    Seller getById(Long id);

    void update(Seller seller);

    Seller getActiveSellersEntityById(Long id);

    void deleteById(Long id);

    void deleteByStoreName(String storeName);
}
