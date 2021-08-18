package ru.alekseiadamov.db.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductListParamsDTO extends PageParamsDTO {
    private Double minPrice;
    private Double maxPrice;
    private String category;
    private String brand;
}
