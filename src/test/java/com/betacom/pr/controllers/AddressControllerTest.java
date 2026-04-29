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

import com.betacom.pr.dto.inputs.AddressReq;
import com.betacom.pr.response.Resp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor
public class AddressControllerTest {

    @Autowired
    private AddressController addressC;

    @Test
    @Order(1)
    public void createAddress() {
        log.debug("Create address 1");
        AddressReq req = new AddressReq();
        req.setCountry("Italia");
        req.setCity("Milano");
        req.setPostalCode(20100);
        req.setStreet("Via Roma");
        req.setNumber(10);

        ResponseEntity<Object> resp = addressC.create(req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp) resp.getBody();
        Assertions.assertThat(r.getMsg()).isEqualTo("Address create");
    }
    
    @Test
    @Order(2)
    public void createAddress2() {
        log.debug("Create address 2");
        AddressReq req = new AddressReq();
        req.setCountry("Francia");
        req.setCity("Parigi");
        req.setPostalCode(90032);
        req.setStreet("Via Italia");
        req.setNumber(11);

        ResponseEntity<Object> resp = addressC.create(req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp) resp.getBody();
        Assertions.assertThat(r.getMsg()).isEqualTo("Address create");
    }


    @SuppressWarnings("unchecked")
    @Test
    @Order(3)
    public void listAddress() {
        log.debug("Test list address");
        ResponseEntity<Object> resp = addressC.list();
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Object body = resp.getBody();
        List<?> l = (List<?>) body;
        Assertions.assertThat(l.size()).isGreaterThan(0);
        l.forEach(a -> log.debug(a.toString()));
    }

    @Test
    @Order(4)
    public void findAddressById() throws Exception {
        log.debug("Test findById address");
        ResponseEntity<Object> resp = addressC.findById(1);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        log.debug(">>" + resp.getBody());
    }

    @Test
    @Order(5)
    public void findAddressById_Error() throws Exception {
        log.debug("Test findById address not found - should fail");
        ResponseEntity<Object> resp = addressC.findById(99);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        log.debug(">>" + resp.getBody());
    }

    @Test
    @Order(6)
    public void updateAddress() {
        log.debug("Test update address");

        ResponseEntity<Object> respL = addressC.list();
        assertEquals(HttpStatus.OK, respL.getStatusCode());
        List<?> l = (List<?>) respL.getBody();
        l.forEach(a -> log.debug(a.toString()));

        AddressReq req = new AddressReq();
        req.setId(1);
        req.setCountry("Italia");
        req.setCity("Torino");
        req.setPostalCode(10100);
        req.setStreet("Via Garibaldi");
        req.setNumber(3);

        ResponseEntity<Object> resp = addressC.update(req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp) resp.getBody();
        Assertions.assertThat(r.getMsg()).isEqualTo("Address update");
    }

    @Test
    @Order(7)
    public void deleteAddress() {
        log.debug("Delete address id 1");
        ResponseEntity<Object> resp = addressC.delete(1);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp) resp.getBody();
        Assertions.assertThat(r.getMsg()).isEqualTo("Address deleted");
    }
}