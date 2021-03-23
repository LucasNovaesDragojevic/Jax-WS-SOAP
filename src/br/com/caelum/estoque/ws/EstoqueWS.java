package br.com.caelum.estoque.ws;

import java.util.List;

import javax.jws.WebService;

import br.com.caelum.estoque.modelo.item.Item;
import br.com.caelum.estoque.modelo.item.ItemDao;

@WebService
public class EstoqueWS {

	private ItemDao dao = new ItemDao();
	
	public List<Item> getItems() {
		System.out.println("Chamando getItens()");
		List<Item> itens = dao.todosItens();
		return itens;
	}
}
