package com.betacom.pr.services.implementations;

import java.util.List;
import java.util.Optional;

import com.betacom.pr.dto.inputs.AddressReq;
import com.betacom.pr.models.Address;
import com.betacom.pr.repositories.IAddressRepository;
import com.betacom.pr.services.interfaces.IAddressServices;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.pr.dto.inputs.LoginReq;
import com.betacom.pr.dto.inputs.UserReq;
import com.betacom.pr.dto.outputs.LoginDTO;
import com.betacom.pr.dto.outputs.UserDTO;
import com.betacom.pr.enums.Roles;
import com.betacom.pr.exceptions.WebServiceExceptions;
import com.betacom.pr.models.User;
import com.betacom.pr.repositories.IUserRepository;
import com.betacom.pr.services.interfaces.IMessaggioServices;
import com.betacom.pr.services.interfaces.IUserServices;
import com.betacom.pr.services.interfaces.IMailServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserImpl implements IUserServices {

	private final IUserRepository usR;
	private final IMessaggioServices msgS;
	// private final IAddressServices addS;
	private final IAddressRepository addR;
	private final PasswordEncoder passwordEncoder;
	private final IMailServices mailS;
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void create(UserReq req) throws Exception {
		log.debug("create {}", req);
		
		if(usR.existsById(req.getUserName()))
			throw new WebServiceExceptions(msgS.get("user_exist"));

		User us = new User();
		us.setUserName(req.getUserName());
		us.setFirstName(req.getFirstName());
		us.setLastName(req.getLastName());
		us.setEmail(req.getEmail());
		us.setPhone(req.getPhone());
		us.setPassword(passwordEncoder.encode(req.getPassword()));
		us.setRole(Roles.USER);
		us.setEnabled(false);
		usR.save(us);
		mailS.sendValidationEmail(us.getEmail(), us.getUserName());
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void update(UserReq req) throws Exception {
		log.debug("update {}", req);

		User us = usR.findById(req.getUserName()) 
				.orElseThrow(() -> new WebServiceExceptions(msgS.get("user_ntfnd")));


		if(req.getEmail() != null)
			us.setEmail(req.getEmail());
		if(req.getPhone() != null)
			us.setPhone(req.getPhone());
		if(req.getPassword() != null)
			us.setPassword(passwordEncoder.encode(req.getPassword()));
		if(req.getRole() != null)
			us.setRole(Roles.valueOf(req.getRole()));

		usR.save(us);

	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void delete(String userName) throws Exception {
		log.debug("delete {}", userName);
		User us = usR.findById(userName) 
				.orElseThrow(() -> new WebServiceExceptions(msgS.get("user_ntfnd"))); 

		usR.delete(us);

	}

	@Override
	public List<UserDTO> list() {
		log.debug("list");
		List<User> lU = usR.findAll();

		return lU.stream()
				.map((u -> UserDTO.builder()
						.userName(u.getUserName())
						.firstName(u.getFirstName())
						.lastName(u.getLastName())
						.email(u.getEmail())
						.phone(u.getPhone())
						.role(u.getRole().toString())
						.build())
						).toList();
	}

	@Override
	public UserDTO getByUserName(String userName) throws Exception {
		log.debug("getByUserName {}", userName);
		User u = usR.findById(userName) 
				.orElseThrow(() -> new Exception(msgS.get("user_ntfnd")));
		
		return UserDTO.builder()
				.userName(u.getUserName())
				.firstName(u.getFirstName())
				.lastName(u.getLastName())
				.email(u.getEmail())
				.phone(u.getPhone())
				.role(u.getRole().toString())
				.build();
	}

	@Override
	public void addAddress(String userName, Integer addId) throws Exception {
		log.debug("addAddress {}", addId);

		Address address = addR.findById(addId)
				.orElseThrow(() -> new Exception(msgS.get("address_ntfnd")));

		User user = usR.findById(userName)
				.orElseThrow(() -> new Exception(msgS.get("user_ntfnd")));

		if (user.getAddresses().contains(address))
			throw new Exception(msgS.get("address_already_linked"));

		user.getAddresses().add(address);
		usR.save(user);
	}

	@Override
	public LoginDTO login(LoginReq req) throws Exception {
				User us = usR.findById(req.getUserName()) 
				.orElseThrow(() -> new Exception(msgS.get("user_ntfnd")));
		
		if (!passwordEncoder.matches(req.getPassword(), us.getPassword()))
			throw new Exception(msgS.get("login_invalid"));
		if (!us.getEnabled()) {
			throw new Exception("You must validate your email before logging in!");
		}
		return LoginDTO.builder()
				.id(us.getUserName())
				.role(us.getRole().toString())
				.build();	
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void mailValidate(String id) throws Exception {
		log.debug("mailValidate {}", id);
		
		User us = usR.findById(id)
				.orElseThrow(() -> new WebServiceExceptions(msgS.get("user_ntfnd")));

		if (us.getEnabled()) {
			throw new WebServiceExceptions("Account already validated."); 
		}

		us.setEnabled(true);
		usR.save(us);
	}
	@Override
	public void requestPasswordReset(String userName) throws Exception {
		
		User us = usR.findById(userName)
				.orElseThrow(() -> new WebServiceExceptions("User not found"));
			
		mailS.sendResetPasswordEmail(us.getEmail(), us.getUserName());
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void changePassword(com.betacom.pr.dto.inputs.ChangePwdReq req) throws Exception {
		
		User us = usR.findById(req.getUserName())
				.orElseThrow(() -> new WebServiceExceptions("User not found"));
		
		us.setPassword(passwordEncoder.encode(req.getNewPassword()));
		usR.save(us);
	}
}
