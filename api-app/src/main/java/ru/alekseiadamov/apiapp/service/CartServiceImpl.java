package ru.alekseiadamov.apiapp.service;

import com.fasterxml.jackson.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import ru.alekseiadamov.apiapp.dto.LineItem;
import ru.alekseiadamov.apiapp.dto.ProductDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartServiceImpl implements CartService {

    private final Map<LineItem, Integer> lineItems;

    public CartServiceImpl() {
        this.lineItems = new HashMap<>();
    }

    @JsonCreator
    public CartServiceImpl(@JsonProperty("lineItems") List<LineItem> lineItems) {
        this.lineItems = lineItems.stream().collect(Collectors.toMap(li -> li, LineItem::getQuantity));
    }

    @Override
    public void addProductQuantity(ProductDTO productDto, String color, String material, int quantity) {
        // Request may be not only from frontend.
        if (productDto == null) {
            return;
        }
        LineItem lineItem = new LineItem(productDto, color, material);
        lineItems.put(lineItem, lineItems.getOrDefault(lineItem, 0) + quantity);
    }

    @Override
    public void removeProductQuantity(ProductDTO productDto, String color, String material, int quantity) {
        // Request may be not only from frontend.
        if (productDto == null) {
            return;
        }
        LineItem lineItem = new LineItem(productDto, color, material);
        int cartQuantity = lineItems.get(lineItem);
        if (quantity >= cartQuantity) {
            lineItems.remove(lineItem);
        } else {
            lineItems.put(lineItem, cartQuantity - quantity);
        }
    }

    @Override
    public void removeProduct(ProductDTO productDto, String color, String material) {
        // Request may be not only from frontend.
        if (productDto == null) {
            return;
        }
        LineItem lineItem = new LineItem(productDto, color, material);
        lineItems.remove(lineItem);
    }

    @Override
    public List<LineItem> getLineItems() {
        lineItems.forEach(LineItem::setQuantity);
        return new ArrayList<>(lineItems.keySet());
    }

    @JsonIgnore
    @Override
    public BigDecimal getSubTotal() {
        lineItems.forEach(LineItem::setQuantity);
        return lineItems.keySet()
                .stream()
                .map(LineItem::getItemTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public void clearCart() {
        lineItems.clear();
    }
}
