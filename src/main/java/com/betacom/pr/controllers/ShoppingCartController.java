package com.betacom.pr.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.pr.dto.inputs.ShoppingCartReq;
import com.betacom.pr.dto.outputs.ShoppingCartDTO;
import com.betacom.pr.response.Resp;
import com.betacom.pr.services.interfaces.IMessaggioServices;
import com.betacom.pr.services.interfaces.IShoppingCartServices;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("rest/shoppingCart")
@CrossOrigin(origins = "http://localhost:4200")

public class ShoppingCartController {
	
	private final IShoppingCartServices ssS;
	private final IMessaggioServices msgS;
	
	@PostMapping("/create")
	public ResponseEntity<Resp> create(@RequestBody(required = true)  ShoppingCartReq req){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			ssS.create(req);
			r.setMsg(msgS.get("rest_created"));
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);		
	}
	
	@PutMapping("/update")
	public ResponseEntity<Resp> update(@RequestBody(required = true)  ShoppingCartReq req){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			ssS.update(req);
			r.setMsg(msgS.get("rest_updated"));
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Resp> delete(@PathVariable(required = true)  Integer id){ 
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			ssS.delete(id);
			r.setMsg(msgS.get("rest_deleted"));
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);		
	}
	

	@GetMapping("/getAll")
	public ResponseEntity<List<ShoppingCartDTO>> getAll() {
	    try {
	        List<ShoppingCartDTO> lista = ssS.getAll();
	        return ResponseEntity.ok(lista);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	@GetMapping("/getByOrder/{orderId}")
	public ResponseEntity<Object> getByOrder(@PathVariable Integer orderId) {
		Object r;
		HttpStatus status = HttpStatus.OK;
		try {
			r = ssS.getAllByUserOrder(orderId);
		} catch (Exception e) {
			r = e.getMessage();
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}

	@GetMapping("/activeCart/{userName}")
	public ResponseEntity<Object> getActiveCart(@PathVariable String userName) {
		Object r;
		HttpStatus status = HttpStatus.OK;
		try {
			r = ssS.getActiveCartByUser(userName);
		} catch (Exception e) {
			r = e.getMessage();
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}

}
