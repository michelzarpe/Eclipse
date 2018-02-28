package action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import util.Util;

import bean.Cidade;
import bean.Cidade.Estado;

import com.opensymphony.xwork2.ActionSupport;

import dao.CidadeDAO;

@Results({ @Result(name = "pesqSimp", value = "/paginas/jsp/pesquisa/pesqCidSimp.jsp"),
		@Result(name = "listResultSimp", value = "/paginas/jsp/pesquisa/listCidSimp.jsp") })
public class PesquisaCidadeAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private String					nome;
	private String					estado;
	private String					campo;
	@Autowired
	@Qualifier("HCidadeDAO")
	private CidadeDAO				cidadeDAO;
	@Autowired
	@Qualifier("Util")
	private Util util;

	public CidadeDAO getCidadeDAO() {
		return cidadeDAO;
	}

	public void setCidadeDAO(CidadeDAO cidadeDAO) {
		this.cidadeDAO = cidadeDAO;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public Estado[] getEstados() {
		return Estado.values();
	}

	public String pesqCidSimp() {
		util.removeDaSessao("cidades");
		return "pesqSimp";
	}

	public void pesquisa() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		if (!this.nome.trim().equals("")) {
			parametros.put("nomCid", nome);
		}
		if (!this.estado.trim().equals("")) {
			parametros.put("estCid", estado);
		}
		List<Cidade> cidades = cidadeDAO.busca(parametros);
		util.registraSessao("cidades", cidades);
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
