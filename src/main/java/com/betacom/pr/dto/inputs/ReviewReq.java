package com.betacom.pr.dto.inputs;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ReviewReq {
    private Long productId;
    private int rating;
    private String comment;
    private String userName;
}