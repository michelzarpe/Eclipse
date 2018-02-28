package action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.XMLResult;
import bean.Colaborador.EstadoCivil;
import bean.CondicaoPagamento;

import com.opensymphony.xwork2.ActionSupport;

import dao.CondicaoPagamentoDAO;

@Results({ @Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })
public class CondicaoPagamentoAction extends ActionSupport {
	private static final long		serialVersionUID	= 1L;
	private Object						xmlModel;
	private String						totalCount;
	@Autowired
	@Qualifier("HCondicaoPagamentoDAO")
	private CondicaoPagamentoDAO	condicaoPagamentoDAO;

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
		List<CondicaoPagamento> condiList = new ArrayList<CondicaoPagamento>();
		condiList = condicaoPagamentoDAO.listAll("desCpg");
		setXmlModel(condiList);
		this.totalCount = String.valueOf(EstadoCivil.values().length);
		return "resultXML";
	}

	public String listAllCompras() throws Exception {
		List<CondicaoPagamento> condiList = new ArrayList<CondicaoPagamento>();
		CondicaoPagamento condicaoPagamento = new CondicaoPagamento();
		condicaoPagamento.setAplCpg("C");
		condicaoPagamento.setSitCpg("A");
		condiList = condicaoPagamentoDAO.listByExample(condicaoPagamento);
		setXmlModel(condiList);
		this.totalCount = String.valueOf(condiList.size());
		return "resultXML";
	}

	public CondicaoPagamentoDAO getCondicaoPagamentoDAO() {
		return condicaoPagamentoDAO;
	}

	public void setCondicaoPagamentoDAO(CondicaoPagamentoDAO condicaoPagamentoDAO) {
		this.condicaoPagamentoDAO = condicaoPagamentoDAO;
	}

}
