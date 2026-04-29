package com.betacom.pr.dto.inputs;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ImageReq {
    private Integer id;        
    private String link;
    private Integer productId;  
}