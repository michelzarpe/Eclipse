package action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.StreamResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.JSONResult;
import util.Util;
import bean.Cotacao;
import bean.Empresa;
import bean.SolicitacaoCompra;
import bean.Usuario;

import com.opensymphony.xwork2.ActionSupport;

import dao.SolicitacaoCompraDAO;

@Results( {
		@Result(name = "txtRes", type = StreamResult.class, value = "inputStream", params = {
				"contentType", "text/plain", "inputName", "inputStream" }),
		@Result(name = "resultJSON", value = "JSONModel", type = JSONResult.class) })
public class SolicitacaoCompraAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	static Logger logger = Logger.getLogger(SolicitacaoCompraAction.class);
	private InputStream inputStream;
	private String start = "";
	private String limit = "";
	private String totalCount;
	private String sort;
	private String dir;
	private Object JSONModel;
	private Class tipo;
	private SolicitacaoCompra solicitacaoCompra;
	private boolean resultado;
	private List<Cotacao> cotacoes = new ArrayList<Cotacao>();
	private int total;
	@Autowired
	@Qualifier("HSolicitacaoCompraDAO")
	private SolicitacaoCompraDAO solicitacaoCompraDAO;
	@Autowired
	@Qualifier("Util")
	private Util util;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public boolean isResultado() {
		return resultado;
	}

	public void setResultado(boolean resultado) {
		this.resultado = resultado;
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

	public Class getTipo() {
		return tipo;
	}

	public void setTipo(Class tipo) {
		this.tipo = tipo;
	}

	public SolicitacaoCompra getSolicitacaoCompra() {
		return solicitacaoCompra;
	}

	public void setSolicitacaoCompra(SolicitacaoCompra solicitacaoCompra) {
		this.solicitacaoCompra = solicitacaoCompra;
	}

	public List<Cotacao> getCotacoes() {
		return cotacoes;
	}

	public void setCotacoes(List<Cotacao> cotacoes) {
		this.cotacoes = cotacoes;
	}

	public Object getJSONModel() {
		return JSONModel;
	}

	public void setJSONModel(Object model) {
		JSONModel = model;
	}

	public String gravar() {
		try {
			Integer idSol = 0;
			Date dataAtual = Calendar.getInstance().getTime();
			Usuario usuarioSessao = (Usuario) util.recuperaSessao("Usuario");
			if (solicitacaoCompra.getId() == 0) {// insert solicitação
				solicitacaoCompra.setDatSol(dataAtual);
				solicitacaoCompra.setUsuSol(usuarioSessao);
				solicitacaoCompra.setEmpresa(new Empresa(3));
				solicitacaoCompra.setUsuAlt(usuarioSessao);
				solicitacaoCompra.setDatAlt(util.dataAtual());
				for (Cotacao cotacao : cotacoes) {
					cotacao.setSolicitacaoCompra(solicitacaoCompra);
					solicitacaoCompra.getCotacoes().add(cotacao);
				}
			} else {
				SolicitacaoCompra solBanco = solicitacaoCompraDAO.loadById(solicitacaoCompra
						.getId());
				solicitacaoCompra.setEmpresa(solBanco.getEmpresa());
				solicitacaoCompra.setUsuAlt(usuarioSessao);
				solicitacaoCompra.setDatAlt(util.dataAtual());
				solicitacaoCompra.setUsuSol(solBanco.getUsuSol());
				solicitacaoCompra.setDatSol(solBanco.getDatSol());
				for (Cotacao cotacao : cotacoes) {
					if (solicitacaoCompra.getCotacoes().contains(cotacao)) {
						solicitacaoCompra.getCotacoes().remove(cotacao);
					}
					cotacao.setSolicitacaoCompra(solicitacaoCompra);
					solicitacaoCompra.getCotacoes().add(cotacao);
				}
			}
			if (solicitacaoCompra.getId() > 0) {
				solicitacaoCompraDAO.merge(solicitacaoCompra);
				idSol = solicitacaoCompra.getId();
			} else {
				idSol = (Integer) solicitacaoCompraDAO.insert(solicitacaoCompra);
			}
			inputStream = new ByteArrayInputStream(
					("{'success':true, 'msg':'Solicitacao gravada com sucesso.', 'id': " + idSol + " }")
							.getBytes());
		} catch (Exception e) {
			logger.error("Erro gravando Solicitação. Causa: " + e.getMessage());
			inputStream = new ByteArrayInputStream(
					("{'success':false, 'msg':\"Nao foi possivel gravar solicitacao. Erro: "
							+ e.getCause() + "\"}").getBytes());
		}
		return "txtRes";
	}

	public String consulta() {
		try {
			List<SolicitacaoCompra> solicitacoes = new ArrayList<SolicitacaoCompra>();
			SolicitacaoCompra solicitacao = solicitacaoCompraDAO.loadById(this.solicitacaoCompra
					.getId());
			solicitacoes.add(solicitacao);
			this.resultado = true;
			this.totalCount = "1";
			this.total = 1;
			this.tipo = SolicitacaoCompra.class;
			setJSONModel(solicitacoes);
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("{'success':false, 'msg':\"Nao foi possivel localizar solicitacao. Erro: "
							+ e.getCause() + "\"}").getBytes());
		}
		return "resultJSON";
	}

	public String listByExample() throws Exception {
		List<SolicitacaoCompra> solicitacoes = solicitacaoCompraDAO
				.listByExample(this.solicitacaoCompra);
		if (solicitacoes.size() > 0) {
			this.total = solicitacoes.size();
			this.resultado = true;
			this.tipo = SolicitacaoCompra.class;
			setJSONModel(solicitacoes);
			return "resultJSON";
		} else {
			inputStream = new ByteArrayInputStream(
					("{map:{items:[], success:true, totalCount : 0, msg: 'Nenhuma requisicao encontrada com esses filtros.'}}")
							.getBytes());
			return "txtRes";
		}
	}

	public SolicitacaoCompraDAO getSolicitacaoCompraDAO() {
		return solicitacaoCompraDAO;
	}

	public void setSolicitacaoCompraDAO(SolicitacaoCompraDAO solicitacaoCompraDAO) {
		this.solicitacaoCompraDAO = solicitacaoCompraDAO;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}
}
