package com.practise.luteat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menu_order ")
public class MenuOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuOrderId;

    @NotEmpty(message = "name cannot be empty")
    @Size(min = 2, max = 10)
    private String name;

    @NotNull
    @Size(min = 3)
    private Double price;

    @NotEmpty
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Order_id", referencedColumnName = "orderId")
    private Order order;
}
