package com.betacom.pr.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.pr.dto.inputs.AddressReq;
import com.betacom.pr.response.Resp;
import com.betacom.pr.services.interfaces.IAddressServices;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("rest/address")
public class AddressController {

    private final IAddressServices addressServices;

    @GetMapping("/list")
    public ResponseEntity<Object> list(){
        Object r = new Object();
        HttpStatus status = HttpStatus.OK;
        try {
            r= addressServices.list();
        } catch (Exception e) {
            r=e.getMessage();
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(r);
    }

    @GetMapping("findById")
    public ResponseEntity<Object> findById(@RequestParam (required = true) Integer id) throws Exception{
        Object r = new Object();
        HttpStatus status = HttpStatus.OK;
        try {
            r= addressServices.findById(id);
        }
        catch (Exception e){
            r=e.getMessage();
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(r);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody AddressReq req, @RequestParam String userName) {
        Resp r = new Resp();
        HttpStatus status = HttpStatus.OK;
        try {
            addressServices.createAndAssign(req, userName);
            r.setMsg("Address created");
        } catch (Exception e) {
            r.setMsg(e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(r);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody(required = true) AddressReq req) {
        Resp r = new Resp();
        HttpStatus status = HttpStatus.OK;
        try {
            addressServices.update(req);
            r.setMsg("Address update");
        } catch (Exception e) {
            r.setMsg(e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(r);
    }

@DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody(required = true) Integer id) {
        Resp r = new Resp();
        HttpStatus status = HttpStatus.OK;
        try {
            addressServices.delete(id);
            r.setMsg("Address deleted");
        } catch (Exception e) {
            r.setMsg(e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(r);
    }

    @GetMapping("/myAddresses")
    public ResponseEntity<Object> findByUserName(@RequestParam String userName) {
        Object r = new Object();
        HttpStatus status = HttpStatus.OK;
        try {
            r = addressServices.findByUserName(userName);
        } catch (Exception e) {
            r = e.getMessage();
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(r);
    }
}
