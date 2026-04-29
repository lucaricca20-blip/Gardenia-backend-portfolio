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

import com.betacom.pr.dto.inputs.CategoryReq;
import com.betacom.pr.dto.outputs.CategoryDTO;
import com.betacom.pr.response.Resp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor
public class CategoryControllerTest {

    @Autowired
    private CategoryController categoryC;

    @Test
    @Order(1)
    public void createCategory() {
        log.debug("Create category 1");
        CategoryReq req = new CategoryReq();
        req.setName("Fiori");

        ResponseEntity<Resp> resp = categoryC.create(req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp) resp.getBody();
        Assertions.assertThat(r.getMsg()).isEqualTo("Categoria creata con successo");
    }

    @Test
    @Order(2)
    public void createCategory2() {
        log.debug("Create category 2");
        CategoryReq req = new CategoryReq();
        req.setName("Piante");

        ResponseEntity<Resp> resp = categoryC.create(req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp) resp.getBody();
        Assertions.assertThat(r.getMsg()).isEqualTo("Categoria creata con successo");
    }

    @SuppressWarnings("unchecked")
    @Test
    @Order(3)
    public void listCategory() {
        log.debug("Test list category");
        ResponseEntity<Object> resp = categoryC.list();
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Object body = resp.getBody();
        List<CategoryDTO> l = (List<CategoryDTO>) body;
        Assertions.assertThat(l.size()).isGreaterThan(0);
        l.forEach(c -> log.debug(c.toString()));
    }

    @Test
    @Order(4)
    public void findCategoryById() {
        log.debug("Test findById category");
        ResponseEntity<Object> resp = categoryC.findById(1);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        log.debug(">>" + resp.getBody());
    }

    @Test
    @Order(5)
    public void findCategoryById_Error() {
        log.debug("Test findById category not found - should fail");
        ResponseEntity<Object> resp = categoryC.findById(99);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        log.debug(">>" + resp.getBody());
    }

    @Test
    @Order(6)
    public void updateCategory() {
        log.debug("Test update category");

        ResponseEntity<Object> respL = categoryC.list();
        assertEquals(HttpStatus.OK, respL.getStatusCode());
        Object body = respL.getBody();
        List<CategoryDTO> l = (List<CategoryDTO>) body;
        l.forEach(c -> log.debug(c.toString()));

        CategoryReq req = new CategoryReq();
        req.setId(1);
        req.setName("Fiori Recisi");

        ResponseEntity<Resp> resp = categoryC.update(req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp) resp.getBody();
        Assertions.assertThat(r.getMsg()).isEqualTo("Categoria aggiornata con successo");

        ResponseEntity<Object> respCheck = categoryC.findById(1);
        CategoryDTO dto = (CategoryDTO) respCheck.getBody();
        Assertions.assertThat(dto.getName()).isEqualTo("Fiori Recisi");
    }

    @Test
    @Order(7)
    public void deleteCategory() {
        log.debug("Delete category id 1");
        ResponseEntity<Resp> resp = categoryC.delete(1);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp) resp.getBody();
        Assertions.assertThat(r.getMsg()).isEqualTo("Categoria eliminata con successo");
    }

    @Test
    @Order(8)
    public void deleteCategory_NotFound_Error() {
        log.debug("Delete category not found - should fail");
        ResponseEntity<Resp> resp = categoryC.delete(99);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        log.debug(">>" + resp.getBody());
    }
}