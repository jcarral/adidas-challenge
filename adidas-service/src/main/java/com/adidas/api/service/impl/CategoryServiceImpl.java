package com.adidas.api.service.impl;

import com.adidas.api.dto.CategoryDTO;
import com.adidas.api.exception.NotFoundException;
import com.adidas.api.exception.ServiceException;
import com.adidas.api.persistence.model.CategoryEntity;
import com.adidas.api.persistence.model.ParentCategoryEntity;
import com.adidas.api.persistence.model.SubcategoryEntity;
import com.adidas.api.persistence.repository.CategoryRepository;
import com.adidas.api.service.CategoryService;
import com.adidas.api.service.ProductService;
import org.apache.commons.collections4.ListUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private static final Type CATEGORY_DTO_LIST_TYPE = new TypeToken<List<CategoryDTO>>() {
    }.getType();

    private CategoryRepository repository;
    private ModelMapper modelMapper;
    private ProductService productService;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductService productService, @Qualifier("com.adidas.modelMapper") ModelMapper modelMapper) {
        this.repository = categoryRepository;
        this.modelMapper = modelMapper;
        this.productService = productService;
    }

    @Override
    public List<CategoryDTO> getCategories() throws ServiceException  {

        try {
            logger.info("Obtaining list of categories");
            List<CategoryEntity> categoryEntities = repository.findAllRootCategories();
            return modelMapper.map(categoryEntities, CATEGORY_DTO_LIST_TYPE);
        } catch(RuntimeException e) {
            logger.error("Error obtainining list of categories", e);
            throw new ServiceException(e);
        }

    }

    @Override
    public CategoryDTO getCategory(Long categoryId) throws ServiceException, NotFoundException {

        try {

            logger.info("Obtaining category with id {}", categoryId);
            return modelMapper.map(findCategoryByIdOrThrowError(categoryId), CategoryDTO.class);

        } catch(RuntimeException e) {
            logger.info("Error obtaining category with id {}", categoryId);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public CategoryDTO create(Long parentId, String name) throws ServiceException, NotFoundException {

        try {
            logger.info("Creating new category {} on {}", name, parentId);

            CategoryEntity nextCategory = new CategoryEntity();

            //If there is no parentId then its a root category
            if(parentId != null) {
                CategoryEntity parentCategoryEntity = findCategoryByIdOrThrowError(parentId);
                ParentCategoryEntity parentCategoryMappedEntity = modelMapper.map(parentCategoryEntity, ParentCategoryEntity.class);
                nextCategory.setParent(parentCategoryMappedEntity);
            }

            nextCategory.setName(name);

            nextCategory = repository.save(nextCategory);

            return modelMapper.map(nextCategory, CategoryDTO.class);

        } catch (RuntimeException e) {
            logger.info("Error creating new category {} on {}", name, parentId, e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public CategoryDTO update(Long categoryId, String name) throws ServiceException, NotFoundException {
        try {

            logger.info("Updating category {} with {}", categoryId, name);

            CategoryEntity categoryEntity = findCategoryByIdOrThrowError(categoryId);
            categoryEntity.setName(name);
            categoryEntity = repository.save(categoryEntity);
            logger.info("Category {} successfully updated", categoryId);

            return modelMapper.map(categoryEntity, CategoryDTO.class);

        } catch(RuntimeException e) {
            logger.info("Error updating category {}", categoryId, e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void delete(Long categoryId) throws ServiceException, NotFoundException {

        try {

            logger.info("Category {} found. Removing", categoryId);
            CategoryEntity categoryEntity = findCategoryByIdOrThrowError(categoryId);
            List<Long> categoriesWithProducts = findCategoriesToRemove(categoryEntity);

            productService.deleteByCategories(categoriesWithProducts);
            repository.delete(categoryEntity);
            logger.info("Category {} removed", categoryId);

        } catch (RuntimeException e) {
            logger.error("Error removing categories", e);
            throw new ServiceException(e);
        }

    }

    private CategoryEntity findCategoryByIdOrThrowError(Long categoryId) throws NotFoundException {
        Optional<CategoryEntity> optionalCategoryEntity = repository.findById(categoryId);

        if(optionalCategoryEntity.isPresent()) {
            return optionalCategoryEntity.get();
        } else {

            logger.error("Category {} not found", categoryId);
            throw new NotFoundException(String.format("Category %s not found", categoryId));

        }
    }

    private List<Long> findCategoriesToRemove(CategoryEntity categoryEntity) {

        if(categoryEntity.getSubcategories().isEmpty()) {
            return Arrays.asList(categoryEntity.getId());
        } else {
            return categoryEntity.getSubcategories()
                    .stream()
                    .map(subcategoryEntity -> searchTreeLastLeafs(subcategoryEntity))
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        }

    }

    private List<Long> searchTreeLastLeafs(SubcategoryEntity subcategory) {
        List<Long> responseList = new ArrayList<>();

        if(subcategory.getSubcategories().isEmpty()) {
            return Arrays.asList(subcategory.getId());
        } else {
            for (SubcategoryEntity currentSubcategory : subcategory.getSubcategories()) {
                responseList = ListUtils.union(responseList, searchTreeLastLeafs(currentSubcategory));
            }
        }

        return responseList;
    }

}
