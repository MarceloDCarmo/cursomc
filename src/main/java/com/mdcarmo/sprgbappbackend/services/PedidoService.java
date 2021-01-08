package com.mdcarmo.sprgbappbackend.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mdcarmo.sprgbappbackend.domain.ItemPedido;
import com.mdcarmo.sprgbappbackend.domain.PagamentoBoleto;
import com.mdcarmo.sprgbappbackend.domain.Pedido;
import com.mdcarmo.sprgbappbackend.domain.enums.EstadoPagamento;
import com.mdcarmo.sprgbappbackend.repositories.ItemPedidoRepository;
import com.mdcarmo.sprgbappbackend.repositories.PagamentoRepository;
import com.mdcarmo.sprgbappbackend.repositories.PedidoRepository;
import com.mdcarmo.sprgbappbackend.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	@Autowired
	private BoletoService boletoService;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id) {
		Optional<Pedido> cat = repo.findById(id);
		return cat.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não econtrado! - Id: " + id + " - Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido insert(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.setCliente(clienteService.find(pedido.getCliente().getId()));
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
				if(pedido.getPagamento() instanceof PagamentoBoleto) {
			PagamentoBoleto pagto = (PagamentoBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoBoleto(pagto, pedido.getInstante());
		}
		pedido = repo.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		for (ItemPedido item : pedido.getItens()) {
			item.setDesconto(0.0);
			item.setProduto(produtoService.find(item.getProduto().getId()));
			item.setPreco(item.getProduto().getPreco());
			item.setPedido(pedido);
		}
		
		itemPedidoRepository.saveAll(pedido.getItens());
		emailService.sendOrdemConfirmationHtmlEmail(pedido);
		return pedido;
	}

}
