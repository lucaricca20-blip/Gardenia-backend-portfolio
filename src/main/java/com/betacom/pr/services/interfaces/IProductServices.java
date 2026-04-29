package com.betacom.pr.services.interfaces;

import java.util.List;

import com.betacom.pr.dto.inputs.ProductReq;
import com.betacom.pr.dto.outputs.ProductDTO;

public interface IProductServices {
	
	Integer create(ProductReq req) throws Exception;
	void update(ProductReq req) throws Exception;
	void delete(Integer id) throws Exception;
	void softDelete(ProductReq req) throws Exception;
	 
	List<ProductDTO> list();
	ProductDTO getById(Integer id) throws Exception;
	List<ProductDTO> getBySubcategoryId(Integer subcategoryId) throws Exception;
}
