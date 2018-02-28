package action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.StreamResult;
import org.apache.struts2.interceptor.ServletRequestAware;

import result.BasicResult;
import util.PropertiesLoader;

import com.opensymphony.xwork2.ActionSupport;

@Results({
		@Result(name = "resultXML", value = "xmlModel", type = BasicResult.class),
		@Result(name = "sucTxt", value = "inputStream", type = StreamResult.class, params = {
				"inputName", "inputStream" }) })
public class ConfiguraSistemaAction extends ActionSupport implements ServletRequestAware {
	private static final long	serialVersionUID	= 1L;
	private String			propriedade;
	private String			valor;
	private Object			xmlModel;
	private InputStream	inputStream;
	private HttpServletRequest servletRequest;

	public String getPropriedade() {
		return propriedade;
	}

	public void setPropriedade(String propriedade) {
		this.propriedade = propriedade;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Object getXmlModel() {
		return xmlModel;
	}

	public void setXmlModel(Object xmlModel) {
		this.xmlModel = xmlModel;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	@Override
   public void setServletRequest(HttpServletRequest servletRequest) {
       this.servletRequest = servletRequest;

   }

	public String lePropriedade() {
		String path = servletRequest.getSession().getServletContext().getRealPath("/WEB-INF/classes/conf.properties");
		PropertiesLoader propertiesLoader = new PropertiesLoader(path);
		String valProp = propertiesLoader.getValor(propriedade);
		inputStream = new ByteArrayInputStream(valProp.getBytes());
		return "sucTxt";
	}

	public String gravaPropriedade() {
		String path = servletRequest.getSession().getServletContext().getRealPath("/WEB-INF/classes/conf.properties");
		PropertiesLoader propertiesLoader = new PropertiesLoader(path);
		if (propertiesLoader.setValor(propriedade, valor))
			inputStream = new ByteArrayInputStream("Gravado com sucesso!".getBytes());
		else
			inputStream = new ByteArrayInputStream("Não foi possível gravar!".getBytes());
		return "sucTxt";
	}
}
