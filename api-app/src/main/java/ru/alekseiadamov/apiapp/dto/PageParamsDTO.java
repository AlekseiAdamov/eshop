package ru.alekseiadamov.apiapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PageParamsDTO {
    private String name;
    private Integer page;
    private Integer size;
    private String sortBy;
    private String sortOrder;
}
