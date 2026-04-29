package com.betacom.pr.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.pr.dto.inputs.CategoryReq;
import com.betacom.pr.dto.outputs.CategoryDTO;
import com.betacom.pr.models.Category;
import com.betacom.pr.repositories.ICategoryRepository;
import com.betacom.pr.services.interfaces.ICategoryServices;
import com.betacom.pr.Utilities.Mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryImpl implements ICategoryServices {

	private final ICategoryRepository categoryR;

	@Override
	public void create(CategoryReq req) throws Exception {
		log.debug("create category: {}", req);
		
		Category c = new Category();
		c.setName(req.getName());
		categoryR.save(c);
	}

	@Override
	public void update(CategoryReq req) throws Exception {
		log.debug("update category: {}", req);
		
		Category c = categoryR.findById(req.getId())
				.orElseThrow(() -> new Exception("Categoria non trovata")); 
		
		if (req.getName() != null) {
			c.setName(req.getName());
		}
		categoryR.save(c);
	}

	@Override
	public void delete(Integer id) throws Exception {
		log.debug("delete category id: {}", id);
		Category c = categoryR.findById(id)
				.orElseThrow(() -> new Exception("Categoria non trovata"));

		categoryR.delete(c);
	}

	@Override
	public List<CategoryDTO> list() {
		log.debug("list categories");
		List<Category> lC = categoryR.findAll();
	
		return Mapper.buildCategoryDTO(lC);
	}

	@Override
	public CategoryDTO getById(Integer id) throws Exception {
		log.debug("getById category: {}", id);
		Category c = categoryR.findById(id)
				.orElseThrow(() -> new Exception("Categoria non trovata"));
		
		return Mapper.buildCategoryDTO(c);
	}
}