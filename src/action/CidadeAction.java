package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.JSONResult;
import result.XMLResult;
import bean.Cidade;
import bean.Estado;

import com.opensymphony.xwork2.ActionSupport;

import dao.CidadeDAO;

@Results({ @Result(name = "resultXML", value = "xmlModel", type = XMLResult.class),
		@Result(name = "jsonResult", type = JSONResult.class, value = "JSONModel") })
public class CidadeAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private Object					xmlModel;
	private String					totalCount;
	private String					start					= "";
	private String					limit					= "";
	private String					sort					= "";
	private String					dir					= "";
	private String 				codEst;
	private Cidade					cidade				= new Cidade();
	private Object					JSONModel;
	private boolean				resultado;
	private int						total;
	private Class					tipo;
	
	@Autowired
	@Qualifier("HCidadeDAO")
	private CidadeDAO				cidadeDAO;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
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

	public Class getTipo() {
		return tipo;
	}

	public void setTipo(Class tipo) {
		this.tipo = tipo;
	}

	public String getCodEst() {
		return codEst;
	}

	public void setCodEst(String codEst) {
		this.codEst = codEst;
	}

	public String listAllXML() throws Exception {
		Map<String, Object> parametros = new HashMap<String, Object>();
		if (cidade.getId() > 0)
			parametros.put("id", cidade.getId());
		
		if ((cidade.getNomCid() != null) && (!cidade.getNomCid().equals(""))) {
			parametros.put("nomCid", cidade.getNomCid());
		}
		
		if ((cidade.getEstCid() != null) && (!cidade.getEstCid().equals(""))) {
			parametros.put("estCid", cidade.getEstCid().toString());
		}
			
		List<Cidade> cidades = cidadeDAO.busca(parametros);
		setXmlModel(cidades);
		this.totalCount = String.valueOf(cidades.size());
		return "resultXML";
	}

	public String listAllXMLByEst() throws Exception {
  		List<Cidade> cidades = cidadeDAO.listAllByUF(codEst);
		setXmlModel(cidades);
		this.totalCount = String.valueOf(cidades.size());
		return "resultXML";
	}
	
	public boolean isResultado() {
		return resultado;
	}

	public void setResultado(boolean resultado) {
		this.resultado = resultado;
	}
	
	public CidadeDAO getCidadeDAO() {
		return cidadeDAO;
	}

	public void setCidadeDAO(CidadeDAO cidadeDAO) {
		this.cidadeDAO = cidadeDAO;
	}

	/*lista de estados para jsonResult*/
	public String listEstados() {
		List<Estado> estados = new ArrayList<Estado>();
		estados.add(new Estado("AC", "Acre"));
		estados.add(new Estado("AL", "Alagoas"));
		estados.add(new Estado("AP", "Amapá"));
		estados.add(new Estado("AM", "Amazonas"));
		estados.add(new Estado("BA", "Bahia"));
		estados.add(new Estado("CE", "Ceará"));
		estados.add(new Estado("DF", "Distrito Federal"));
		estados.add(new Estado("ES", "Espírito Santo"));
		estados.add(new Estado("GO", "Goiás"));
		estados.add(new Estado("MA", "Maranhão"));
		estados.add(new Estado("MT", "Mato Grosso"));
		estados.add(new Estado("MS", "Mato Grosso do Sul"));
		estados.add(new Estado("MG", "Minas Gerais"));
		estados.add(new Estado("PA", "Pará"));
		estados.add(new Estado("PB", "Paraíba"));
		estados.add(new Estado("PR", "Paraná"));
		estados.add(new Estado("PE", "Pernambuco"));
		estados.add(new Estado("PI", "Piauí"));
		estados.add(new Estado("RJ", "Rio de Janeiro"));
		estados.add(new Estado("RN", "Rio Grande do Norte"));
		estados.add(new Estado("RS", "Rio Grande do Sul"));
		estados.add(new Estado("RO", "Rondônia"));
		estados.add(new Estado("RR", "Roraima"));
		estados.add(new Estado("SC", "Santa Catarina"));
		estados.add(new Estado("SP", "São Paulo"));
		estados.add(new Estado("SE", "Sergipe"));
		estados.add(new Estado("TO", "Tocantins"));
		this.total = estados.size();
		this.resultado = true;
		this.tipo = Estado.class;
		setJSONModel(estados);
		return "jsonResult";
	}

}