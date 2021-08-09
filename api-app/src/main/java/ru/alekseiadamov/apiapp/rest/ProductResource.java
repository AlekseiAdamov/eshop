package ru.alekseiadamov.apiapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.alekseiadamov.adminapp.controller.NotFoundException;
import ru.alekseiadamov.adminapp.service.ProductService;
import ru.alekseiadamov.db.dto.ProductDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductResource {

    private final ProductService service;

    @Autowired
    public ProductResource(ProductService service) {
        this.service = service;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<ProductDTO> findAll() {
        return service.findAll();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ProductDTO findById(@PathVariable Long id) {
        return service.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Product with id %d not found", id)));
    }

    @PostMapping(produces = "application/json")
    public ProductDTO create(@RequestBody ProductDTO product) {
        if (product.getId() != null) {
            throw new BadRequestException("Product id should be null!");
        }
        service.save(product);
        return product;
    }

    @PutMapping(produces = "application/json")
    public void update(@RequestBody ProductDTO product) {
        if (product.getId() == null) {
            throw new BadRequestException("Product id should not be null!");
        }
        service.save(product);
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
