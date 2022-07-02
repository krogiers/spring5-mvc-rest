package guru.springfamework.service;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import org.apache.tomcat.util.digester.ArrayStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CategoryServiceImplTest {

    public static final String NAME = "Cat 1";
    public static final long ID = 1L;
    @Mock
    CategoryRepository categoryRepository;

    CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryService = new CategoryServiceImpl(categoryRepository, CategoryMapper.INSTANCE);
    }

    @Test
    void getAllCategories() {
        // Given
        List<Category> categories = new ArrayStack<>();
        categories.add(Category.builder().id(1L).name("Cat 1").build());
        categories.add(Category.builder().id(2L).name("Cat 2").build());

        when(categoryRepository.findAll()).thenReturn(categories);

        // When
        List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();

        // Then
        assertEquals(2, categoryDTOS.size());
    }

    @Test
    void getCategoryByName() {
        // Given
        Category category =  Category.builder().id(ID).name(NAME).build();
        when(categoryRepository.findByName(anyString())).thenReturn(category);

        // When
        CategoryDTO categoryDTOS = categoryService.getCategoryByName(NAME);

        // Then
        assertEquals(ID, categoryDTOS.getId());
        assertEquals(NAME, categoryDTOS.getName());
    }
}