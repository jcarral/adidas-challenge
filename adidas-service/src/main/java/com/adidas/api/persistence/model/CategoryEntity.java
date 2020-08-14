package com.adidas.api.persistence.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "CATEGORIES")
public class CategoryEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne
    private ParentCategoryEntity parent;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentId")
    private List<SubcategoryEntity> subcategories;

    public CategoryEntity() {
    }

    public CategoryEntity(Long id, String name, ParentCategoryEntity parent, List<SubcategoryEntity> subcategories) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.subcategories = subcategories;
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

    public ParentCategoryEntity getParent() {
        return parent;
    }

    public void setParent(ParentCategoryEntity parent) {
        this.parent = parent;
    }

    public List<SubcategoryEntity> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<SubcategoryEntity> subcategories) {
        this.subcategories = subcategories;
    }

}
