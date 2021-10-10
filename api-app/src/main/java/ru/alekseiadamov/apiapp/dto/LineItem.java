package ru.alekseiadamov.apiapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LineItem {

    private Long productId;

    @EqualsAndHashCode.Exclude
    private ProductDTO productDto;

    @EqualsAndHashCode.Exclude
    private Integer quantity;

    private String color;

    private String material;

    public LineItem(ProductDTO productDto, String color, String material) {
        this.productId = productDto.getId();
        this.productDto = productDto;
        this.color = color;
        this.material = material;
    }

    public BigDecimal getItemTotal() {
        return productDto.getPrice().multiply(new BigDecimal(quantity));
    }
}
