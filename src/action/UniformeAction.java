package action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.XMLResult;
import bean.Caracteristica;
import bean.Uniforme;

import com.opensymphony.xwork2.ActionSupport;

import dao.UniformeDAO;

@Results({ @Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })
public class UniformeAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private Object					xmlModel;
	private String					totalCount;
	private String					start					= "";
	private String					limit					= "";
	private String					sort					= "";
	private String					dir					= "";
	private String					sexo					= "";
	private int						empresa				= 0;
	private Uniforme				uniforme				= new Uniforme();
	@Autowired
	@Qualifier("HUniformeDAO")
	UniformeDAO						uniformeDAO;

	public int getEmpresa() {
		return empresa;
	}

	public void setEmpresa(int empresa) {
		this.empresa = empresa;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Uniforme getUniforme() {
		return uniforme;
	}

	public void setUniforme(Uniforme uniforme) {
		this.uniforme = uniforme;
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
		List<Uniforme> uniformes = new ArrayList<Uniforme>();
		Caracteristica caracteristicaSexo = new Caracteristica();

		// separa características de sexo e características de segmento
		// características de sexo
		if (this.sexo.equals("F")) {// MULHERES
			caracteristicaSexo.setId("FEM");
		}

		if (this.sexo.equals("M")) {// HOMENS
			caracteristicaSexo.setId("MSC");
		}

		// caracteristica de segmento
		Caracteristica caracteristicaSegmento = new Caracteristica();
		if (this.empresa == 1) {
			caracteristicaSegmento.setId("VIG");
		} else if ((this.empresa == 23) || (this.empresa == 5)) {
			caracteristicaSegmento.setId("ASS");
		} else {
			caracteristicaSegmento.setId("ADM");
		}

		uniformes = uniformeDAO.listByCaracteristica(uniforme.getTipoEPI().getId(),
				uniforme.getSitEpi(), caracteristicaSexo, caracteristicaSegmento);

		// uniformes = uniformeDAO.listByExample(uniforme);
		setXmlModel(uniformes);
		this.totalCount = String.valueOf(uniformes.size());
		return "resultXML";
	}

	public UniformeDAO getUniformeDAO() {
		return uniformeDAO;
	}

	public void setUniformeDAO(UniformeDAO uniformeDAO) {
		this.uniformeDAO = uniformeDAO;
	}
}
