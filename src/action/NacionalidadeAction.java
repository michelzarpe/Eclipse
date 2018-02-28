package action;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.JSONResult;
import result.XMLResult;
import bean.Nacionalidade;

import com.opensymphony.xwork2.ActionSupport;

import dao.NacionalidadeDAO;

@Results({ @Result(name = "success", value = "", type = JSONResult.class),
		@Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })
public class NacionalidadeAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private Object					xmlModel;
	private String					totalCount;
	private Nacionalidade		nacionalidade		= new Nacionalidade();
	@Autowired
	@Qualifier("HNacionalidadeDAO")
	private NacionalidadeDAO	nacionalidadeDAO;
	
	public String listAllXML() throws Exception {
		List<Nacionalidade> nacionalidades = nacionalidadeDAO.listAll("desNac");
		setXmlModel(nacionalidades);
		this.totalCount = String.valueOf(nacionalidades.size());
		return "resultXML";
	}

	public Nacionalidade getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(Nacionalidade nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public NacionalidadeDAO getNacionalidadeDAO() {
		return nacionalidadeDAO;
	}

	public void setNacionalidadeDAO(NacionalidadeDAO nacionalidadeDAO) {
		this.nacionalidadeDAO = nacionalidadeDAO;
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

}
