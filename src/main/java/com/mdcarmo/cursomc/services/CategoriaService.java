package com.mdcarmo.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdcarmo.cursomc.domain.Categoria;
import com.mdcarmo.cursomc.repositories.CategoriaRepository;
import com.mdcarmo.cursomc.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> cat = repo.findById(id);
		return cat.orElseThrow(() -> new ObjectNotFoundException("Obejeto não econtrado! - Id: " + id + " - Tipo: " + Categoria.class.getName()));
	}
}
