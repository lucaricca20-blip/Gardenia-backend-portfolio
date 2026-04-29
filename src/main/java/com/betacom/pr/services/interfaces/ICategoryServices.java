package com.betacom.pr.services.interfaces;

import java.util.List;

import com.betacom.pr.dto.inputs.CategoryReq;
import com.betacom.pr.dto.outputs.CategoryDTO;

public interface ICategoryServices {
	
	void create(CategoryReq req) throws Exception;
	void update(CategoryReq req) throws Exception;
	void delete(Integer id) throws Exception;
	
	List<CategoryDTO> list();
	CategoryDTO getById(Integer id) throws Exception;
}