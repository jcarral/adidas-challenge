package com.adidas.api.persistence.repository;

import com.adidas.api.persistence.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Query("FROM CategoryEntity c WHERE c.parent is NULL")
    List<CategoryEntity> findAllRootCategories();

}
