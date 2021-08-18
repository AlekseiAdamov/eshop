package ru.alekseiadamov.adminapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.alekseiadamov.db.dao.BrandRepository;
import ru.alekseiadamov.db.dto.BrandDTO;
import ru.alekseiadamov.db.dto.BrandParamsDTO;
import ru.alekseiadamov.db.entity.Brand;
import ru.alekseiadamov.db.entity.BrandSpecification;
import ru.alekseiadamov.db.util.PageParametersProcessor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository repository;

    @Autowired
    public BrandServiceImpl(BrandRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<BrandDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(brand -> new BrandDTO(
                        brand.getId(),
                        brand.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BrandDTO> findById(Long id) {
        return repository.findById(id)
                .map(brand -> new BrandDTO(
                        brand.getId(),
                        brand.getName()));
    }

    @Override
    public BrandDTO getById(Long id) {
        final Brand brand = repository.getById(id);
        return new BrandDTO(brand.getId(), brand.getName());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void save(BrandDTO brand) {
        Brand persistentBrand = new Brand(
                brand.getId(),
                brand.getName());
        repository.save(persistentBrand);
    }

    @Override
    public Optional<BrandDTO> findByName(String name) {
        return repository.findByName(name)
                .map(brand -> new BrandDTO(
                        brand.getId(),
                        brand.getName()));
    }

    @Override
    public Page<BrandDTO> findWithFilter(BrandParamsDTO params) {
        Specification<Brand> specification = Specification.where(null);
        if (params.getName() != null && !params.getName().isBlank()) {
            specification = specification.and(BrandSpecification.brandName(params.getName()));
        }

        return repository.findAll(specification, PageParametersProcessor.getPageRequest(params))
                .map(brand -> new BrandDTO(
                        brand.getId(),
                        brand.getName()));
    }
}
