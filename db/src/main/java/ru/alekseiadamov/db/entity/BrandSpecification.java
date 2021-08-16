package ru.alekseiadamov.db.entity;

import org.springframework.data.jpa.domain.Specification;

public final class BrandSpecification {

    private BrandSpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<Brand> brandName(String prefix) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + prefix + "%");
    }
}
