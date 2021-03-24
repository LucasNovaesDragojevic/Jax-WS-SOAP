package br.com.caelum.estoque.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import br.com.caelum.estoque.exception.AutorizacaoException;
import br.com.caelum.estoque.modelo.item.Filtro;
import br.com.caelum.estoque.modelo.item.Filtros;
import br.com.caelum.estoque.modelo.item.Item;
import br.com.caelum.estoque.modelo.item.ItemDao;
import br.com.caelum.estoque.modelo.item.ItemValidador;
import br.com.caelum.estoque.modelo.item.ListaItens;
import br.com.caelum.estoque.modelo.usuario.TokenDao;
import br.com.caelum.estoque.modelo.usuario.TokenUsuario;

@WebService
public class EstoqueWS {

	private ItemDao dao = new ItemDao();
	
	@WebMethod(operationName = "TodosOsItens")
	@WebResult(name = "itens")
	public ListaItens getItems(
			@WebParam(name = "filtros") Filtros filtros) 
	{
		System.out.println("Chamando getItens()");
		List<Filtro> listaFiltros = filtros.getLista();
		List<Item> itens = dao.todosItens(listaFiltros);
		return new ListaItens(itens);
	}
	
	@WebMethod(operationName = "CadastrarItem")
	@WebResult(name = "item")
	public Item cadastrarItem(
			@WebParam(name = "token", header = true) TokenUsuario token, 
			@WebParam(name = "item") Item item) 
			throws AutorizacaoException 
	{
		System.out.println("Cadastrando item");
		boolean ehValido = new TokenDao().ehValido(token);
		
		if (!ehValido) 
			throw new AutorizacaoException("Falha de autorização");
		
		new ItemValidador(item).validate();
		
		dao.cadastrar(item);
		return item;
	}
}
