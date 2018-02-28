package action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.XMLResult;
import bean.Produto;
import bean.Produto.Situacao;
import bean.Produto.TipoProduto;

import com.opensymphony.xwork2.ActionSupport;

import dao.ProdutoDAO;

@Results({ @Result(name = "resultXML", value = "xmlModel", type = XMLResult.class) })
public class ProdutoAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private Object					xmlModel;
	private String					totalCount;
	private String					start					= "";
	private String					limit					= "";
	private String					sort					= "";
	private String					dir					= "";
	private Class					tipo;
	private Produto				produto				= new Produto();
	@Autowired
	@Qualifier("HProdutoDAO")
	private ProdutoDAO			produtoDAO;

	public ProdutoDAO getProdutoDAO() {
		return produtoDAO;
	}

	public void setProdutoDAO(ProdutoDAO produtoDAO) {
		this.produtoDAO = produtoDAO;
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

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Class getTipo() {
		return tipo;
	}

	public void setTipo(Class tipo) {
		this.tipo = tipo;
	}

	public String listAllMatExp() throws Exception {
		List<Produto> produtos = new ArrayList<Produto>();
		produto.setSitPro(Situacao.A);
		produto.setRequisitavel(true);
		produtos = produtoDAO.listByExample(produto);
		setXmlModel(produtos);
		this.totalCount = String.valueOf(produtos.size());
		this.tipo = Produto.class;
		return "resultXML";
	}

	public String listAllTipoMat() {
		setXmlModel(TipoProduto.values());
		this.totalCount = String.valueOf(TipoProduto.values().length);
		return "resultXML";
	}
}
