package com.betacom.pr.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.betacom.pr.dto.inputs.AddressReq;
import com.betacom.pr.dto.inputs.ChangePwdReq;
import com.betacom.pr.dto.inputs.LoginReq;
import com.betacom.pr.dto.inputs.UserReq;
import com.betacom.pr.dto.outputs.LoginDTO;
import com.betacom.pr.dto.outputs.UserDTO;
import com.betacom.pr.models.User;


public interface IUserServices {
	
	void create(UserReq req) throws Exception;
	void update(UserReq req) throws Exception;
	void delete(String userName) throws Exception;
	void mailValidate(String id) throws Exception;
	LoginDTO login(LoginReq req) throws Exception;
	List<UserDTO> list();
	UserDTO getByUserName(String userName) throws Exception;

	void addAddress(String userName, Integer addId) throws Exception;
	void requestPasswordReset(String userName) throws Exception;
	void changePassword(ChangePwdReq req) throws Exception;

}
