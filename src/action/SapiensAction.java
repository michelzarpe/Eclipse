package action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.config.Result;
import org.apache.struts2.dispatcher.StreamResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import util.PropertiesLoader;
import util.Util;

import com.opensymphony.xwork2.ActionSupport;

@Result(name = "responseTXT", type = StreamResult.class, value = "inputStream", params = {
		"contentType", "text/plain", "inputName", "inputStream" })
public class SapiensAction extends ActionSupport implements ServletRequestAware {
	private static final long	serialVersionUID	= 1L;
	private InputStream inputStream;
	@Autowired
	@Qualifier("Util")
	private Util util;
	private HttpServletRequest	servletRequest;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String loginSapiensSID() {
		String codigoConexao = util.loginSapiensSID(getIpSapiensSID());
		inputStream = new ByteArrayInputStream((String.valueOf(codigoConexao)).getBytes());
		return "responseTXT";
	}

	public String novaSolicitacao() {
		String ipServidor = getIpSapiensSID();
		String codigoConexao = util.loginSapiensSID(ipServidor);
		/**
		 * TODO quando passar para a base oficial, mudar o endereço
		 */
		
		String urlSID = "http://"+ipServidor+":8080/sapiensweb/conector?SIS=CO&LOGIN=SID&BASE=TESTE&USER=agendador&CONNECTION="
				+ codigoConexao
				+ "&ACAO=SID.Est.GravarSolCompra&NUMSOL=&SEQSOL=1&CODPRO=0588&QTDSOL=3";
		String retorno = util.acaoSID(urlSID);
		inputStream = new ByteArrayInputStream((String.valueOf(retorno)).getBytes());
		return "responseTXT";
	}
	
	public String novaRequisicao() {
		String ipServidor = getIpSapiensSID();
		String codigoConexao = util.loginSapiensSID(ipServidor);
		
		/**
		 * TODO quando passar para a base oficial, mudar o endereço
		 */
		String urlSID = "http://"+ipServidor+":8080/sapiensweb/conector.exe?SIS=CO&LOGIN=SID&BASE=TESTE&USER=agendador&CONNECTION="
				+ codigoConexao
				+ "&ACAO=SID.Est.Requisitar&CODPRO=&QTDEME=3&CPLPRO=CASACO_DE_PELES&UNIMED=UN";
		String retorno = util.acaoSID(urlSID);
		inputStream = new ByteArrayInputStream((String.valueOf(retorno)).getBytes());
		return "responseTXT";
	}
	
	public String insereSolicitacaoServico(){
		String ipServidor = getIpSapiensSID();
		String codigoConexao = util.loginSapiensSID(ipServidor);
		String urlSID = "http://"+ipServidor+":8080/sapiensweb/conector.exe?SIS=CO&LOGIN=SID&BASE=TESTE&USER=agendador&CONNECTION="
			+ codigoConexao
			+ "&ACAO=SID.Est.GravarSolCompra&NUMSOL=&SEQSOL=&CODSER=DIVERSOS&QTDSOL=1&CPLPRO=SERVICO X&UNIMED=UN&DATPRV=15/03/2010&PRESOL=50.00";
	String retorno = util.acaoSID(urlSID);
	inputStream = new ByteArrayInputStream((String.valueOf(retorno)).getBytes());
	return "responseTXT";
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}
	
	public String getIpSapiensSID(){
		String path = servletRequest.getSession().getServletContext().getRealPath("/WEB-INF/classes/conf.properties");
		PropertiesLoader propertiesLoader = new PropertiesLoader(path);
		String valProp = propertiesLoader.getValor("servidor_sapienssid");
		return valProp;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		
	}
}
