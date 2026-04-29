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

import com.betacom.pr.dto.inputs.SubcategoryReq;
import com.betacom.pr.dto.outputs.SubcategoryDTO;
import com.betacom.pr.response.Resp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor
public class SubcategoryControllerTest {

    @Autowired
    private SubcategoryController subcategoryC;

    @Test
    @Order(1)
    public void createSubcategory() {
        log.debug("Create subcategory 1");
        SubcategoryReq req = new SubcategoryReq();
        req.setSubcategoryName("Piante da Interno");
        req.setCategoryId(2);

        ResponseEntity<Resp> resp = subcategoryC.create(req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp) resp.getBody();
        Assertions.assertThat(r.getMsg()).isEqualTo("Sottocategoria creata con successo");
    }

    @Test
    @Order(2)
    public void createSubcategory2() {
        log.debug("Create subcategory 2");
        SubcategoryReq req = new SubcategoryReq();
        req.setSubcategoryName("Piante da Esterno");
        req.setCategoryId(2);

        ResponseEntity<Resp> resp = subcategoryC.create(req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp) resp.getBody();
        Assertions.assertThat(r.getMsg()).isEqualTo("Sottocategoria creata con successo");
    }

    @SuppressWarnings("unchecked")
    @Test
    @Order(3)
    public void listSubcategory() {
        log.debug("Test list subcategory");
        ResponseEntity<Object> resp = subcategoryC.list();
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Object body = resp.getBody();
        List<SubcategoryDTO> l = (List<SubcategoryDTO>) body;
        Assertions.assertThat(l.size()).isGreaterThan(0);
        l.forEach(s -> log.debug(s.toString()));
    }

    @Test
    @Order(4)
    public void findSubcategoryById() {
        log.debug("Test findById subcategory");
        ResponseEntity<Object> resp = subcategoryC.findById(1);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        log.debug(">>" + resp.getBody());
    }

    @Test
    @Order(5)
    public void findSubcategoryById_Error() {
        log.debug("Test findById subcategory not found - should fail");
        ResponseEntity<Object> resp = subcategoryC.findById(99);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        log.debug(">>" + resp.getBody());
    }

    @Test
    @Order(6)
    public void updateSubcategory() {
        log.debug("Test update subcategory");

        ResponseEntity<Object> respL = subcategoryC.list();
        assertEquals(HttpStatus.OK, respL.getStatusCode());
        List<SubcategoryDTO> l = (List<SubcategoryDTO>) respL.getBody();
        l.forEach(s -> log.debug(s.toString()));

        SubcategoryReq req = new SubcategoryReq();
        req.setCategoryId(1);
        req.setSubcategoryName("Piante da Interno Aggiornate");
        req.setCategoryId(2);

        ResponseEntity<Resp> resp = subcategoryC.update(req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp) resp.getBody();
        Assertions.assertThat(r.getMsg()).isEqualTo("Sottocategoria aggiornata con successo");
    }

    @Test
    @Order(7)
    public void deleteSubcategory() {
        log.debug("Delete subcategory id 1");
        ResponseEntity<Resp> resp = subcategoryC.delete(1);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp) resp.getBody();
        Assertions.assertThat(r.getMsg()).isEqualTo("Sottocategoria eliminata con successo");
    }

    @Test
    @Order(8)
    public void deleteSubcategory_NotFound_Error() {
        log.debug("Delete subcategory not found - should fail");
        ResponseEntity<Resp> resp = subcategoryC.delete(99);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        log.debug(">>" + resp.getBody());
    }
}