package action;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import result.JSONResult;
import result.XMLResult;
import com.opensymphony.xwork2.ActionSupport;

import bean.Escala;
import dao.EscalaDAO;

@Results({ @Result(name = "success", value = "", type = JSONResult.class),
	@Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })

public class EscalaAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private Object					JSONModel;
	private Object					xmlModel;
	private String					totalCount;
	
	@Autowired
	@Qualifier("HEscalaDAO")
	private EscalaDAO				escalaDAO;

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

	public String listAll() throws Exception {
		setJSONModel(escalaDAO.listAll("nomEsc"));
		return SUCCESS;
	}

	public String listAllXML() throws Exception {
		List<Escala> escalas = escalaDAO.listAll("nomEsc");
		setXmlModel(escalas);
		this.totalCount = String.valueOf(escalas.size());
		return "resultXML";
	}

	public EscalaDAO getEscalaDAO() {
		return escalaDAO;
	}

	public void setEscalaDAO(EscalaDAO escalaDAO) {
		this.escalaDAO = escalaDAO;
	}
}
