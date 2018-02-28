package action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.JSONResult;
import result.XMLResult;
import bean.Hierarquia;

import com.opensymphony.xwork2.ActionSupport;

import dao.HierarquiaDAO;

@Results({ @Result(name = "success", value = "", type = JSONResult.class),
		@Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })
public class LocalAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private Object					JSONModel;
	private Object					xmlModel;
	private String					totalCount;
	private String					start					= "";
	private String					limit					= "";
	private String					sort					= "";
	private String					dir					= "";
	private String					nomLoc				= "";
	private String					nivel;
	private String					nivelAnterior;
	private String					codLoc				= "";
	
	@Autowired
	@Qualifier("HHierarquiaDAO")
	private HierarquiaDAO		hierarquiaDAO;

	public String getCodLoc() {
		return codLoc;
	}

	public void setCodLoc(String codLoc) {
		this.codLoc = codLoc;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getNivelAnterior() {
		return nivelAnterior;
	}

	public void setNivelAnterior(String nivelAnterior) {
		this.nivelAnterior = nivelAnterior;
	}

	public String getNomLoc() {
		return nomLoc;
	}

	public void setNomLoc(String nomLoc) {
		this.nomLoc = nomLoc;
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

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public Object getJSONModel() {
		return JSONModel;
	}

	public void setJSONModel(Object model) {
		JSONModel = model;
	}

	public String listAllEmpresas() {
		setJSONModel(hierarquiaDAO.listAllNivelEmpresa());
		return SUCCESS;
	}

	public String listAllXML() throws Exception {
		String dadoBusca = nomLoc;
		Map<String, Object> parametros = new HashMap<String, Object>();
		if (!this.nomLoc.trim().equals("")) {// nomLoc
			parametros.put("dadoBusca", dadoBusca);
		}
		if (!this.codLoc.trim().equals("")) {// codLoc
			parametros.put("codLoc", codLoc);
		}
		if (this.nivel.trim().equals("2")) {
			if (!nivelAnterior.trim().equals("")) {
				parametros.put("nivelAnterior", nivelAnterior);
			}
		}
		List<Hierarquia> hierarquias = hierarquiaDAO.busca(dadoBusca, nivelAnterior, nivel, codLoc);
		setXmlModel(hierarquias);
		this.totalCount = String.valueOf(hierarquias.size());
		return "resultXML";
	}

	public HierarquiaDAO getHierarquiaDAO() {
		return hierarquiaDAO;
	}

	public void setHierarquiaDAO(HierarquiaDAO hierarquiaDAO) {
		this.hierarquiaDAO = hierarquiaDAO;
	}
}
