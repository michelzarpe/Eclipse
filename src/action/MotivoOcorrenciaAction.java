package action;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.JSONResult;

import com.opensymphony.xwork2.ActionSupport;

import dao.MotivoOcorrenciaDAO;

@Results({ @Result(name = "success", value = "", type = JSONResult.class) })
public class MotivoOcorrenciaAction extends ActionSupport {
	private static final long		serialVersionUID	= 1L;
	private Object						JSONModel;
	@Autowired
	@Qualifier("HMotivoOcorrenciaDAO")
	private MotivoOcorrenciaDAO	motivoOcorrenciaDAO;

	public Object getJSONModel() {
		return JSONModel;
	}

	public void setJSONModel(Object model) {
		JSONModel = model;
	}

	public String listAll() throws Exception {
		setJSONModel(motivoOcorrenciaDAO.listAll("desMot"));
		return SUCCESS;
	}

	public MotivoOcorrenciaDAO getMotivoOcorrenciaDAO() {
		return motivoOcorrenciaDAO;
	}

	public void setMotivoOcorrenciaDAO(MotivoOcorrenciaDAO motivoOcorrenciaDAO) {
		this.motivoOcorrenciaDAO = motivoOcorrenciaDAO;
	}
}
