package com.adidas.api.controller;

import com.adidas.api.dto.CategoryDTO;
import com.adidas.api.exception.NotFoundException;
import com.adidas.api.exception.ServiceException;
import com.adidas.api.service.CategoryService;
import com.adidas.api.utils.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {

    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private CategoryService categoryService;

    @Autowired
    private CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody Map<String, String> parameters) throws Exception {
        logger.info("[POST] Creating category");

        Long parent = NumberUtils.isNumeric(parameters.get("parent"))
                ? Long.valueOf(parameters.get("parent")) : null;
        String name = parameters.get("name");
        CategoryDTO category = categoryService.create(parent, name);
        return ResponseEntity.ok(category);

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CategoryDTO>> getCategories() throws Exception {

        logger.info("[GET] Get list of categories");

        List<CategoryDTO> responseCategories = categoryService.getCategories();
        return ResponseEntity.ok(responseCategories);
    }

    @GetMapping(path = "/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable("categoryId") Long categoryId) throws Exception {

        logger.info("[GET] category by id {}", categoryId);

        CategoryDTO category = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(category);

    }


    @PatchMapping(path = "/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable("categoryId") Long categoryId, @NotEmpty @RequestParam("name") String name)
            throws NotFoundException, ServiceException {
        logger.info("[PATCH] Update category {} name {}", categoryId, name);

        CategoryDTO category = categoryService.update(categoryId, name);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping(path = "/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity deleteCategory(@PathVariable("categoryId") Long categoryId)
            throws NotFoundException, ServiceException {
        logger.info("[DELETE] remove category by id {}", categoryId);
        categoryService.delete(categoryId);
        return ResponseEntity.ok().build();
    }
}
