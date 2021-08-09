package ru.alekseiadamov.db.entity;

import org.springframework.data.jpa.domain.Specification;

public final class CategorySpecification {
    public static Specification<Category> categoryName(String prefix) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + prefix + "%");
    }
}
