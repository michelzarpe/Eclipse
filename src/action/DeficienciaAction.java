package action;

import java.util.List;

import org.apache.poi.hslf.record.Sound;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionSupport;

import bean.Deficiencia;
import dao.DeficienciaDAO;
import result.JSONResult;
import result.XMLResult;

@Results({ @Result(name = "success", value = "", type = JSONResult.class),
	@Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })
public class DeficienciaAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private Object					JSONModel;
	private Object					xmlModel;
	private String					totalCount;

	@Autowired
	@Qualifier("HDeficienciaDAO")
	private DeficienciaDAO deficienciaDAO;

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

	public DeficienciaDAO getDeficienciaDAO() {
		return deficienciaDAO;
	}

	public void setDeficienciaDAO(DeficienciaDAO deficienciaDAO) {
		this.deficienciaDAO = deficienciaDAO;
	}
	
	
	public String listAll() throws Exception {
		setJSONModel(deficienciaDAO.listAll("codDef"));
		return SUCCESS;
	}
	
	public String listAllXML() throws Exception {
	   List<Deficiencia> deficiencia = deficienciaDAO.listAll("codDef");
		setXmlModel(deficiencia);
		this.totalCount = String.valueOf(deficiencia.size());
		return "resultXML";
		}
	
}
