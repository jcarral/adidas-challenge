package com.adidas.api.service;

import com.adidas.api.dto.CategoryDTO;
import com.adidas.api.exception.NotFoundException;
import com.adidas.api.exception.ServiceException;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getCategories() throws ServiceException;
    CategoryDTO getCategory(Long categoryId) throws ServiceException, NotFoundException;
    CategoryDTO create(Long parentId, String name) throws ServiceException, NotFoundException;
    CategoryDTO update(Long categoryId, String nextName) throws ServiceException, NotFoundException;
    void delete(Long categoryId) throws ServiceException, NotFoundException;

}
