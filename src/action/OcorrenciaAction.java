package action;

import java.util.Date;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.JSONResult;
import util.Util;
import util.WebOperConstantes;
import bean.Ocorrencia;
import bean.Usuario;

import com.opensymphony.xwork2.ActionSupport;

import dao.OcorrenciaDAO;

@Results({ @Result(name = "listAll", value = "/paginas/jsp/listaOcorrencias.jsp"),
		@Result(name = "success_list", value = "", type = JSONResult.class),
		@Result(name = "success", value = "/paginas/jsp/ocorrencia/ocorrencia.jsp"),
		@Result(name = "input", value = "/paginas/jsp/ocorrencia/ocorrencia.jsp") })
public class OcorrenciaAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private Ocorrencia			ocorrencia			= new Ocorrencia();
	private Object					JSONModel;
	@Autowired
	@Qualifier("HOcorrenciaDAO")
	private OcorrenciaDAO		ocorrenciaDAO;
	@Autowired
	@Qualifier("Util")
	private Util util;

	public OcorrenciaDAO getOcorrenciaDAO() {
		return ocorrenciaDAO;
	}

	public void setOcorrenciaDAO(OcorrenciaDAO ocorrenciaDAO) {
		this.ocorrenciaDAO = ocorrenciaDAO;
	}

	public Object getJSONModel() {
		return JSONModel;
	}

	public void setJSONModel(Object model) {
		JSONModel = model;
	}

	public Ocorrencia getOcorrencia() {
		return ocorrencia;
	}

	public void setOcorrencia(Ocorrencia ocorrencia) {
		this.ocorrencia = ocorrencia;
	}

	public String listByDateByUser() {
		Date dataAtual = new Date();
		Usuario usuario = (Usuario) util.recuperaSessao(WebOperConstantes.USUARIO);
		List<Ocorrencia> ocorrencias = ocorrenciaDAO.listByDateByUser(dataAtual, usuario);
		setJSONModel(ocorrencias);
		return "success_list";
	}

	public String gravar() {
		try {
			ocorrenciaDAO.insert(this.ocorrencia);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}
}
