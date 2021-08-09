package ru.alekseiadamov.adminapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.alekseiadamov.db.dao.ProductRepository;
import ru.alekseiadamov.db.dto.ProductDTO;
import ru.alekseiadamov.db.dto.ProductListParamsDTO;
import ru.alekseiadamov.db.entity.Product;
import ru.alekseiadamov.db.entity.ProductSpecification;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Autowired
    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProductDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getCategory()))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductDTO> findWithFilter(ProductListParamsDTO params) {

        Specification<Product> specification = Specification.where(null);
        if (params.getName() != null && !params.getName().isBlank()) {
            specification = specification.and(ProductSpecification.name(params.getName()));
        }
        if (params.getMinPrice() != null) {
            specification = specification.and(ProductSpecification.minPrice(params.getMinPrice()));
        }
        if (params.getMaxPrice() != null) {
            specification = specification.and(ProductSpecification.maxPrice(params.getMaxPrice()));
        }
        if (params.getCategory() != null) {
            specification = specification.and(ProductSpecification.category(params.getCategory()));
        }

        final Sort sortDirection = Optional.ofNullable(params.getSortOrder())
                .orElse("asc")
                .equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(Optional.ofNullable(params.getSortBy()).orElse("id")).ascending()
                : Sort.by(Optional.ofNullable(params.getSortBy()).orElse("id")).descending();

        final PageRequest pageRequest = PageRequest.of(
                Optional.ofNullable(params.getPage()).orElse(1) - 1,
                Optional.ofNullable(params.getSize()).orElse(3),
                sortDirection
        );

        return repository.findAll(specification, pageRequest)
                .map(product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getCategory()));
    }

    @Override
    public Optional<ProductDTO> findById(Long id) {
        return repository.findById(id)
                .map(product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getCategory()));
    }

    @Override
    public ProductDTO getById(Long id) {
        final Product product = repository.getById(id);
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void save(ProductDTO product) {
        Product persistentProduct = new Product(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory());
        repository.save(persistentProduct);
    }

    @Override
    public Optional<ProductDTO> findByName(String name) {
        return repository.findByName(name)
                .map(product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getCategory()));
    }
}
