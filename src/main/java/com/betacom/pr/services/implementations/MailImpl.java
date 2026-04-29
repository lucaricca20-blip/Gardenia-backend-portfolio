package com.betacom.pr.services.implementations;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.betacom.pr.services.interfaces.IMailServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailImpl implements IMailServices {

	private final JavaMailSender emailSender;

	@Override
	public void sendValidationEmail(String toEmail, String userName) throws Exception {
		log.debug("Invio email di validazione a: {}", toEmail);
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("gardeniainfo67@gmail.com"); 
		message.setTo(toEmail);
		message.setSubject("Confirm your registration on Gardenia!");
		
		
		String link = "http://localhost:4200/emailValidation/" + userName;
		message.setText("Hello " + userName + ",\n\n"
				+ "Welcome to Gardenia! Please click the link below to confirm your account:\n"
				+ link + "\n\n"
				+ "See you soon!");
		
		emailSender.send(message);
	}
	@Override
	public void sendResetPasswordEmail(String toEmail, String userName) throws Exception {
		log.debug("Invio email di reset password a: {}", toEmail);
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("gardeniainfo67@gmail.com"+ ""); 
		message.setTo(toEmail);
		message.setSubject("Reset your Gardenia password");
		
	
		String link = "http://localhost:4200/reset-password/" + userName;
		message.setText("Hello " + userName + ",\n\n"
				+ "You requested to reset your password. Click the link below to choose a new one:\n"
				+ link + "\n\n"
				+ "If you did not request a password reset, please ignore this email.\n\n"
				+ "Best regards,\nThe Gardenia Team");
		
		emailSender.send(message);
	}
}