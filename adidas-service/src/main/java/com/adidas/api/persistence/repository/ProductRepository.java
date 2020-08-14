package com.adidas.api.persistence.repository;

import com.adidas.api.persistence.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findAll();

    @Transactional
    @Modifying
    @Query("DELETE FROM ProductEntity p WHERE p.categoryId in :ids")
    void deleteByCategoryIdList(@Param("ids") List<Long> ids);

}
