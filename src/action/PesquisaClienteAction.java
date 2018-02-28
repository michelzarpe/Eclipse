package action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import util.Util;

import bean.Cliente;
import bean.Empresa;

import com.opensymphony.xwork2.ActionSupport;

import dao.ClienteDAO;
import dao.EmpresaDAO;

@Results({ @Result(name = "pesqSimp", value = "/paginas/jsp/pesquisa/pesqCliSimp.jsp"),
		@Result(name = "listResultSimp", value = "/paginas/jsp/pesquisa/listCliSimp.jsp") })
public class PesquisaClienteAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private String					nome;
	private String					empresa;
	@Autowired
	@Qualifier("HEmpresaDAO")
	private EmpresaDAO			empresaDAO;
	@Autowired
	@Qualifier("HClienteDAO")
	private ClienteDAO			clienteDAO;
	@Autowired
	@Qualifier("Util")
	private Util util;

	public EmpresaDAO getEmpresaDAO() {
		return empresaDAO;
	}

	public void setEmpresaDAO(EmpresaDAO empresaDAO) {
		this.empresaDAO = empresaDAO;
	}

	public ClienteDAO getClienteDAO() {
		return clienteDAO;
	}

	public void setClienteDAO(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public List<Empresa> getEmpresas() throws Exception {
		return empresaDAO.listAll("nomEmp");
	}

	public String pesqCliSimp() {
		util.removeDaSessao("clientes");
		return "pesqSimp";
	}

	public void pesquisa() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		if (!this.nome.trim().equals("")) {
			parametros.put("razSoc", nome);
		}
		if (!this.empresa.trim().equals("")) {
			parametros.put("empresaId", empresa);
		}
		List<Cliente> clientes = clienteDAO.busca(parametros);
		util.registraSessao("clientes", clientes);
	}

	public String listResultSimp() {
		pesquisa();
		return "listResultSimp";
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}
}
