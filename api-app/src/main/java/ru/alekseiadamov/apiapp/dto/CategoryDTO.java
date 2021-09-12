package ru.alekseiadamov.apiapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CategoryDTO {
    private Long id;
    private String name;
}
