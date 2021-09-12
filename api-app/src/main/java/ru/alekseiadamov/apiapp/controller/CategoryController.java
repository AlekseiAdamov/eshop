package ru.alekseiadamov.apiapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alekseiadamov.apiapp.dto.CategoryDTO;
import ru.alekseiadamov.apiapp.service.CategoryService;

import java.util.List;

@RestController("apiCategoryController")
@RequestMapping("/v1/category")
public class CategoryController {

    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<CategoryDTO> findAll() {
        return service.findAll();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public CategoryDTO findById(@PathVariable Long id) {
        return service.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id %d not found", id)));
    }
}
