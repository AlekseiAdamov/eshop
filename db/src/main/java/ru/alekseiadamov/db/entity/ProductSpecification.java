package ru.alekseiadamov.db.entity;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public final class ProductSpecification {

    private ProductSpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<Product> name(String name) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Product> minPrice(Double price) {
        return (root, query, builder) -> builder.ge(root.get("price"), price);
    }

    public static Specification<Product> maxPrice(Double price) {
        return (root, query, builder) -> builder.le(root.get("price"), price);
    }

    public static Specification<Product> category(Integer categoryId) {
        return (root, query, builder) -> {
            Join<Product, Category> joinCategory = root.join("category");
            return builder.equal(joinCategory.get("id"), categoryId);
        };
    }

    public static Specification<Product> brand(String brand) {
        return (root, query, builder) -> {
            Join<Product, Category> joinCategory = root.join("brand");
            return builder.like(joinCategory.get("name"), "%" + brand + "%");
        };
    }
}
