package ru.alekseiadamov.db.dto;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
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
}
