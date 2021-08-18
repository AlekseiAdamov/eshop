package ru.alekseiadamov.adminapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.alekseiadamov.db.dao.ProductRepository;
import ru.alekseiadamov.db.dto.ProductDTO;
import ru.alekseiadamov.db.dto.ProductListParamsDTO;
import ru.alekseiadamov.db.entity.Product;
import ru.alekseiadamov.db.entity.ProductSpecification;
import ru.alekseiadamov.db.util.PageParametersProcessor;

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
                        product.getCategory(),
                        product.getBrand()))
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
        if (params.getCategory() != null) {
            specification = specification.and(ProductSpecification.brand(params.getBrand()));
        }

        return repository.findAll(specification, PageParametersProcessor.getPageRequest(params))
                .map(product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getCategory(),
                        product.getBrand()));
    }

    @Override
    public Optional<ProductDTO> findById(Long id) {
        return repository.findById(id)
                .map(product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getCategory(),
                        product.getBrand()));
    }

    @Override
    public ProductDTO getById(Long id) {
        final Product product = repository.getById(id);
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory(),
                product.getBrand());
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
                product.getCategory(),
                product.getBrand());
        repository.save(persistentProduct);
    }

    @Override
    public Optional<ProductDTO> findByName(String name) {
        return repository.findByName(name)
                .map(product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getCategory(),
                        product.getBrand()));
    }
}
