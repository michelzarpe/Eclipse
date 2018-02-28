package action;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.JSONResult;
import result.XMLResult;
import bean.Cargo;

import com.opensymphony.xwork2.ActionSupport;

import dao.CargoDAO;

@Results({ @Result(name = "success", value = "", type = JSONResult.class),
		@Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })
public class CargoAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private Cargo					cargo;
	private Object					JSONModel;
	private Object					xmlModel;
	private String					totalCount;
	@Autowired
	@Qualifier("HCargoDAO")
	private CargoDAO				cargoDAO;

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
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

	public Object getJSONModel() {
		return JSONModel;
	}

	public void setJSONModel(Object model) {
		JSONModel = model;
	}

	public String listAll() throws Exception {
		setJSONModel(cargoDAO.listAll("titCar"));
		return SUCCESS;
	}

	public String listAllXML() throws Exception {
		List<Cargo> cargos = cargoDAO.listByExample(cargo);
		setXmlModel(cargos);
		this.totalCount = String.valueOf(cargos.size());
		return "resultXML";
	}

	public CargoDAO getCargoDAO() {
		return cargoDAO;
	}

	public void setCargoDAO(CargoDAO cargoDAO) {
		this.cargoDAO = cargoDAO;
	}
}
