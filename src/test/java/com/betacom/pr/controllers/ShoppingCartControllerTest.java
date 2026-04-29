package com.betacom.pr.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.betacom.pr.dto.inputs.ShoppingCartReq;
import com.betacom.pr.response.Resp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor
public class ShoppingCartControllerTest {

    @Autowired
    private ShoppingCartController cartC;

    @Test
    @Order(1)
    public void createCart() {
        log.debug("Create shoppingCart 1");
        ShoppingCartReq req = new ShoppingCartReq();
        req.setIdOrder(2);
        req.setIdProduct(2);
        req.setAmount(3);
        req.setPrice(12.0);

        ResponseEntity<Resp> resp = cartC.create(req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp) resp.getBody();
        Assertions.assertThat(r.getMsg()).isEqualTo("rest_created");
    }

    @Test
    @Order(2)
    public void createCart2() {
        log.debug("Create shoppingCart 2");
        ShoppingCartReq req = new ShoppingCartReq();
        req.setIdOrder(2);
        req.setIdProduct(2);
        req.setAmount(1);
        req.setPrice(12.0);

        ResponseEntity<Resp> resp = cartC.create(req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp) resp.getBody();
        Assertions.assertThat(r.getMsg()).isEqualTo("rest_created");
    }

    @Test
    @Order(3)
    public void updateCart() {
        log.debug("Test update shoppingCart");
        ShoppingCartReq req = new ShoppingCartReq();
        req.setId(1);
        req.setIdOrder(2);
        req.setIdProduct(2);
        req.setAmount(5);
        req.setPrice(12.0);

        ResponseEntity<Resp> resp = cartC.update(req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp) resp.getBody();
        Assertions.assertThat(r.getMsg()).isEqualTo("rest_updated");
    }

    @Test
    @Order(4)
    public void deleteCart() {
        log.debug("Delete shoppingCart id 1");
        ResponseEntity<Resp> resp = cartC.delete(1);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp) resp.getBody();
        Assertions.assertThat(r.getMsg()).isEqualTo("rest_deleted");
    }

    @Test
    @Order(5)
    public void deleteCart_NotFound_Error() {
        log.debug("Delete shoppingCart not found - should fail");
        ResponseEntity<Resp> resp = cartC.delete(99);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        log.debug(">>" + resp.getBody());
    }
}