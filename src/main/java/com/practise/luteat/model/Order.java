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
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private Instant createdDate;

    @NotNull
    @Size(min=3)
    private Double totalPrice;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order")
    @Size(min = 1, message = "Must contain at least one Menu")
    @JoinTable(name = "order_menuOrder", joinColumns =
    @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "menuOrder_id", referencedColumnName = "id"))
    private List<MenuOrder> orders;
}
