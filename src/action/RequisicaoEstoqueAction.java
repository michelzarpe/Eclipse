package action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.StreamResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.JSONResult;
import result.XMLResult;
import util.HibernateUtil;
import util.PropertiesLoader;
import util.ServiceFactory;
import util.Util;
import bean.Centro;
import bean.Cliente;
import bean.Filter;
import bean.Produto.TipoProduto;
import bean.RequisicaoEstoque;
import bean.RequisicaoEstoque.Setor;
import bean.RequisicaoEstoque.Situacao;
import bean.Usuario;

import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import converter.RespostaSIDConverter;
import dao.RequisicaoEstoqueDAO;
import flexjson.DateTransformer;
import flexjson.JSONDeserializer;

@Results({
		@Result(name = "txtRes", type = StreamResult.class, value = "inputStream", params = {
				"contentType", "application/json", "inputName", "inputStream" }),
		@Result(name = "resultXML", value = "xmlModel", type = XMLResult.class),
		@Result(name = "sucTxt", value = "inputStream", type = StreamResult.class, params = {
				"inputName", "inputStream", "contentType", "text/json; charset=UTF-8" }),
		@Result(name = "jsonResult", type = JSONResult.class, value = "JSONModel") })
public class RequisicaoEstoqueAction extends ActionSupport implements ServletRequestAware {
	private static final long			serialVersionUID	= 1L;
	static Logger							logger				= Logger
																				.getLogger(RequisicaoEstoqueAction.class);
	private InputStream					inputStream;
	private String							start;
	private String							limit;
	private String							sort;
	private String							dir;
	private Object							xmlModel;
	private Object							JSONModel;
	private Class							tipo;
	private RequisicaoEstoque			requisicaoEstoque	= new RequisicaoEstoque();
	private String							tipoMaterial;
	private int								codEmp;
	private String							items;
	private String							mensagem				= "";
	private boolean						resultado;
	private Cliente						cliente				= new Cliente();
	private List<RequisicaoEstoque>	requisicoes			= new ArrayList<RequisicaoEstoque>();
	private String							cmpReq;
	private String							setor;
	private int								total;
	private String							totalCount;
	private String							filter;
	private boolean						listAprovados;
	protected int							sequencia;
	private Centro							centro;
	@Autowired
	@Qualifier("HRequisicaoEstoqueDAO")
	private RequisicaoEstoqueDAO		requisicaoEstoqueDAO;
	@Autowired
	@Qualifier("Util")
	private Util							util;
	private HttpServletRequest			servletRequest;

	private void aprovaItem(JSONObject object) {
		Usuario usuarioSessao = (Usuario) util.recuperaSessao("Usuario");
		Date dataAtual = new Date();
		requisicaoEstoque = new JSONDeserializer<RequisicaoEstoque>()
				.use(null, RequisicaoEstoque.class)
				.use(Date.class, new DateTransformer("dd/MM/yyyy HH:mm:ss"))
				.deserialize(object.toString());
		if (tipoMaterial.equals("MC"))
			this.setCliente(requisicaoEstoque.getCliente());
		requisicaoEstoque.setUsuApr(usuarioSessao);
		requisicaoEstoque.setDatApr(dataAtual);
		try {
			requisicaoEstoque.setSitEme(Situacao.Aprovado);
			requisicaoEstoqueDAO.update(requisicaoEstoque);
			this.mensagem = "Requisição aprovada com sucesso";
			this.resultado = true;
		} catch (Exception e) {
			logger.error("Erro aprovando requisição. Causa: " + e.getMessage());
			this.mensagem = "Requisição não pode ser aprovada.";
			this.resultado = false;
		}
	}

	public String aprovar() throws UnsupportedEncodingException {
		HttpServletRequest req = ServletActionContext.getRequest();
		this.items = (String) req.getParameter("map.items");
		if (String.valueOf(this.items.charAt(0)).equals("[")) {
			JSONArray jsonArray = JSONArray.fromObject(this.items);
			for (Iterator iterator = jsonArray.iterator(); iterator.hasNext();) {
				JSONObject object = (JSONObject) iterator.next();
				aprovaItem(object);
			}
			inputStream = new ByteArrayInputStream(("{map:{items:[], success:" + this.resultado
					+ ", totalCount : 0, msg: '" + this.mensagem + "'}}").getBytes("UTF-8"));
			return "txtRes";
		} else {
			JSONObject object = (JSONObject) JSONObject.fromObject(this.items);
			aprovaItem(object);
			inputStream = new ByteArrayInputStream(("{map:{items:[], success:" + this.resultado
					+ ", totalCount : 0, msg: '" + this.mensagem + "'}}").getBytes("UTF-8"));
			return "txtRes";
		}

	}

	public String excluiItem() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		RequisicaoEstoque reqEstoque = new RequisicaoEstoque();
		int idReq = Integer.valueOf(req.getParameter("map.items"));
		reqEstoque = requisicaoEstoqueDAO.loadById(idReq);
		this.setCliente(reqEstoque.getCliente());
		try {
			requisicaoEstoqueDAO.delete(reqEstoque);
			inputStream = new ByteArrayInputStream(
					("{map:{items:[], success:true, totalCount : 0, msg: 'Item excluído com sucesso.'}}")
							.getBytes("UTF-8"));
			return "txtRes";
		} catch (Exception e) {
			logger.error("NAO FOI POSSIVEL EXCLUIR O ITEM. Detalhes: " + e.getCause());
			inputStream = new ByteArrayInputStream(
					("{map:{items:[], success:true, totalCount : 0, msg: ''}}").getBytes("UTF-8"));
			return "txtRes";
		}
	}

	public String findByDateCli() throws UnsupportedEncodingException {
		List<RequisicaoEstoque> requisicoes = new ArrayList<RequisicaoEstoque>();
		if (tipoMaterial.equals("MC")) {
			if (this.cliente.getId().getCodFil() != null) {
				requisicoes = requisicaoEstoqueDAO.listPendentes(TipoProduto.MC, cliente);
			} else {
				requisicoes = requisicaoEstoqueDAO.listPendentes(TipoProduto.MC,
						this.requisicaoEstoque.getCliente());
			}
		} else if ((tipoMaterial.equals("ME")) || (tipoMaterial.equals("MI"))) {
			requisicoes = requisicaoEstoqueDAO.listPendentes(TipoProduto.ME);
		}
		if (requisicoes.size() > 0) {
			this.total = requisicoes.size();
			this.resultado = true;
			this.tipo = RequisicaoEstoque.class;
			setJSONModel(requisicoes);
			return "jsonResult";
		} else {
			inputStream = new ByteArrayInputStream(
					("{map:{items:[], success:true, totalCount : 0, msg: ''}}").getBytes("UTF-8"));
			return "txtRes";
		}
	}

	public String geraXMLItem(JSONObject object) {
		String xml = "";
		requisicaoEstoque = new JSONDeserializer<RequisicaoEstoque>()
				.use(null, RequisicaoEstoque.class)
				.use(Date.class, new DateTransformer("dd/MM/yyyy HH:mm:ss"))
				.deserialize(object.toString());
		String contaFinanceira = "";
		if (tipoMaterial.equals("LI")) {
			contaFinanceira = "7130";
		} else {
			if ((tipoMaterial.equals("MI")) || (tipoMaterial.equals("ME"))) {
				contaFinanceira = "5158";
			}
		}
		if (requisicaoEstoque.getQtdApr() > 0) {
			xml = "<sid acao='SID.Est.Requisitar' retorno='Retorno'>";
			if (sequencia > 1)
				xml += "<param nome='NUMEME' valor='@RET_NUMEME'/>";
			xml += "<param nome='SEQEME' valor='" + sequencia + "'/>";
			xml += "<param nome='CODPRO' valor='" + requisicaoEstoque.getProduto().getId().getCodPro()
					+ "'/>";
			xml += "<param nome='QTDEME' valor='" + requisicaoEstoque.getQtdApr() + "'/>";
			xml += "<param nome='QTDAPR' valor='" + requisicaoEstoque.getQtdApr() + "'/>";
			xml += "<param nome='CCURES' valor='" + requisicaoEstoque.getCentro().getId() + "'/>";
			xml += "<param nome='OBSEME' valor='" + requisicaoEstoque.getObsEme() + "'/>";
			xml += "<param nome='SITEME' valor='" + Situacao.Aprovado.getId() + "'/>";
			xml += "<param nome='CTAFIN' valor='" + contaFinanceira + "'/>";// conta financeira
			if (tipoMaterial.equals("ME"))
				xml += "<param nome='CODDEP' valor='103'/>";
			else
				xml += "<param nome='CODDEP' valor='101'/>";
			xml += "</sid>";
			if (sequencia == 1) {
				xml += "<sid acao='SID.SRV.REGRA' retorno='RET_NUMEME'>";
				xml += "<param nome='NUMREG' valor='117' /> ";
				xml += "<param nome='vAlfaNumEme' valor='@Retorno' />";
				xml += "</sid>";
			}
			sequencia++;
		}
		return xml;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public String getCmpReq() {
		return cmpReq;
	}

	public int getCodEmp() {
		return codEmp;
	}

	public String getDir() {
		return dir;
	}

	public String getFilter() {
		return filter;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public String getItems() {
		return items;
	}

	public Object getJSONModel() {
		return JSONModel;
	}

	public String getLimit() {
		return limit;
	}

	public String getMensagem() {
		return mensagem;
	}

	public RequisicaoEstoque getRequisicaoEstoque() {
		return requisicaoEstoque;
	}

	public List<RequisicaoEstoque> getRequisicoes() {
		return requisicoes;
	}

	public String getSetor() {
		return setor;
	}

	public String getSetores() {
		setXmlModel(Setor.values());
		this.totalCount = String.valueOf(Setor.values().length);
		return "resultXML";
	}

	public String getSort() {
		return sort;
	}

	public String getStart() {
		return start;
	}

	public Class getTipo() {
		return tipo;
	}

	public String getTipoMaterial() {
		return tipoMaterial;
	}

	public int getTotal() {
		return total;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public Object getXmlModel() {
		return xmlModel;
	}

	private void gravaItem(JSONObject object) throws Exception {
		Usuario usuarioSessao = (Usuario) util.recuperaSessao("Usuario");
		requisicaoEstoque = new JSONDeserializer<RequisicaoEstoque>()
				.use(null, RequisicaoEstoque.class)
				.use(Date.class, new DateTransformer("dd/MM/yyyy HH:mm:ss"))
				.deserialize(object.toString());
		requisicaoEstoque.setVlrUni(requisicaoEstoque.getProduto().getPreUni());
		this.centro = requisicaoEstoque.getCentro();
		if (tipoMaterial.equals("MC"))
			this.setCliente(requisicaoEstoque.getCliente());
		requisicaoEstoque.setUsuSol(usuarioSessao);
		int idReq = 0;
		try {
			if (requisicaoEstoque.getId() == 0) {
				Date dataAtual = new Date();
				requisicaoEstoque.setDatReq(dataAtual);
				requisicaoEstoque.setSitEme(Situacao.Digitado);
				idReq = (Integer) requisicaoEstoqueDAO.insert(requisicaoEstoque);
				cmpReq = requisicaoEstoque.getCmpReq();
				this.requisicoes.add(requisicaoEstoqueDAO.loadById(idReq));
			} else {
				requisicaoEstoqueDAO.update(requisicaoEstoque);
			}
			this.mensagem = "Requisição gravada com sucesso";
			this.resultado = true;
		} catch (Exception e) {
			logger.error("Erro gravando requisição. Causa: " + e.getMessage());
			this.mensagem = "Requisição não pode ser gravada.";
			this.resultado = false;
			throw e;
		}
	}

	public String gravar() throws Exception {
		String retorno = "";
		try {
			HttpServletRequest req = ServletActionContext.getRequest();
			this.items = (String) req.getParameter("map.items");
			if (String.valueOf(this.items.charAt(0)).equals("[")) {
				JSONArray jsonArray = JSONArray.fromObject(this.items);
				for (Iterator iterator = jsonArray.iterator(); iterator.hasNext();) {
					JSONObject object = (JSONObject) iterator.next();
					gravaItem(object);
				}
			} else {
				JSONObject object = (JSONObject) JSONObject.fromObject(this.items);
				gravaItem(object);
			}
			if (tipoMaterial.equals("MC")) {
				retorno = findByDateCli();
			} else if (tipoMaterial.equals("ME")) {
				this.total = this.requisicoes.size();
				if (this.requisicoes.size() > 1) {
					return listDigitados();
				} else {
					setJSONModel(this.requisicoes);
					this.tipo = RequisicaoEstoque.class;
					retorno = "jsonResult";
				}
			} else if (tipoMaterial.equals("MI")) {
				retorno = listReqMatInsByCmp();
			} else if (tipoMaterial.equals("LI")) {
				retorno = listReqMatLimByCmp();
			} else {
				this.resultado = false;
				this.tipo = RequisicaoEstoque.class;
				this.mensagem = "Não foi possível retornar os itens gravados. Informe administração do sistema.";
				setJSONModel(this.requisicoes);
				return "jsonResult";
			}
		} catch (Exception e) {
			this.resultado = false;
			this.tipo = RequisicaoEstoque.class;
			this.mensagem = "Não foi possível gravar os itens. Informe administração do sistema: "
					+ e.getMessage();
			setJSONModel(this.requisicoes);
			return "jsonResult";
		}

		return retorno;
	}

	public void imprimirRequisicao() throws JRException, IOException {
		Usuario usuarioSessao = (Usuario) util.recuperaSessao("Usuario");
		Map parametros = new HashMap();
		List<RequisicaoEstoque> requisicoesImprimir = new ArrayList<RequisicaoEstoque>();
		if (tipoMaterial.equals("MI")) {
			parametros.put("Titulo",
					"Requisição de Material de Instalação/Ferramentas - Competência : " + cmpReq);
			requisicoesImprimir = requisicaoEstoqueDAO.listReqMatInsByCmp(usuarioSessao, cmpReq);
		} else if (tipoMaterial.equals("ME")) {
			parametros.put("Titulo", "Requisição de Material de Expediente - Competência : " + cmpReq);
			if (setor == null)
				requisicoesImprimir = requisicaoEstoqueDAO.listReqExpByCmp(usuarioSessao, cmpReq);
			else
				requisicoesImprimir = requisicaoEstoqueDAO.listReqExpByCmp(usuarioSessao, cmpReq,
						Setor.valueOf(setor));

		} else if (tipoMaterial.equals("LI")) {
			parametros.put("Titulo", "Requisição de Material de Limpeza - Competência : " + cmpReq);
			if (setor == null)
				requisicoesImprimir = requisicaoEstoqueDAO.listReqMatLimByCmp(usuarioSessao, cmpReq);
			else
				requisicoesImprimir = requisicaoEstoqueDAO.listReqMatLimByCmp(usuarioSessao, cmpReq,
						Setor.valueOf(setor));

		}
		parametros.put("UsuSessao", usuarioSessao.getCodUsu());
		String cmpFmt = cmpReq.replace("/", "-");
		util.geraRelatorio(requisicoesImprimir, "RequisicoesPeriodo.jasper",
				"Requisicao_" + tipoMaterial + "_" + usuarioSessao.getCodUsu() + "_" + cmpFmt + "_"
						+ new Date().getTime() + ".pdf", parametros, "pdf");
	}

	public boolean isListAprovados() {
		return listAprovados;
	}

	public boolean isResultado() {
		return resultado;
	}

	public void listaAcompanhamentoMensal() throws Exception {
		String path = servletRequest.getSession().getServletContext()
				.getRealPath("/paginas/reports/");
		SessionFactory sf = (SessionFactory) ServiceFactory.getBean("sessionFactory");
		Map parametros = new HashMap();
		parametros.put("SUBREPORT_DIR", path + "//");
		parametros.put("HIBERNATE_SESSION", sf.getCurrentSession());
		parametros.put("cmpReq", this.cmpReq);
		util.geraRelatorio("AcompanhamentoRequisicaoMensal.jasper",
				"Acompanhamento" + new Date().getTime() + ".pdf", parametros, "pdf");
	}

	public void listaDistribuicaoMensal() throws JRException, IOException {
		Map parametros = new HashMap();
		SessionFactory sf = (SessionFactory) ServiceFactory.getBean("sessionFactory");
		parametros.put("HIBERNATE_SESSION", sf.getCurrentSession());
		parametros.put("cmpReq", this.cmpReq);
		parametros.put("tipoMaterial", this.tipoMaterial);
		util.geraRelatorio("DistribuicaoMaterial.jasper", "Distribuicao" + new Date().getTime()
				+ ".pdf", parametros, "pdf");
	}

	public String listAll() throws Exception {
		List<RequisicaoEstoque> requisicoes = requisicaoEstoqueDAO.listPendentes(TipoProduto
				.valueOf(tipoMaterial));

		this.tipo = RequisicaoEstoque.class;
		this.resultado = true;
		this.total = requisicoes.size();
		setJSONModel(requisicoes);
		return "jsonResult";
	}

	public String listAprovados() {
		List<RequisicaoEstoque> requisicoes = requisicaoEstoqueDAO.listPendentes(
				TipoProduto.valueOf(tipoMaterial), Situacao.Aprovado);

		this.tipo = RequisicaoEstoque.class;
		this.resultado = true;
		this.total = requisicoes.size();
		setJSONModel(requisicoes);
		return "jsonResult";
	}

	public String listByExample() throws Exception {
		this.requisicoes = requisicaoEstoqueDAO.listByExample(this.requisicaoEstoque,
				TipoProduto.valueOf(tipoMaterial), (Usuario) util.recuperaSessao("Usuario"));
		if (this.requisicoes.size() > 0) {
			this.total = this.requisicoes.size();
			this.resultado = true;
			this.tipo = RequisicaoEstoque.class;
			setJSONModel(this.requisicoes);
			return "jsonResult";
		} else {
			inputStream = new ByteArrayInputStream(
					("{map:{items:[], success:true, totalCount : 0, msg: 'Nenhuma requisicao encontrada com esses filtros.'}}")
							.getBytes("UTF-8"));
			return "txtRes";
		}
	}

	public String listDigitados() throws UnsupportedEncodingException {
		Usuario usuarioSessao = (Usuario) util.recuperaSessao("Usuario");
		List<Filter> filtros = new ArrayList<Filter>();
		if (filter != null) {
			Filter filtro = new Filter();
			JSONArray jsonArray = JSONArray.fromObject(filter);
			for (Iterator iterator = jsonArray.iterator(); iterator.hasNext();) {
				JSONObject object = (JSONObject) iterator.next();
				filtro = new JSONDeserializer<Filter>().use(null, Filter.class).deserialize(
						object.toString());
				filtros.add(filtro);
			}
		}
		List<Situacao> situacoes = new ArrayList<Situacao>();
		situacoes.add(Situacao.Digitado);
		if (isListAprovados() == true)
			situacoes.add(Situacao.Aprovado);
		List<RequisicaoEstoque> requisicoes = requisicaoEstoqueDAO.listPendentes(
				TipoProduto.valueOf(tipoMaterial), situacoes, usuarioSessao, sort, dir, filtros);
		List<RequisicaoEstoque> subList = new ArrayList<RequisicaoEstoque>();
		if ((start != null) && (limit != null)) {
			if (!(start.equals("")) && !(limit.equals("")))
				subList.addAll(requisicoes.subList(Integer.valueOf(start),
						util.calculaUltimoIndice(requisicoes, start, limit)));
			else
				subList.addAll(requisicoes.subList(Integer.valueOf(start),
						util.calculaUltimoIndice(requisicoes, "0", "25")));
		} else {
			subList.addAll(requisicoes);
		}
		if (requisicoes.size() > 0) {
			this.tipo = RequisicaoEstoque.class;
			this.resultado = true;
			this.total = requisicoes.size();
			setJSONModel(subList);
			return "jsonResult";
		} else {
			inputStream = new ByteArrayInputStream(
					("{map:{items:[], success:true, totalCount : 0, msg: ''}}").getBytes("UTF-8"));
			return "txtRes";
		}
	}

	/**
	 * retorna as requisições de material de expediente da competência informada, do centro de custo do usuário
	 * 
	 * @throws UnsupportedEncodingException
	 **/
	public String listReqExpByCmp() throws UnsupportedEncodingException {
		List<RequisicaoEstoque> requisicoes = new ArrayList<RequisicaoEstoque>();
		Usuario usuarioSessao = (Usuario) util.recuperaSessao("Usuario");
		try {
			requisicoes = requisicaoEstoqueDAO.listReqExpByCmp(usuarioSessao, cmpReq,
					Setor.valueOf(setor), centro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (requisicoes.size() > 0) {
			this.total = requisicoes.size();
			this.resultado = true;
			this.tipo = RequisicaoEstoque.class;
			setJSONModel(requisicoes);
			return "jsonResult";
		} else {
			inputStream = new ByteArrayInputStream(
					("{map:{items:[], success:true, totalCount : 0, msg: ''}}").getBytes("UTF-8"));
			return "txtRes";
		}
	}

	/**
	 * retorna as requisições de material de instalação e ferramentas na competência informada, do centro de custo do usuário
	 * 
	 * @throws UnsupportedEncodingException
	 **/
	public String listReqMatInsByCmp() throws UnsupportedEncodingException {
		List<RequisicaoEstoque> requisicoes = new ArrayList<RequisicaoEstoque>();
		Usuario usuarioSessao = (Usuario) util.recuperaSessao("Usuario");
		try {
			requisicoes = requisicaoEstoqueDAO.listReqMatInsByCmp(usuarioSessao, cmpReq,
					Setor.valueOf(setor), centro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (requisicoes.size() > 0) {
			this.total = requisicoes.size();
			this.resultado = true;
			this.tipo = RequisicaoEstoque.class;
			setJSONModel(requisicoes);
			return "jsonResult";
		} else {
			inputStream = new ByteArrayInputStream(
					("{map:{items:[], success:true, totalCount : 0, msg: ''}}").getBytes("UTF-8"));
			return "txtRes";
		}
	}

	public String listReqMatLimByCmp() throws UnsupportedEncodingException {
		List<RequisicaoEstoque> requisicoes = new ArrayList<RequisicaoEstoque>();
		Usuario usuarioSessao = (Usuario) util.recuperaSessao("Usuario");
		try {
			requisicoes = requisicaoEstoqueDAO.listReqMatLimByCmp(usuarioSessao, cmpReq,
					Setor.valueOf(setor), centro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (requisicoes.size() > 0) {
			this.total = requisicoes.size();
			this.resultado = true;
			this.tipo = RequisicaoEstoque.class;
			setJSONModel(requisicoes);
			return "jsonResult";
		} else {
			inputStream = new ByteArrayInputStream(
					("{map:{items:[], success:true, totalCount : 0, msg: ''}}").getBytes("UTF-8"));
			return "txtRes";
		}
	}

	public String processar() throws UnsupportedEncodingException {
		String path = servletRequest.getSession().getServletContext()
				.getRealPath("/WEB-INF/classes/conf.properties");
		PropertiesLoader propertiesLoader = new PropertiesLoader(path);
		String valProp = propertiesLoader.getValor("servidor_sapienssid");
		String empBaiMatExp = propertiesLoader.getValor("empresa_baixa_material_expediente");//retorna do arquivo de configurações do sistema a empresa para baixar o material de expediente

		String codigoConexao = util.loginSapiensSID(valProp);
		String resultadoConexao = codigoConexao;
		if ((resultadoConexao.contains("ERRO")) || (resultadoConexao.contains("Aviso"))) {
			if (resultadoConexao.contains("Aviso")) {
				this.resultado = false;
				this.tipo = RequisicaoEstoque.class;
				this.mensagem = resultadoConexao;
				inputStream = new ByteArrayInputStream(("{map:{items:[], success:" + this.resultado
						+ ", totalCount : 0, msg: '" + this.mensagem + "'}}").getBytes("UTF-8"));
				return "txtRes";
			} else {
				String mensagem = codigoConexao.substring(51, 107);
				this.resultado = false;
				this.tipo = RequisicaoEstoque.class;
				this.mensagem = mensagem;
				inputStream = new ByteArrayInputStream(("{map:{items:[], success:" + this.resultado
						+ ", totalCount : 0, msg: '" + this.mensagem + "'}}").getBytes("UTF-8"));
				return "txtRes";
			}

		} else {
			String xml = "<sidxml>";
			xml += "<param retorno='XML'/>";
			// alterar empresa ativa, dependendo do tipo de requisição
			if (tipoMaterial.equals("LI")) {// limpeza e material de expediente - alterado ME dia 02/09 para requisitar
														// pela onserv
				xml += "<sid acao='SID.Srv.AltEmpFil' retorno='retAltEmp'>";
				xml += "<param nome='CODEMP' valor='6'/>";
				xml += "<param nome='CODFIL' valor='1'/>";
				xml += "</sid>";
			} else // material de instalação
			if (tipoMaterial.equals("MI")) {
				xml += "<sid acao='SID.Srv.AltEmpFil' retorno='retAltEmp'>";
				xml += "<param nome='CODEMP' valor='2'/>";
				xml += "<param nome='CODFIL' valor='1'/>";
				xml += "</sid>";
			} else if (tipoMaterial.equals("ME")) {
				xml += "<sid acao='SID.Srv.AltEmpFil' retorno='retAltEmp'>";
				xml += "<param nome='CODEMP' valor='" + empBaiMatExp + "'/>";
				xml += "<param nome='CODFIL' valor='1'/>";
				xml += "</sid>";
			}
			HttpServletRequest req = ServletActionContext.getRequest();
			this.items = (String) req.getParameter("map.items");
			if (String.valueOf(this.items.charAt(0)).equals("[")) {
				JSONArray jsonArray = JSONArray.fromObject(this.items);
				sequencia = 1;
				for (Iterator iterator = jsonArray.iterator(); iterator.hasNext();) {
					JSONObject object = (JSONObject) iterator.next();
					xml += geraXMLItem(object);
				}
				xml += "</sidxml>";
			} else {
				JSONObject object = (JSONObject) JSONObject.fromObject(this.items);
				xml += geraXMLItem(object);
				xml += "</sidxml>";
			}
			xml = URLEncoder.encode(xml, "UTF-8");
			String urlSID = "http://" + valProp
					+ ":8080/sapiensweb/conector?SIS=CO&LOGIN=SID&USER=agendador&CONNECTION="

					+ codigoConexao + "&ACAO=SID.SRV.XML" + "&POSTQUERY=" + xml;
			String retorno = util.acaoSID(urlSID);

			XStream xStream = new XStream(new DomDriver());
			xStream.registerConverter(new RespostaSIDConverter());
			xStream.alias("sidxml", Map.class);
			Map<String, String> resultados = (Map<String, String>) xStream.fromXML(retorno);

			boolean resultSinc = false;
			if (resultados.get("resultado").equals("OK")) {
				if (String.valueOf(this.items.charAt(0)).equals("[")) {
					JSONArray jsonArray = JSONArray.fromObject(this.items);
					int sequencia = 1;
					for (Iterator iterator = jsonArray.iterator(); iterator.hasNext();) {
						JSONObject object = (JSONObject) iterator.next();
						try {
							resultSinc = sincronizaItem(object, sequencia, resultados.get("2"));
						} catch (Exception e) {
							// inseriu, mas não conseguiu atualizar web
							this.resultado = false;
							this.tipo = RequisicaoEstoque.class;
							this.mensagem = "Requisição processada, mas não atualizada na web. Não processar novamente. Informe administração do sistema.";
							inputStream = new ByteArrayInputStream(
									("{map:{items:[], success:" + this.resultado
											+ ", totalCount : 0, msg: '" + this.mensagem + "'}}")
											.getBytes("UTF-8"));
							return "txtRes";
						}
					}
				} else {
					JSONObject object = (JSONObject) JSONObject.fromObject(this.items);
					try {
						resultSinc = sincronizaItem(object, 1, resultados.get("2"));
					} catch (Exception e) {
						// inseriu, mas não conseguiu atualizar web
						this.resultado = false;
						this.tipo = RequisicaoEstoque.class;
						this.mensagem = "Requisição processada, mas não atualizada na web. Não processar novamente. Informe administração do sistema.";
						inputStream = new ByteArrayInputStream(
								("{map:{items:[], success:" + this.resultado + ", totalCount : 0, msg: '"
										+ this.mensagem + "'}}").getBytes("UTF-8"));
						return "txtRes";
					}
				}
			}
			if (resultSinc == true) {
				this.tipoMaterial = "ME";
				this.resultado = true;
				this.tipo = RequisicaoEstoque.class;
				this.mensagem = "Requisição(ões) processada com sucesso.";
				inputStream = new ByteArrayInputStream(("{map:{items:[], success:" + this.resultado
						+ ", totalCount : 0, msg: '" + this.mensagem + "'}}").getBytes("UTF-8"));
				return "txtRes";
			} else {
				this.resultado = false;
				this.tipo = RequisicaoEstoque.class;
				this.mensagem = "Não foi possível processar os itens: "
						+ resultados.get("1").replaceAll("'", "");
				inputStream = new ByteArrayInputStream(("{map:{items:[], success:" + this.resultado
						+ ", totalCount : 0, msg: '" + this.mensagem + "'}}").getBytes("UTF-8"));
				return "txtRes";
			}
		}
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setCmpReq(String cmpReq) {
		this.cmpReq = cmpReq;
	}

	public void setCodEmp(int codEmp) {
		this.codEmp = codEmp;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public void setJSONModel(Object model) {
		JSONModel = model;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public void setListAprovados(boolean listAprovados) {
		this.listAprovados = listAprovados;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public void setRequisicaoEstoque(RequisicaoEstoque requisicaoEstoque) {
		this.requisicaoEstoque = requisicaoEstoque;
	}

	public void setRequisicoes(List<RequisicaoEstoque> requisicoes) {
		this.requisicoes = requisicoes;
	}

	public void setResultado(boolean resultado) {
		this.resultado = resultado;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public void setTipo(Class tipo) {
		this.tipo = tipo;
	}

	public void setTipoMaterial(String tipoMaterial) {
		this.tipoMaterial = tipoMaterial;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public void setXmlModel(Object xmlModel) {
		this.xmlModel = xmlModel;
	}

	public Centro getCentro() {
		return centro;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
	}

	private boolean sincronizaItem(JSONObject object, int sequencia, String numEme) throws Exception {
		Usuario usuarioSessao = (Usuario) util.recuperaSessao("Usuario");
		Date dataAtual = new Date();
		int posBarra = numEme.indexOf("/");
		String numeroReq = numEme.substring(0, posBarra);
		requisicaoEstoque = new JSONDeserializer<RequisicaoEstoque>()
				.use(null, RequisicaoEstoque.class)
				.use(Date.class, new DateTransformer("dd/MM/yyyy HH:mm:ss"))
				.deserialize(object.toString());
		requisicaoEstoque.setSitEme(Situacao.Compras);
		requisicaoEstoque.setUsuPrc(usuarioSessao);
		requisicaoEstoque.setSeqEme(sequencia);
		requisicaoEstoque.setNumEme(Integer.valueOf(numeroReq));
		requisicaoEstoque.setDatPrc(dataAtual);
		try {
			requisicaoEstoqueDAO.update(requisicaoEstoque);
			return true;
		} catch (Exception e) {
			logger.error("Erro sincronizando requisição. Causa: " + e.getMessage());
			return false;
		}
	}

	public RequisicaoEstoqueDAO getRequisicaoEstoqueDAO() {
		return requisicaoEstoqueDAO;
	}

	public void setRequisicaoEstoqueDAO(RequisicaoEstoqueDAO requisicaoEstoqueDAO) {
		this.requisicaoEstoqueDAO = requisicaoEstoqueDAO;
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