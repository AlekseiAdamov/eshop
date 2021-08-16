package ru.alekseiadamov.apiapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.alekseiadamov.adminapp.controller.NotFoundException;
import ru.alekseiadamov.adminapp.service.BrandService;
import ru.alekseiadamov.db.dto.BrandDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brand")
public class BrandResource {

    private final BrandService service;

    @Autowired
    public BrandResource(BrandService service) {
        this.service = service;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<BrandDTO> findAll() {
        return service.findAll();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public BrandDTO findById(@PathVariable Long id) {
        return service.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Brand with id %d not found", id)));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(produces = "application/json")
    public BrandDTO create(@RequestBody BrandDTO brand) {
        if (brand.getId() != null) {
            throw new BadRequestException("Brand id should be null!");
        }
        service.save(brand);
        return brand;
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(produces = "application/json")
    public void update(@RequestBody BrandDTO brand) {
        if (brand.getId() == null) {
            throw new BadRequestException("Brand id should not be null!");
        }
        service.save(brand);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(path = "/{id}", produces = "application/json")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
