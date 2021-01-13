package com.mdcarmo.sprgbappbackend.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mdcarmo.sprgbappbackend.domain.Cliente;
import com.mdcarmo.sprgbappbackend.repositories.ClienteRepository;
import com.mdcarmo.sprgbappbackend.services.exception.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private BCryptPasswordEncoder pe;
	@Autowired
	private EmailService emailService;
	
	private Random rdn = new Random();
	
	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		if(cliente == null)
			throw new ObjectNotFoundException("Email não encontrado");
	
		String newPass = newPassword();
		cliente.setSenha(pe.encode(newPass));
		
		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPass);
		
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < vet.length; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rdn.nextInt(3);
		switch(opt) {
			case 0: //gera um dígito
				return (char) (rdn.nextInt(10) + 48);
			case 1: //gera letra maiúscula
				return (char) (rdn.nextInt(26) + 65);
			default://gera letra minúsucla
				return (char) (rdn.nextInt(26) + 97);
		}	
	}
	
}
