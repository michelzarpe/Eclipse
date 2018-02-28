package dao;

import java.util.List;

import bean.Produto;
import bean.Produto.TipoProduto;

public interface ProdutoDAO extends BaseDAO<Produto> {
	List<Produto> listAllByTipo(TipoProduto tipo);
}
