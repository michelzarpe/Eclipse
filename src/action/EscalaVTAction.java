package action;

import java.util.List;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.opensymphony.xwork2.ActionSupport;
import bean.EscalaVT;
import dao.EscalaVTDAO;
import result.JSONResult;
import result.XMLResult;

@Results({ @Result(name = "success", value = "", type = JSONResult.class),
	@Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })

public class EscalaVTAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private Object					JSONModel;
	private Object					xmlModel;
	private String					totalCount;

	@Autowired
	@Qualifier("HEscalaVTDAO")
	private EscalaVTDAO escalaVTDAO;

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

	public EscalaVTDAO getEscalaVTDAO() {
		return escalaVTDAO;
	}

	public void setEscalaVTDAO(EscalaVTDAO escalaVTDAO) {
		this.escalaVTDAO = escalaVTDAO;
	}

	public String listAll() throws Exception {
		setJSONModel(escalaVTDAO.listAll("escVtr"));
		return SUCCESS;
	}
	
	public String listAllXML() throws Exception {
	   List<EscalaVT> escalaVT = escalaVTDAO.listAll("escVtr");
		setXmlModel(escalaVT);
		this.totalCount = String.valueOf(escalaVT.size());
		return "resultXML";
		}

}
