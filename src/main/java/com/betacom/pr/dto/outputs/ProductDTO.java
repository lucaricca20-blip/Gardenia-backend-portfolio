package com.betacom.pr.dto.outputs;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class ProductDTO {
	private Integer id;
	private String name;
	private String description;
	private Double price;
	private Integer stock;
	private Boolean isDeleted;
	
    private Integer subcategoryId;
    private String subcategoryName;
    
    private Integer productStock;
    
    private List<ImageDTO> images;
    
}
