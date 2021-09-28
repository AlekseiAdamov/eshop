package ru.alekseiadamov.apiapp.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
        LineItem lineItem = new LineItem(productDto, color, material);
        lineItems.put(lineItem, lineItems.getOrDefault(lineItem, 0) + quantity);
    }

    @Override
    public void removeProductQuantity(ProductDTO productDto, String color, String material, int quantity) {
        // TODO
    }

    @Override
    public void removeProduct(ProductDTO productDto, String color, String material) {
        // TODO
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
}
