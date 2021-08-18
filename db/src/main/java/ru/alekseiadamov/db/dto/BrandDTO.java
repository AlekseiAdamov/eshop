package ru.alekseiadamov.db.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class BrandDTO {
    private Long id;

    @NotBlank
    private String name;

    public BrandDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
