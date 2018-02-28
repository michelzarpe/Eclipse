package action;

import org.apache.struts2.config.Result;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import result.BasicResult;
import result.JSONResult; 
import result.XMLResult;
import util.Util;
import com.opensymphony.xwork2.ActionSupport;
import dao.ClasseEscalaRondaDAO;
import dao.EscalaHorarioRondaMZDAO;
import bean.EscalaHorarioRondaMZ;

@Results({ @Result(name = "success", value = "", type = JSONResult.class),
		@Result(name = "resultBasicEsc", value = "xmlModel", type = BasicResult.class),
		@Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })

public class EscalaHorarioRondaMZAction extends ActionSupport {

	private static final long			serialVersionUID			= 1L;
	private Object							JSONModel;
	private Object							xmlModel;
	private String							totalCount;
	
	private String                   nomesc;
	private int								claesc;
	private Class tipo							= EscalaHorarioRondaMZ.class;
	private EscalaHorarioRondaMZ		escalaHorarioRondaMZ	= new EscalaHorarioRondaMZ();

	@Autowired
	@Qualifier("Util")
	private Util							util;

	@Autowired
	@Qualifier("HEscalaHorarioRondaMZDAO")
	private EscalaHorarioRondaMZDAO	escalaHorarioRondaMZDAO;

	@Autowired
	@Qualifier("HClasseEscalaRondaDAO")
	private ClasseEscalaRondaDAO		classeEscalaRondaDAO;

	public Class getTipo() {
		return tipo;
	}

	public void setTipo(Class tipo) {
		this.tipo = tipo;
	}

	public EscalaHorarioRondaMZ getEscalaHorarioRondaMZ() {
		return escalaHorarioRondaMZ;
	}

	public void setEscalaHorarioRondaMZ(EscalaHorarioRondaMZ escalaHorarioRondaMZ) {
		this.escalaHorarioRondaMZ = escalaHorarioRondaMZ;
	}

	public Object getJSONModel() {
		return JSONModel;
	}

	public void setJSONModel(Object jSONModel) {
		JSONModel = jSONModel;
	}

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

	public EscalaHorarioRondaMZDAO getEscalaHorarioRondaMZDAO() {
		return escalaHorarioRondaMZDAO;
	}

	public void setEscalaHorarioRondaMZDAO(EscalaHorarioRondaMZDAO escalaHorarioRondaMZDAO) {
		this.escalaHorarioRondaMZDAO = escalaHorarioRondaMZDAO;
	}

	public String getNomesc() {
		return nomesc;
	}

	public void setNomesc(String nomesc) {
		this.nomesc = nomesc;
	}

	public int getClaesc() {
		return claesc;
	}

	public void setClaesc(int claesc) {
		this.claesc = claesc;
	}

	public String listAll() throws Exception {
		setJSONModel(escalaHorarioRondaMZDAO.retEscClasse(claesc));
		return SUCCESS;
	}

	public String listFiltrosXML() throws Exception {
		Map<String, Object> parametros = new HashMap<String, Object>();
		if (claesc > 0)
			parametros.put("claEsc", claesc);
	
	List<EscalaHorarioRondaMZ> list_escalaHorarioRondaMZ =	new ArrayList<EscalaHorarioRondaMZ>();
	list_escalaHorarioRondaMZ = escalaHorarioRondaMZDAO.busca(parametros);
		setXmlModel(list_escalaHorarioRondaMZ);
		this.totalCount = String.valueOf(list_escalaHorarioRondaMZ.size());
		return "resultXML";
	}
 
	public String listAllXML() throws Exception {
		if (claesc > 0) {
			List<EscalaHorarioRondaMZ> list_escalaHorarioRondaMZ = escalaHorarioRondaMZDAO.retEscClasse(claesc);
			setXmlModel(list_escalaHorarioRondaMZ);
			this.totalCount = String.valueOf(list_escalaHorarioRondaMZ.size());
		}

		return "resultXML";
	}
}
