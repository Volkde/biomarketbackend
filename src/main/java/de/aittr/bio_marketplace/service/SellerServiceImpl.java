package de.aittr.bio_marketplace.service;

import de.aittr.bio_marketplace.domain.entity.Seller;
import de.aittr.bio_marketplace.domain.entity.User;
import de.aittr.bio_marketplace.repository.SellerRepository;
import de.aittr.bio_marketplace.repository.UserRepository;
import de.aittr.bio_marketplace.service.interfaces.SellerService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {

    private final SellerRepository repository;

    public SellerServiceImpl(SellerRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Seller saveSeller(Seller seller) {
        return repository.save(seller);
    }

    @Override
    public List<Seller> getAllActiveSellers() {
        return repository.findAll()
                .stream()
                .toList();
    }

    @Override
    public Seller getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    @Transactional
    public void update(Seller seller) {
        Long id = seller.getId();
        Seller findSeller = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seller not found"));
        findSeller.setStoreName(seller.getStoreName());
        findSeller.setStoreDescription(seller.getStoreDescription());
        findSeller.setStoreLogo(seller.getStoreLogo());
    }

    @Override
    public Seller getActiveSellersEntityById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
         repository.deleteById(id);
    }

    @Override
    public void deleteByStoreName(String storeName) {
        repository.deleteByStoreName(storeName);
    }
}
