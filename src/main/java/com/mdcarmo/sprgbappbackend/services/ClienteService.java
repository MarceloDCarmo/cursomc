package com.mdcarmo.sprgbappbackend.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdcarmo.sprgbappbackend.domain.Cliente;
import com.mdcarmo.sprgbappbackend.repositories.ClienteRepository;
import com.mdcarmo.sprgbappbackend.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		Optional<Cliente> cat = repo.findById(id);
		return cat.orElseThrow(() -> new ObjectNotFoundException("Objeto não econtrado! - Id: " + id + " - Tipo: " + Cliente.class.getName()));
	}
}
