package com.betacom.pr.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.betacom.pr.Utilities.Mapper;
import com.betacom.pr.dto.inputs.ProductReq;
import com.betacom.pr.dto.outputs.ProductDTO;
import com.betacom.pr.models.Product;
import com.betacom.pr.models.Subcategory;
import com.betacom.pr.repositories.IProductRepository;
import com.betacom.pr.repositories.ISubcategoryRepository;
import com.betacom.pr.services.interfaces.IProductServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductImpl implements IProductServices {

	private final IProductRepository productR;
	private final ISubcategoryRepository subcategoryR;

	@Override
	public Integer create(ProductReq req) throws Exception { 
		log.debug("create product: {}", req);
		
		Product p = new Product();
		p.setName(req.getName());
		p.setDescription(req.getDescription());
		p.setPrice(req.getPrice());
		p.setStock(req.getStock());
		p.setIsDeleted(false);
		
		if (req.getSubcategoryId() != null) {
			Subcategory s = subcategoryR.findById(req.getSubcategoryId())
					.orElseThrow(() -> new Exception("Sottocategoria non trovata"));
			p.setSubcategory(s);
		} else {
			throw new Exception("La sottocategoria è obbligatoria");
		}
		
		Product savedProduct = productR.save(p);
		return savedProduct.getId();
	}
	@Override
	public void update(ProductReq req) throws Exception {
		log.debug("update product: {}", req);

		Product p = productR.findById(req.getId())
				.orElseThrow(() -> new Exception("Prodotto non trovato"));
		
		if (req.getName() != null) p.setName(req.getName());
		if (req.getDescription() != null) p.setDescription(req.getDescription());
		if (req.getPrice() != null) p.setPrice(req.getPrice());
		if (req.getStock() != null) p.setStock(req.getStock());
		if (req.getIsDeleted() != null) p.setIsDeleted(req.getIsDeleted());
		
		
		if (req.getSubcategoryId() != null) {
			Subcategory subCat = subcategoryR.findById(req.getSubcategoryId())
					.orElseThrow(() -> new Exception("Sottocategoria non trovata"));
			p.setSubcategory(subCat);
		}
		
		productR.save(p);
	}

	@Override
	public void delete(Integer id) throws Exception {
		log.debug("delete product id: {}", id);
		Product p = productR.findById(id)
				.orElseThrow(() -> new Exception("Prodotto non trovato"));

		productR.delete(p);
	}

	@Override
	public List<ProductDTO> list() {
        log.debug("list products");
        List<Product> lP = productR.findAll().stream()
                .filter(p->p.getIsDeleted()!=true)
                .collect(Collectors.toList());
		
		return Mapper.buildProductDTO(lP);
	}

	@Override
	public ProductDTO getById(Integer id) throws Exception {
		log.debug("getById product: {}", id);
		Product p = productR.findById(id)
				.orElseThrow(() -> new Exception("Prodotto non trovato"));
		
		return Mapper.buildProductDTO(p);
	}

	@Override
	public List<ProductDTO> getBySubcategoryId(Integer subcategoryId) throws Exception {
		log.debug("getBySubcategoryId: {}", subcategoryId);

		Subcategory subcategory = subcategoryR.findById(subcategoryId)
				.orElseThrow(() -> new Exception("Sottocategoria non trovata"));

		List<Product> products = productR.findAllBySubcategory_Id(subcategory.getId()).stream()
                .filter(p->p.getIsDeleted()!=true)
                .collect(Collectors.toList());
		return Mapper.buildProductDTO(products);
	}
	@Override
	public void softDelete(ProductReq req) throws Exception {
		log.debug("softDeleted product: {}", req);

		Product p = productR.findById(req.getId())
				.orElseThrow(() -> new Exception("Prodotto non trovato"));
		
		if (req.getIsDeleted() == false) p.setIsDeleted(true);
	
		productR.save(p);
	}
}
