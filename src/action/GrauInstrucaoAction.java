package action;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.XMLResult;
import bean.GrauInstrucao;

import com.opensymphony.xwork2.ActionSupport;

import dao.GrauInstrucaoDAO;

@Results({ @Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })
public class GrauInstrucaoAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private Object					xmlModel;
	private String					totalCount;
	@Autowired
	@Qualifier("HGrauInstrucaoDAO")
	private GrauInstrucaoDAO	grauInstrucaoDAO;

	public GrauInstrucaoDAO getGrauInstrucaoDAO() {
		return grauInstrucaoDAO;
	}

	public void setGrauInstrucaoDAO(GrauInstrucaoDAO grauInstrucaoDAO) {
		this.grauInstrucaoDAO = grauInstrucaoDAO;
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

	public String listAllXML() throws Exception {
		List<GrauInstrucao> graus = grauInstrucaoDAO.listAll("desGra");
		setXmlModel(graus);
		this.totalCount = String.valueOf(graus.size());
		return "resultXML";
	}
}
