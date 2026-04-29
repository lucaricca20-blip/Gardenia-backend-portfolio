package com.betacom.pr.services.implementations;

import com.betacom.pr.Utilities.Mapper;
import com.betacom.pr.dto.inputs.WishlistReq;
import com.betacom.pr.dto.outputs.WishlistDTO;
import com.betacom.pr.models.Product;
import com.betacom.pr.models.User;
import com.betacom.pr.models.Wishlist;
import com.betacom.pr.repositories.IProductRepository;
import com.betacom.pr.repositories.IUserRepository;
import com.betacom.pr.repositories.IWishlistRepository;
import com.betacom.pr.services.interfaces.IWishlistServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishlistImpl implements IWishlistServices {

    private final IWishlistRepository wishlistR;
    private final IUserRepository userR;
    private final IProductRepository productR;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(WishlistReq req) throws Exception {
        log.debug("Aggiunta prodotto {} alla wishlist di {}", req.getProductId(), req.getUserName());

        Optional<Wishlist> existing = wishlistR.findByUser_UserNameAndProduct_Id(req.getUserName(), req.getProductId());
        if (existing.isPresent()) {
            log.info("Prodotto già presente nella wishlist");
            return;
        }

        User user = userR.findByUserName(req.getUserName())
                .orElseThrow(() -> new Exception("Utente non trovato"));
        
        Product product = productR.findById(req.getProductId())
                .orElseThrow(() -> new Exception("Prodotto non trovato"));

        Wishlist w = new Wishlist();
        w.setUser(user);
        w.setProduct(product);
        
        wishlistR.save(w);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Integer id) throws Exception {
        log.debug("Rimozione riga wishlist id: {}", id);
        if (!wishlistR.existsById(id)) {
            throw new Exception("Elemento wishlist non trovato");
        }
        wishlistR.deleteById(id);
    }

    @Override
    public List<WishlistDTO> getByUser(String userName) {
        log.debug("Recupero wishlist per utente: {}", userName);
        return wishlistR.findByUser_UserName(userName).stream()
                .map(Mapper::buildWishlistDTO)
                .collect(Collectors.toList());
    }
}