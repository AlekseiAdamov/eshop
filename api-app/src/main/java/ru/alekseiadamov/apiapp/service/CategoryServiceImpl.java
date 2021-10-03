package ru.alekseiadamov.apiapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.alekseiadamov.apiapp.dto.CategoryDTO;
import ru.alekseiadamov.apiapp.dto.CategoryParamsDTO;
import ru.alekseiadamov.apiapp.util.PageParametersProcessor;
import ru.alekseiadamov.db.dao.CategoryRepository;
import ru.alekseiadamov.db.entity.Category;
import ru.alekseiadamov.db.entity.CategorySpecification;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("apiCategoryServiceImpl")
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CategoryDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(category -> new CategoryDTO(
                        category.getId(),
                        category.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CategoryDTO> findById(Long id) {
        return repository.findById(id)
                .map(category -> new CategoryDTO(
                        category.getId(),
                        category.getName()));
    }

    @Override
    public CategoryDTO getById(Long id) {
        final Category category = repository.getById(id);
        return new CategoryDTO(category.getId(), category.getName());
    }

    @Override
    public Optional<CategoryDTO> findByName(String name) {
        return repository.findByName(name)
                .map(category -> new CategoryDTO(
                        category.getId(),
                        category.getName()));
    }

    @Override
    public Page<CategoryDTO> findWithFilter(CategoryParamsDTO params) {
        Specification<Category> specification = Specification.where(null);
        if (params.getName() != null && !params.getName().isBlank()) {
            specification = specification.and(CategorySpecification.categoryName(params.getName()));
        }

        return repository.findAll(specification, PageParametersProcessor.getPageRequest(params))
                .map(category -> new CategoryDTO(
                        category.getId(),
                        category.getName()));
    }
}
