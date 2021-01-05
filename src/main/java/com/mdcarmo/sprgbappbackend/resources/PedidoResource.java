package com.mdcarmo.sprgbappbackend.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mdcarmo.sprgbappbackend.domain.Pedido;
import com.mdcarmo.sprgbappbackend.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService service;

	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {

		Pedido cat = service.find(id);
		return ResponseEntity.ok(cat);
	}
}
