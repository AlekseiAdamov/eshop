package ru.alekseiadamov.apiapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductListParamsDTO extends PageParamsDTO {
    private Double minPrice;
    private Double maxPrice;
    private Integer category;
    private String brand;
}
