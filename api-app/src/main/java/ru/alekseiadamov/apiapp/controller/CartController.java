package ru.alekseiadamov.apiapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alekseiadamov.apiapp.dto.AddLineItemDTO;
import ru.alekseiadamov.apiapp.dto.AllCartDTO;
import ru.alekseiadamov.apiapp.dto.LineItem;
import ru.alekseiadamov.apiapp.dto.ProductDTO;
import ru.alekseiadamov.apiapp.service.CartService;
import ru.alekseiadamov.apiapp.service.ProductService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    @Autowired
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<LineItem> addToCart(AddLineItemDTO addLineItemDto) {
        log.info("New LineItem: productId = {}, quantity = {}",
                addLineItemDto.getProductId(),
                addLineItemDto.getQuantity());

        final Long id = addLineItemDto.getProductId();
        ProductDTO productDto = productService.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Product with id %d not found", id)));

        cartService.addProductQuantity(
                productDto,
                addLineItemDto.getColor(),
                addLineItemDto.getMaterial(),
                addLineItemDto.getQuantity()
        );
        return cartService.getLineItems();
    }

    @GetMapping(path = "/all")
    public AllCartDTO findAll() {
        return new AllCartDTO(cartService.getLineItems(), cartService.getSubTotal());
    }
}
