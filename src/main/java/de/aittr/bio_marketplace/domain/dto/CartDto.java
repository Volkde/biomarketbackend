package de.aittr.bio_marketplace.domain.dto;

import java.util.List;
import java.util.Objects;

public class CartDto {

    private Long id;
    private List<CartItemDto> items;

    public CartDto() {
    }

    public CartDto(Long id, List<CartItemDto> items) {
        this.id = id;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public List<CartItemDto> getItems() {
        return items;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartDto cartDto = (CartDto) o;
        return Objects.equals(id, cartDto.id) &&
                Objects.equals(items, cartDto.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, items);
    }
}
