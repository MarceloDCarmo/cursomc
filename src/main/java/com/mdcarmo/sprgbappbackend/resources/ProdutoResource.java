package com.mdcarmo.sprgbappbackend.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mdcarmo.sprgbappbackend.domain.Produto;
import com.mdcarmo.sprgbappbackend.dto.ProdutoDTO;
import com.mdcarmo.sprgbappbackend.resources.utils.URL;
import com.mdcarmo.sprgbappbackend.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService service;

	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Produto produto = service.find(id);
		return ResponseEntity.ok(produto);
	}
	
	@GetMapping
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "") String categorias,
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome")String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC")String direction){
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> list = URL.decodeIntList(categorias);
		Page<Produto> Produtos = service.search(nomeDecoded, list, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> ProdutosDTO = Produtos.map(produto -> new ProdutoDTO(produto));
		return ResponseEntity.ok(ProdutosDTO);
	}
}
