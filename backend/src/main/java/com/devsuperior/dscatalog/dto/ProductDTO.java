package com.devsuperior.dscatalog.dto;

import com.devsuperior.dscatalog.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ProductDTO {
    private Long id;

    @Size(min = 3, max = 80, message = "Name must have between 3 or 80 characters")
    @NotBlank(message = "Name field is Required!")
    private String name;

    @Size(min = 10, message = "Description must have at least 10 characters")
    @NotBlank(message = "Description field is Required!")
    private String description;

    @Positive(message = "Price must be a positive number")
    private Double price;

    private String imgUrl;

    private Instant date;

    private List<CategoryDTO> categoryDTOList = new ArrayList<>();

    public ProductDTO(Long id, String name, String description, Double price, String imgUrl, Instant date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.date = date;
    }

    public ProductDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();
        date = entity.getDate();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Instant getDate() {
        return date;
    }

    public List<CategoryDTO> getCategoryDTOList() {
        return categoryDTOList;
    }
}
