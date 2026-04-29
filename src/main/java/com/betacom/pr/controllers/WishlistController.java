package com.betacom.pr.controllers;

import com.betacom.pr.dto.inputs.WishlistReq;
import com.betacom.pr.dto.outputs.WishlistDTO;
import com.betacom.pr.response.Resp;
import com.betacom.pr.services.interfaces.IMessaggioServices;
import com.betacom.pr.services.interfaces.IWishlistServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/wishlist")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class WishlistController {

    private final IWishlistServices wishlistS;
    private final IMessaggioServices msgS;

    @PostMapping("/create")
    public ResponseEntity<Resp> create(@RequestBody WishlistReq req) {
        Resp r = new Resp();
        HttpStatus status = HttpStatus.OK;
        try {
            wishlistS.add(req);
            r.setMsg(msgS.get("rest_created"));
        } catch (Exception e) {
            r.setMsg(e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(r);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Resp> delete(@PathVariable Integer id) {
        Resp r = new Resp();
        HttpStatus status = HttpStatus.OK;
        try {
            wishlistS.remove(id);
            r.setMsg(msgS.get("rest_deleted"));
        } catch (Exception e) {
            r.setMsg(e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(r);
    }

    @GetMapping("/list/{userName}")
    public ResponseEntity<List<WishlistDTO>> getByUser(@PathVariable String userName) {
        try {
            List<WishlistDTO> lista = wishlistS.getByUser(userName);
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}