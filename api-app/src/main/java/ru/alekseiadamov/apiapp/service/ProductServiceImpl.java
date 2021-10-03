package ru.alekseiadamov.apiapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.alekseiadamov.apiapp.dto.ProductDTO;
import ru.alekseiadamov.apiapp.dto.ProductListParamsDTO;
import ru.alekseiadamov.apiapp.util.PageParametersProcessor;
import ru.alekseiadamov.db.dao.ProductRepository;
import ru.alekseiadamov.db.entity.Picture;
import ru.alekseiadamov.db.entity.Product;
import ru.alekseiadamov.db.entity.ProductSpecification;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("apiProductServiceImpl")
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(this::getProductDTO)
                .collect(Collectors.toList());
    }

    private ProductDTO getProductDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory(),
                product.getBrand(),
                product.getPictures()
                        .stream()
                        .map(Picture::getId)
                        .collect(Collectors.toList()));
    }

    @Override
    public Page<ProductDTO> findWithFilter(ProductListParamsDTO params) {
        Specification<Product> specification = getProductSpecification(params);
        return productRepository.findAll(specification, PageParametersProcessor.getPageRequest(params))
                .map(this::getProductDTO);
    }

    private Specification<Product> getProductSpecification(ProductListParamsDTO params) {
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
        if (params.getCategory() != null && params.getCategory() != 0) {
            specification = specification.and(ProductSpecification.category(params.getCategory()));
        }
        if (params.getCategory() != null) {
            specification = specification.and(ProductSpecification.brand(params.getBrand()));
        }
        return specification;
    }

    @Override
    public Optional<ProductDTO> findById(Long id) {
        return productRepository.findById(id)
                .map(this::getProductDTO);
    }

    @Override
    public ProductDTO getById(Long id) {
        final Product product = productRepository.getById(id);
        return getProductDTO(product);
    }

    @Override
    public Optional<ProductDTO> findByName(String name) {
        return productRepository.findByName(name)
                .map(this::getProductDTO);
    }
}
