package com.practise.luteat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
public class OrderResponse {

        private String menuName;
        private Double menuPrice;
        private Date dateCreated;
        private BigInteger number_of_times_ordered;

}
