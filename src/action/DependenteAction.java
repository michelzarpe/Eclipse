package action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.StreamResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionSupport;

import bean.Dependente;
import dao.DependenteDAO;
import result.BasicResult;

@Results({
	@Result(name = "resultXML", value = "xmlModel", type = BasicResult.class),
	@Result(name = "sucTxt", value = "inputStream", type = StreamResult.class, params = {"inputName", "inputStream" }) 
	})
public class DependenteAction  extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private Object					JSONModel;
	private Object					xmlModel;
	private String					totalCount;
	private InputStream					inputStream;
	private int codDep;
		
	@Autowired
	@Qualifier("HDependenteDAO")
	private DependenteDAO dependenteDAO;

	
	
	public int getCodDep() {
		return codDep;
	}

	public void setCodDep(int codDep) {
		this.codDep = codDep;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

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

	public DependenteDAO getDependenteDAO() {
		return dependenteDAO;
	}

	public void setDependenteDAO(DependenteDAO dependenteDAO) {
		this.dependenteDAO = dependenteDAO;
	}
	
	public String excluir() {
	try {
		Dependente dependente = new Dependente();
		dependente = dependenteDAO.loadById(codDep);
		 dependenteDAO.delete(dependente);
		 inputStream = new ByteArrayInputStream(("Dependnete excluido.").getBytes());
	} catch (Exception e) {
		inputStream = new ByteArrayInputStream(("Dependnete nao foi excluido.").getBytes());
	}
	return "sucTxt";
	}

}
