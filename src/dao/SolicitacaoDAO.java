package dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;

import bean.Colaborador;
import bean.ItemSolicitacao;
import bean.Solicitacao;
import bean.Usuario;
import bean.ItemSolicitacao.Situacao;

public interface SolicitacaoDAO extends BaseDAO<Solicitacao> {

	Solicitacao loadByActualDate(int idCol);

	List<Solicitacao> busca(Map<String, Object> parametros);

	List<Solicitacao> busca(Map<String, Object> parametros, int start, int limit, String sort,
			String dir);

	List<Solicitacao> listAllByActualDateByUsuario(Usuario usuario, Integer start, Integer limit,
			String sort, String dir);

	List<Solicitacao> listAllByActualDateByUsuario(Usuario usuario);

	List<Solicitacao> listAllByActualDate(int motivo);

	List<Solicitacao> listAllByPrevDate() throws Exception;

	List<Solicitacao> listByPrevDate(List<Situacao> situacoes, int qtdDias, int start, int limit,
			String sort, String dir, Usuario usuarioLogado) throws Exception;

	List<Solicitacao> listByPrevDate(List<Situacao> situacoes, int qtdDias, List<Order> ordens);

	List<Solicitacao> listPendentesAdmissaoByColaborador(Colaborador colaborador);

	Solicitacao loadByChaveVetorh(int numEmp, int numCad, Date datEnt);

	List<Solicitacao> listAllByEmp(int numEmp);

	List<Solicitacao> listPendentes(Integer start, Integer limit, String sort, String dir);

	List<Solicitacao> listPendentesAdmissao() throws Exception;

	List<ItemSolicitacao> litAllByCol(int id);

	List<Solicitacao> listSolAdmitidos(List<Situacao> situacoes) throws Exception;

	List<Solicitacao> listPendentesEnvio(List<Situacao> situacoes) throws Exception;

	List<Solicitacao> listSolAberto(Colaborador colaborador) throws Exception;

	List<Solicitacao> listPendentesAdmissao(Integer start, Integer limit, String sort, String dir)
			throws Exception;
}
