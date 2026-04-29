package com.betacom.pr.dto.inputs;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SubcategoryReq {
	private Integer id;
	 private String subcategoryName;
	 private Integer categoryId;
}
