package com.betacom.pr.dto.inputs;

import lombok.Data;

@Data
public class ChangePwdReq {
	private String userName;
	private String newPassword;
}