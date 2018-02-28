package action;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import result.XMLResult;
import bean.ItemSolicitacao.Situacao;

import com.opensymphony.xwork2.ActionSupport;
@Results( { @Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })
public class SituacaoItemAction extends ActionSupport{
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
		setXmlModel(Situacao.values());
		this.totalCount = String.valueOf(Situacao.values().length);
		return "resultXML";
	}
}
