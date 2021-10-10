package ru.alekseiadamov.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "order_date")
    private LocalDateTime orderDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLineItem> orderLineItems;

    public Order(Long id, LocalDateTime orderDate, OrderStatus status, User user) {
        this.id = id;
        this.orderDate = orderDate;
        this.status = status;
        this.user = user;
    }

    public enum OrderStatus {
        CREATED, PROCESSED, IN_DELIVERY, DELIVERED, CLOSED, CANCELED
    }
}
