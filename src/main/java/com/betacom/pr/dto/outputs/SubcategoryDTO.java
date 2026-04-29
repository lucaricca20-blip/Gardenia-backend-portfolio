package com.betacom.pr.dto.outputs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SubcategoryDTO {
	 private Integer id;
	 private String subcategoryName;
	 private Integer categoryId;
}
