package action;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionSupport;

import bean.NaturezaDespesa;
import dao.NaturezaDespesaDAO;
import result.JSONResult;
import result.XMLResult;

@Results({ @Result(name = "success", value = "", type = JSONResult.class),
	@Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })
public class NaturezaDespesaAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private Object					JSONModel;
	private Object					xmlModel;
	private String					totalCount;
	
	@Autowired
	@Qualifier("HNaturezaDespesaDAO")
   private NaturezaDespesaDAO naturezaDespesaDAO;
	
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

	public NaturezaDespesaDAO getNaturezaDespesaDAO() {
		return naturezaDespesaDAO;
	}

	public void setNaturezaDespesaDAO(NaturezaDespesaDAO naturezaDespesaDAO) {
		this.naturezaDespesaDAO = naturezaDespesaDAO;
	}

	public String listAll() throws Exception {
		setJSONModel(naturezaDespesaDAO.listAll("natDes"));
		return SUCCESS;
	}

	public String listAllXML() throws Exception {
		List<NaturezaDespesa> naturezaDespesa = naturezaDespesaDAO.listAll("natDes");
		setXmlModel(naturezaDespesa);
		this.totalCount = String.valueOf(naturezaDespesa.size());
		return "resultXML";
	}
	
}
