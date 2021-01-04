package com.mdcarmo.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mdcarmo.cursomc.domain.Cliente;
import com.mdcarmo.cursomc.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;

	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {

		Cliente cat = service.buscar(id);
		return ResponseEntity.ok(cat);
	}
}
