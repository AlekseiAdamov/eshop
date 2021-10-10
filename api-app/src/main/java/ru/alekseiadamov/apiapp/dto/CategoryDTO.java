package ru.alekseiadamov.apiapp.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class CategoryDTO {
    private Long id;
    private String name;
}
