package dao;

import java.util.List;

import bean.Centro;
import bean.Cliente;
import bean.Filter;
import bean.RequisicaoEstoque;
import bean.Usuario;
import bean.Produto.TipoProduto;
import bean.RequisicaoEstoque.Setor;
import bean.RequisicaoEstoque.Situacao;

public interface RequisicaoEstoqueDAO extends BaseDAO<RequisicaoEstoque> {

	double buscaQtdAprMesAnt(RequisicaoEstoque requisicao);

	public List<RequisicaoEstoque> listByExample(RequisicaoEstoque requisicaoEstoque,
			TipoProduto tipo, Usuario solicitante) throws Exception;

	List<RequisicaoEstoque> listPendentes(TipoProduto tipoProduto);

	List<RequisicaoEstoque> listPendentes(TipoProduto mc, Cliente cliente);

	List<RequisicaoEstoque> listPendentes(TipoProduto tipoProduto, List<Situacao> situacoes,
			Usuario usuSol, String sort, String dir, List<Filter> filtros);

	List<RequisicaoEstoque> listPendentes(TipoProduto tipoProduto, Situacao situacao);

	List<RequisicaoEstoque> listReqExpByCmp(Usuario usuario, String cmpReq);

	List<RequisicaoEstoque> listReqExpByCmp(Usuario usuario, String cmpReq, Setor setor);

	List<RequisicaoEstoque> listReqExpByCmp(Usuario usuario, String cmpReq, Setor setor,
			Centro centro);

	List<RequisicaoEstoque> listReqMatInsByCmp(Usuario usuario, String cmpReq);

	List<RequisicaoEstoque> listReqMatInsByCmp(Usuario usuario, String cmpReq, Setor setor);

	List<RequisicaoEstoque> listReqMatInsByCmp(Usuario usuario, String cmpReq, Setor setor,
			Centro centro);

	List<RequisicaoEstoque> listReqMatLimByCmp(Usuario usuario, String cmpReq);

	List<RequisicaoEstoque> listReqMatLimByCmp(Usuario usuario, String cmpReq, Setor setor);

	List<RequisicaoEstoque> listReqMatLimByCmp(Usuario usuario, String cmpReq, Setor setor,
			Centro centro);
}
