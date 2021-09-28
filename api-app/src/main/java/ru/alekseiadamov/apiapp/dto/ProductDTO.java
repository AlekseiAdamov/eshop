package ru.alekseiadamov.apiapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.alekseiadamov.db.entity.Brand;
import ru.alekseiadamov.db.entity.Category;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {

    private Long id;
    private String name;
    private BigDecimal price;
    private Category category;
    private Brand brand;
    private List<Long> pictures;

    public ProductDTO(Long id,
                      String name,
                      BigDecimal price,
                      Category category,
                      Brand brand,
                      List<Long> pictures) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.brand = brand;
        this.pictures = new ArrayList<>(pictures);
    }
}
