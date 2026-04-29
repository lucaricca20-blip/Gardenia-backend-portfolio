package com.betacom.pr.controllers;

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

import com.betacom.pr.dto.inputs.SubcategoryReq;
import com.betacom.pr.response.Resp;
import com.betacom.pr.services.interfaces.ISubcategoryServices;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("rest/subcategory") 
public class SubcategoryController {

	private final ISubcategoryServices subcategoryS;
	
	@PostMapping("/create")
	public ResponseEntity<Resp> create(@RequestBody(required = true) SubcategoryReq req){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			subcategoryS.create(req);
			r.setMsg("Sottocategoria creata con successo");
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);		
	}
	
	@PutMapping("/update")
	public ResponseEntity<Resp> update(@RequestBody(required = true) SubcategoryReq req){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			subcategoryS.update(req);
			r.setMsg("Sottocategoria aggiornata con successo");
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
			subcategoryS.delete(id);
			r.setMsg("Sottocategoria eliminata con successo");
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
			r = subcategoryS.list(); 
		} catch (Exception e) {
			r = e.getMessage();
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}

	@GetMapping("/listByCategory")
	public ResponseEntity<Object> listByCategory(@RequestParam(required = true) Integer id){
		Object r;
		HttpStatus status = HttpStatus.OK;
		try {
			r = subcategoryS.listByCategory_Id(id);
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
			r = subcategoryS.getById(id);
		} catch (Exception e) {
			r = e.getMessage();
			status = HttpStatus.BAD_REQUEST; 
		}
		return ResponseEntity.status(status).body(r);
	}
}