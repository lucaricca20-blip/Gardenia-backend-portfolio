package com.betacom.pr.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.pr.Utilities.Mapper;
import com.betacom.pr.dto.inputs.UserOrderReq;
import com.betacom.pr.dto.outputs.ShoppingCartDTO;
import com.betacom.pr.dto.outputs.UserOrderDTO;
import com.betacom.pr.enums.Status;
import com.betacom.pr.exceptions.WebServiceExceptions;
import com.betacom.pr.models.Product;
import com.betacom.pr.models.ShoppingCart;
import com.betacom.pr.models.User;
import com.betacom.pr.models.UserOrder;
import com.betacom.pr.repositories.IAddressRepository;
import com.betacom.pr.repositories.IProductRepository;
import com.betacom.pr.repositories.IShoppingCartRepository;
import com.betacom.pr.repositories.IUserOrderRepository;
import com.betacom.pr.repositories.IUserRepository;
import com.betacom.pr.services.interfaces.IMessaggioServices;
import com.betacom.pr.services.interfaces.IUserOrderServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserOrderImpl implements IUserOrderServices {
    
    private final IUserOrderRepository orderR;
    private final IUserRepository userR;
    private final IAddressRepository addR;
    private final IMessaggioServices msgS;
    private final IShoppingCartRepository shopCartR;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(UserOrderReq req) throws Exception {
        log.debug("Creazione ordine per utente id: {}", req.getUserId());

        User user = userR.findByUserName(req.getUserId()) 
                .orElseThrow(() -> new Exception("Utente non trovato: " + req.getUserId()));

        UserOrder order = new UserOrder();
        order.setUser(user);
        order.setDate(req.getDate());
        order.setTotalPrice(req.getTotalPrice());
        order.setWharehouse(req.getWharehouse());
        
        if (req.getStatus() != null) {
            order.setStatus(Status.valueOf(req.getStatus().toUpperCase()));
        } else {
            order.setStatus(Status.PENDING);
        }
        
        order.setIsPaid(req.getIsPaid() != null ? req.getIsPaid() : false);

        UserOrder savedOrder = orderR.save(order);

        List<ShoppingCart> activeCartItems = shopCartR.findByUserAndUserOrderIsNull(user);

        if (activeCartItems.isEmpty()) {
            log.warn("Tentativo di creare un ordine con carrello vuoto per l'utente: {}", user.getUserName());
            throw new Exception("Carrello vuoto");
        }

        for (ShoppingCart cartItem : activeCartItems) {
            Product product = cartItem.getProduct();
            
            int quantityOrdered = cartItem.getAmount();
            int currentStock = product.getStock();

            if (currentStock < quantityOrdered) {
                throw new Exception("Stock insufficiente per: " + product.getName());
            }

            product.setStock(currentStock - quantityOrdered);
            
            cartItem.setUserOrder(savedOrder);
        }

        shopCartR.saveAll(activeCartItems);
        
        log.info("Ordine {} creato con successo. Stock aggiornato.", savedOrder.getId());
    }

    @Override
    public UserOrderDTO getById(Integer id) throws Exception {
        log.debug("Recupero ordine id: {}", id);

        UserOrder order = orderR.findById(id)
            .orElseThrow(() -> new Exception("Ordine non trovato"));

        List<ShoppingCartDTO> itemsList = null;
        
		if (order.getProducts() != null) {
		    itemsList = Mapper.buildShoppingCartDTO(order.getProducts());
		}

        return UserOrderDTO.builder()
                        .id(order.getId())
                        .wharehouse(order.getWharehouse())
                        .isPaid(order.getIsPaid())
                        .userName(order.getUser() != null ? order.getUser().getUserName() : null)
                        .addressId(order.getAddress() != null ? order.getAddress().getId() : null)
                        .date(order.getDate())
                        .totalPrice(order.getTotalPrice())
                        .statusDescription(order.getStatus() != null ? order.getStatus().toString() : null)
                        .items(itemsList) 
                        .build();
    }

	@Override
	public List<UserOrderDTO> getByUserId(String userName) {
	    log.debug("Lista ordini per utente: {}", userName);
	    return orderR.findAllByUser_UserName(userName).stream()
	            .map(order -> {
	                UserOrderDTO dto = UserOrderDTO.builder()
	                        .id(order.getId())
	                        .wharehouse(order.getWharehouse())
	                        .isPaid(order.getIsPaid())
	                        .userName(order.getUser().getUserName())
	                        .date(order.getDate())
	                        .statusDescription(order.getStatus().toString())
	                        .totalPrice(order.getTotalPrice())
	                        .items(Mapper.buildShoppingCartDTO(order.getProducts()))
	                        .build();
	                return dto;
	            }).collect(Collectors.toList());
	}

    @Override
    public List<UserOrderDTO> getAll() {
        log.debug("Lista di tutti gli ordini");
        return orderR.findAll().stream()
                .map(order -> UserOrderDTO.builder()
                        .id(order.getId())
                        .wharehouse(order.getWharehouse())
                        .isPaid(order.getIsPaid())
                        .userName(order.getUser().getUserName())
                        .addressId(order.getAddress() != null ? order.getAddress().getId() : null)
                        .date(order.getDate())
                        .statusDescription(order.getStatus().toString())
                        .totalPrice(order.getTotalPrice())
                        .build()
                ).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserOrderReq req) throws Exception {
        log.debug("Aggiornamento ordine id: {}", req.getId());

        UserOrder us = orderR.findById(req.getId())
                .orElseThrow(() -> new WebServiceExceptions(msgS.get("order_ntfnd")));

        if(req.getWharehouse() != null)
            us.setWharehouse(req.getWharehouse());
        
        if(req.getIsPaid() != null) {
            if (req.getIsPaid())
                us.setStatus(Status.PENDING);
            us.setIsPaid(req.getIsPaid());
        }
        
        if(req.getDate() != null)
            us.setDate(req.getDate());
        
        if(req.getUserId() != null)
            us.setUser(userR.findById(req.getUserId()).get());
        
        if(req.getAddressId() != null)
            us.setAddress(addR.findById(req.getAddressId()).get());
        
        if(req.getStatus() != null)
            us.setStatus(Status.valueOf(req.getStatus().toUpperCase()));

        orderR.save(us);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(UserOrderReq req) throws Exception {
        log.debug("Aggiornamento stato ordine id: {}", req.getId());

        UserOrder us = orderR.findById(req.getId())
                .orElseThrow(() -> new WebServiceExceptions(msgS.get("order_ntfnd")));
        
        if (req.getStatus() != null) {
            us.setStatus(Status.valueOf(req.getStatus().toUpperCase()));
        }
        orderR.save(us);
    }
}