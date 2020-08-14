package com.adidas.api.controller;


import com.adidas.api.constants.Currency;
import com.adidas.api.dto.ProductDTO;
import com.adidas.api.exception.NotFoundException;
import com.adidas.api.exception.ServiceException;
import com.adidas.api.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO product, @RequestParam(name = "currency") String currency)
            throws Exception {
        logger.info("[POST] Create new product");

        if(!StringUtils.isEmpty(currency) && !Currency.validCurrency(currency)) {
            logger.error("Currency {} is not valid", currency);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        ProductDTO productDTO = productService.create(product, currency);
        return ResponseEntity.ok(productDTO);

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductDTO>> getProducts() throws Exception {
        logger.info("[GET] get products");

        List<ProductDTO> responseProducts = productService.getProducts();
        return ResponseEntity.ok(responseProducts);
    }

    @GetMapping(path = "/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("productId") Long productId) throws NotFoundException, ServiceException {
        logger.info("[GET] product by id {}", productId);

        ProductDTO productDTO = productService.get(productId);
        return ResponseEntity.ok(productDTO);

    }

    @PutMapping(path = "/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO product, @PathVariable("productId") Long productId) {
        return null;
    }

    @DeleteMapping(path = "/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity deleteProduct(@PathVariable("productId") Long productId) throws NotFoundException, ServiceException {
        logger.info("[DELETE] delete product by id {}", productId);

        productService.delete(productId);
        return ResponseEntity.ok().build();
    }
}
