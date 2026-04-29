package com.betacom.pr.controllers;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.pr.dto.inputs.ImageReq;
import com.betacom.pr.response.Resp;
import com.betacom.pr.services.interfaces.IImageServices;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("rest/image") 
@CrossOrigin(origins = "http://localhost:4200")
public class ImageController {

    private final IImageServices imageS;

   
    @PostMapping("/create")
    public ResponseEntity<Resp> create(@RequestBody(required = true) ImageReq req) {
        Resp r = new Resp();
        HttpStatus status = HttpStatus.OK;
        try {
            imageS.create(req);
            r.setMsg("Immagine collegata con successo!");
        } catch (Exception e) {
            r.setMsg("Errore: " + e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(r);       
    }

    @PutMapping("/update")
    public ResponseEntity<Resp> update(@RequestBody(required = true) ImageReq req){
        Resp r = new Resp();
        HttpStatus status = HttpStatus.OK;
        try {
            imageS.update(req);
            r.setMsg("Immagine aggiornata con successo");
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
            imageS.delete(id);
            r.setMsg("Immagine eliminata con successo");
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
            r = imageS.list(); 
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
            r = imageS.getById(id);
        } catch (Exception e) {
            r = e.getMessage();
            status = HttpStatus.BAD_REQUEST; 
        }
        return ResponseEntity.status(status).body(r);
    }
    
    @GetMapping("/findByProduct")
    public ResponseEntity<Object> findByProduct(@RequestParam(required = true) Integer productId) {
        Object r;
        HttpStatus status = HttpStatus.OK;
        try {
            r = imageS.getByProductId(productId);
        } catch (Exception e) {
            r = e.getMessage();
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(r);
    }
}