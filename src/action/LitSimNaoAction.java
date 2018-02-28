package action;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.opensymphony.xwork2.ActionSupport;
import bean.Colaborador.LitSimNao;
import result.XMLResult;

@Results( { @Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })
public class LitSimNaoAction extends ActionSupport{
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
		setXmlModel(LitSimNao.values());
		this.totalCount = String.valueOf(LitSimNao.values().length);
		return "resultXML";
	}
}
