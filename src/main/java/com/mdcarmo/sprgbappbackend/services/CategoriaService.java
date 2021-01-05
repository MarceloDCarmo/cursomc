package com.mdcarmo.sprgbappbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mdcarmo.sprgbappbackend.domain.Categoria;
import com.mdcarmo.sprgbappbackend.repositories.CategoriaRepository;
import com.mdcarmo.sprgbappbackend.services.exception.DataIntegrityException;
import com.mdcarmo.sprgbappbackend.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		Optional<Categoria> cat = repo.findById(id);
		return cat.orElseThrow(() -> new ObjectNotFoundException("Objeto não econtrado! - Id: " + id + " - Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria cat) {
		cat.setId(null);
		return repo.save(cat);
	}
	
	public Categoria update(Categoria cat) {
		find(cat.getId());
		return repo.save(cat);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException ex) {
			throw new DataIntegrityException("Não é possível excluir Categorias que possuam produtos!");
		}
	}

	public List<Categoria> findAll() {
		return repo.findAll();
	}
}
