package com.practise.luteat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menu_orders")
public class MenuOrders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuOrderId;

    @NotEmpty(message = "name cannot be empty")
    @Column(length=1200)
    private String name;

    @Column(name = "price", nullable = false)
    @Range(max = 10000, message = "price cannot be greater than 10000")
    private Double price;

}
