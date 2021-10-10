package ru.alekseiadamov.apiapp.service;

import org.springframework.stereotype.Service;
import ru.alekseiadamov.apiapp.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    List<OrderDTO> findOrdersByUsername(String username);

    void createOrder(String username);
}
