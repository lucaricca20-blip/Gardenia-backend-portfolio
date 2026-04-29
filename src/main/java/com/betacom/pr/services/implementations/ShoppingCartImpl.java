package com.betacom.pr.services.implementations;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.betacom.pr.Utilities.Mapper; // <--- USA IL TUO MAPPER
import com.betacom.pr.dto.inputs.ShoppingCartReq;
import com.betacom.pr.dto.outputs.ShoppingCartDTO;
import com.betacom.pr.exceptions.WebServiceExceptions;
import com.betacom.pr.models.ShoppingCart;
import com.betacom.pr.repositories.IProductRepository;
import com.betacom.pr.repositories.IShoppingCartRepository;
import com.betacom.pr.repositories.IUserOrderRepository;
import com.betacom.pr.repositories.IUserRepository;
import com.betacom.pr.services.interfaces.IMessaggioServices;
import com.betacom.pr.services.interfaces.IShoppingCartServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShoppingCartImpl implements IShoppingCartServices {

    private final IShoppingCartRepository ssR;
    private final IProductRepository pR;
    private final IUserOrderRepository uoR;
    private final IUserRepository uR; 
    private final IMessaggioServices msgS;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ShoppingCartReq req) throws Exception {
        log.debug("Creazione item carrello: {}", req);
        ShoppingCart cart = new ShoppingCart();
        cart.setAmount(req.getAmount());
        cart.setPrice(req.getPrice());

        if (req.getUserName() != null) {
            cart.setUser(uR.findById(req.getUserName())
                .orElseThrow(() -> new WebServiceExceptions("Utente non trovato: " + req.getUserName())));
        }

        if (req.getIdProduct() != null) {
            cart.setProduct(pR.findById(req.getIdProduct())
                .orElseThrow(() -> new WebServiceExceptions(msgS.get("product_not_found"))));
        }
        cart.setUserOrder(null); 
        ssR.save(cart);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ShoppingCartReq req) throws Exception {
        log.debug("update {}", req);
        ShoppingCart us = ssR.findById(req.getId())
                .orElseThrow(() -> new WebServiceExceptions(msgS.get("cart_ntfnd")));

        if (req.getAmount() != null) us.setAmount(req.getAmount());
        if (req.getPrice() != null) us.setPrice(req.getPrice());
        
        if (req.getIdOrder() != null) {
            us.setUserOrder(uoR.findById(req.getIdOrder())
                .orElseThrow(() -> new WebServiceExceptions(msgS.get("order_not_found"))));
        }
        if (req.getIdProduct() != null) {
            us.setProduct(pR.findById(req.getIdProduct())
                .orElseThrow(() -> new WebServiceExceptions(msgS.get("product_not_found"))));
        }
        ssR.save(us);
    }

    @Override
    public List<ShoppingCartDTO> getActiveCartByUser(String userName) throws Exception {
        log.debug("Recupero carrello attivo per utente: {}", userName);
        
        List<ShoppingCart> entities = ssR.findByUserAndUserOrderIsNull(
            uR.findById(userName).orElseThrow(() -> new WebServiceExceptions("Utente non trovato"))
        );

        return Mapper.buildShoppingCartDTO(entities);
    }

    @Override
    public List<ShoppingCartDTO> getAllByUserOrder(Integer userOrderId) throws Exception {
        log.debug("Recupero items per ordine: {}", userOrderId);
        List<ShoppingCart> entities = ssR.findAllByUserOrder_Id(userOrderId);
        
        return Mapper.buildShoppingCartDTO(entities);
    }
    
    @Override
    public List<ShoppingCartDTO> getAll() {
        log.debug("get all shopping cart items");
        List<ShoppingCart> entities = ssR.findAll();
        
        return Mapper.buildShoppingCartDTO(entities);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer Id) throws Exception {
        log.debug("delete {}", Id);
        ShoppingCart us = ssR.findById(Id)
                .orElseThrow(() -> new WebServiceExceptions(msgS.get("cart_ntfnd")));
        ssR.delete(us);
    }
}