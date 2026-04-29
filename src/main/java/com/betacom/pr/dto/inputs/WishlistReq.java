package com.betacom.pr.dto.inputs;

import lombok.Data;

@Data
public class WishlistReq {
    private String userName;
    private Integer productId;
}