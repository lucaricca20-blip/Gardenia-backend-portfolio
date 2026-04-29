package com.betacom.pr.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.betacom.pr.dto.inputs.ProductReq;
import com.betacom.pr.dto.outputs.ProductDTO;
import com.betacom.pr.response.Resp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor
public class ProductControllerTest {

    @Autowired
    private ProductController productC;

    @Test
    @Order(1)
    public void createProduct() {
        log.debug("Create product 1");
        ProductReq req = new ProductReq();
        req.setName("Rosa Rossa");
        req.setDescription("Rosa di colore rosso");
        req.setPrice(5.99);
        req.setStock(100);
        req.setSubcategoryId(2);
        req.setIsDeleted(false);

        ResponseEntity<Resp> resp = productC.create(req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp) resp.getBody();
        Assertions.assertThat(r.getMsg()).isEqualTo("Prodotto creato con successo");
    }
    
    @Test
    @Order(2)
    public void createProduct2() {
        log.debug("Create product 2");
        ProductReq req = new ProductReq();
        req.setName("Cactus");
        req.setDescription("Pianta grassa");
        req.setPrice(12.50);
        req.setStock(50);
        req.setSubcategoryId(2);
        req.setIsDeleted(false);

        ResponseEntity<Resp> resp = productC.create(req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp) resp.getBody();
        Assertions.assertThat(r.getMsg()).isEqualTo("Prodotto creato con successo");
    }

    @SuppressWarnings("unchecked")
    @Test
    @Order(3)
    public void listProduct() {
        log.debug("Test list product");
        ResponseEntity<Object> resp = productC.list();
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Object body = resp.getBody();
        List<ProductDTO> l = (List<ProductDTO>) body;
        Assertions.assertThat(l.size()).isGreaterThan(0);
        l.forEach(p -> log.debug(p.toString()));
    }

    @Test
    @Order(4)
    public void findProductById() {
        log.debug("Test findById product");
        ResponseEntity<Object> resp = productC.findById(1);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        log.debug(">>" + resp.getBody());
    }

    @Test
    @Order(5)
    public void findProductById_Error() {
        log.debug("Test findById product not found - should fail");
        ResponseEntity<Object> resp = productC.findById(99);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        log.debug(">>" + resp.getBody());
    }

    @Test
    @Order(6)
    public void updateProduct() {
        log.debug("Test update product");

        ResponseEntity<Object> respL = productC.list();
        assertEquals(HttpStatus.OK, respL.getStatusCode());
        List<ProductDTO> l = (List<ProductDTO>) respL.getBody();
        l.forEach(p -> log.debug(p.toString()));

        ProductReq req = new ProductReq();
        req.setId(1);
        req.setName("Rosa Bianca");
        req.setDescription("Rosa di colore bianco");
        req.setPrice(6.50);
        req.setStock(80);
        req.setSubcategoryId(2);

        ResponseEntity<Resp> resp = productC.update(req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp) resp.getBody();
        Assertions.assertThat(r.getMsg()).isEqualTo("Prodotto aggiornato con successo");

        ResponseEntity<Object> respCheck = productC.findById(1);
        ProductDTO dto = (ProductDTO) respCheck.getBody();
        Assertions.assertThat(dto.getName()).isEqualTo("Rosa Bianca");
    }

    @Test
    @Order(7)
    public void deleteProduct() {
        log.debug("Delete product id 1");
        ResponseEntity<Resp> resp = productC.delete(1);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp) resp.getBody();
        Assertions.assertThat(r.getMsg()).isEqualTo("Prodotto eliminato con successo");
    }

    @Test
    @Order(8)
    public void deleteProduct_NotFound_Error() {
        log.debug("Delete product not found - should fail");
        ResponseEntity<Resp> resp = productC.delete(99);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        log.debug(">>" + resp.getBody());
    }
}