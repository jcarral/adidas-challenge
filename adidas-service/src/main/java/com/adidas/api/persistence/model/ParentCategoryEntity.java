package com.adidas.api.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CATEGORIES")
public class ParentCategoryEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne
    private ParentCategoryEntity parent;

    public ParentCategoryEntity() {
    }

    public ParentCategoryEntity(Long id, String name, ParentCategoryEntity parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
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
}
