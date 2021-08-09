package ru.alekseiadamov.adminapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.alekseiadamov.db.dao.CategoryRepository;
import ru.alekseiadamov.db.dto.CategoryDTO;
import ru.alekseiadamov.db.dto.CategoryParamsDTO;
import ru.alekseiadamov.db.entity.Category;
import ru.alekseiadamov.db.entity.CategorySpecification;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void save(CategoryDTO category) {
        Category persistentCategory = new Category(
                category.getId(),
                category.getName());
        repository.save(persistentCategory);
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
                .map(category -> new CategoryDTO(
                        category.getId(),
                        category.getName()));
    }
}
