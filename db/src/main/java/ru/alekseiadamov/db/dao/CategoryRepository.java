package ru.alekseiadamov.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alekseiadamov.db.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
