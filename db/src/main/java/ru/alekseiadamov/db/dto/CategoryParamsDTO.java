package ru.alekseiadamov.db.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryParamsDTO {
    private String name;
    private Integer page;
    private Integer size;
    private String sortBy;
    private String sortOrder;
}
