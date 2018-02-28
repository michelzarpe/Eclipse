package action;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.XMLResult;
import bean.Uniforme;

import com.opensymphony.xwork2.ActionSupport;

import dao.UniformeDAO;

@Results({ @Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })
public class PesquisaUniformeAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private Object					xmlModel;
	private String					start					= "";
	private String					limit					= "";
	private String					totalCount;
	private String					sort;
	private String					dir;
	private Uniforme				uniforme				= new Uniforme();
	@Autowired
	@Qualifier("HUniformeDAO")
	private UniformeDAO			uniformeDAO;

	public UniformeDAO getUniformeDAO() {
		return uniformeDAO;
	}

	public void setUniformeDAO(UniformeDAO uniformeDAO) {
		this.uniformeDAO = uniformeDAO;
	}

	public Uniforme getUniforme() {
		return uniforme;
	}

	public void setUniforme(Uniforme uniforme) {
		this.uniforme = uniforme;
	}

	public Object getXmlModel() {
		return xmlModel;
	}

	public void setXmlModel(Object xmlModel) {
		this.xmlModel = xmlModel;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String buscaXML() throws Exception {
		List<Uniforme> uniformes = uniformeDAO.listByExample(uniforme);
		setXmlModel(uniformes);
		return "resultXML";
	}
}
