package com.betacom.pr.controllers;

import java.time.LocalDateTime;
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

import com.betacom.pr.dto.inputs.ReviewReq;
import com.betacom.pr.dto.outputs.ReviewDTO;
import com.betacom.pr.models.Review;
import com.betacom.pr.repositories.IReviewRepository;
import com.betacom.pr.response.Resp;
import com.betacom.pr.services.interfaces.IMessaggioServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rest/reviews")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ReviewController {

    private final IReviewRepository revR;
    private final IMessaggioServices msgS;

    @GetMapping("/product/{productId}")
    public List<ReviewDTO> getByProduct(@PathVariable Long productId) {
        return revR.findByProductId(productId).stream().map(this::convertToDTO).toList();
    }

    @PostMapping("/create")
    public ResponseEntity<Resp> create(@RequestBody ReviewReq req) {
        Resp r = new Resp();
        HttpStatus status = HttpStatus.OK;
        try {
            Review entity = new Review();
            entity.setProductId(req.getProductId());
            entity.setRating(req.getRating());
            entity.setComment(req.getComment());
            entity.setUserName(req.getUserName()); 
            entity.setDate(LocalDateTime.now());
            
            revR.save(entity);
            
            r.setMsg(msgS.get("rest_created"));
        } catch (Exception e) {
            r.setMsg(e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(r);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Resp> update(@PathVariable Long id, @RequestBody ReviewReq req) {
        Resp r = new Resp();
        HttpStatus status = HttpStatus.OK;
        try {
            Review review = revR.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
                
            review.setRating(req.getRating());
            review.setComment(req.getComment());
            revR.save(review);
            
            r.setMsg(msgS.get("rest_updated"));
        } catch (Exception e) {
            r.setMsg(e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(r);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Resp> delete(@PathVariable Long id) {
        Resp r = new Resp();
        HttpStatus status = HttpStatus.OK;
        try {
            revR.deleteById(id);
            r.setMsg(msgS.get("rest_deleted"));
        } catch (Exception e) {
            r.setMsg(e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(r);
    }

    private ReviewDTO convertToDTO(Review saved) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(saved.getId());
        dto.setUserName(saved.getUserName());
        dto.setComment(saved.getComment());
        dto.setRating(saved.getRating());
        dto.setProductId(saved.getProductId());
        
        if (saved.getDate() != null) {
            dto.setDate(saved.getDate().toString()); 
        }
        
        return dto;
    }
}