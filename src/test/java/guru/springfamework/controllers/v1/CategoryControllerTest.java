package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.service.CategoryService;
import guru.springfamework.service.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryControllerTest {
    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void testListCategories() throws Exception {
        // Given
        CategoryDTO catDTO1 = CategoryDTO.builder().id(1L).name("Cate 1").build();
        CategoryDTO catDTO2 = CategoryDTO.builder().id(2L).name("Cate 2").build();
        List<CategoryDTO> categories = Arrays.asList(catDTO1, catDTO2);

        when (categoryService.getAllCategories()).thenReturn(categories);

        // When
        mockMvc.perform(get(CategoryController.BASE_URL + "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(2)));

    }

    @Test
    public void testCategoryByName() throws Exception {
        // Given
        CategoryDTO catDTO1 = CategoryDTO.builder().id(1L).name("Cate_1").build();

        when (categoryService.getCategoryByName(anyString())).thenReturn(catDTO1);

        // When
        mockMvc.perform(get(CategoryController.BASE_URL + "/Cate_1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetByNameNotFound() throws Exception {
        when (categoryService.getCategoryByName(anyString())).thenThrow(ResourceNotFoundException.class);

        // When
        mockMvc.perform(get(CategoryController.BASE_URL + "/Foo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}