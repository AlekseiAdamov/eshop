package ru.alekseiadamov.apiapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alekseiadamov.apiapp.dto.BrandDTO;
import ru.alekseiadamov.apiapp.service.BrandService;

import java.util.List;

@RestController("apiBrandController")
@RequestMapping("/v1/brand")
public class BrandController {

    private final BrandService service;

    @Autowired
    public BrandController(BrandService service) {
        this.service = service;
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BrandDTO> findAll() {
        return service.findAll();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BrandDTO findById(@PathVariable Long id) {
        return service.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Brand with id %d not found", id)));
    }
}
