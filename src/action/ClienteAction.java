package action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.XMLResult;
import bean.Cliente;

import com.opensymphony.xwork2.ActionSupport;

import dao.ClienteDAO;

@Results({ @Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })
public class ClienteAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private Object					xmlModel;
	private String					totalCount;
	private Class					tipo;
	private Cliente				cliente				= new Cliente();
	@Autowired
	@Qualifier("HClienteDAO")
	private ClienteDAO			clienteDAO;

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

	public Class getTipo() {
		return tipo;
	}

	public void setTipo(Class tipo) {
		this.tipo = tipo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String listByExample() {
		List<Cliente> clientes = new ArrayList<Cliente>();
		try {
			clientes = clienteDAO.listByExample(this.cliente);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.totalCount = String.valueOf(clientes.size());
		this.tipo = Cliente.class;
		this.setXmlModel(clientes);
		return "resultXML";
	}

	public ClienteDAO getClienteDAO() {
		return clienteDAO;
	}

	public void setClienteDAO(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}
}
