package ru.alekseiadamov.apiapp.service;

import ru.alekseiadamov.apiapp.dto.LineItem;
import ru.alekseiadamov.apiapp.dto.ProductDTO;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {

    void addProductQuantity(ProductDTO productDto, String color, String material, int quantity);

    void removeProductQuantity(ProductDTO productDto, String color, String material, int quantity);

    void removeProduct(ProductDTO productDto, String color, String material);

    List<LineItem> getLineItems();

    BigDecimal getSubTotal();

    void clearCart();
}
