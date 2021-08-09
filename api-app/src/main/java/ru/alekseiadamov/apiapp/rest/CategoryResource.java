package ru.alekseiadamov.apiapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.alekseiadamov.adminapp.controller.NotFoundException;
import ru.alekseiadamov.adminapp.service.CategoryService;
import ru.alekseiadamov.db.dto.CategoryDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryResource {

    private final CategoryService service;

    @Autowired
    public CategoryResource(CategoryService service) {
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

    @Secured("ROLE_ADMIN")
    @PostMapping(produces = "application/json")
    public CategoryDTO create(@RequestBody CategoryDTO category) {
        if (category.getId() != null) {
            throw new BadRequestException("Category id should be null!");
        }
        service.save(category);
        return category;
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(produces = "application/json")
    public void update(@RequestBody CategoryDTO category) {
        if (category.getId() == null) {
            throw new BadRequestException("Category id should not be null!");
        }
        service.save(category);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(path = "/{id}", produces = "application/json")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
