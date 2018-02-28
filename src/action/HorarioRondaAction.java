package action;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.opensymphony.xwork2.ActionSupport;
import bean.HorarioRonda;
import dao.HorarioRondaDAO;
import result.BasicResult;
import result.JSONResult;
import result.XMLResult;
import util.Util;
import java.util.List;


@Results({ @Result(name = "success", value = "", type = JSONResult.class),
	@Result(name = "resultBasicEsc", value = "xmlModel", type = BasicResult.class),
	@Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })

public class HorarioRondaAction extends ActionSupport {

	private static final long			serialVersionUID			= 1L;
	private Object							JSONModel;
	private Object							xmlModel;
	private String							totalCount;
	private Class							tipo							= HorarioRonda.class;
	private int 							codEsc;
	
	
	@Autowired
	@Qualifier("Util")
	private Util							util;

	@Autowired
	@Qualifier("HHorarioRondaDAO")
	private HorarioRondaDAO	horarioRondaDAO;
	
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


	public Class getTipo() {
		return tipo;
	}


	public void setTipo(Class tipo) {
		this.tipo = tipo;
	}


	public int getCodEsc() {
		return codEsc;
	}


	public void setCodEsc (int codEsc) {
		this.codEsc = codEsc;
	}


	public Util getUtil() {
		return util;
	}


	public void setUtil(Util util) {
		this.util = util;
	}


	public HorarioRondaDAO getHorarioRondaDAO() {
		return horarioRondaDAO;
	}


	public void setHorarioRondaDAO(HorarioRondaDAO horarioRondaDAO) {
		this.horarioRondaDAO = horarioRondaDAO;
	}


	public String listAll() throws Exception {
		setJSONModel(horarioRondaDAO.retHorRonda(codEsc));
		return SUCCESS;
	}


	public String listAllXML() throws Exception {
		HorarioRonda hor;
		if (codEsc > 0) {
			List<HorarioRonda> list_HorarioRonda = horarioRondaDAO.retHorRonda(codEsc);
			for(int x=0; x < list_HorarioRonda.size(); x++){
				 hor = new HorarioRonda(list_HorarioRonda.get(x).getCodhor(), list_HorarioRonda.get(x).getDeshor());
				 hor.setNumSeq(x+1);
				 list_HorarioRonda.set(x, hor);
				 list_HorarioRonda.get(x).getDiaSem();
			}
			setXmlModel(list_HorarioRonda);
			this.totalCount = String.valueOf(list_HorarioRonda.size());
		}

		return "resultXML";
	}

}