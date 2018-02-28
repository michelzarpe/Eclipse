package action;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.StreamResult;
import org.apache.tools.ant.filters.StringInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.XMLResult;
import bean.Banco;

import com.opensymphony.xwork2.ActionSupport;

import dao.BancoDAO;

@Results( {
		@Result(name = "success", value = "inputStream", type = StreamResult.class, params = {
				"contentType", "text/html; charadd=UTF-8", "inputName", "inputStream" }),
		@Result(name = "successXML", value = "xmlModel", type = XMLResult.class),
		@Result(name = "successBusca", value = "inputStream", type = StreamResult.class, params = {
				"contentType", "text/html; charadd=UTF-8", "inputName", "inputStream" }),
		@Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })
public class BancoAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private String id;
	private String nomBan;
	private String mensagem;
	private int numero;
	private int resultado;
	private Object xmlModel;
	private InputStream inputStream;
	private Banco banco;
	private String totalCount;
	@Autowired
	@Qualifier("HBancoDAO")
	private BancoDAO bancoDAO;

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNomBan() {
		return nomBan;
	}

	public void setNomBan(String nomBan) {
		this.nomBan = nomBan;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getResultado() {
		return resultado;
	}

	public void setResultado(int resultado) {
		this.resultado = resultado;
	}

	public Object getXmlModel() {
		return xmlModel;
	}

	public void setXmlModel(Object xmlModel) {
		this.xmlModel = xmlModel;
	}

	public String gravar() {
		String resultado = "Banco cadastrado";
		inputStream = new StringInputStream(resultado);
		return SUCCESS;
	}

	public String calcular() {
		this.resultado = this.numero * 10;
		return SUCCESS;
	}

	public String listar() throws Exception {
		List<Banco> bancos = new LinkedList<Banco>();
		bancos = bancoDAO.listAll("nomBan");
		setXmlModel(bancos);
		return "successXML";
	}

	public String buscar() throws Exception {
		List<Banco> bancos = bancoDAO.buscar("nomBan", this.nomBan);
		String resultado = "<ul>";
		for (Banco banco : bancos) {
			resultado = resultado + "<li>" + banco.getNomBan() + "</li>";
		}
		resultado = resultado + "</ul>";
		inputStream = new StringInputStream(resultado);
		return "successBusca";
	}

	public String listAllXML() throws Exception {
		List<Banco> bancos = bancoDAO.listAll("nomBan");
		setXmlModel(bancos);
		this.totalCount = String.valueOf(bancos.size());
		return "resultXML";
	}
}
