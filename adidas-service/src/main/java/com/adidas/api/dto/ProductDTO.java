package com.adidas.api.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ProductDTO {

    private Long id;
    @NotEmpty(message = "Please provide a product name")
    private String name;
    private int modelYear;
    @NotNull(message = "Please provide a price")
    private Double price;
    private String imagePath;
    @NotNull(message = "Please provide a category id")
    private Long categoryId;

    public ProductDTO() {
    }

    public ProductDTO(Long id, @NotEmpty(message = "Please provide a product name") String name, int modelYear, @NotNull(message = "Please provide a price") Double price, String imagePath, @NotNull(message = "Please provide a category id") Long categoryId) {
        this.id = id;
        this.name = name;
        this.modelYear = modelYear;
        this.price = price;
        this.imagePath = imagePath;
        this.categoryId = categoryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getModelYear() {
        return modelYear;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
