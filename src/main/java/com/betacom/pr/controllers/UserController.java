package com.betacom.pr.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.betacom.pr.dto.inputs.ChangePwdReq;
import com.betacom.pr.dto.inputs.LoginReq;
import com.betacom.pr.dto.inputs.UserReq;
import com.betacom.pr.response.Resp;
import com.betacom.pr.services.interfaces.IMessaggioServices;
import com.betacom.pr.services.interfaces.IUserServices;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@RestController
@RequestMapping("rest/user")
public class UserController {
	
	private final IUserServices usS;
	private final IMessaggioServices msgS;
	
	@PostMapping("/register")
	public ResponseEntity<Resp> create(@RequestBody(required = true)  UserReq req){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			usS.create(req);
			r.setMsg(msgS.get("rest_created"));
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);		
	}
	
	@PutMapping("/update")
	public ResponseEntity<Resp> update(@RequestBody(required = true)  UserReq req){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			usS.update(req);
			r.setMsg(msgS.get("rest_updated"));
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);		
	}
	
	@DeleteMapping("/delete/{userName}")
	public ResponseEntity<Resp> delete(@PathVariable(required = true)  String userName){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			usS.delete(userName);
			r.setMsg(msgS.get("rest_deleted"));
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);		
	}
	
	@GetMapping("/list")
	public ResponseEntity<Object> list(){
		Object r = new Object();
		HttpStatus status = HttpStatus.OK;
		try {
			r= usS.list();
		} catch (Exception e) {
			r=e.getMessage();
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}
	
	@GetMapping("/findByUserName")
	public ResponseEntity<Object> findById (@RequestParam (required = true)  String id){ 
		Object r = new Object();
		HttpStatus status = HttpStatus.OK;
		try {
			r= usS.getByUserName(id);
		} catch (Exception e) {
			r=e.getMessage();
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}

	@PostMapping("/{username}/addresses")
	public ResponseEntity<Resp> addAddress(@PathVariable(required = true)  String username, @RequestParam(required = true) Integer addressId){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			usS.addAddress(username, addressId);
			r.setMsg(msgS.get("rest_added"));
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> login (@RequestBody(required = true)  LoginReq req){
		Object r = new Object();
		HttpStatus status = HttpStatus.OK;
		try {
			r= usS.login(req);
		} catch (Exception e) {
			r=new Resp();
			((Resp)r).setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}
	@GetMapping("/emailValidate")
	public ResponseEntity<Resp> emailValidate(@RequestParam(required = true) String id) {
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			
			usS.mailValidate(id); 
			r.setMsg(msgS.get("rest_email_validated")); 
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}
	
		@GetMapping("/requestResetPassword")
		public ResponseEntity<Resp> requestResetPassword(@RequestParam(required = true) String userName) {
			Resp r = new Resp();
			HttpStatus status = HttpStatus.OK;
			try {
				usS.requestPasswordReset(userName);
				r.setMsg("Email di recupero inviata con successo.");
			} catch (Exception e) {
				r.setMsg(e.getMessage());
				status = HttpStatus.BAD_REQUEST;
			}
			return ResponseEntity.status(status).body(r);
		}

		
		@PostMapping("/changePassword")
		public ResponseEntity<Resp> changePassword(@RequestBody ChangePwdReq req) {
			Resp r = new Resp();
			HttpStatus status = HttpStatus.OK;
			try {
				usS.changePassword(req);
				r.setMsg("Password modificata con successo.");
			} catch (Exception e) {
				r.setMsg(e.getMessage());
				status = HttpStatus.BAD_REQUEST;
			}
			return ResponseEntity.status(status).body(r);
		}

}
