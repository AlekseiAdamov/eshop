package ru.alekseiadamov.db.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RoleDTO {

    private Long id;

    @NotBlank
    private String name;
}
