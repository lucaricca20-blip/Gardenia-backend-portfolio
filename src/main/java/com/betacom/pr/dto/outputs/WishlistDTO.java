package com.betacom.pr.dto.outputs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WishlistDTO {
    private Integer id;
    private Integer productId;
    private String productName;
    private Double price;
    private String immagine;
    private Integer productStock;
    
    
}