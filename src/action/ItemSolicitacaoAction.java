package action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.StreamResult;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.BasicResult;
import result.XMLResult;
import util.Util;
import bean.Empresa;
import bean.ItemSolicitacao;
import bean.ItemSolicitacao.Situacao;
import bean.Solicitacao;
import bean.Uniforme;
import bean.Usuario;
import bean.Usuario.TipoUsuario;

import com.opensymphony.xwork2.ActionSupport;

import dao.ItemSolicitacaoDAO;
import dao.SolicitacaoDAO;
import dao.UniformeDAO;

@Results({
		@Result(name = "resultBasic", value = "xmlModel", type = BasicResult.class),
		@Result(name = "resultXML", value = "xmlModel", type = XMLResult.class),
		@Result(name = "sucTxt", value = "inputStream", type = StreamResult.class, params = {
				"inputName", "inputStream" }) })
public class ItemSolicitacaoAction extends ActionSupport {
	private static final long		serialVersionUID	= 1L;
	static Logger						logger				= Logger.getLogger(ItemSolicitacaoAction.class);
	private ItemSolicitacao			itemSolicitacao;
	private Solicitacao				solicitacao			= new Solicitacao();
	private int							idSol;
	private int							idUni;
	private int							idCol;
	private Date						dataEnvio;
	private Object						xmlModel;
	private InputStream				inputStream;
	private String						start					= "";
	private String						limit					= "";
	private String						totalCount;
	private String						sort;
	private String						dir;
	private boolean					mostraNegados;
	private String						motLib;
	private boolean					cobCol;
	private List<ItemSolicitacao>	itens					= new ArrayList<ItemSolicitacao>();
	private Class						tipo;
	@Autowired
	@Qualifier("HItemSolicitacaoDAO")
	private ItemSolicitacaoDAO		itemSolicitacaoDAO;
	@Autowired
	@Qualifier("HSolicitacaoDAO")
	private SolicitacaoDAO			solicitacaoDAO;
	@Autowired
	@Qualifier("HUniformeDAO")
	private UniformeDAO				uniformeDAO;
	@Autowired
	@Qualifier("Util")
	private Util util;

	public ItemSolicitacaoDAO getItemSolicitacaoDAO() {
		return itemSolicitacaoDAO;
	}

	public void setItemSolicitacaoDAO(ItemSolicitacaoDAO itemSolicitacaoDAO) {
		this.itemSolicitacaoDAO = itemSolicitacaoDAO;
	}

	public SolicitacaoDAO getSolicitacaoDAO() {
		return solicitacaoDAO;
	}

	public void setSolicitacaoDAO(SolicitacaoDAO solicitacaoDAO) {
		this.solicitacaoDAO = solicitacaoDAO;
	}

	public UniformeDAO getUniformeDAO() {
		return uniformeDAO;
	}

	public void setUniformeDAO(UniformeDAO uniformeDAO) {
		this.uniformeDAO = uniformeDAO;
	}

	public String atendeItem() {
		try {
			ItemSolicitacao item = itemSolicitacaoDAO.loadById(this.idSol, this.idUni);
			long cadastro = item.getSolicitacao().getColaborador().getNumCad();
			if (cadastro > 0)
				item.setSitItm(Situacao.SL);
			else
				item.setSitItm(Situacao.SAA);
			item.setDatAte(new Date());
			itemSolicitacaoDAO.update(item);
			inputStream = new ByteArrayInputStream(("Item atendido com sucesso.").getBytes());
		} catch (Exception e) {
			logger.error("NAO FOI POSSIVEL ALTERAR SITUACAO DO ITEM O ITEM. Detalhes: " + e.getCause());
			inputStream = new ByteArrayInputStream(
					("Item não pode ser atendido." + e.getMessage()).getBytes());
		}

		return "sucTxt";
	}

	@SkipValidation
	public String autorizaItem() throws Exception {
		try {
			ItemSolicitacao item = itemSolicitacaoDAO.loadById(this.idSol, this.idUni);
			int idEmpresa = 0;
			/** Empresa 5 e 23 devem utilizar o estoque de uniformes da empresa 23, pois não há movimentação de estoque na 5 **/
			if (item.getSolicitacao().getColaborador().getEmpresa().getId() == 5) {
				idEmpresa = 23;
			} else {
				idEmpresa = item.getSolicitacao().getColaborador().getEmpresa().getId();
			}
			Empresa empresa = new Empresa(idEmpresa);
			if (util.existeEstoque(item.getUniforme(), empresa, item)) {
				item.setSitItm(Situacao.LB);
				item.setDatLib(new Date());
				item.setEntAut("S");
				inputStream = new ByteArrayInputStream(("Item autorizado com sucesso").getBytes());
			} else {
				item.setSitItm(Situacao.NA);
				item.setDatLib(new Date());
				item.setEntAut("S");
				inputStream = new ByteArrayInputStream(
						("Item autorizado mas não atendido por falta de estoque.").getBytes());
			}
			item.setCobCol(cobCol);
			item.setUsuarioLiberacao((Usuario) util.recuperaSessao("Usuario"));
			itemSolicitacaoDAO.update(item);
			return "sucTxt";
		} catch (Exception e) {
			logger.error("Não foi possível gravar processamento. Detalhes: " + e.getCause());
			throw new Exception("Item não foi autorizado: " + e.getCause());
		}
	}

	public String estornarItem() {
		try {
			ItemSolicitacao item = itemSolicitacaoDAO.loadById(this.idSol, this.idUni);
			item.setSitItm(Situacao.SL);
			item.getSolicitacao().setSituacao(bean.Solicitacao.Situacao.AB);
			item.setDatEnv(null);
			item.setDatPro(null);
			itemSolicitacaoDAO.update(item);
			inputStream = new ByteArrayInputStream(("Item estornado com sucesso.").getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			inputStream = new ByteArrayInputStream(("Item nao foi estornado.").getBytes());
		}
		return "sucTxt";
	}

	@SkipValidation
	public String excluiItem() throws Exception {
		ItemSolicitacao item = itemSolicitacaoDAO.loadById(this.idSol, this.idUni);
		Usuario usuario = (Usuario) util.recuperaSessao("usuario");
		try {
			if ((item.getSitItm().equals(Situacao.EN))
					&& ((!usuario.getTipUsu().equals(TipoUsuario.AAL)) || (!usuario.getTipUsu().equals(
							TipoUsuario.ASI)))) {
				inputStream = new ByteArrayInputStream(
						("Item já enviado. Não pode ser excluído.").getBytes());
			} else {
				itemSolicitacaoDAO.delete(item);
				if (solicitacao.getItensSolicitacao().size() == 0) {
					solicitacaoDAO.delete(solicitacao);
				} else {
					util.verificaSituacaoSolicitacao(solicitacao);
				}
				inputStream = new ByteArrayInputStream(("ITEM EXCLUIDO COM SUCESSO.").getBytes());
			}

		} catch (Exception e) {
			logger.error("NAO FOI POSSIVEL EXCLUIR O ITEM. Detalhes: " + e.getCause());
			inputStream = new ByteArrayInputStream(("NAO FOI POSSIVEL EXCLUIR O ITEM.").getBytes());
		}
		return "sucTxt";
	}

	public Date getDataEnvio() {
		return dataEnvio;
	}

	public String getDir() {
		return dir;
	}

	public int getIdCol() {
		return idCol;
	}

	public int getIdSol() {
		return idSol;
	}

	public int getIdUni() {
		return idUni;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public ItemSolicitacao getItemSolicitacao() {
		return itemSolicitacao;
	}

	public List<ItemSolicitacao> getItens() {
		return itens;
	}

	public String getLimit() {
		return limit;
	}

	public String getMotLib() {
		return motLib;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
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

	public String getTotalCount() {
		return totalCount;
	}

	public Object getXmlModel() {
		return xmlModel;
	}

	@SkipValidation
	public String gravarProcessoAgrupado() throws Exception {
		try {
			for (ItemSolicitacao item : itens) {
				int idSoli = solicitacao.getId();
				int idUnif = item.getId().getUniforme().getId();
				item = itemSolicitacaoDAO.loadById(idSoli, idUnif);
				item.setSitItm(Situacao.EN);
				item.setDatEnv(this.itemSolicitacao.getDatEnv());
				item.setDatAtu(Calendar.getInstance().getTime());
				item.setDatPro(new Date());
				itemSolicitacaoDAO.updateWithFlush(item);
				util.verificaSituacaoSolicitacao(solicitacaoDAO.loadById(idSoli));
			}
			inputStream = new ByteArrayInputStream(
					("{success:true, msg: 'Itens processados com sucesso.'}").getBytes());
		} catch (Exception e) {
			String msg = "Itens não foram processados. Erro: " + e.getMessage();
			inputStream = new ByteArrayInputStream(("{success:false, msg: " + msg + "}").getBytes());
			logger.error("Não foi possível gravar processamento. Detalhes: " + e.getCause());
			throw e;
		}
		return "sucTxt";
	}

	public boolean isCobCol() {
		return cobCol;
	}

	public boolean isMostraNegados() {
		return mostraNegados;
	}

	@SkipValidation
	public String liberarItem() throws Exception {
		this.solicitacao = solicitacaoDAO.loadById(this.idSol);
		if (this.solicitacao != null)
			Hibernate.initialize(this.solicitacao.getItensSolicitacao());
		util.registraSessao("solicitacao", this.solicitacao);
		return "liberarItem";
	}

	@SkipValidation
	public String libXML() throws Exception {
		this.solicitacao = solicitacaoDAO.loadById(this.idSol);
		this.tipo = ItemSolicitacao.class;
		setXmlModel(solicitacao.getItensSolicitacao());
		return "resultBasic";
	}

	@SkipValidation
	public String listSolicitacoesLiberar() throws Exception {
		Usuario usuarioLogado = (Usuario) util.recuperaSessao("Usuario");
		List<Situacao> situacoes = new ArrayList<Situacao>();
		situacoes.add(Situacao.SP);
		if (mostraNegados == true)
			situacoes.add(Situacao.NE);
		List<Solicitacao> liberacoesPro = solicitacaoDAO.listByPrevDate(situacoes, 1,
				Integer.valueOf(this.start), Integer.valueOf(this.limit), sort.replace("_", "."), dir,
				usuarioLogado);
		List<Order> ordens = new ArrayList<Order>();
		this.totalCount = String
				.valueOf((solicitacaoDAO.listByPrevDate(situacoes, 1, ordens)).size());
		setXmlModel(liberacoesPro);
		this.tipo = Solicitacao.class;
		return "resultBasic";
	}

	@SkipValidation
	public String negaItem() throws Exception {
		try {
			ItemSolicitacao item = itemSolicitacaoDAO.loadById(this.idSol, this.idUni);
			item.setSitItm(Situacao.NE);
			item.setDatNeg(new Date());
			item.setEntAut("N");
			item.setMotLib(this.motLib);
			item.setUsuarioLiberacao((Usuario) util.recuperaSessao("Usuario"));
			itemSolicitacaoDAO.update(item);
			List<Usuario> usuarios = new ArrayList<Usuario>();
			usuarios.add(item.getSolicitacao().getSolicitante());
			usuarios.add(item.getSolicitacao().getSolicitante().getGerente());
			String texto = "Prezado gestor "
					+ item.getSolicitacao().getSolicitante().getGerente().getNomFun() + " e usuário "
					+ item.getSolicitacao().getSolicitante().getNomFun() + ", sua solicitação de "
					+ item.getQtdEnt() + " unidade(s) do item " + item.getUniforme().getDesEpi()
					+ " para o colaborador " + item.getSolicitacao().getColaborador().getNomFun()
					+ " foi negada.\n";
			texto += "Observe abaixo o motivo:\n";
			texto += motLib + "\n\n";
			texto += item.getUsuarioLiberacao().getNomFun();
			util.mandaEmail("200.215.53.150", item.getUsuarioLiberacao().getEmail(), usuarios,
					"Solicitação negada", texto);
			inputStream = new ByteArrayInputStream(("Item negado com sucesso").getBytes());
			return "sucTxt";
		} catch (Exception e) {
			logger.error("Não foi possível gravar processamento. Detalhes: " + e.getCause());
			throw new Exception("Item não foi negado: " + e.getCause());
		}
	}

	@SkipValidation
	public String processar() {
		this.itemSolicitacao = itemSolicitacaoDAO.loadById(this.idSol, this.idUni);
		return "processarItem";
	}

	@SkipValidation
	public String processarItem() throws Exception {
		this.solicitacao = solicitacaoDAO.loadById(this.idSol);
		if (this.solicitacao != null)
			Hibernate.initialize(this.solicitacao.getItensSolicitacao());
		util.registraSessao("solicitacao", this.solicitacao);
		return "processarItem";
	}

	public void setCobCol(boolean cobCol) {
		this.cobCol = cobCol;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public void setIdCol(int idCol) {
		this.idCol = idCol;
	}

	public void setIdSol(int idSol) {
		this.idSol = idSol;
	}

	public void setIdUni(int idUni) {
		this.idUni = idUni;
	}

	public void setItemSolicitacao(ItemSolicitacao itemSolicitacao) {
		this.itemSolicitacao = itemSolicitacao;
	}

	public void setItens(List<ItemSolicitacao> itens) {
		this.itens = itens;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public void setMostraNegados(boolean mostraNegados) {
		this.mostraNegados = mostraNegados;
	}

	public void setMotLib(String motLib) {
		this.motLib = motLib;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
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

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public void setXmlModel(Object xmlModel) {
		this.xmlModel = xmlModel;
	}

	public String verificaValidade() throws Exception {
		Uniforme uniforme = new Uniforme();
		uniforme = uniformeDAO.loadById(idUni);
		Date dataAtual = new Date();
		Date dataTroca = itemSolicitacaoDAO.getDataPermitidaTroca(idCol, uniforme, dataAtual);
		if (dataTroca != null) {
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(dataTroca);
			calendar.add(Calendar.DATE, uniforme.getDiaVal());
			dataTroca = calendar.getTime();
			if (dataTroca.compareTo(dataAtual) == 1) {
				/** data da troca é maior que a data atual **/
				// buscar saldo possível de solicitação item
				Calendar cal02 = Calendar.getInstance();
				cal02.setTime(dataAtual);
				cal02.add(Calendar.DATE, uniforme.getDiaVal() * -1);
				Date dataInicial = cal02.getTime();
				int saldo = itemSolicitacaoDAO.getSaldo(idCol, uniforme, dataAtual, dataInicial);
				inputStream = new ByteArrayInputStream((String.valueOf(saldo)).getBytes());
			} else {
				inputStream = new ByteArrayInputStream(
						(String.valueOf(uniforme.getQtdMax())).getBytes());
			}
		} else {// se devolver null é porque nunca foi solicitado esse uniforme
			// para esse colaborador
			inputStream = new ByteArrayInputStream((String.valueOf(uniforme.getQtdMax())).getBytes());
		}
		return "sucTxt";
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

}
