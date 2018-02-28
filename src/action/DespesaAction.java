package action;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.XMLResult;
import bean.Despesa;

import com.opensymphony.xwork2.ActionSupport;

import dao.DespesaDAO;

@Results({ @Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })
public class DespesaAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private Object					xmlModel;
	@Autowired
	@Qualifier("HDespesaDAO")
	private DespesaDAO			despesaDAO;

	public Object getXmlModel() {
		return xmlModel;
	}

	public void setXmlModel(Object xmlModel) {
		this.xmlModel = xmlModel;
	}

	public String listAllXML() throws Exception {
		List<Despesa> despesas = despesaDAO.listAll("desDes");
		this.setXmlModel(despesas);
		return "resultXML";
	}

	public DespesaDAO getDespesaDAO() {
		return despesaDAO;
	}

	public void setDespesaDAO(DespesaDAO despesaDAO) {
		this.despesaDAO = despesaDAO;
	}
}
