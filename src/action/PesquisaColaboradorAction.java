package action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.BasicResult;
import result.JSONResult;
import result.XMLResult;
import util.Util;
import bean.Colaborador;
import bean.Usuario;

import com.opensymphony.xwork2.ActionSupport;

import dao.ColaboradorDAO;

@Results({ @Result(name = "success_pesquisa", value = "", type = JSONResult.class),
		@Result(name = "resultXML", value = "xmlModel", type = XMLResult.class),
		@Result(name = "resultBasic", value = "xmlModel", type = BasicResult.class) })
public class PesquisaColaboradorAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private boolean				buscaDemitidos;
	private Object					JSONModel;
	private Object					xmlModel;
	private String					start					= "";
	private String					limit					= "";
	private String					totalCount;
	private String					sort;
	private String					dir;
	private Colaborador			colaborador;
	private Class					tipo;
	@Autowired
	@Qualifier("HColaboradorDAO")
	private ColaboradorDAO		colaboradorDAO;
	@Autowired
	@Qualifier("Util")
	private Util util;

	public Class getTipo() {
		return tipo;
	}

	public void setTipo(Class tipo) {
		this.tipo = tipo;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
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

	public Object getJSONModel() {
		return JSONModel;
	}

	public void setJSONModel(Object model) {
		JSONModel = model;
	}

	public boolean isBuscaDemitidos() {
		return buscaDemitidos;
	}

	public void setBuscaDemitidos(boolean buscaDemitidos) {
		this.buscaDemitidos = buscaDemitidos;
	}

	public String listResult() {
		return "listResult";
	}
	public ColaboradorDAO getColaboradorDAO() {
		return colaboradorDAO;
	}

	public void setColaboradorDAO(ColaboradorDAO colaboradorDAO) {
		this.colaboradorDAO = colaboradorDAO;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	/**
	 * public void pesquisa() { ColaboradorDAO colaboradorDAO = Factory.criaColaboradorDAO(); Map<String, Object> parametros = new HashMap<String,
	 * Object>(); if (!this.colaborador.getNomFun().trim().equals("")) { parametros.put("nomFun", this.colaborador.getNomFun()); } if
	 * (!(this.colaborador.getEmpresa().getId() > 0)) { parametros.put("empresa.id", this.colaborador.getEmpresa().getId()); } if
	 * (!this.isBuscaDemitidos()) {// não trazer demitidos parametros.put("notFindDemitidos", 7); } Usuario usuario = (Usuario)
	 * util.Util.recuperaSessao("Usuario"); parametros.put("usuario", usuario.getId()); List<Colaborador> colaboradores =
	 * colaboradorDAO.busca(parametros); util.Util.registraSessao("colaboradores", colaboradores); }
	 * 
	 * public String pesquisaCol() { ColaboradorDAO colaboradorDAO = Factory.criaColaboradorDAO(); Map<String, Object> parametros = new HashMap<String,
	 * Object>(); if (!this.colaborador.getNomFun().trim().equals("")) { parametros.put("nomFun", this.colaborador.getNomFun()); } if
	 * (!(this.colaborador.getEmpresa().getId() > 0)) { parametros.put("empresa.id", this.colaborador.getEmpresa().getId()); } if
	 * (!this.isBuscaDemitidos()) {// não trazer demitidos parametros.put("notFindDemitidos", 7); } Usuario usuario = (Usuario)
	 * util.Util.recuperaSessao("Usuario"); parametros.put("usuario", usuario.getId()); setJSONModel(colaboradorDAO.busca(parametros)); return
	 * "success_pesquisa"; }
	 **/
	public String buscaXML() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		if ((this.colaborador.getNomFun() != null)
				&& (!this.colaborador.getNomFun().trim().equals(""))) {
			parametros.put("nomFun", this.colaborador.getNomFun());
		}
		if(this.colaborador.getNumCad() > 0){
			parametros.put("numCad", this.colaborador.getNumCad());
		}
		if(this.colaborador.getNumCpf() != ""){
			parametros.put("numCpf", this.colaborador.getNumCpf());
		}
		if (this.colaborador.getId() > 0) {
			parametros.put("id", this.colaborador.getId());
		}
		if ((this.colaborador.getEmpresa().getId() > 0)) {
			parametros.put("empresa.id", this.colaborador.getEmpresa().getId());
		}
		if (!this.isBuscaDemitidos()) {// não trazer demitidos
			parametros.put("notFindDemitidos", 7);
		}
		Usuario usuario = (Usuario) util.recuperaSessao("Usuario");
		parametros.put("usuario", usuario.getId());
		this.tipo = Colaborador.class;
		setXmlModel(colaboradorDAO.busca(parametros));
		return "resultBasic";
	}

	public String buscaXMLFull() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		if ((this.colaborador.getNomFun() != null) && (!this.colaborador.getNomFun().trim().equals(""))) {
			parametros.put("nomFun", this.colaborador.getNomFun());
		}
		if (this.colaborador.getId() > 0) {
			parametros.put("id", this.colaborador.getId());
		}
		if ((this.colaborador.getEmpresa().getId() > 0)) {
			parametros.put("empresa.id", this.colaborador.getEmpresa().getId());
		}
		if (!this.isBuscaDemitidos()) {// não trazer demitidos
			parametros.put("notFindDemitidos", 7);
		}
		Usuario usuario = (Usuario) util.recuperaSessao("Usuario");
		parametros.put("usuario", usuario.getId());
		List<Colaborador> col = colaboradorDAO.busca(parametros);
		setXmlModel(col);
		return "resultXML";
	}

	
}
