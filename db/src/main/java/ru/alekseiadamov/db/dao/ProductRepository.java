package ru.alekseiadamov.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.alekseiadamov.db.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findAllByPriceGreaterThan(double minPrice);

    List<Product> findAllByPriceLessThan(double maxPrice);

    List<Product> findAllByPriceBetween(double minPrice, double maxPrice);
}
