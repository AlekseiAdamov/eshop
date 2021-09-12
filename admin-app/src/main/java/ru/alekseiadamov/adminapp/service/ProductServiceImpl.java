package ru.alekseiadamov.adminapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.alekseiadamov.adminapp.controller.NotFoundException;
import ru.alekseiadamov.db.dao.BrandRepository;
import ru.alekseiadamov.db.dao.CategoryRepository;
import ru.alekseiadamov.db.dao.ProductRepository;
import ru.alekseiadamov.db.dto.ProductDTO;
import ru.alekseiadamov.db.dto.ProductListParamsDTO;
import ru.alekseiadamov.db.entity.*;
import ru.alekseiadamov.db.util.PageParametersProcessor;
import ru.alekseiadamov.pictureservice.service.PictureService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final PictureService pictureService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              BrandRepository brandRepository,
                              PictureService pictureService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.pictureService = pictureService;
        this.brandRepository = brandRepository;
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
                product.getBrand());
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
        if (params.getCategory() != null) {
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
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void save(ProductDTO product) {
        Product persistentProduct = getPersistentProduct(product);
        productRepository.save(persistentProduct);
    }

    private Product getPersistentProduct(ProductDTO product) {
        Product persistentProduct;
        if (product.getId() != null) {
            persistentProduct = productRepository.findById(product.getId())
                    .orElseThrow(() -> new NotFoundException(String.format("Product with id %d not found", product.getId())));
        } else {
            persistentProduct = new Product();
        }
        Category category = categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Brand brand = brandRepository.findById(product.getBrand().getId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        persistentProduct.setId(product.getId());
        persistentProduct.setName(product.getName());
        persistentProduct.setPrice(product.getPrice());
        persistentProduct.setCategory(category);
        persistentProduct.setBrand(brand);

        if (product.getNewPictures() != null) {
            addPictures(product, persistentProduct);
        }
        return persistentProduct;
    }

    private void addPictures(ProductDTO product, Product persistentProduct) {
        final List<Picture> pictures = persistentProduct.getPictures();
        for (MultipartFile newPicture : product.getNewPictures()) {
            Picture picture = null;
            try {
                picture = new Picture(
                        null,
                        newPicture.getOriginalFilename(),
                        newPicture.getContentType(),
                        pictureService.createPicture(newPicture.getBytes()));
                picture.setProduct(persistentProduct);
            } catch (IOException e) {
                log.error("Unable to add picture {}: {}",
                        newPicture.getOriginalFilename(),
                        e.getMessage());
            }
            pictures.add(picture);
        }
    }

    @Override
    public Optional<ProductDTO> findByName(String name) {
        return productRepository.findByName(name)
                .map(this::getProductDTO);
    }
}
