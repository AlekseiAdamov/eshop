package ru.alekseiadamov.apiapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private LocalDateTime orderDate;
    private String status;
    private String username;
    private List<OrderLineItemDTO> orderLineItems;
}
