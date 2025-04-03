package de.aittr.bio_marketplace.service;

import de.aittr.bio_marketplace.domain.dto.SellerDto;
import de.aittr.bio_marketplace.domain.entity.Seller;
import de.aittr.bio_marketplace.exception_handling.exceptions.SellerNotFoundException;
import de.aittr.bio_marketplace.exception_handling.exceptions.SellerValidationException;
import de.aittr.bio_marketplace.repository.SellerRepository;
import de.aittr.bio_marketplace.service.interfaces.SellerService;
import de.aittr.bio_marketplace.service.mapping.SellerMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {

    private final SellerRepository repository;
    private final SellerMapper mapper;

    public SellerServiceImpl(SellerRepository repository, SellerMapper sellerMapper) {
        this.repository = repository;
        this.mapper = sellerMapper;
    }

    @Override
    @Transactional
    public SellerDto saveSeller(SellerDto seller) {
        try {
            Seller entity = mapper.mapDtoToEntity(seller);
            entity = repository.save(entity);
            return mapper.mapEntityToDto(entity);
        }  catch (Exception e) {
            throw new SellerValidationException(e);
        }
    }

    @Override
    public List<SellerDto> getAllActiveSellers() {
        return repository.findAll()
                .stream()
                .map(mapper::mapEntityToDto)
                .toList();
    }

    @Override
    public SellerDto getById(Long id) {
        return mapper.mapEntityToDto(getActiveSellersEntityById(id));
    }

    @Override
    @Transactional
    public SellerDto update(SellerDto seller) {
        Long id = seller.getId();
        Seller findSeller = getActiveSellersEntityById(id);
        findSeller.setStoreName(seller.getStoreName());
        findSeller.setStoreDescription(seller.getStoreDescription());
        findSeller.setStoreLogo(seller.getStoreLogo());
        return mapper.mapEntityToDto(findSeller);
    }

    @Override
    public Seller getActiveSellersEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(()-> new SellerNotFoundException(id));
    }

    @Override
    public SellerDto deleteById(Long id) {
         Seller seller = getActiveSellersEntityById(id);
         repository.deleteById(id);
         return mapper.mapEntityToDto(seller);
    }

    @Override
    @Transactional
    public SellerDto deleteByStoreName(String storeName) {
        Seller seller = repository.findByStoreName(storeName).orElseThrow(()-> new SellerNotFoundException(storeName));
        repository.deleteByStoreName(storeName);
        return mapper.mapEntityToDto(seller);
    }
}
