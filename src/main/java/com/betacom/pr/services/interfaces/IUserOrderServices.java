package com.betacom.pr.services.interfaces;

import java.util.List;

import com.betacom.pr.dto.inputs.UserOrderReq;
import com.betacom.pr.dto.outputs.UserOrderDTO;

public interface IUserOrderServices {

    void create(UserOrderReq req) throws Exception;
    void update(UserOrderReq req) throws Exception;
    void updateStatus(UserOrderReq req) throws Exception;
    UserOrderDTO getById(Integer id) throws Exception;
    List<UserOrderDTO> getByUserId(String userName);
    List<UserOrderDTO> getAll();
}