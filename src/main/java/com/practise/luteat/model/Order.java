package com.practise.luteat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    @Column(name = "created_date")
    private Instant createdDate;

    @NotNull
    @Size(min=3)
    @Column(name = "total_price")
    private Double totalPrice;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Size(min = 1, message = "Must contain at least one Menu")
//    @JoinTable(name = "order_menuorder", joinColumns =
//    @JoinColumn(name = "order_id", referencedColumnName = "id"),
//            inverseJoinColumns =
//            @JoinColumn(name = "menuorder_id", referencedColumnName = "id"))
    private List<MenuOrder> orders;
}
