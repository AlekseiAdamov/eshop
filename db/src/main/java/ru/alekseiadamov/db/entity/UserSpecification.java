package ru.alekseiadamov.db.entity;

import org.springframework.data.jpa.domain.Specification;

public final class UserSpecification {

    private UserSpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<User> username(String prefix) {
        return (root, query, builder) -> builder.like(root.get("username"), "%" + prefix + "%");
    }
}
