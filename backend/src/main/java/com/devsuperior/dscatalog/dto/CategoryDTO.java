package com.devsuperior.dscatalog.dto;

import com.devsuperior.dscatalog.entities.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class CategoryDTO {
    private Long id;

    @Size(min = 3, max = 80, message = "Name must have between 3 or 80 characters")
    @NotBlank
    private String name;
    private Instant createdAt;
    private Set<ProductDTO> productDTOS = new HashSet<>();

    public CategoryDTO(Long id, String name, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public CategoryDTO(Category entity) {
        id = entity.getId();
        name = entity.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Set<ProductDTO> getProductDTOS() {
        return productDTOS;
    }
}
