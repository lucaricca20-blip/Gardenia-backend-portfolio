package com.betacom.pr.dto.outputs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class UserDTO {
	
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String role;

}
