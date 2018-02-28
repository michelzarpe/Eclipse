package action;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionSupport;

import bean.Vinculo;
import dao.VinculoDAO;
import result.JSONResult;
import result.XMLResult;

@Results({ @Result(name = "success", value = "", type = JSONResult.class),
	@Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })

public class VinculoAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private Object					JSONModel;
	private Object					xmlModel;
	private String					totalCount;
	
	@Autowired
	@Qualifier("HVinculoDAO")
	private VinculoDAO vinculoDAO;

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

	public VinculoDAO getVinculoDAO() {
		return vinculoDAO;
	}

	public void setVinculoDAO(VinculoDAO vinculoDAO) {
		this.vinculoDAO = vinculoDAO;
	}
	
	public String listAll() throws Exception {
		setJSONModel(vinculoDAO.listAll("codVin"));
		return SUCCESS;
	}

	public String listAllXML() throws Exception {
		List<Vinculo> vinculo = vinculoDAO.listAll("codVin");
		setXmlModel(vinculo);
		this.totalCount = String.valueOf(vinculo.size());
		return "resultXML";
	}
	

}
