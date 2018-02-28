package action;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.opensymphony.xwork2.ActionSupport;
import bean.Bairro;
import util.Util;
import dao.BairroDAO;
import result.JSONResult;
import result.XMLResult;

@Results({ @Result(name = "success", value = "", type = JSONResult.class),
	@Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })
public class BairroAction extends ActionSupport {
	private static final long			serialVersionUID			= 1L;
	private Object							JSONModel;
	private Object							xmlModel;
	private String							totalCount;
	
	private Class							tipo							= Bairro.class;
	private int 							codCid;
	
	@Autowired
	@Qualifier("Util")
	private Util							util;
	
	@Autowired
	@Qualifier("HBairroDAO")
	private BairroDAO		bairroDAO;

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

	public int getCodCid() {
		return codCid;
	}

	public void setCodCid(int codCid) {
		this.codCid = codCid;
	}

	public Class getTipo() {
		return tipo;
	}

	public void setTipo(Class tipo) {
		this.tipo = tipo;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public BairroDAO getBairroDAO() {
		return bairroDAO;
	}

	public void setBairroDAO(BairroDAO bairroDAO) {
		this.bairroDAO = bairroDAO;
	}
	
	public String listAll() throws Exception {
		if (codCid > 0) {
			setJSONModel(this.bairroDAO.listAllByCid(codCid));
		}
    	return SUCCESS;
	}

	public String listAllXML() throws Exception {
		List<Bairro> list_Bairros;
			if (codCid > 0) {
				list_Bairros = bairroDAO.listAllByCid(codCid);
				setXmlModel(list_Bairros);
				this.totalCount = String.valueOf(list_Bairros.size());
			}else{
				list_Bairros = bairroDAO.listAll("nomBai");
				setXmlModel(list_Bairros);
				this.totalCount = String.valueOf(list_Bairros.size());
			}

		return "resultXML";
	}
	
}
