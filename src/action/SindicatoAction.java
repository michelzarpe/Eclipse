package action;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionSupport;

import bean.Sindicato;
import dao.SindicatoDAO;
import result.JSONResult;
import result.XMLResult;

@Results({ @Result(name = "success", value = "", type = JSONResult.class),
	@Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })
public class SindicatoAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private Object					JSONModel;
	private Object					xmlModel;
	private String					totalCount;
	@Autowired
	@Qualifier("HSindicatoDAO")
	private SindicatoDAO  sindicatoDAO;
	public Object getJSONModel() {
		return JSONModel;
	}
	public void setJSONModel(Object jSONModel) {
		JSONModel = jSONModel;
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
	public SindicatoDAO getSindicatoDAO() {
		return sindicatoDAO;
	}
	public void setSindicatoDAO(SindicatoDAO sindicatoDAO) {
		this.sindicatoDAO = sindicatoDAO;
	}
	
	public String listAll() throws Exception {
		setJSONModel(sindicatoDAO.listAll("codSin"));
		return SUCCESS;
	}

	public String listAllXML() throws Exception {
		List<Sindicato> sindicato = sindicatoDAO.listAll("codSin");
		setXmlModel(sindicato);
		this.totalCount = String.valueOf(sindicato.size());
		return "resultXML";
	}

}
