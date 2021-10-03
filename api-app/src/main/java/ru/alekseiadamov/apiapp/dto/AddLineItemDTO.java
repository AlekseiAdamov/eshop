package ru.alekseiadamov.apiapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AddLineItemDTO {
    private Long productId;
    private Integer quantity;
    private String color;
    private String material;
}
