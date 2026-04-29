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

import com.betacom.pr.dto.inputs.UserOrderReq;
import com.betacom.pr.dto.outputs.UserOrderDTO;
import com.betacom.pr.response.Resp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor
public class UserOrderControllerTest {

    @Autowired
    private UserOrderController orderC;

    @Test
    @Order(1)
    public void createOrder() {
        log.debug("Create order 1");
        UserOrderReq req = new UserOrderReq();
        req.setUserId("mario.rossi");
        req.setAddressId(2);
        req.setWharehouse("Magazzino Milano");
        req.setIsPaid(false);

        ResponseEntity<Resp> resp = orderC.create(req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp) resp.getBody();
        Assertions.assertThat(r.getMsg()).isEqualTo("Ordine creato con successo");
    }

    @Test
    @Order(2)
    public void createOrder2() {
        log.debug("Create order 2");
        UserOrderReq req = new UserOrderReq();
        req.setUserId("mario.rossi");
        req.setAddressId(2);
        req.setWharehouse("Magazzino Roma");
        req.setIsPaid(false);

        ResponseEntity<Resp> resp = orderC.create(req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp) resp.getBody();
        Assertions.assertThat(r.getMsg()).isEqualTo("Ordine creato con successo");
    }

    @SuppressWarnings("unchecked")
    @Test
    @Order(3)
    public void listOrders() {
        log.debug("Test list orders");
        ResponseEntity<Object> resp = orderC.list();
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Object body = resp.getBody();
        List<UserOrderDTO> l = (List<UserOrderDTO>) body;
        Assertions.assertThat(l.size()).isGreaterThan(0);
        l.forEach(o -> log.debug(o.toString()));
    }

    @Test
    @Order(4)
    public void getOrderById() {
        log.debug("Test getById order");
        ResponseEntity<Object> resp = orderC.getById(1);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        log.debug(">>" + resp.getBody());
    }

    @Test
    @Order(5)
    public void getOrderById_Error() {
        log.debug("Test getById order not found - should fail");
        ResponseEntity<Object> resp = orderC.getById(99);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        log.debug(">>" + resp.getBody());
    }

    @Test
    @Order(6)
    public void updateOrder() {
        log.debug("Test update order");

        ResponseEntity<Object> respL = orderC.list();
        assertEquals(HttpStatus.OK, respL.getStatusCode());
        List<UserOrderDTO> l = (List<UserOrderDTO>) respL.getBody();
        l.forEach(o -> log.debug(o.toString()));

        UserOrderReq req = new UserOrderReq();
        req.setId(1);
        req.setWharehouse("Magazzino Torino");
        req.setIsPaid(true);
        req.setStatus("PENDING");

        ResponseEntity<Resp> resp = orderC.update(req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp) resp.getBody();
        Assertions.assertThat(r.getMsg()).isEqualTo("rest_updated");
    }
}