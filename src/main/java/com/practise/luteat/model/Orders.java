package com.practise.luteat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    @Column(name = "created_date")
    private Instant createdDate;

    //@NotNull
    //@Range(max = , message = "price cannot be greater than 10000")
    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @NotEmpty(message = "Must contain at least one Menu")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    //@Size(min = 1, message = "Must contain at least one Menu")
    @JoinTable(name = "orders_menu_order", joinColumns =
    @JoinColumn(name = "order_id", referencedColumnName = "orderId"),
            inverseJoinColumns =
            @JoinColumn(name = "menu_order_id", referencedColumnName = "menuOrderId"))
    private List<MenuOrders> orders;
}
