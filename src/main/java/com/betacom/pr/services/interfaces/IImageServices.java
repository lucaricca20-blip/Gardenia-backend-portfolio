package com.betacom.pr.services.interfaces;

import java.util.List;

import com.betacom.pr.dto.inputs.ImageReq;   
import com.betacom.pr.dto.outputs.ImageDTO; 

public interface IImageServices {
	
	void create(ImageReq req) throws Exception;  
	void update(ImageReq req) throws Exception;  
	void delete(Integer id) throws Exception;
	
	List<ImageDTO> list();
	ImageDTO getById(Integer id) throws Exception;
	List<ImageDTO> getByProductId(Integer productId) throws Exception;
}
