package dao;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Order;

import bean.ItemSolicitacao;
import bean.ItemSolicitacao.Situacao;
import bean.Solicitacao;
import bean.Uniforme;
import bean.Usuario;

public interface ItemSolicitacaoDAO extends BaseDAO<ItemSolicitacao> {

	boolean isForaPrazo(int idColaborador, Uniforme uniforme);

	ItemSolicitacao loadById(int idSol, int idUni);

	Date getDataPermitidaTroca(int idColaborador, Uniforme uniforme, Date dataConsulta);

	ItemSolicitacao loadByChaveVetorh(int numEmp, int numCad, int codEpi, Date datEnt);

	List<ItemSolicitacao> listPendentesEnvio(int codCcu) throws Exception;

	int getTotalSLItem(int codEpi, int codEmp);

	int getTotalSLItem(int codEpi, boolean consideraPL);

	List<ItemSolicitacao> getTransferencias() throws Exception;

	List<ItemSolicitacao> listPendentesAdmissao() throws Exception;

	List<ItemSolicitacao> listByPrevDate(List<Situacao> situacoes, int qtdDias, List<Order> ordens,
			Usuario usuario) throws Exception;

	List<ItemSolicitacao> listNaoAtendidos() throws Exception;

	List<Solicitacao> listLiberados(Date datIni, Date datFin, Usuario usuario) throws Exception;

	List<Solicitacao> listNegados(Date datIni, Date datFin, Usuario usuario) throws Exception;

	List<ItemSolicitacao> listBySituacao(List<Situacao> situacoes, List<Integer> motivos,
			boolean onlyImpl) throws Exception;

	int getSaldo(int idCol, Uniforme uniforme, Date dataFinal, Date dataInicial) throws Exception;

}
