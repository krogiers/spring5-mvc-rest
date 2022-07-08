package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.domain.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryMapperTest {

    public static final String JOE = "Joe";
    public static final long ID = 3L;
    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    public void setUp()  {

    }

    @Test
    public void categoryToCategoryDTO() {
        // Given
        Category category = new Category();
        category.setName(JOE);
        category.setId(ID);

        // When
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        // Then
        assertEquals(Long.valueOf(ID), categoryDTO.getId());
        assertEquals(JOE, categoryDTO.getName());
    }
}