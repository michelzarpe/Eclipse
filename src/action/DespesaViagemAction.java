package action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.StreamResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import result.BasicResult;
import bean.Centro;
import bean.Despesa;
import bean.DespesaViagem;
import bean.DespesaViagem.Id;
import bean.Viagem;

import com.opensymphony.xwork2.ActionSupport;

import dao.DespesaViagemDAO;

@Results({
		@Result(name = "resultXML", value = "xmlModel", type = BasicResult.class),
		@Result(name = "sucTxt", value = "inputStream", type = StreamResult.class, params = {"inputName", "inputStream" }) })
public class DespesaViagemAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private Object					xmlModel;
	private DespesaViagem		despesaViagem		= new DespesaViagem();
	private String					totalCount;
	private Class					tipo					= DespesaViagem.class;
	private InputStream			inputStream;
	
	@Autowired
	@Qualifier("HDespesaViagemDAO")
	private DespesaViagemDAO	despesaViagemDAO;

	public DespesaViagemDAO getDespesaViagemDAO() {
		return despesaViagemDAO;
	}

	public void setDespesaViagemDAO(DespesaViagemDAO despesaViagemDAO) {
		this.despesaViagemDAO = despesaViagemDAO;
	}

	public Object getXmlModel() {
		return xmlModel;
	}

	public void setXmlModel(Object xmlModel) {
		this.xmlModel = xmlModel;
	}

	public DespesaViagem getDespesaViagem() {
		return despesaViagem;
	}

	public void setDespesaViagem(DespesaViagem despesaViagem) {
		this.despesaViagem = despesaViagem;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public Class getTipo() {
		return tipo;
	}

	public void setTipo(Class tipo) {
		this.tipo = tipo;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String excluir() {
		try {
			Id id = new Id();
			id.setCentro(new Centro(despesaViagem.getId().getCentro().getId()));
			id.setDespesa(new Despesa(despesaViagem.getId().getDespesa().getId()));
			id.setViagem(new Viagem(despesaViagem.getId().getViagem().getId()));
			despesaViagem = despesaViagemDAO.loadById(id);
			despesaViagemDAO.delete(despesaViagem);
			inputStream = new ByteArrayInputStream(("Despesa excluida com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(("Despesa nao foi excluida.").getBytes());
		}
		return "sucTxt";
	}
}
