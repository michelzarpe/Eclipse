package action;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.JSONResult;

import com.opensymphony.xwork2.ActionSupport;

import dao.SubTipoMotivoDAO;
@Results( { @Result(name = "success", value = "", type = JSONResult.class)})
public class SubTipoMotivoAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private int motivoId;
	private Object JSONModel;
	@Autowired
	@Qualifier("HSubTipoMotivoDAO")
	private SubTipoMotivoDAO subTipoMotivoDAO;

	public Object getJSONModel() {
		return JSONModel;
	}

	public void setJSONModel(Object model) {
		JSONModel = model;
	}

	public int getMotivoId() {
		return motivoId;
	}

	public void setMotivoId(int motivoId) {
		this.motivoId = motivoId;
	}

	public String listAllByMotivo() throws Exception {
		setJSONModel(subTipoMotivoDAO.listAllByMotivo(this.motivoId));
		return SUCCESS;
	}
	
	public String listAll() throws Exception {
		setJSONModel(subTipoMotivoDAO.listAll("desSub"));
		return SUCCESS;
	}

	public SubTipoMotivoDAO getSubTipoMotivoDAO() {
		return subTipoMotivoDAO;
	}

	public void setSubTipoMotivoDAO(SubTipoMotivoDAO subTipoMotivoDAO) {
		this.subTipoMotivoDAO = subTipoMotivoDAO;
	}
}
