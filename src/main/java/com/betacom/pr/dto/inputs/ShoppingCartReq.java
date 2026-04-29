package com.betacom.pr.dto.inputs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShoppingCartReq {

    private Integer id;
    private String userName;
    private Integer idProduct;
    private Integer idOrder;
    private Integer amount;
    private Double price;
}