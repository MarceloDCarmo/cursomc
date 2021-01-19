package com.mdcarmo.sprgbappbackend.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mdcarmo.sprgbappbackend.domain.Cidade;
import com.mdcarmo.sprgbappbackend.domain.Cliente;
import com.mdcarmo.sprgbappbackend.domain.Endereco;
import com.mdcarmo.sprgbappbackend.domain.enums.Perfil;
import com.mdcarmo.sprgbappbackend.domain.enums.TipoCliente;
import com.mdcarmo.sprgbappbackend.dto.ClienteDTO;
import com.mdcarmo.sprgbappbackend.dto.ClienteNewDTO;
import com.mdcarmo.sprgbappbackend.repositories.ClienteRepository;
import com.mdcarmo.sprgbappbackend.repositories.EnderecoRepository;
import com.mdcarmo.sprgbappbackend.security.UserSS;
import com.mdcarmo.sprgbappbackend.services.exception.AuthorizationException;
import com.mdcarmo.sprgbappbackend.services.exception.DataIntegrityException;
import com.mdcarmo.sprgbappbackend.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private BCryptPasswordEncoder pe;
	@Autowired
	private S3Service s3Service;

	public Cliente find(Integer id) {

		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}

		Optional<Cliente> cliente = repo.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não econtrado! - Id: " + id + " - Tipo: " + Cliente.class.getName()));
	}

	public Cliente update(Cliente cliente) {
		Cliente newCliente = find(cliente.getId());
		updateData(newCliente, cliente);
		return repo.save(newCliente);
	}

	private void updateData(Cliente newCliente, Cliente cliente) {
		newCliente.setNome(cliente.getNome());
		newCliente.setEmail(cliente.getEmail());
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException ex) {
			throw new DataIntegrityException("Não é possível excluir porque há Pedidos relacionados");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO dto) {
		return new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), null, null, null);
	}

	public Cliente fromDTO(ClienteNewDTO dto) {
		Cliente cliente = new Cliente(null, dto.getNome(), dto.getEmail(), dto.getCpfOuCnpj(),
				TipoCliente.toEnum(dto.getTipo()), pe.encode(dto.getSenha()));
		Cidade cidade = new Cidade(dto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, dto.getLogradouro(), dto.getNumero(), dto.getComplemento(), dto.getBairro(),
				dto.getCep(), cliente, cidade);
		cliente.getEnderecos().add(end);
		cliente.getTelefones().add(dto.getTelefone1());
		if (dto.getTelefone2() != null)
			cliente.getTelefones().add(dto.getTelefone2());
		if (dto.getTelefone3() != null)
			cliente.getTelefones().add(dto.getTelefone3());
		return cliente;
	}

	@Transactional
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		cliente = repo.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
		return repo.save(cliente);
	}

	public URI uploadProfilePicture(MultipartFile file) {
		UserSS user = UserService.authenticated();

		Cliente cliente = repo.findById(user.getId()).orElseThrow(() -> new AuthorizationException("Acesso negado!"));
		URI uri = s3Service.uploadFile(file);
		cliente.setImgUrl(uri.toString());
		repo.save(cliente);
		
		return s3Service.uploadFile(file);
	}
}
