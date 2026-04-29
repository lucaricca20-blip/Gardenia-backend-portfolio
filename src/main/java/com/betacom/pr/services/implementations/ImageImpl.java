package com.betacom.pr.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.pr.dto.inputs.ImageReq;
import com.betacom.pr.dto.outputs.ImageDTO;
import com.betacom.pr.models.Image;
import com.betacom.pr.models.Product;
import com.betacom.pr.repositories.IImageRepository;
import com.betacom.pr.repositories.IProductRepository;
import com.betacom.pr.services.interfaces.IImageServices;
import com.betacom.pr.Utilities.Mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageImpl implements IImageServices {

	private final IImageRepository imageR;
	private final IProductRepository productR;

	
	@Override
	public void create(ImageReq req) throws Exception {
	   
	    Product product = productR.findById(req.getProductId())
	            .orElseThrow(() -> new Exception("Prodotto non trovato con ID: " + req.getProductId()));
	       
	    Image img = new Image();
	    img.setLink(req.getLink());
	    img.setProduct(product);
	       
	    imageR.save(img); 
	}

	@Override
	public void update(ImageReq req) throws Exception {
		log.debug("update image: {}", req);
		
		Image img = imageR.findById(req.getId())
				.orElseThrow(() -> new Exception("Immagine non trovata"));
		
		if (req.getLink() != null) {
			img.setLink(req.getLink());
		}
		
		
		if (req.getProductId() != null) {
			Product p = productR.findById(req.getProductId())
					.orElseThrow(() -> new Exception("Prodotto non trovato"));
			img.setProduct(p);
		}
		
		imageR.save(img);
	}

	@Override
	public void delete(Integer id) throws Exception {
		log.debug("delete image id: {}", id);
		Image img = imageR.findById(id)
				.orElseThrow(() -> new Exception("Immagine non trovata"));

		imageR.delete(img);
	}

	@Override
	public List<ImageDTO> list() {
		log.debug("list images");
		List<Image> lI = imageR.findAll();
		return Mapper.buildImageDTO(lI); 
	}

	@Override
	public ImageDTO getById(Integer id) throws Exception {
		log.debug("getById image: {}", id);
		Image img = imageR.findById(id)
				.orElseThrow(() -> new Exception("Immagine non trovata"));
		
		return Mapper.buildImageDTO(img);
	}

	@Override
	public List<ImageDTO> getByProductId(Integer productId) throws Exception {
		log.debug("getByProductId: {}", productId);
		List<Image> images = imageR.findAllByProduct_Id(productId);
		return Mapper.buildImageDTO(images);
	}
}
