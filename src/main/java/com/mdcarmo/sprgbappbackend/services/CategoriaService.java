package com.mdcarmo.sprgbappbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mdcarmo.sprgbappbackend.domain.Categoria;
import com.mdcarmo.sprgbappbackend.dto.CategoriaDTO;
import com.mdcarmo.sprgbappbackend.repositories.CategoriaRepository;
import com.mdcarmo.sprgbappbackend.services.exception.DataIntegrityException;
import com.mdcarmo.sprgbappbackend.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id) {
		Optional<Categoria> cat = repo.findById(id);
		return cat.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não econtrado! - Id: " + id + " - Tipo: " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria cat) {
		cat.setId(null);
		return repo.save(cat);
	}

	public Categoria update(Categoria cliente) {
		Categoria newCategoria = find(cliente.getId());
		updateData(newCategoria, cliente);
		return repo.save(newCategoria);
	}

	private void updateData(Categoria newCategoria, Categoria cliente) {
		newCategoria.setNome(cliente.getNome());
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

	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Categoria fromDTO(CategoriaDTO dto) {
		return new Categoria(dto.getId(), dto.getNome());
	}
}
