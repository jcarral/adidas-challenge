package com.adidas.api.service;

import com.adidas.api.dto.ProductDTO;
import com.adidas.api.exception.NotFoundException;
import com.adidas.api.exception.ServiceException;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getProducts() throws ServiceException;
    ProductDTO get(Long productId) throws ServiceException, NotFoundException;
    ProductDTO create(ProductDTO product, String currency) throws ServiceException;
    ProductDTO update(Long productId, ProductDTO productDTO) throws ServiceException, NotFoundException;
    void delete(Long productId) throws ServiceException, NotFoundException;
    void deleteByCategories(List<Long> categories) throws ServiceException;
}
