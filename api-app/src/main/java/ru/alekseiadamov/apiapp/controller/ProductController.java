package ru.alekseiadamov.apiapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alekseiadamov.apiapp.dto.ProductDTO;
import ru.alekseiadamov.apiapp.dto.ProductListParamsDTO;
import ru.alekseiadamov.apiapp.service.ProductService;

@RestController("apiProductController")
@RequestMapping("/v1/product")
public class ProductController {

    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public Page<ProductDTO> findAll(ProductListParamsDTO params) {
        return service.findWithFilter(params);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ProductDTO findById(@PathVariable Long id) {
        return service.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Product with id %d not found", id)));
    }
}
