package com.adidas.api.dto;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class CategoryDTO {

    private Long id;

    @NotEmpty(message = "Please provide a category name")
    private String name;
    private CategoryDTO parent;
    private List<CategoryDTO> subcategories;

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

    public CategoryDTO getParent() {
        return parent;
    }

    public void setParent(CategoryDTO parent) {
        this.parent = parent;
    }

    public List<CategoryDTO> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<CategoryDTO> subcategories) {
        this.subcategories = subcategories;
    }
}
