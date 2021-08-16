package ru.alekseiadamov.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.alekseiadamov.db.dto.ProductDTO;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128, nullable = false)
    private String name;

    @Column(precision = 2)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;

    public Product(Long id, String name, Double price, Category category, Brand brand) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", brand=" + brand +
                '}';
    }
}
