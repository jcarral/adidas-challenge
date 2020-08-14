package com.adidas.api.service;

import com.adidas.api.config.TestConfig;
import com.adidas.api.dto.CategoryDTO;
import com.adidas.api.exception.NotFoundException;
import com.adidas.api.persistence.model.CategoryEntity;
import com.adidas.api.persistence.model.ParentCategoryEntity;
import com.adidas.api.persistence.repository.CategoryRepository;
import com.adidas.api.service.impl.CategoryServiceImpl;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = { TestConfig.class, CategoryServiceImpl.class })
public class TestCategoryService {

    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductService productService;

    @Spy
    private ModelMapper modelMapper;

    @Before
    public void setUp() throws Exception {
        categoryService = new CategoryServiceImpl(categoryRepository, productService, modelMapper);
    }

    @Test
    public void testGetAllCategoriesWithData() throws Exception {

        Mockito.when(categoryRepository.findAllRootCategories()).thenReturn(Arrays.asList(new CategoryEntity(1L, "Shoes", null, new ArrayList<>())));

        List<CategoryDTO> categoryDTOList = categoryService.getCategories();
        Assert.assertNotNull(categoryDTOList);
        Assert.assertFalse(categoryDTOList.isEmpty());

    }

    @Test
    public void testGetAllCategoriesWithoutData() throws Exception {

        Mockito.when(categoryRepository.findAllRootCategories()).thenReturn(Arrays.asList());

        List<CategoryDTO> categoryDTOList = categoryService.getCategories();
        Assert.assertNotNull(categoryDTOList);
        Assert.assertTrue(categoryDTOList.isEmpty());

    }

    @Test(expected = NotFoundException.class)
    public void testInvalidCategory() throws Exception {

        Mockito.when(categoryRepository.findById(10L)).thenReturn(Optional.empty());
        categoryService.getCategory(10L);

    }


    @Test
    public void testValidCategory() throws Exception {
        Long id = 2L;
        Mockito.when(categoryRepository.findById(2L))
               .thenReturn(Optional.of(new CategoryEntity(id, "Shoes", new ParentCategoryEntity(1L, "Root Category", null), new ArrayList<>())));
        CategoryDTO categoryDTO = categoryService.getCategory(id);
        Assert.assertNotNull(categoryDTO);
        Assert.assertEquals(categoryDTO.getName(), "Shoes");
        Assert.assertNotNull(categoryDTO.getParent());
        Assert.assertNull(categoryDTO.getParent().getSubcategories());
        Assert.assertTrue((long) 1L == (long) categoryDTO.getParent().getId());


    }


    @Test
    public void testCreateRootCategory() throws Exception {

        Mockito.when(categoryRepository.save(Mockito.any(CategoryEntity.class)))
               .thenReturn(new CategoryEntity(1L, "Test category", null, null));

        CategoryDTO createdCategory = categoryService.create(null, "Test category");
        Assert.assertNotNull(createdCategory);
        Assert.assertEquals("Test category", createdCategory.getName());
        Assert.assertNull(createdCategory.getParent());
        Assert.assertTrue(1L == createdCategory.getId().longValue());


    }

    @Test
    public void testCreateChildCategory() throws Exception {
        ParentCategoryEntity parentCartegory = new ParentCategoryEntity(1L, "Parent", null);
        Mockito.when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(new CategoryEntity(1L, "Parent", null, new ArrayList<>())));
        Mockito.when(categoryRepository.save(Mockito.any(CategoryEntity.class)))
                .thenReturn(new CategoryEntity(2L, "Test category", parentCartegory, null));

        CategoryDTO createdCategory = categoryService.create(1L, "Test category");
        Assert.assertNotNull(createdCategory);
        Assert.assertEquals("Test category", createdCategory.getName());
        Assert.assertNotNull(createdCategory.getParent());
        Assert.assertTrue(2L == createdCategory.getId().longValue());


    }

    @Test
    public void testUpdateCategory() throws Exception {

        Mockito.when(categoryRepository.save(Mockito.any(CategoryEntity.class)))
                .thenReturn(new CategoryEntity(2L, "Test update category", null, null));
        Mockito.when(categoryRepository.findById(2L))
               .thenReturn(Optional.of(new CategoryEntity(2L, "Test update category", null, null)));

        CategoryDTO createdCategory = categoryService.update(2L, "Test update category");
        Assert.assertNotNull(createdCategory);
        Assert.assertEquals("Test update category", createdCategory.getName());
        Assert.assertTrue(2L == createdCategory.getId().longValue());

    }


    @Test
    public void testDeleteLeafCategory() throws Exception {
    }


}
