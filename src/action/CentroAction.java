package action;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.JSONResult;
import result.XMLResult;
import bean.Centro;
import bean.Centro.Situacao;

import com.opensymphony.xwork2.ActionSupport;

import dao.CentroDAO;

@Results({ @Result(name = "success", value = "", type = JSONResult.class),
		@Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })
public class CentroAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private Object					xmlModel;
	private String					totalCount;
	private Centro					centro				= new Centro();
	@Autowired
	@Qualifier("HCentroDAO")
	private CentroDAO				centroDAO;

	public String listAllXML() throws Exception {
		List<Centro> centros = centroDAO.listAll("nomCcu");
		setXmlModel(centros);
		this.totalCount = String.valueOf(centros.size());
		return "resultXML";
	}

	public String listByExample() throws Exception {
		centro.setSitCcu(Situacao.A);
		List<Centro> centros = centroDAO.listByExample(centro);
		setXmlModel(centros);
		this.totalCount = String.valueOf(centros.size());
		return "resultXML";
	}

	public Centro getCentro() {
		return centro;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
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

	public CentroDAO getCentroDAO() {
		return centroDAO;
	}

	public void setCentroDAO(CentroDAO centroDAO) {
		this.centroDAO = centroDAO;
	}
}
