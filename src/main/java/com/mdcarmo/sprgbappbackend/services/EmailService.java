package com.mdcarmo.sprgbappbackend.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.mdcarmo.sprgbappbackend.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage smm);

	void sendOrdemConfirmationHtmlEmail(Pedido pedido);
	
	void sendHtmlEmail(MimeMessage mm);
	
}
