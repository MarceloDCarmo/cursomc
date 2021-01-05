package com.mdcarmo.sprgbappbackend.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdcarmo.sprgbappbackend.domain.Pedido;
import com.mdcarmo.sprgbappbackend.repositories.PedidoRepository;
import com.mdcarmo.sprgbappbackend.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	public Pedido find(Integer id) {
		Optional<Pedido> cat = repo.findById(id);
		return cat.orElseThrow(() -> new ObjectNotFoundException("Objeto não econtrado! - Id: " + id + " - Tipo: " + Pedido.class.getName()));
	}
}
