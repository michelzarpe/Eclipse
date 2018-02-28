package action;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.XMLResult;
import bean.MotivoViagem;

import com.opensymphony.xwork2.ActionSupport;

import dao.MotivoViagemDAO;

@Results({ @Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })
public class MotivoViagemAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private Object					xmlModel;
	@Autowired
	@Qualifier("HMotivoViagemDAO")
	private MotivoViagemDAO		motivoViagemDAO;

	public MotivoViagemDAO getMotivoViagemDAO() {
		return motivoViagemDAO;
	}

	public void setMotivoViagemDAO(MotivoViagemDAO motivoViagemDAO) {
		this.motivoViagemDAO = motivoViagemDAO;
	}

	public Object getXmlModel() {
		return xmlModel;
	}

	public void setXmlModel(Object xmlModel) {
		this.xmlModel = xmlModel;
	}

	public String listAllXML() throws Exception {
		List<MotivoViagem> motivos = motivoViagemDAO.listAll("desMtv");
		this.setXmlModel(motivos);
		return "resultXML";
	}
}
