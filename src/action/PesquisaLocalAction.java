package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.LocalJSONResult;
import util.Util;
import bean.Hierarquia;

import com.opensymphony.xwork2.ActionSupport;

import dao.HierarquiaDAO;

@Results({ @Result(name = "pesqSimp", value = "/paginas/jsp/pesquisa/pesqLocSimp.jsp"),
		@Result(name = "listResultSimp", value = "/paginas/jsp/pesquisa/listLocSimp.jsp"),
		@Result(name = "success_busca", value = "", type = LocalJSONResult.class) })
public class PesquisaLocalAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private String					nivelAnterior;
	private String					dadoBusca;
	private String					nivel;
	private List<Hierarquia>	hierarquias			= new ArrayList<Hierarquia>();
	private Object					JSONModel;
	@Autowired
	@Qualifier("HHierarquiaDAO")
	private HierarquiaDAO		hierarquiaDAO;
	@Autowired
	@Qualifier("Util")
	private Util util;

	public HierarquiaDAO getHierarquiaDAO() {
		return hierarquiaDAO;
	}

	public void setHierarquiaDAO(HierarquiaDAO hierarquiaDAO) {
		this.hierarquiaDAO = hierarquiaDAO;
	}

	public Object getJSONModel() {
		return JSONModel;
	}

	public void setJSONModel(Object model) {
		JSONModel = model;
	}

	public String getNivelAnterior() {
		return nivelAnterior;
	}

	public void setNivelAnterior(String nivelAnterior) {
		this.nivelAnterior = nivelAnterior;
	}

	public String getDadoBusca() {
		return dadoBusca;
	}

	public List<Hierarquia> getHierarquias() {
		return hierarquias;
	}

	public void setHierarquias(List<Hierarquia> hierarquias) {
		this.hierarquias = hierarquias;
	}

	public void setDadoBusca(String dadoBusca) {
		this.dadoBusca = dadoBusca;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public List<Hierarquia> getEmpresas() throws Exception {
		List<Hierarquia> hierarquias = hierarquiaDAO.listAllNivelEmpresa();
		return hierarquias;
	}

	public String pesqLocSimp() {
		util.removeDaSessao("hierarquias");
		return "pesqSimp";
	}

	public void pesquisa() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		if (!this.dadoBusca.trim().equals("")) {// nomLoc
			parametros.put("dadoBusca", dadoBusca);
		}
		if (nivel.trim().equals("3")) {
			if (!this.nivelAnterior.trim().equals("")) {// o que for começado
				// por
				parametros.put("nivelAnterior", nivelAnterior);
			}
		}

		hierarquias = hierarquiaDAO.busca(dadoBusca, nivelAnterior, nivel, "");
		util.registraSessao("hierarquias", hierarquias);
	}

	public String listResultSimp() {
		pesquisa();
		return "listResultSimp";
	}

	public String listResultJson() {
		pesquisa();
		setJSONModel(this.hierarquias);
		return "success_busca";
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

}
