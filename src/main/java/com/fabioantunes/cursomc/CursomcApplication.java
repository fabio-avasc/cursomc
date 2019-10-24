package com.fabioantunes.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fabioantunes.cursomc.domain.Categoria;
import com.fabioantunes.cursomc.domain.Cidade;
import com.fabioantunes.cursomc.domain.Cliente;
import com.fabioantunes.cursomc.domain.Endereco;
import com.fabioantunes.cursomc.domain.Estado;
import com.fabioantunes.cursomc.domain.ItemPedido;
import com.fabioantunes.cursomc.domain.Pagamento;
import com.fabioantunes.cursomc.domain.PagamentoComBoleto;
import com.fabioantunes.cursomc.domain.PagamentoComCartao;
import com.fabioantunes.cursomc.domain.Pedido;
import com.fabioantunes.cursomc.domain.Produto;
import com.fabioantunes.cursomc.domain.enums.EstadoPagamento;
import com.fabioantunes.cursomc.domain.enums.TipoCliente;
import com.fabioantunes.cursomc.repositories.CategoriaRepository;
import com.fabioantunes.cursomc.repositories.CidadeRepository;
import com.fabioantunes.cursomc.repositories.ClienteRepository;
import com.fabioantunes.cursomc.repositories.EnderecoRepository;
import com.fabioantunes.cursomc.repositories.EstadoRepository;
import com.fabioantunes.cursomc.repositories.ItemPedidoRepository;
import com.fabioantunes.cursomc.repositories.PagamentoRepository;
import com.fabioantunes.cursomc.repositories.PedidoRepository;
import com.fabioantunes.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Escritório");
		Categoria cat2 = new Categoria(null, "Informática");
		
		Produto p1 = new Produto(null,"Computador", 2000.00);
		Produto p2 = new Produto(null,"Impressora", 800.00);
		Produto p3 = new Produto(null,"Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		Estado e1 = new Estado(null, "São Paulo");
		Estado e2 = new Estado(null, "Minas Gerais");
		
		Cidade c1 = new Cidade(null, "São Paulo", e1);
		Cidade c2 = new Cidade(null, "Campinas", e1);
		Cidade c3 = new Cidade(null, "Uberlândia", e2);

		e1.getCidades().addAll(Arrays.asList(c1,c2));
		e2.getCidades().addAll(Arrays.asList(c3));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		estadoRepository.saveAll(Arrays.asList(e1,e2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377",TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323","93838393"));
		
		Endereco end1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cli1, c3);
		Endereco end2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c1);
		
		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(end1, end2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		
		Pedido pd1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, end1);
		Pedido pd2 = new Pedido(null, sdf.parse("10/10/2017 10:32"), cli1, end1);
		Pagamento pg1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pd1, 6);
		pd1.setPagamento(pg1);
		Pagamento pg2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pd2, sdf.parse("20/10/2017 00:00"), null);
		pd2.setPagamento(pg2);
		
		cli1.getPedidos().addAll(Arrays.asList(pd1,  pd2));
		
		pedidoRepository.saveAll(Arrays.asList(pd1,pd2));
		pagamentoRepository.saveAll(Arrays.asList(pg1,pg2));
		
		ItemPedido ip1 = new ItemPedido(pd1, p1, 200.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(pd1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(pd2, p2, 100.00, 1, 800.00);
		
		pd1.getItens().addAll(Arrays.asList(ip1,ip2));
		pd2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
	}

}
