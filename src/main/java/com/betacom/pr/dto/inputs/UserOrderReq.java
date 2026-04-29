package com.betacom.pr.dto.inputs;

import java.util.List;

import com.betacom.pr.dto.outputs.ShoppingCartDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserOrderReq {
	
	private Integer id;
    private String wharehouse;
    private Boolean isPaid;   
    private String userId;
    private Integer addressId;
    private String status;
    private String date;
    private Double totalPrice;
    
    private List<ShoppingCartDTO> items;
}