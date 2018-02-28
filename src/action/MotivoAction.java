package action;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.XMLResult;
import bean.Motivo;
import bean.RequisicaoEstoque.MotivoRequisicao;

import com.opensymphony.xwork2.ActionSupport;

import dao.MotivoDAO;

@Results({ @Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })
public class MotivoAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private Object					xmlModel;
	private String					totalCount;
	private String					start					= "";
	private String					limit					= "";
	private String					sort					= "";
	private String					dir					= "";
	@Autowired
	@Qualifier("HMotivoDAO")
	private MotivoDAO				motivoDAO;

	public MotivoDAO getMotivoDAO() {
		return motivoDAO;
	}

	public void setMotivoDAO(MotivoDAO motivoDAO) {
		this.motivoDAO = motivoDAO;
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

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String listAllXML() throws Exception {
		List<Motivo> motivos = motivoDAO.listAll("desMtv");
		setXmlModel(motivos);
		this.totalCount = String.valueOf(motivos.size());
		return "resultXML";
	}

	public String listMotivosReqMC() {
		MotivoRequisicao[] motivos = MotivoRequisicao.values();
		setXmlModel(motivos);
		return "resultXML";

	}
}
