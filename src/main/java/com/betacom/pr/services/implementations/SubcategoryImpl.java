package com.betacom.pr.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.pr.dto.inputs.SubcategoryReq;
import com.betacom.pr.dto.outputs.SubcategoryDTO;
import com.betacom.pr.models.Category;
import com.betacom.pr.models.Subcategory;
import com.betacom.pr.repositories.ICategoryRepository;
import com.betacom.pr.repositories.ISubcategoryRepository;
import com.betacom.pr.services.interfaces.ISubcategoryServices;
import com.betacom.pr.Utilities.Mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubcategoryImpl implements ISubcategoryServices {

	private final ISubcategoryRepository subcategoryR;
	private final ICategoryRepository categoryR;

	@Override
	public void create(SubcategoryReq req) throws Exception {
		log.debug("create subcategory: {}", req);
		
		Subcategory s = new Subcategory();
		s.setName(req.getSubcategoryName());
		
		
		if (req.getCategoryId() != null) {
			Category c = categoryR.findById(req.getCategoryId())
					.orElseThrow(() -> new Exception("Categoria padre non trovata"));
			s.setCategory(c);
		} else {
		    throw new Exception("L'ID della categoria padre è obbligatorio");
		}
		
		subcategoryR.save(s);
	}

	@Override
	public void update(SubcategoryReq req) throws Exception {
		log.debug("update subcategory: {}", req);
		
		Subcategory s = subcategoryR.findById(req.getId())
				.orElseThrow(() -> new Exception("Sottocategoria non trovata"));
		
		if (req.getSubcategoryName() != null) {
			s.setName(req.getSubcategoryName());
		}
		
		if (req.getCategoryId() != null) {
			Category c = categoryR.findById(req.getCategoryId())
					.orElseThrow(() -> new Exception("Categoria padre non trovata"));
			s.setCategory(c);
		}
		
		subcategoryR.save(s);
	}

	@Override
	public void delete(Integer id) throws Exception {
		log.debug("delete subcategory id: {}", id);
		Subcategory s = subcategoryR.findById(id)
				.orElseThrow(() -> new Exception("Sottocategoria non trovata"));

		subcategoryR.delete(s);
	}

	@Override
	public List<SubcategoryDTO> list() {
		log.debug("list subcategories");
		List<Subcategory> lS = subcategoryR.findAll();
		return Mapper.buildSubcategoryDTO(lS); 
	}

	@Override
	public List<SubcategoryDTO> listByCategory_Id(Integer categoryId) {
		log.debug("list by category id: {}", categoryId);
		List<Subcategory> lS = subcategoryR.findAllByCategory_Id(categoryId);
		return Mapper.buildSubcategoryDTO(lS);
	}

	@Override
	public SubcategoryDTO getById(Integer id) throws Exception {
		log.debug("getById subcategory: {}", id);
		Subcategory s = subcategoryR.findById(id)
				.orElseThrow(() -> new Exception("Sottocategoria non trovata"));
		
		return Mapper.buildSubcategoryDTO(s);
	}
}