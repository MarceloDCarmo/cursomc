package com.mdcarmo.sprgbappbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mdcarmo.sprgbappbackend.domain.Cliente;
import com.mdcarmo.sprgbappbackend.repositories.ClienteRepository;
import com.mdcarmo.sprgbappbackend.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ClienteRepository clienteRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cliente = clienteRepo.findByEmail(email);
		if(cliente == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return new UserSS(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfis());
	}

}
