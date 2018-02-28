package action;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.JSONResult;
import result.XMLResult;
import bean.Empresa;

import com.opensymphony.xwork2.ActionSupport;

import dao.EmpresaDAO;

@Results({ @Result(name = "success", value = "", type = JSONResult.class),
		@Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })
public class EmpresaAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private Object					JSONModel;
	private Object					xmlModel;
	private String					totalCount;
	@Autowired
	@Qualifier("HEmpresaDAO")
	private EmpresaDAO			empresaDAO;

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public Object getXmlModel() {
		return xmlModel;
	}

	public void setXmlModel(Object xmlModel) {
		this.xmlModel = xmlModel;
	}

	public Object getJSONModel() {
		return JSONModel;
	}

	public void setJSONModel(Object model) {
		JSONModel = model;
	}

	public String listAll() throws Exception {
		setJSONModel(empresaDAO.listAll("nomEmp"));
		return SUCCESS;
	}

	public String listAllXML() throws Exception {
		List<Empresa> empresas = empresaDAO.listAll("nomEmp");
		this.totalCount = String.valueOf(empresas.size());
		setXmlModel(empresas);

		return "resultXML";
	}

	public EmpresaDAO getEmpresaDAO() {
		return empresaDAO;
	}

	public void setEmpresaDAO(EmpresaDAO empresaDAO) {
		this.empresaDAO = empresaDAO;
	}

}
