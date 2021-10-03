package ru.alekseiadamov.db.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import ru.alekseiadamov.db.entity.Brand;
import ru.alekseiadamov.db.entity.Category;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal price;

    @NotNull
    private Category category;

    @NotNull
    private Brand brand;

    private List<Long> pictures;

    private MultipartFile[] newPictures;

    public ProductDTO(Long id, String name, BigDecimal price, Category category, Brand brand) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.brand = brand;
    }
}
