package action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.BasicResult;
import result.XMLResult;
import util.Util;
import bean.Acesso;
import bean.Colaborador;
import bean.Solicitacao;
import bean.Usuario;
import bean.Usuario.TipoUsuario;

import com.opensymphony.xwork2.ActionSupport;

import dao.ColaboradorDAO;
import dao.SolicitacaoDAO;

@Results({
		@Result(name = "homeGeral", type = ServletRedirectResult.class, value = "pesquisa!input.action"),
		@Result(name = "resultXML", value = "xmlModel", type = XMLResult.class),
		@Result(name = "resultBasic", value = "xmlModel", type = BasicResult.class) })
public class HomeAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	List<Solicitacao>				liberacoes;
	List<Acesso>					acessos;
	private int						idSol;
	private InputStream			inputStream;
	private String					start					= "";
	private String					limit					= "";
	private String					totalCount;
	private String					sort;
	private String					dir;
	private Object					xmlModel;
	private Class					tipo;
	@Autowired
	@Qualifier("HColaboradorDAO")
	private ColaboradorDAO		colaboradorDAO;
	@Autowired
	@Qualifier("HSolicitacaoDAO")
	private SolicitacaoDAO		solicitacaoDAO;
	@Autowired
	@Qualifier("Util")
	private Util util;

	public ColaboradorDAO getColaboradorDAO() {
		return colaboradorDAO;
	}

	public void setColaboradorDAO(ColaboradorDAO colaboradorDAO) {
		this.colaboradorDAO = colaboradorDAO;
	}

	public SolicitacaoDAO getSolicitacaoDAO() {
		return solicitacaoDAO;
	}

	public void setSolicitacaoDAO(SolicitacaoDAO solicitacaoDAO) {
		this.solicitacaoDAO = solicitacaoDAO;
	}

	public Class getTipo() {
		return tipo;
	}

	public void setTipo(Class tipo) {
		this.tipo = tipo;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public Object getXmlModel() {
		return xmlModel;
	}

	public void setXmlModel(Object xmlModel) {
		this.xmlModel = xmlModel;
	}

	public List<Acesso> getAcessos() {
		return acessos;
	}

	public void setAcessos(List<Acesso> acessos) {
		this.acessos = acessos;
	}

	public int getIdSol() {
		return idSol;
	}

	public void setIdSol(int idSol) {
		this.idSol = idSol;
	}

	public List<Solicitacao> getLiberacoes() {
		return liberacoes;
	}

	public void setLiberacoes(List<Solicitacao> liberacoes) {
		this.liberacoes = liberacoes;
	}

	public String listAdm() throws Exception {
		Usuario usuarioLogado = (Usuario) util.recuperaSessao("Usuario");
		List<Colaborador> colaboradores = new ArrayList<Colaborador>();
		List<Colaborador> colList = new ArrayList<Colaborador>();
		if (usuarioLogado.getTipUsu().equals(TipoUsuario.ASI)	|| usuarioLogado.getTipUsu().equals(TipoUsuario.OAD) || usuarioLogado.getTipUsu().equals(TipoUsuario.AAU)) {
			colaboradores = colaboradorDAO.listBySituacao(1000, 0, 999999, sort.replace("_", "."), dir);
		} else {
			colaboradores = colaboradorDAO.listBySituacao(1000, usuarioLogado);
		}
		colList.addAll(colaboradores.subList(Integer.valueOf(this.start),
				util.calculaUltimoIndice(colaboradores, start, limit)));
		this.totalCount = String.valueOf(colaboradores.size());
		this.tipo = Colaborador.class;
		setXmlModel(colList);
		return "resultBasic";
	}

	public String listSol() throws Exception {
		Usuario usuarioLogado = (Usuario) util.recuperaSessao("Usuario");
		List<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();
		List<Solicitacao> subSol = new ArrayList<Solicitacao>();
		if (sort.indexOf(".") > 0)
			sort = sort.substring(sort.indexOf(".") + 1);
		if (usuarioLogado.getTipUsu().equals(TipoUsuario.OUN)) {
			solicitacoes = solicitacaoDAO.listAllByActualDateByUsuario(usuarioLogado, 0, 999999,
					sort.replace("_", "."), dir);
		} else if (usuarioLogado.getTipUsu().equals(TipoUsuario.OAL)) {
			solicitacoes = solicitacaoDAO.listPendentes(0, 999999, sort.replace("_", "."), dir);
		} else if (usuarioLogado.getTipUsu().equals(TipoUsuario.ASI)) {
			solicitacoes = solicitacaoDAO.listPendentes(0, 999999, sort.replace("_", "."), dir);
		}
		subSol.addAll(solicitacoes.subList(Integer.valueOf(this.start),
				util.calculaUltimoIndice(solicitacoes, start, limit)));
		this.totalCount = String.valueOf(solicitacoes.size());
		setXmlModel(subSol);
		this.tipo = Solicitacao.class;
		return "resultBasic";
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

}
