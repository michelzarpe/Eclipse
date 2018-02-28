package action;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import result.JSONResult;
import result.XMLResult;
import com.opensymphony.xwork2.ActionSupport;
import bean.ClasseEscalaRonda;
import dao.ClasseEscalaRondaDAO;


@Results({ @Result(name = "success", value = "", type = JSONResult.class),
		@Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })

public class ClasseEscalaRondaAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private Object					JSONModel;
	private Object					xmlModel;
	private String					totalCount;
	
	@Autowired
	@Qualifier("HClasseEscalaRondaDAO")
	private ClasseEscalaRondaDAO classeEscalaRondaDAO;

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

	public ClasseEscalaRondaDAO getClasseEscalaRondaDAO() {
		return classeEscalaRondaDAO;
	}

	public void setClasseEscalaRondaDAO(ClasseEscalaRondaDAO classeEscalaRondaDAO) {
		this.classeEscalaRondaDAO = classeEscalaRondaDAO;
	}
	public String listAll() throws Exception {
		setJSONModel(classeEscalaRondaDAO.listAll("claEsc"));
		return SUCCESS;
	}

	public String listAllXML() throws Exception {
		List<ClasseEscalaRonda> classeEscalaRonda = classeEscalaRondaDAO.listAll("claEsc");
		setXmlModel(classeEscalaRonda);
		this.totalCount = String.valueOf(classeEscalaRonda.size());
		return "resultXML";
	}

	
}
