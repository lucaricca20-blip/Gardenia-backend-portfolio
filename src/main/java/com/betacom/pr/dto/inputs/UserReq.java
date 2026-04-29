package com.betacom.pr.dto.inputs;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserReq {
	
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String password;
	private String role;

}
