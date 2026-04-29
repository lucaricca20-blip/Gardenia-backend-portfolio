// package com.betacom.pr.controllers;

// import static org.junit.jupiter.api.Assertions.assertEquals;

// import java.util.List;

// import org.assertj.core.api.Assertions;
// import org.junit.jupiter.api.MethodOrderer;
// import org.junit.jupiter.api.Order;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.TestMethodOrder;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;

// import com.betacom.pr.dto.inputs.ImageReq;
// import com.betacom.pr.dto.outputs.ImageDTO;
// import com.betacom.pr.response.Resp;

// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;

// @Slf4j
// @SpringBootTest
// @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// @RequiredArgsConstructor
// public class ImageControllerTest {

//     @Autowired
//     private ImageController imageC;

//     /*
//      * Creo la prima immagine collegata al prodotto id 2 (Cactus),
//      * che non è stato eliminato in ProductControllerTest
//      */
//     @Test
//     @Order(1)
//     public void createImage() {
//         log.debug("Create image 1");
//         ImageReq req = new ImageReq();
//         req.setLink("https://images.test.com/cactus1.jpg");
//         req.setProductId(2);

//         ResponseEntity<Resp> resp = imageC.create(req);
//         assertEquals(HttpStatus.OK, resp.getStatusCode());
//         Resp r = (Resp) resp.getBody();
//         Assertions.assertThat(r.getMsg()).isEqualTo("Immagine collegata con successo");
//     }

//     /*
//      * Creo una seconda immagine da non eliminare, per avere lista non vuota dopo la delete
//      */
//     @Test
//     @Order(2)
//     public void createImage2() {
//         log.debug("Create image 2");
//         ImageReq req = new ImageReq();
//         req.setLink("https://images.test.com/cactus2.jpg");
//         req.setProductId(2);

//         ResponseEntity<Resp> resp = imageC.create(req);
//         assertEquals(HttpStatus.OK, resp.getStatusCode());
//         Resp r = (Resp) resp.getBody();
//         Assertions.assertThat(r.getMsg()).isEqualTo("Immagine collegata con successo");
//     }

//     /*
//      * Provo a creare un'immagine senza productId: mi aspetto BAD_REQUEST
//      * perché ImageImpl lancia eccezione se productId è null
//      */
//     @Test
//     @Order(3)
//     public void createImage_SenzaProdotto_Error() {
//         log.debug("Create image without productId - should fail");
//         ImageReq req = new ImageReq();
//         req.setLink("https://images.test.com/noprodotto.jpg");
//         // productId non impostato → null

//         ResponseEntity<Resp> resp = imageC.create(req);
//         assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
//         log.debug("Expected error: {}", ((Resp) resp.getBody()).getMsg());
//     }

//     /*
//      * Provo a creare un'immagine con un productId inesistente: mi aspetto BAD_REQUEST
//      */
//     @Test
//     @Order(4)
//     public void createImage_ProdottoInesistente_Error() {
//         log.debug("Create image with invalid productId - should fail");
//         ImageReq req = new ImageReq();
//         req.setLink("https://images.test.com/invalid.jpg");
//         req.setProductId(99);

//         ResponseEntity<Resp> resp = imageC.create(req);
//         assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
//         log.debug("Expected error: {}", ((Resp) resp.getBody()).getMsg());
//     }

//     @SuppressWarnings("unchecked")
//     @Test
//     @Order(5)
//     public void listImage() {
//         log.debug("Test list image");
//         ResponseEntity<Object> resp = imageC.list();
//         assertEquals(HttpStatus.OK, resp.getStatusCode());
//         Object body = resp.getBody();
//         List<ImageDTO> l = (List<ImageDTO>) body;
//         Assertions.assertThat(l.size()).isGreaterThan(0);
//         l.forEach(img -> log.debug(img.toString()));
//     }

//     @Test
//     @Order(6)
//     public void findImageById() {
//         log.debug("Test findById image");
//         ResponseEntity<Object> resp = imageC.findById(1);
//         assertEquals(HttpStatus.OK, resp.getStatusCode());
//         log.debug(">>" + resp.getBody());
//     }

//     /*
//      * Cerco un id inesistente: mi aspetto BAD_REQUEST
//      */
//     @Test
//     @Order(7)
//     public void findImageById_Error() {
//         log.debug("Test findById image not found - should fail");
//         ResponseEntity<Object> resp = imageC.findById(99);
//         assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
//         log.debug(">>" + resp.getBody());
//     }

//     @Test
//     @Order(8)
//     public void updateImage() {
//         log.debug("Test update image");

//         // Prima vedo la lista per capire cosa c'è
//         ResponseEntity<Object> respL = imageC.list();
//         assertEquals(HttpStatus.OK, respL.getStatusCode());
//         Object body = respL.getBody();
//         List<ImageDTO> l = (List<ImageDTO>) body;
//         l.forEach(img -> log.debug(img.toString()));

//         // Aggiorno l'immagine con id 1
//         ImageReq req = new ImageReq();
//         req.setId(1);
//         req.setLink("https://images.test.com/cactus1_updated.jpg");
//         req.setProductId(2);

//         ResponseEntity<Resp> resp = imageC.update(req);
//         assertEquals(HttpStatus.OK, resp.getStatusCode());
//         Resp r = (Resp) resp.getBody();
//         Assertions.assertThat(r.getMsg()).isEqualTo("Immagine aggiornata con successo");

//         // Verifico che la modifica sia avvenuta
//         ResponseEntity<Object> respCheck = imageC.findById(1);
//         assertEquals(HttpStatus.OK, respCheck.getStatusCode());
//         ImageDTO dto = (ImageDTO) respCheck.getBody();
//         Assertions.assertThat(dto.getLink()).isEqualTo("https://images.test.com/cactus1_updated.jpg");
//     }

//     /*
//      * Provo a fare update di un'immagine inesistente: mi aspetto BAD_REQUEST
//      */
//     @Test
//     @Order(9)
//     public void updateImage_NotFound_Error() {
//         log.debug("Test update image not found - should fail");
//         ImageReq req = new ImageReq();
//         req.setId(99);
//         req.setLink("https://images.test.com/inesistente.jpg");
//         req.setProductId(2);

//         ResponseEntity<Resp> resp = imageC.update(req);
//         assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
//         log.debug("Expected error: {}", ((Resp) resp.getBody()).getMsg());
//     }

//     @Test
//     @Order(10)
//     public void deleteImage() {
//         log.debug("Delete image id 1");
//         ResponseEntity<Resp> resp = imageC.delete(1);
//         assertEquals(HttpStatus.OK, resp.getStatusCode());
//         Resp r = (Resp) resp.getBody();
//         Assertions.assertThat(r.getMsg()).isEqualTo("Immagine eliminata con successo");
//     }

//     /*
//      * Elimino un id inesistente: mi aspetto BAD_REQUEST
//      */
//     @Test
//     @Order(11)
//     public void deleteImage_NotFound_Error() {
//         log.debug("Delete image not found - should fail");
//         ResponseEntity<Resp> resp = imageC.delete(99);
//         assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
//         log.debug(">>" + resp.getBody());
//     }
// }