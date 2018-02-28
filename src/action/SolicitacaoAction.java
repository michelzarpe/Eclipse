package action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.StreamResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.BasicResult;
import util.Acao;
import util.ConnectSqlServerSapiens;
import util.Util;
import util.WebOperConstantes;
import bean.Cliente;
import bean.Colaborador;
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
		@Result(name = "txtRes", type = StreamResult.class, value = "inputStream", params = {
				"contentType", "text/plain", "inputName", "inputStream" }),
		@Result(name = "resultXML", value = "xmlModel", type = BasicResult.class) })
public class SolicitacaoAction extends ActionSupport implements ServletRequestAware {
	private static final long			serialVersionUID			= 1L;
	static Logger							logger						= Logger.getLogger(SolicitacaoAction.class);
	static ConnectSqlServerSapiens	connectSqlServerSapiens	= ConnectSqlServerSapiens.getInstance();
	private Colaborador					colaborador					= new Colaborador();									;
	private Solicitacao					solicitacao					= new Solicitacao();
	private ItemSolicitacao				itemSolicitacao;
	private List<Cliente>				filiais						= new ArrayList<Cliente>();
	private List<Colaborador>			colaboradores				= new ArrayList<Colaborador>();
	private List<ItemSolicitacao>		itens							= new ArrayList<ItemSolicitacao>();
	private int								id;
	private int								idSol;
	private int								idUni;
	private boolean						foraPrazo;
	private String							acao;
	private int								motivo;
	private int								supervisor;
	private int								tipUni;
	private InputStream					inputStream;
	private String							start							= "";
	private String							limit							= "";
	private String							totalCount;
	private String							sort;
	private String							dir;
	private Object							xmlModel;
	private Class							tipo;
	@Autowired
	@Qualifier("HItemSolicitacaoDAO")
	private ItemSolicitacaoDAO			itemSolicitacaoDAO;
	@Autowired
	@Qualifier("HSolicitacaoDAO")
	private SolicitacaoDAO				solicitacaoDAO;
	@Autowired
	@Qualifier("HUniformeDAO")
	private UniformeDAO					uniformeDAO;
	@Autowired
	@Qualifier("Util")
	private Util util;
	private HttpServletRequest servletRequest;

	public Class getTipo() {
		return tipo;
	}

	public void setTipo(Class tipo) {
		this.tipo = tipo;
	}

	public List<ItemSolicitacao> getItens() {
		return itens;
	}

	public void setItens(List<ItemSolicitacao> itens) {
		this.itens = itens;
	}

	public Object getXmlModel() {
		return xmlModel;
	}

	public void setXmlModel(Object xmlModel) {
		this.xmlModel = xmlModel;
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

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
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

	public void setFiliais(List<Cliente> filiais) {
		this.filiais = filiais;
	}

	public void setColaboradores(List<Colaborador> colaboradores) {
		this.colaboradores = colaboradores;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public int getMotivo() {
		return motivo;
	}

	public void setMotivo(int motivo) {
		this.motivo = motivo;
	}

	public int getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(int supervisor) {
		this.supervisor = supervisor;
	}

	public String getAcao() {
		return acao;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public List<Colaborador> getColaboradores() {
		return colaboradores;
	}

	public List<Cliente> getFiliais() {
		return filiais;
	}

	public int getId() {
		return id;
	}

	public int getIdSol() {
		return idSol;
	}

	public int getIdUni() {
		return idUni;
	}

	public ItemSolicitacao getItemSolicitacao() {
		return itemSolicitacao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public void setForaPrazo(boolean foraPrazo) {
		this.foraPrazo = foraPrazo;
	}

	public void setId(int id) {
		this.id = id;
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

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public int getTipUni() {
		return tipUni;
	}

	public void setTipUni(int tipUni) {
		this.tipUni = tipUni;
	}

	// -----------FIM DOS GETTERS E SETTERS -----------------------
	public String editar() {
		this.itemSolicitacao = itemSolicitacaoDAO.loadById(this.idSol, this.idUni);
		this.tipUni = this.itemSolicitacao.getId().getUniforme().getTipoEPI().getId();
		this.solicitacao = this.itemSolicitacao.getId().getSolicitacao();
		this.colaborador = this.solicitacao.getColaborador();
		if (itemSolicitacao.getSitItm().equals(ItemSolicitacao.Situacao.SP)) {
			this.setForaPrazo(true);
		}
		this.acao = Acao.EDITAR.name();
		return "successItem";
	}

	public String editarSolicitacao() throws Exception {
		this.solicitacao = solicitacaoDAO.loadById(idSol);
		util.registraSessao("solicitacao", solicitacao);
		Hibernate.initialize(this.solicitacao.getItensSolicitacao());
		// this.colaborador = this.solicitacao.getColaborador();
		this.acao = Acao.INSERIR.name();
		return "successItem";
	}

	public String excluir() {
		try {
			Solicitacao solicitacao = solicitacaoDAO.loadById(idSol);
			util.removeDaSessao("solicitacao");
			solicitacaoDAO.delete(solicitacao);
			addActionMessage("Solicitação excluída com sucesso.");
		} catch (Exception e) {
			addActionError("Solicitação não pode ser excluído. Causa:" + e.getMessage());
		}
		Usuario usuario = (Usuario) util.recuperaSessao(WebOperConstantes.USUARIO);
		if (usuario.getTipUsu().equals(TipoUsuario.ASI)) {
			return "homeAdm";
		} else {
			return "home";
		}
	}

	public String excluirItem() throws UnsupportedEncodingException {
		try {
			ItemSolicitacao itemSolicitacao = itemSolicitacaoDAO.loadById(idSol, idUni);
			util.removeDaSessao("solicitacao");
			itemSolicitacaoDAO.delete(itemSolicitacao);
			addActionMessage("Item excluï¿½do com sucesso.");
			this.acao = Acao.INSERIR.name();
			solicitacao = solicitacaoDAO.loadById(idSol);
			if (solicitacao != null) {
				Hibernate.initialize(this.solicitacao.getItensSolicitacao());
				util.registraSessao("solicitacao", solicitacao);
				colaborador = solicitacao.getColaborador();
				geraRetornoTxt("Item excluído com sucesso!");
				return "txtRes";
			} else { // quando a solicitaï¿½ï¿½o sï¿½ tem 1 item, e ele ï¿½ excluï¿½do,
				// entï¿½o a solicitaï¿½ï¿½o ï¿½ excluï¿½da tb
				return "inputSol";
			}
		} catch (Exception e) {
			addActionError("Item não pode ser excluído. Causa:" + e.getMessage());
			geraRetornoTxt("Item não pode ser excluído. Causa:" + e.getMessage());
			return "txtRes";
		}
	}

	private void geraRetornoTxt(String texto) throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer(texto);
		ByteArrayInputStream bis = new ByteArrayInputStream(sb.toString().getBytes("UTF-8"));
		inputStream = bis;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public String gravar() {
		try {
			Date dataAtual = Calendar.getInstance().getTime();
			Usuario usuarioSessao = (Usuario) util.recuperaSessao("Usuario");
			solicitacao.setSolicitante(usuarioSessao);
			if (solicitacao.getId() == 0) {// insert solicitaï¿½ï¿½o
				solicitacao.setDatEnt(dataAtual);
				solicitacao.setViaWeb(true);
				solicitacao.setDatInc(dataAtual);
			}
			solicitacao.setSituacao(bean.Solicitacao.Situacao.FE);
			for (ItemSolicitacao item : itens) {// verifica estoque antes de
				// gravar
				if (!item.getSitItm().equals(Situacao.SP)) {
					if ((!util.existeEstoque(item.getId().getUniforme(), item, false, true))
							&& (foraPrazo == false)) {
						item.setSitItm(Situacao.NA);
					}
				}
				/** ======================================================= **/
				// configura a data de vencimento e data do ï¿½ltimo envio
				Uniforme uniforme = new Uniforme();
				uniforme = uniformeDAO.loadById(item.getId().getUniforme().getId());
				int idCol = solicitacao.getColaborador().getId();
				int diasValidade = uniforme.getDiaVal();
				Date dataUltimaEntrega = itemSolicitacaoDAO.getDataPermitidaTroca(idCol, uniforme,
						solicitacao.getDatEnt());
				if (dataUltimaEntrega != null) {
					item.setDatUltEn(dataUltimaEntrega);
					Calendar calendar = GregorianCalendar.getInstance();
					calendar.setTime(dataUltimaEntrega);
					calendar.add(Calendar.DATE, diasValidade);
					Date dataTroca = calendar.getTime();
					if (dataTroca != null)
						item.setDatVen(dataTroca);
				}
				/** ======================================================= **/
				if (!item.getSitItm().equals(Situacao.EN)) {// configura
					// situaï¿½ï¿½o

					solicitacao.setSituacao(bean.Solicitacao.Situacao.AB);
				}

				if (solicitacao.getItensSolicitacao().contains(item)) {
					// item jï¿½ existente - remover ou alterar?
					item.setDatAtu(dataAtual);// atualiza data de alteraï¿½ï¿½o
					item.setUsuAlteracao(usuarioSessao);
					solicitacao.getItensSolicitacao().remove(item);
				} else {
					item.setDatInc(dataAtual); // data de inclusï¿½o do item na
					// solicitaï¿½ï¿½o
				}
				item.getId().setSolicitacao(solicitacao);
				solicitacao.getItensSolicitacao().add(item);
			}
			if (solicitacao.getId() > 0) {
				if (solicitacao.getDatInc() == null) {
					solicitacao.setDatInc(solicitacao.getDatEnt());
				}
				solicitacaoDAO.update(solicitacao);
			} else {
				idSol = (Integer) solicitacaoDAO.insert(solicitacao);
			}
			inputStream = new ByteArrayInputStream((String.valueOf(idSol)).getBytes());

		} catch (Exception e) {
			logger.error("Erro gravando Solicitação. Causa: " + e.getMessage());
			inputStream = new ByteArrayInputStream(
					("NAO FOI POSSIVEL INSERIR SOLICITACAO.").getBytes());
			ServletActionContext.getResponse().setStatus(400);
		}
		return "txtRes";
	}

	public String gravarItem() {
		try {
			Date dataAtual = Calendar.getInstance().getTime();
			solicitacao.setDatEnt(dataAtual);
			solicitacao.setSolicitante((Usuario) util.recuperaSessao("Usuario"));
			solicitacao.setViaWeb(true);
			solicitacao.setDatInc(dataAtual);
			/**
			 * Verifica se o item jï¿½ existe na solicitaï¿½ï¿½o, para atualizar ou inserir
			 */
			if (itemSolicitacao.getId().getSolicitacao() != null
					&& itemSolicitacao.getId().getUniforme() != null) {
				ItemSolicitacao itemAtual = itemSolicitacaoDAO.loadById(itemSolicitacao.getId());
				itemSolicitacao.setSitItm(itemAtual.getSitItm());
				this.itemSolicitacao.setDatAtu(dataAtual);
				// verifica se existe estoque para o item, independente da
				// empresa
				if ((!util.existeEstoque(this.itemSolicitacao.getId().getUniforme(),
						this.itemSolicitacao, true, true)) && (foraPrazo == false)) {
					this.itemSolicitacao.setSitItm(Situacao.NA);
				}
				this.solicitacao.getItensSolicitacao().remove(itemAtual);
				this.solicitacao.getItensSolicitacao().add(this.itemSolicitacao);
			} else {
				/**
				 * inserï¿½ï¿½o - se o colaborador estiver com situaï¿½ï¿½o Aguardando admissï¿½o, entï¿½o, a situaï¿½ï¿½o do item fica com Solicitado, aguardando
				 * admissï¿½o
				 */
				if (this.colaborador.getAfastamento().getId() == 1000) {
					this.itemSolicitacao.setSitItm(Situacao.SAA);
					/**
					 * Quando o colaborador estï¿½ ainda para ser admitido, obrigatoriamente, a data de entrega da solicitaï¿½ï¿½o deve ser a data de admissï¿½o
					 */
					this.solicitacao.setDatEnt(this.colaborador.getDatAdm());
					this.itemSolicitacao.setEntAut("S");
				}
				this.itemSolicitacao.setSolicitacao(solicitacao);
				this.itemSolicitacao.getId().setSolicitacao(this.solicitacao);
				this.itemSolicitacao.getId().setUniforme(this.itemSolicitacao.getUniforme());
				this.itemSolicitacao.setDatAtu(dataAtual);
				this.solicitacao.getItensSolicitacao().add(this.itemSolicitacao);
			}
			// Verifica se a solicitaï¿½ï¿½o jï¿½ existe, para atualizar ou
			// inserir
			if (this.solicitacao.getId() > 0) {
				solicitacaoDAO.updateWithFlush(solicitacao);
				this.solicitacao = solicitacaoDAO.loadById(solicitacao.getId());
			} else {
				// verifica se existe estoque para o item, independente da
				// empresa
				if ((!util.existeEstoque(this.itemSolicitacao.getId().getUniforme(),
						this.itemSolicitacao, false, true)) && (foraPrazo == false)) {
					this.itemSolicitacao.setSitItm(Situacao.NA);
				}
				int idGerado = (Integer) solicitacaoDAO.insert(solicitacao);
				this.solicitacao = solicitacaoDAO.loadById(idGerado);
			}
			/**
			 * Atualiza o objeto solicitaï¿½ï¿½o na sessï¿½o, para poder listar corretamente as alteraï¿½ï¿½es e continuar inserindo itens na mesma solicitaï¿½ï¿½o
			 */
			Hibernate.initialize(this.solicitacao.getItensSolicitacao());
			util.registraSessao("solicitacao", this.solicitacao);
			itemSolicitacao = new ItemSolicitacao();
			addActionMessage("Item inserido/atualizado com sucesso.");
			foraPrazo = false;

			inputStream = new ByteArrayInputStream(
					("{success:true, msg: 'SOLICITACAO GRAVADA COM SUCESSO.'}").getBytes());

		} catch (Exception e) {
			logger.error("Erro gravando Solicitação. Causa: " + e.getMessage());
			inputStream = new ByteArrayInputStream(
					("{success:false, msg: 'NAO FOI POSSIVEL INSERIR SOLICITACAO.'}").getBytes());
		}
		return "sucTxt";
	}

	public void imprimir() throws Exception {
		this.solicitacao = solicitacaoDAO.loadById(idSol);
		util.geraRelatorio(solicitacao.getItensSolicitacao(), "Espelho.jasper", "Espelho.pdf",
				null, "pdf");
	}

	public boolean isForaPrazo() {
		return foraPrazo;
	}

	public void imprimirProtocolo() throws Exception {
		Solicitacao solicitacao = solicitacaoDAO.loadById(this.idSol);
		String path = servletRequest.getSession().getServletContext().getRealPath("/paginas/reports/");
		Map parametros = new HashMap();
		parametros.put("SUBREPORT_DIR", path + "//");
		JRDataSource dsDetail = new JRBeanCollectionDataSource(solicitacao.getItensSolicitacao());
		JRDataSource dsDetail2 = new JRBeanCollectionDataSource(solicitacao.getItensSolicitacao());
		parametros.put("DATA", dsDetail);
		parametros.put("DATA2", dsDetail2);
		util.geraRelatorio(solicitacao.getItensSolicitacao(), "Protocolo3vias.jasper",
				"Protocolo" + solicitacao.getColaborador().getNomFun() + new Date().getTime() + ".pdf",
				parametros, "pdf");
	}

	public void reimprimirProtocolo() throws Exception {
		Solicitacao solicitacao = solicitacaoDAO.loadById(this.idSol);
		String path = servletRequest.getSession().getServletContext().getRealPath("/paginas/reports/");
		Map parametros = new HashMap();
		parametros.put("SUBREPORT_DIR", path + "//");
		JRDataSource dsDetail = new JRBeanCollectionDataSource(solicitacao.getItensSolicitacao());
		JRDataSource dsDetail2 = new JRBeanCollectionDataSource(solicitacao.getItensSolicitacao());
		parametros.put("DATA", dsDetail);
		parametros.put("DATA2", dsDetail2);
		util.geraRelatorio(solicitacao.getItensSolicitacao(), "ProtocoloReimpresso.jasper",
				"Protocolo" + solicitacao.getColaborador().getNomFun() + new Date().getTime() + ".pdf",
				parametros, "pdf");
	}

	public void imprimirEspelho() throws Exception {
		Solicitacao solicitacao = solicitacaoDAO.loadById(this.idSol);
		util.geraRelatorio(solicitacao.getItensSolicitacao(), "Espelho.jasper",
				solicitacao.getId() + ".pdf", null, "pdf");
	}

	public String listPendentesAdmissao() {
		try {
			List<Solicitacao> solicitacoes = solicitacaoDAO.listPendentesAdmissao(0, 999999,
					sort.replace("_", "."), dir);
			List<Solicitacao> subSol = new ArrayList<Solicitacao>();
			subSol.addAll(solicitacoes.subList(Integer.valueOf(this.start),
					util.calculaUltimoIndice(solicitacoes, start, limit)));
			this.totalCount = String.valueOf(solicitacoes.size());
			this.tipo = Solicitacao.class;
			setXmlModel(subSol);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "resultXML";
	}

	public String findByDateCol() {
		try {
			solicitacao.setSituacao(bean.Solicitacao.Situacao.AB);
			List<Solicitacao> solicitacoes = solicitacaoDAO.listByExample(solicitacao);
			this.totalCount = String.valueOf(solicitacoes.size());
			this.tipo = Solicitacao.class;
			setXmlModel(solicitacoes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "resultXML";
	}

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
