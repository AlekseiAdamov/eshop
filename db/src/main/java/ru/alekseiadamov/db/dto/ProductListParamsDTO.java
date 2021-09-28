package ru.alekseiadamov.db.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductListParamsDTO extends PageParamsDTO {
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer category;
    private String brand;
}
