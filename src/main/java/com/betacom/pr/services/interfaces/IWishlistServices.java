package com.betacom.pr.services.interfaces;

import com.betacom.pr.dto.inputs.WishlistReq;
import com.betacom.pr.dto.outputs.WishlistDTO;
import java.util.List;

public interface IWishlistServices {
    void add(WishlistReq req) throws Exception;
    void remove(Integer id) throws Exception;
    List<WishlistDTO> getByUser(String userName);
}