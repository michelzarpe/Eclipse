package action;

import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.JSONResult;

import com.opensymphony.xwork2.ActionSupport;

import dao.EscalaRondaDAO;

@Result(name = "success", value = "", type = JSONResult.class)
public class EscalaRondaAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private Object JSONModel;
	
	@Autowired
	@Qualifier("HEscalaRondaDAO")
	private EscalaRondaDAO escalaRondaDAO;

	public EscalaRondaDAO getEscalaRondaDAO() {
		return escalaRondaDAO;
	}

	public void setEscalaRondaDAO(EscalaRondaDAO escalaRondaDAO) {
		this.escalaRondaDAO = escalaRondaDAO;
	}

	public Object getJSONModel() {
		return JSONModel;
	}

	public void setJSONModel(Object model) {
		JSONModel = model;
	}

	public String listAll() throws Exception {
		setJSONModel(escalaRondaDAO.listAll("nomEsc"));
		return SUCCESS;
	}
}
