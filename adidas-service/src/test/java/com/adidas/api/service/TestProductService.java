package com.adidas.api.service;

import com.adidas.api.config.TestConfig;
import com.adidas.api.constants.Currency;
import com.adidas.api.dto.ProductDTO;
import com.adidas.api.exception.NotFoundException;
import com.adidas.api.persistence.model.ProductEntity;
import com.adidas.api.persistence.repository.ProductRepository;
import com.adidas.api.service.impl.ProductServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = { TestConfig.class, ProductServiceImpl.class })
public class TestProductService {

    private ProductService productService;

    @Mock
    private ProductRepository productRepository;


    @Mock
    private CurrencyService currencyService;

    @Spy
    private ModelMapper modelMapper;

    @Before
    public void setUp() throws Exception {
        productService = new ProductServiceImpl(productRepository, currencyService, modelMapper);
    }

    @Test
    public void testGetAllProducts() throws Exception {

        ProductEntity productMock = new ProductEntity(1L, "Ultraboost", 2020, "/ultra20.png", 110.1, 1L);
        ProductEntity secondProductMock = new ProductEntity(1L, "Ultraboost", 2019, "/ultra19.png", 120.1, 1L);

        Mockito.when(productRepository.findAll())
                .thenReturn(Arrays.asList(productMock, secondProductMock));

        List<ProductDTO> productDTOList = productService.getProducts();
        Assert.assertNotNull(productDTOList);
        Assert.assertTrue(productDTOList.size() == 2);

    }

    @Test(expected = NotFoundException.class)
    public void testGetInvalidProduct() throws Exception {

        Mockito.when(productRepository.findById(0L)).thenReturn(Optional.empty());
        productService.get(0L);
    }

    @Test
    public void testValidProduct() throws Exception {
        ProductEntity productMock = new ProductEntity(1L, "Ultraboost", 2020, "/ultra20.png", 110.1, 1L);

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(productMock));

        ProductDTO responseProduct = productService.get(1L);
        Assert.assertNotNull(responseProduct);
        Assert.assertTrue("Ultraboost".equals(responseProduct.getName()));

    }


    @Test
    public void testCreateProductWithConversion() throws Exception {

        Mockito.when(currencyService.convertToEur(200D, Currency.USD))
                .thenReturn(220D);
        Mockito.when(productRepository.save(Mockito.any(ProductEntity.class)))
                .thenReturn(new ProductEntity(1L, "Ultraboost", 2020, null, 220D, null));


        ProductDTO nextProduct = new ProductDTO(null, "Ultraboost", 2020, 200D, null, null);
        ProductDTO createdProduct = productService.create(nextProduct, Currency.USD.toString());

        Assert.assertNotNull(createdProduct);
        Assert.assertTrue("Ultraboost".equals(createdProduct.getName()));
        Assert.assertNotNull(createdProduct.getId());
        Assert.assertTrue((double) 220D == (double) createdProduct.getPrice());
    }

    @Test
    public void testCreateProductWithoutConversion() throws Exception {

        Mockito.when(productRepository.save(Mockito.any(ProductEntity.class)))
                .thenReturn(new ProductEntity(1L, "Ultraboost", 2020, null, 200D, null));
        ProductDTO nextProduct = new ProductDTO(null, "Ultraboost", 2020, 200D, null, null);
        ProductDTO createdProduct = productService.create(nextProduct, Currency.USD.toString());

        Assert.assertNotNull(createdProduct);
        Assert.assertTrue("Ultraboost".equals(createdProduct.getName()));
        Assert.assertNotNull(createdProduct.getId());
        Assert.assertTrue((double) 200D == (double) createdProduct.getPrice());

    }


    @Test
    public void testUpdateProduct() throws Exception {
        ProductDTO mockedProduct = new ProductDTO(2L, "Ultraboost", 2020, 200D, null, 1L);
        ProductEntity entityMock = new ProductEntity(2L, "Pulseboost", 2020, null, 200D, 1L);
        ProductEntity updatedEntityMock = new ProductEntity(2L, "Ultraboost", 2020, null, 200D, 1L);

        Mockito.when(productRepository.findById(2L))
                .thenReturn(Optional.of(entityMock));
        Mockito.when(productRepository.save(Mockito.any(ProductEntity.class)))
                .thenReturn(updatedEntityMock);

        ProductDTO updatedProduct = productService.update(2L, mockedProduct);

        Assert.assertNotNull(updatedProduct);
        Assert.assertTrue("Ultraboost".equals(updatedProduct.getName()));
        Assert.assertNotNull(updatedProduct.getId());
        Assert.assertTrue((long) 2L == (long) updatedProduct.getId());

    }
}
