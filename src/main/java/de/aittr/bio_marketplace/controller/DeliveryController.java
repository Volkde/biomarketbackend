package de.aittr.bio_marketplace.controller;

import de.aittr.bio_marketplace.domain.dto.DeliveryDto;
import de.aittr.bio_marketplace.service.interfaces.DeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @Operation(summary = "Создать новую доставку (привязав её к существующему заказу)")
    @PostMapping
    public DeliveryDto createDelivery(@RequestBody DeliveryDto deliveryDto) {
        return deliveryService.createDelivery(deliveryDto);
    }

    @Operation(summary = "Получить доставку по ID")
    @GetMapping("/{id}")
    public DeliveryDto getDeliveryById(@PathVariable Long id) {
        return deliveryService.getById(id);
    }

    @Operation(summary = "Получить список всех доставок")
    @GetMapping
    public List<DeliveryDto> getAllDeliveries() {
        return deliveryService.getAll();
    }

    @Operation(summary = "Обновить данные доставки")
    @PutMapping
    public DeliveryDto updateDelivery(@RequestBody DeliveryDto deliveryDto) {
        return deliveryService.update(deliveryDto);
    }

    @Operation(summary = "Удалить доставку по ID")
    @DeleteMapping("/{id}")
    public void deleteDelivery(@PathVariable Long id) {
        deliveryService.deleteById(id);
    }
}
