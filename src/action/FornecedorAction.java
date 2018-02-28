package action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.StreamResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.BasicResult;
import bean.Fornecedor;

import com.opensymphony.xwork2.ActionSupport;

import dao.FornecedorDAO;

@Results({
		@Result(name = "resultXML", value = "xmlModel", type = BasicResult.class),
		@Result(name = "sucTxt", value = "inputStream", type = StreamResult.class, params = {
				"inputName", "inputStream" }) })
public class FornecedorAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private Fornecedor			fornecedor			= new Fornecedor();
	private Class					tipo;
	private String					totalCount;
	private Object					xmlModel;
	private InputStream			inputStream;
	@Autowired
	@Qualifier("HFornecedorDAO")
	private FornecedorDAO		fornecedorDAO;

	public Object getXmlModel() {
		return xmlModel;
	}

	public void setXmlModel(Object xmlModel) {
		this.xmlModel = xmlModel;
	}

	public Class getTipo() {
		return tipo;
	}

	public void setTipo(Class tipo) {
		this.tipo = tipo;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String findByExample() {
		try {
			List<Fornecedor> fornecedores = fornecedorDAO.listByExample(fornecedor);
			this.totalCount = String.valueOf(fornecedores.size());
			this.tipo = Fornecedor.class;
			setXmlModel(fornecedores);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "resultXML";
	}

	public String gravaPreCadastro() {
		try {
			if (this.fornecedor.getId() > 0) {
				fornecedorDAO.update(this.fornecedor);
			} else {
				fornecedorDAO.insert(this.fornecedor);
			}
			inputStream = new ByteArrayInputStream(
					("{success:true, msg: 'PRE-CADASTRO REALIZADO COM SUCESSO'}").getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			String msg = "PRE-CADASTRO NAO REALIZADO. ERRO: " + e.getMessage();
			inputStream = new ByteArrayInputStream(("{success:false, msg: " + msg + "}").getBytes());
		}
		return "sucTxt";
	}

	public FornecedorDAO getFornecedorDAO() {
		return fornecedorDAO;
	}

	public void setFornecedorDAO(FornecedorDAO fornecedorDAO) {
		this.fornecedorDAO = fornecedorDAO;
	}
}
