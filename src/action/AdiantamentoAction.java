package action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.StreamResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.BasicResult;
import util.Util;
import bean.AdiantamentoViagem;
import bean.AdiantamentoViagem.Id;
import bean.Viagem;

import com.opensymphony.xwork2.ActionSupport;

import dao.AdiantamentoViagemDAO;
import dao.ViagemDAO;

@Results({
		@Result(name = "resultXML", value = "xmlModel", type = BasicResult.class),
		@Result(name = "sucTxt", value = "inputStream", type = StreamResult.class, params = {
				"inputName", "inputStream" }) })
public class AdiantamentoAction extends ActionSupport implements ServletRequestAware {
	private static final long		serialVersionUID		= 1L;
	private Object						xmlModel;
	private AdiantamentoViagem		adiantamentoViagem	= new AdiantamentoViagem();
	private String						totalCount;
	private Class						tipo						= AdiantamentoViagem.class;
	private int							viagemId;
	private int							adiantamentoId;
	private InputStream				inputStream;
	@Autowired
	@Qualifier("HViagemDAO")
	private ViagemDAO					viagemDAO;
	@Autowired
	@Qualifier("HAdiantamentoViagemDAO")
	private AdiantamentoViagemDAO	adiantamentoViagemDAO;
	@Autowired
	@Qualifier("Util")
	private Util util;
	private HttpServletRequest servletRequest;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public int getViagemId() {
		return viagemId;
	}

	public void setViagemId(int viagemId) {
		this.viagemId = viagemId;
	}

	public int getAdiantamentoId() {
		return adiantamentoId;
	}

	public void setAdiantamentoId(int adiantamentoId) {
		this.adiantamentoId = adiantamentoId;
	}

	public Object getXmlModel() {
		return xmlModel;
	}

	public void setXmlModel(Object xmlModel) {
		this.xmlModel = xmlModel;
	}

	public AdiantamentoViagem getAdiantamentoViagem() {
		return adiantamentoViagem;
	}

	public void setAdiantamentoViagem(AdiantamentoViagem adiantamentoViagem) {
		this.adiantamentoViagem = adiantamentoViagem;
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

	public void imprimirAdiantamento() throws Exception {
		Viagem viagem = viagemDAO.loadById(viagemId);
		Id id = new Id(viagem, adiantamentoId);
		AdiantamentoViagem adiantamentoViagem = adiantamentoViagemDAO.loadById(id);
		String path = servletRequest.getSession().getServletContext().getRealPath("/paginas/reports/");
		Map parametros = new HashMap();
		parametros.put("SUBREPORT_DIR", path + "//");
		JRDataSource dsCentros = new JRBeanCollectionDataSource(viagem.getCentrosVisitados());
		parametros.put("DATA", dsCentros);
		ArrayList<Object> dados = new ArrayList<Object>();
		dados.add(adiantamentoViagem);
		JRDataSource adt1 = new JRBeanCollectionDataSource(dados);
		JRDataSource adt2 = new JRBeanCollectionDataSource(dados);
		parametros.put("DATA2", adt1);
		parametros.put("DATA3", adt2);

		util.geraRelatorio(dados, "Adiantamento2vias.jasper", "Adiantamento", parametros, "pdf");
	}

	public String excluir() {
		try {
			Id id = new Id(new Viagem(adiantamentoViagem.getId().getViagem().getId()), adiantamentoViagem.getId().getId());
			this.adiantamentoViagem = adiantamentoViagemDAO.loadById(id);
			adiantamentoViagemDAO.delete(adiantamentoViagem);
			inputStream = new ByteArrayInputStream(("Adiantamento excluido com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(("Adiantamento nao foi excluido.").getBytes());
		}
		return "sucTxt";
	}

	public String fecharAdiantamento() throws Exception {

		return "sucTxt";
	}

	public ViagemDAO getViagemDAO() {
		return viagemDAO;
	}

	public void setViagemDAO(ViagemDAO viagemDAO) {
		this.viagemDAO = viagemDAO;
	}

	public AdiantamentoViagemDAO getAdiantamentoViagemDAO() {
		return adiantamentoViagemDAO;
	}

	public void setAdiantamentoViagemDAO(AdiantamentoViagemDAO adiantamentoViagemDAO) {
		this.adiantamentoViagemDAO = adiantamentoViagemDAO;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	@Override
	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;	
	}

}
