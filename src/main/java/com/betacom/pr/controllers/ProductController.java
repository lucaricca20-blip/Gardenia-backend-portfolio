package com.betacom.pr.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.pr.dto.inputs.ProductReq;
import com.betacom.pr.response.Resp;
import com.betacom.pr.services.interfaces.IProductServices;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("rest/product") 
public class ProductController {

	private final IProductServices productS;
	
	@PostMapping("/create")
	public ResponseEntity<Object> create(@RequestBody(required = true) ProductReq req){
		Map<String, Object> r = new HashMap<>(); 
		HttpStatus status = HttpStatus.OK;
		try {
			Integer newId = productS.create(req);
			r.put("msg", "Prodotto creato con successo");
			r.put("id", newId);
		} catch (Exception e) {
			r.put("msg", e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);		
	}
	
	@PutMapping("/update")
	public ResponseEntity<Resp> update(@RequestBody(required = true) ProductReq req){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			productS.update(req);
			r.setMsg("Prodotto aggiornato con successo");
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);		
	}

	@PutMapping("/softDelete")
	public ResponseEntity<Resp> softDelete(@RequestBody(required = true) ProductReq req){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			productS.softDelete(req);
			r.setMsg("Prodotto aggiornato con successo");
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);		
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Resp> delete(@PathVariable(required = true) Integer id){ 
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			productS.delete(id);
			r.setMsg("Prodotto eliminato con successo");
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);		
	}

	@GetMapping("/list")
	public ResponseEntity<Object> list(){
		Object r;
		HttpStatus status = HttpStatus.OK;
		try {
			r = productS.list(); 
		} catch (Exception e) {
			r = e.getMessage();
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}
	
	@GetMapping("/findById")
	public ResponseEntity<Object> findById(@RequestParam(required = true) Integer id){ 
		Object r;
		HttpStatus status = HttpStatus.OK;
		try {
			r = productS.getById(id);
		} catch (Exception e) {
			r = e.getMessage();
			status = HttpStatus.BAD_REQUEST; 
		}
		return ResponseEntity.status(status).body(r);
	}
	
	@GetMapping("/findBySubcategory")
	public ResponseEntity<Object> findBySubcategory(@RequestParam(required = true) Integer subcategoryId) {
		Object r;
		HttpStatus status = HttpStatus.OK;
		try {
			r = productS.getBySubcategoryId(subcategoryId);
		} catch (Exception e) {
			r = e.getMessage();
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}
}