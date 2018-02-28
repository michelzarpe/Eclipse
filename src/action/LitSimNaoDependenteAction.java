package action;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import com.opensymphony.xwork2.ActionSupport;
import bean.Dependente.LitSimNaoDependente;
import result.XMLResult;

@Results( { @Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })
public class LitSimNaoDependenteAction extends ActionSupport{
	private static final long	serialVersionUID	= 1L;
	private Object xmlModel;
	private String totalCount;

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
		setXmlModel(LitSimNaoDependente.values());
		this.totalCount = String.valueOf(LitSimNaoDependente.values().length);
		return "resultXML";
	}

}
