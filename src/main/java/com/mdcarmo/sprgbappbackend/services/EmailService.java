package com.mdcarmo.sprgbappbackend.services;

import org.springframework.mail.SimpleMailMessage;

import com.mdcarmo.sprgbappbackend.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage smm);
}
