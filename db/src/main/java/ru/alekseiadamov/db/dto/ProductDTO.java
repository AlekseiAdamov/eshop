package ru.alekseiadamov.db.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.alekseiadamov.db.entity.Brand;
import ru.alekseiadamov.db.entity.Category;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @DecimalMin(value = "0.01")
    private Double price;

    @NotNull
    private Category category;

    @NotNull
    private Brand brand;

    public ProductDTO(Long id, String name, Double price, Category category, Brand brand) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.brand = brand;
    }
}
