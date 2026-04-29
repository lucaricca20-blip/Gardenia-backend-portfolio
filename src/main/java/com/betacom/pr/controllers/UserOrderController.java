package com.betacom.pr.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.pr.dto.inputs.UserOrderReq;
import com.betacom.pr.response.Resp;
import com.betacom.pr.services.interfaces.IMessaggioServices;
import com.betacom.pr.services.interfaces.IUserOrderServices;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("rest/order")
@CrossOrigin(origins = "http://localhost:4200")
public class UserOrderController {

    private final IUserOrderServices orderS;
    private final IMessaggioServices msgS;

    @PostMapping("/create")
    public ResponseEntity<Resp> create(@RequestBody UserOrderReq req) {
        Resp r = new Resp();
        HttpStatus status = HttpStatus.OK;
        try {
            orderS.create(req);
            r.setMsg("Ordine creato con successo");
        } catch (Exception e) {
            r.setMsg(e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(r);
    }
    
	@PutMapping("/update")
	public ResponseEntity<Resp> update(@RequestBody UserOrderReq req){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			orderS.update(req);
			r.setMsg(msgS.get("rest_updated"));
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);		
	}

    @GetMapping("/list")
    public ResponseEntity<Object> list() {
        Object r;
        HttpStatus status = HttpStatus.OK;
        try {
            r = orderS.getAll();
        } catch (Exception e) {
            r = e.getMessage();
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(r);
    }

    @GetMapping("/getById")
    public ResponseEntity<Object> getById(@RequestParam Integer id) {
        Object r;
        HttpStatus status = HttpStatus.OK;
        try {
            r = orderS.getById(id);
        } catch (Exception e) {
            r = e.getMessage();
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(r);
    }

    @GetMapping("/listByUser")
    public ResponseEntity<Object> listByUser(@RequestParam String userName) {
        Object r;
        HttpStatus status = HttpStatus.OK;
        try {
            r = orderS.getByUserId(userName);
        } catch (Exception e) {
            r = e.getMessage();
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(r);
    }
}
