package com.betacom.pr.services.interfaces;

import java.util.List;

import com.betacom.pr.dto.inputs.SubcategoryReq;
import com.betacom.pr.dto.outputs.SubcategoryDTO;

public interface ISubcategoryServices {
	
	void create(SubcategoryReq req) throws Exception;
	void update(SubcategoryReq req) throws Exception;
	void delete(Integer id) throws Exception;
	
	List<SubcategoryDTO> list();
	List<SubcategoryDTO> listByCategory_Id(Integer categoryId);
	SubcategoryDTO getById(Integer id) throws Exception;
}