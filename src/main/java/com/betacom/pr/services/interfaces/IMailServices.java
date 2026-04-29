package com.betacom.pr.services.interfaces;

public interface IMailServices {
	void sendValidationEmail(String toEmail, String userName) throws Exception;
	void sendResetPasswordEmail(String toEmail, String userName) throws Exception;
}