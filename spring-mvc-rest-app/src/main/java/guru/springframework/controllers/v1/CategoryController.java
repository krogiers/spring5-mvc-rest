package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.api.v1.model.CategoryListDTO;
import guru.springframework.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
//@Controller
@RestController
@Tag(name = "Category Actions", description = "This is a api to do some Category actions")
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {
    public static final String BASE_URL = "/api/v1/categories";
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoryListDTO getCategoryDTOs() {
        return new CategoryListDTO(categoryService.getAllCategories());
    }

    @GetMapping("{name}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO getCategoryByName(@PathVariable String name) {
        return categoryService.getCategoryByName(name);
    }

    /*
    @GetMapping
    public ResponseEntity<CategoryListDTO> getCategoryDTOs() {
        return new ResponseEntity<CategoryListDTO> (
                new CategoryListDTO(categoryService.getAllCategories()),
                HttpStatus.OK);
    }

    @GetMapping("{name}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name) {
        return new ResponseEntity<CategoryDTO> (categoryService.getCategoryByName(name), HttpStatus.OK);
    }
    */

}
