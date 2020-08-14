package com.adidas.api.service.impl;

import com.adidas.api.constants.Currency;
import com.adidas.api.dto.ProductDTO;
import com.adidas.api.exception.NotFoundException;
import com.adidas.api.exception.ServiceException;
import com.adidas.api.persistence.model.ProductEntity;
import com.adidas.api.persistence.repository.ProductRepository;
import com.adidas.api.service.CurrencyService;
import com.adidas.api.service.ProductService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private static final Type PRODUCT_DTO_LIST_TYPE = new TypeToken<List<ProductDTO>>() {
    }.getType();

    private ProductRepository repository;
    private ModelMapper modelMapper;
    private CurrencyService currencyService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CurrencyService currencyService, @Qualifier("com.adidas.modelMapper") ModelMapper modelMapper) {
        this.repository = productRepository;
        this.modelMapper = modelMapper;
        this.currencyService = currencyService;
    }

    @Override
    public List<ProductDTO> getProducts() throws ServiceException {

        try {
            logger.info("Obtaining list of products");
            List<ProductEntity> productEntities = repository.findAll();
            return modelMapper.map(productEntities, PRODUCT_DTO_LIST_TYPE);
        } catch (RuntimeException e) {
            logger.error("Error obtaining list of products", e);
            throw new ServiceException(e);
        }


    }

    @Override
    public ProductDTO get(Long productId) throws ServiceException, NotFoundException {

        try {

            logger.info("Obtaining product with id {}", productId);

            return modelMapper.map(findProductEntityOrThrowError(productId), ProductDTO.class);

        } catch(RuntimeException e) {
            logger.info("Error obtaining product with id {}", productId);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ProductDTO create(ProductDTO product, String currency) throws ServiceException {

        try {

            ProductEntity productEntity = modelMapper.map(product, ProductEntity.class);

            if(!StringUtils.isEmpty(currency)) {
                logger.info("Price needs to be calculated from {} EUR to {}", product.getPrice(), currency);
                Currency currencyEnum = Currency.fromString(currency);
                Double calculatedPrice = currencyService.convertToEur(product.getPrice(), currencyEnum);
                logger.info("Price has been calculated [{} EUR => {} {}]", product.getPrice(), calculatedPrice, currency);
                productEntity.setPrice(calculatedPrice);
            }

            productEntity = repository.save(productEntity);
            logger.info("New product has been created with id", productEntity.getId());
            return modelMapper.map(productEntity, ProductDTO.class);
        } catch (RuntimeException e) {
            logger.error("Error creating product", e);
            throw new ServiceException(e);
        }

    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ProductDTO update(Long productId, ProductDTO productDTO) throws ServiceException, NotFoundException {

        try {

            logger.info("Updating product with id {}", productId);

            if(productId != productDTO.getId()) {
                logger.error("Resource product id and productDto id does not match");
                throw new ServiceException("Resource product id and productDto id does not match");
            }

            findProductEntityOrThrowError(productId);

            ProductEntity productEntity = modelMapper.map(productDTO, ProductEntity.class);
            productEntity = repository.save(productEntity);
            logger.info("Updated product with id {}", productId);

            return modelMapper.map(productEntity, ProductDTO.class);

        } catch(RuntimeException e) {

            logger.error("Error updating product with id", productId, e);
            throw new ServiceException(e);

        }

    }


    @Override
    @Transactional(rollbackOn = Exception.class)
    public void delete(Long productId) throws ServiceException, NotFoundException {

        try {
            logger.info("Removing product by id {}", productId);

            ProductEntity productEntity = findProductEntityOrThrowError(productId);

            repository.delete(productEntity);
            logger.info("Product {} successfully removed", productId);

        } catch (RuntimeException e) {
            logger.error("Error removing products by categories", e);
            throw new ServiceException(e);
        }

    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deleteByCategories(List<Long> categories) throws ServiceException {

        try {
            logger.info("Removing products by list of categories");
            repository.deleteByCategoryIdList(categories);

        } catch (RuntimeException e) {
            logger.error("Error removing products by categories", e);
            throw new ServiceException(e);
        }

    }


    private ProductEntity findProductEntityOrThrowError(Long productId) throws NotFoundException {
        Optional<ProductEntity> optional = repository.findById(productId);
        if(optional.isPresent()) {
            return optional.get();
        } else {
            logger.error("Product {} not found", productId);
            throw new NotFoundException(String.format("Product %s not found", productId));
        }

    }
}
