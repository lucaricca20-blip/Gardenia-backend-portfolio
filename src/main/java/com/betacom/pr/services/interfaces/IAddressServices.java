package com.betacom.pr.services.interfaces;

import com.betacom.pr.dto.inputs.AddressReq;
import com.betacom.pr.dto.outputs.AddressDTO;

import java.util.List;

public interface IAddressServices {
    Integer create(AddressReq req) throws Exception;
    void createAndAssign(AddressReq req, String userName) throws Exception;
    void update(AddressReq req) throws Exception;
    void delete(Integer id) throws Exception;

List<AddressDTO> list() throws Exception;
    AddressDTO findById(Integer id) throws Exception;

    List<AddressDTO> findByUserName(String userName) throws Exception;
}
