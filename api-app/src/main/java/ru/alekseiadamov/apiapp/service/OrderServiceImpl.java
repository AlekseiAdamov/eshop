package ru.alekseiadamov.apiapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alekseiadamov.adminapp.controller.NotFoundException;
import ru.alekseiadamov.db.dao.OrderRepository;
import ru.alekseiadamov.db.dao.ProductRepository;
import ru.alekseiadamov.db.dao.UserRepository;
import ru.alekseiadamov.db.entity.Order;
import ru.alekseiadamov.db.entity.OrderLineItem;
import ru.alekseiadamov.db.entity.Product;
import ru.alekseiadamov.db.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("apiOrderServiceImpl")
public class OrderServiceImpl {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            CartService cartService,
                            UserRepository userRepository,
                            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public List<Order> findOrdersByUsername(String username) {
        return orderRepository.findAllByUsername(username);
    }

    @Transactional
    public void createOrder(String username) {
        if (cartService.getLineItems().isEmpty()) {
            log.info("Can't create order for empty Cart");
            return;
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(String.format("User with name %s not found", username)));

        Order order = orderRepository.save(new Order(
                null,
                LocalDateTime.now(),
                Order.OrderStatus.CREATED,
                user
        ));

        List<OrderLineItem> orderLineItems = cartService.getLineItems()
                .stream()
                .map(li -> new OrderLineItem(
                        null,
                        order,
                        findProductById(li.getProductId()),
                        li.getProductDto().getPrice(),
                        li.getQuantity(),
                        li.getColor(),
                        li.getMaterial()
                ))
                .collect(Collectors.toList());
        order.setOrderLineItems(orderLineItems);
        orderRepository.save(order);
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Product with id %d not found.", id)));
    }
}
