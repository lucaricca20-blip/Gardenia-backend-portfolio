package com.betacom.pr.user;

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

import com.betacom.pr.controllers.UserController;
import com.betacom.pr.dto.inputs.LoginReq;
import com.betacom.pr.dto.inputs.UserReq;
import com.betacom.pr.dto.outputs.UserDTO;
import com.betacom.pr.response.Resp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor
public class UserControllerTest {

    @Autowired
    private UserController userC;

    /*
     * Creo il primo utente che useremo anche per login e order
     */
    @Test
    @Order(1)
    public void registerUser() {
        log.debug("Register user 1");
        UserReq req = new UserReq();
        req.setUserName("mario.rossi");
        req.setFirstName("Mario");
        req.setLastName("Rossi");
        req.setEmail("mario.rossi@test.com");
        req.setPhone("3331234567");
        req.setPassword("password123");
        req.setRole("USER");

        ResponseEntity<Resp> resp = userC.create(req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp) resp.getBody();
        Assertions.assertThat(r.getMsg()).isEqualTo("rest_created");
    }

    /*
     * Creo un secondo utente che eliminerò dopo
     */
    @Test
    @Order(2)
    public void registerUser2() {
        log.debug("Register user 2");
        UserReq req = new UserReq();
        req.setUserName("luigi.verdi");
        req.setFirstName("Luigi");
        req.setLastName("Verdi");
        req.setEmail("luigi.verdi@test.com");
        req.setPhone("3339876543");
        req.setPassword("pass456");
        req.setRole("USER");

        ResponseEntity<Resp> resp = userC.create(req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp) resp.getBody();
        Assertions.assertThat(r.getMsg()).isEqualTo("rest_created");
    }

    /*
     * Provo a registrare un utente con userName già esistente: mi aspetto BAD_REQUEST
     */
    @Test
    @Order(3)
    public void registerUserDuplicato_Error() {
        log.debug("Register duplicate user - should fail");
        UserReq req = new UserReq();
        req.setUserName("mario.rossi");
        req.setFirstName("Mario");
        req.setLastName("Rossi");
        req.setEmail("mario.rossi@test.com");
        req.setPassword("password123");
        req.setRole("USER");

        ResponseEntity<Resp> resp = userC.create(req);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        log.debug("Expected error: {}", ((Resp) resp.getBody()).getMsg());
    }

    @Test
    @Order(4)
    public void listUsers() {
        log.debug("Test list users");
        ResponseEntity<Object> resp = userC.list();
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Object body = resp.getBody();
        List<UserDTO> l = (List<UserDTO>) body;
        Assertions.assertThat(l.size()).isGreaterThan(0);
        l.forEach(u -> log.debug(u.toString()));
    }

    @Test
    @Order(5)
    public void findByUserName() {
        log.debug("Test findByUserName");
        ResponseEntity<Object> resp = userC.findById("mario.rossi");
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        log.debug(">>" + resp.getBody());
    }

    /*
     * Cerco uno userName inesistente: mi aspetto BAD_REQUEST
     */
    @Test
    @Order(6)
    public void findByUserName_Error() {
        log.debug("Test findByUserName not found - should fail");
        ResponseEntity<Object> resp = userC.findById("utente.inesistente");
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        log.debug(">>" + resp.getBody());
    }

    @Test
    @Order(7)
    public void updateUser() {
        log.debug("Test update user mario.rossi");
        UserReq req = new UserReq();
        req.setUserName("mario.rossi");
        req.setFirstName("Mario Updated");
        req.setLastName("Rossi Updated");
        req.setEmail("mario.updated@test.com");
        req.setPhone("3330000001");
        req.setPassword("newpassword123");
        req.setRole("USER");

        ResponseEntity<Resp> resp = userC.update(req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp) resp.getBody();
        Assertions.assertThat(r.getMsg()).isEqualTo("rest_updated");

//        // Verifico che la modifica sia effettiva
//        ResponseEntity<Object> respFind = userC.findById("mario.rossi");
//        assertEquals(HttpStatus.OK, respFind.getStatusCode());
//        UserDTO dto = (UserDTO) respFind.getBody();
//        Assertions.assertThat(dto.getFirstName()).isEqualTo("Mario Updated");
    }

    @Test
    @Order(8)
    public void loginSuccess() {
        log.debug("Test login success");
        LoginReq req = new LoginReq();
        req.setUserName("mario.rossi");
        req.setPassword("newpassword123");

        ResponseEntity<Object> resp = userC.login(req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        log.debug(">>" + resp.getBody());
    }

    /*
     * Login con password sbagliata: mi aspetto BAD_REQUEST
     */
    @Test
    @Order(9)
    public void loginWrongPassword_Error() {
        log.debug("Test login wrong password - should fail");
        LoginReq req = new LoginReq();
        req.setUserName("mario.rossi");
        req.setPassword("passwordSbagliata");

        ResponseEntity<Object> resp = userC.login(req);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        log.debug(">>" + resp.getBody());
    }

    @Test
    @Order(10)
    public void deleteUser() {
        log.debug("Delete user luigi.verdi");
        ResponseEntity<Resp> resp = userC.delete("luigi.verdi");
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp) resp.getBody();
        Assertions.assertThat(r.getMsg()).isEqualTo("rest_deleted");
    }

    /*
     * Elimino uno userName inesistente: mi aspetto BAD_REQUEST
     */
    @Test
    @Order(11)
    public void deleteUser_NotFound_Error() {
        log.debug("Delete user not found - should fail");
        ResponseEntity<Resp> resp = userC.delete("utente.inesistente");
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        log.debug(">>" + resp.getBody());
    }
}