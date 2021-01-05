package com.mdcarmo.sprgbappbackend.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mdcarmo.sprgbappbackend.domain.Categoria;
import com.mdcarmo.sprgbappbackend.dto.CategoriaDTO;
import com.mdcarmo.sprgbappbackend.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;

	@GetMapping("/{id}")
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria cat = service.find(id);
		return ResponseEntity.ok(cat);
	}
	
	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> findAll(){
		List<Categoria> categorias = service.findAll();
		List<CategoriaDTO> categoriasDTO = categorias.stream().map(cat -> new CategoriaDTO(cat)).collect(Collectors.toList());
		return ResponseEntity.ok(categoriasDTO);
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome")String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC")String direction){
		Page<Categoria> categorias = service.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> categoriasDTO = categorias.map(cat -> new CategoriaDTO(cat));
		return ResponseEntity.ok(categoriasDTO);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody Categoria cat){
		cat = service.insert(cat);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cat.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody Categoria cat, @PathVariable Integer id){
		cat.setId(id);
		cat = service.update(cat);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
