package ru.alekseiadamov.apiapp.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AllCartDTO {
    private List<LineItem> lineItems;
    private BigDecimal subtotal;
}
