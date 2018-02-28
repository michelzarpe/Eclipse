package action;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.XMLResult;
import bean.TipoEPI;

import com.opensymphony.xwork2.ActionSupport;

import dao.TipoEPIDAO;
@Results( { @Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })
public class TipoEPIAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private Object xmlModel;
	private String totalCount;
	private String start = "";
	private String limit = "";
	private String sort = "";
	private String dir = "";
	@Autowired
	@Qualifier("HTipoEPIDAO")
	private TipoEPIDAO tipoEPIDAO;

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

	public String listAllXML() throws Exception {
		List<TipoEPI> tipos = tipoEPIDAO.listTiposAtivos();
		setXmlModel(tipos);
		this.totalCount = String.valueOf(tipos.size());
		return "resultXML";
	}

	public TipoEPIDAO getTipoEPIDAO() {
		return tipoEPIDAO;
	}

	public void setTipoEPIDAO(TipoEPIDAO tipoEPIDAO) {
		this.tipoEPIDAO = tipoEPIDAO;
	}
}
