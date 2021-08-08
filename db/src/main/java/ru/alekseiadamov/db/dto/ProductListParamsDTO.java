package ru.alekseiadamov.db.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductListParamsDTO {
    private String productName;
    private Double minPrice;
    private Double maxPrice;
    private Integer page;
    private Integer size;
    private String sortBy;
    private String sortOrder;
}
