package action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.BasicResult;
import util.Util;
import bean.Colaborador;
import bean.Solicitacao;
import bean.Usuario;
import bean.Usuario.TipoUsuario;

import com.opensymphony.xwork2.ActionSupport;

import dao.SolicitacaoDAO;

@Results({ @Result(name = "success", value = "/paginas/jsp/pesquisa.jsp"),
		@Result(name = "input", value = "/paginas/jsp/pesquisa.jsp"),
		@Result(name = "resultXML", value = "xmlModel", type = BasicResult.class) })
public class PesquisaAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private Colaborador			colaborador			= new Colaborador();
	private Solicitacao			solicitacao			= new Solicitacao();
	private Date					datIni;
	private Date					datFin;
	private Date					datPro;
	private List<Solicitacao>	solicitacoes;
	Logger							logger				= Logger.getLogger(PesquisaAction.class);
	private Object					xmlModel;
	private String					start					= "";
	private String					limit					= "";
	private String					totalCount;
	private String					sort					= "";
	private String					dir					= "";
	private Class					tipo					= Solicitacao.class;
	@Autowired
	@Qualifier("HSolicitacaoDAO")
	private SolicitacaoDAO		solicitacaoDAO;
	@Autowired
	@Qualifier("Util")
	private Util util;

	public SolicitacaoDAO getSolicitacaoDAO() {
		return solicitacaoDAO;
	}

	public void setSolicitacaoDAO(SolicitacaoDAO solicitacaoDAO) {
		this.solicitacaoDAO = solicitacaoDAO;
	}

	public Class getTipo() {
		return tipo;
	}

	public Date getDatPro() {
		return datPro;
	}

	public void setDatPro(Date datPro) {
		this.datPro = datPro;
	}

	public void setTipo(Class tipo) {
		this.tipo = tipo;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
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

	private List<Solicitacao> consultaBD(boolean paginado) {
		if (start.equals("")) {
			start = "0";
		}
		if (limit.equals("")) {
			limit = "999999";
		}
		Map<String, Object> parametros = new HashMap<String, Object>();
		List<Solicitacao> subSol = new ArrayList<Solicitacao>();
		if ((colaborador.getNomFun() != null) && (!colaborador.getNomFun().equals("")))
			parametros.put("colaborador.nomFun", colaborador.getNomFun());
		if (colaborador.getNumCad() > 0)
			parametros.put("colaborador.numCad", colaborador.getNumCad());
		if ((datIni != null) && (datFin != null)) {
			parametros.put("datIni", datIni);
			parametros.put("datFin", datFin);
		}
		if (datPro != null) {
			parametros.put("datPro", datPro);
		}
		if (solicitacao.getNumSeq() > 0) {
			parametros.put("solicitacao.numSeq", this.solicitacao.getNumSeq());
		}
		if (solicitacao.getId() > 0) {
			parametros.put("id", this.solicitacao.getId());
		}
		Usuario usuario = (Usuario) util.recuperaSessao("Usuario");
		if (usuario.getTipUsu().equals(TipoUsuario.OUN))
			parametros.put("usuario", usuario);
		if (paginado == false) {
			solicitacoes = solicitacaoDAO.busca(parametros);
			subSol.addAll(solicitacoes);
		} else {// quando é dividido em páginas
			solicitacoes = solicitacaoDAO.busca(parametros, 0, 999999, sort.replace("_", "."), dir);
			subSol.addAll(solicitacoes.subList(Integer.valueOf(this.start),
					util.calculaUltimoIndice(solicitacoes, start, limit)));
		}
		return subSol;
	}

	public String buscaXML() {
		List<Solicitacao> subSol = new ArrayList<Solicitacao>();
		subSol = consultaBD(true);
		totalCount = String.valueOf(solicitacoes.size());
		setXmlModel(subSol);
		return "resultXML";
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public Date getDatFin() {
		return datFin;
	}

	public Date getDatIni() {
		return datIni;
	}

	public List<Solicitacao> getSolicitacoes() {
		return solicitacoes;
	}

	public String listResult() {
		return "listResult";
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public void setDatFin(Date datFin) {
		this.datFin = datFin;
	}

	public void setDatIni(Date datIni) {
		this.datIni = datIni;
	}

	public void setSolicitacoes(List<Solicitacao> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}
}
