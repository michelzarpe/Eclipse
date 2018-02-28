package action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.StreamResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import result.BasicResult;
import util.Util;
import bean.AdiantamentoViagem;
import bean.Centro;
import bean.DespesaViagem;
import bean.Usuario;
import bean.Viagem;
import bean.Viagem.Situacao;

import com.opensymphony.xwork2.ActionSupport;

import dao.AdiantamentoViagemDAO;
import dao.UsuarioDAO;
import dao.ViagemDAO;

@Results({
		@Result(name = "resultXML", value = "xmlModel", type = BasicResult.class),
		@Result(name = "sucTxt", value = "inputStream", type = StreamResult.class, params = {
				"inputName", "inputStream" }) })
public class ViagemAction extends ActionSupport implements ServletRequestAware {
	private static final long			serialVersionUID	= 1L;
	private Object							xmlModel;
	private Viagem							viagem				= new Viagem();
	private String							totalCount;
	private Class							tipo					= Viagem.class;
	private InputStream					inputStream;
	private List<Centro>					centros				= new ArrayList<Centro>();
	private List<AdiantamentoViagem>	adiantamentos		= new ArrayList<AdiantamentoViagem>();
	private List<DespesaViagem>		despesas				= new ArrayList<DespesaViagem>();
	private String							situacao;
	private AdiantamentoViagem			adiantamentoViagem;
	@Autowired
	@Qualifier("HViagemDAO")
	private ViagemDAO						viagemDAO;
	@Autowired
	@Qualifier("HUsuarioDAO")
	private UsuarioDAO					usuarioDAO;
	
	@Autowired
	@Qualifier("HAdiantamentoViagemDAO")
	private AdiantamentoViagemDAO		adiantamentoViagemDAO;
	
	@Autowired
	@Qualifier("Util")
	private Util util;
	private HttpServletRequest servletRequest;
	
	@Override
   public void setServletRequest(HttpServletRequest servletRequest) {
       this.servletRequest = servletRequest;
   }

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public List<AdiantamentoViagem> getAdiantamentos() {
		return adiantamentos;
	}

	public void setAdiantamentos(List<AdiantamentoViagem> adiantamentos) {
		this.adiantamentos = adiantamentos;
	}

	public List<DespesaViagem> getDespesas() {
		return despesas;
	}

	public void setDespesas(List<DespesaViagem> despesas) {
		this.despesas = despesas;
	}

	public List<Centro> getCentros() {
		return centros;
	}

	public void setCentros(List<Centro> centros) {
		this.centros = centros;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
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

	public Object getXmlModel() {
		return xmlModel;
	}

	public void setXmlModel(Object xmlModel) {
		this.xmlModel = xmlModel;
	}

	public Viagem getViagem() {
		return viagem;
	}

	public void setViagem(Viagem viagem) {
		this.viagem = viagem;
	}

	public AdiantamentoViagem getAdiantamentoViagem() {
		return adiantamentoViagem;
	}

	public void setAdiantamentoViagem(AdiantamentoViagem adiantamentoViagem) {
		this.adiantamentoViagem = adiantamentoViagem;
	}

	public String listByExample() throws Exception {
		if (situacao != null) {
			viagem.setSituacao(Situacao.valueOf(situacao));
		}
		Usuario usuario = usuarioDAO.loadById(((Usuario)util.recuperaSessao("Usuario")).getId());
		viagem.setUsuario(usuario);
		List<Viagem> viagens = viagemDAO.listByExample(viagem);
		this.totalCount = String.valueOf(viagens.size());
		this.setXmlModel(viagens);
		return "resultXML";
	}

	public String buscaViagem() throws Exception {
		Viagem viagem = viagemDAO.loadById(this.viagem.getId());
		this.setXmlModel(viagem);
		return "resultXML";
	}

	public String gravar() {
		Usuario usuario = (Usuario) util.recuperaSessao("Usuario");
		int idViagem;
		
		try {
			viagem.setUsuario(usuario);
			viagem.setSituacao(Situacao.AB);
			// Altera√ß√£o de viagem
			if (this.viagem.getId() > 0) {
				idViagem = this.viagem.getId();
				for (Centro centro : centros) {
					if ((!viagem.getCentrosVisitados().contains(centro)) && (centro != null))
							viagem.getCentrosVisitados().add(centro);
				}
				for (DespesaViagem despesa : despesas) {
					if (viagem.getDespesas().contains(despesa))
							viagem.getDespesas().remove(despesa);
							despesa.getId().setViagem(viagem);
							despesa.setUsuario(usuario);
					if (adiantamentos.isEmpty()) {
						despesa.setAdiantamentoViagem(null);
					}
					viagem.getDespesas().add(despesa);
				}
				
				if (!adiantamentos.isEmpty()) {
					for (AdiantamentoViagem adiantamento : adiantamentos) {
						if ((!viagem.getAdiantamentos().contains(adiantamento)) && (adiantamento != null)) {
							adiantamento.setUsuario(usuario);
							adiantamento.setDatInc(new Date());
							adiantamento.getId().setViagem(viagem);
							viagem.getAdiantamentos().add(adiantamento);
						}
					}
				}
				viagemDAO.update(this.viagem);
			} else {// inclus√£o de viagem
				viagem.setDatInc(new Date());
				viagem.setDatSol(new Date());
				viagem.setSituacao(Situacao.AB);
				for (Centro centro : centros) {
					if ((!viagem.getCentrosVisitados().contains(centro)) && (centro != null))
						viagem.getCentrosVisitados().add(centro);
				}
				for (DespesaViagem despesa : despesas) {
					despesa.getId().setViagem(viagem);
					despesa.setUsuario(usuario);
					viagem.getDespesas().add(despesa);
				}
				for (AdiantamentoViagem adiantamento : adiantamentos) {
					adiantamento.getId().setViagem(viagem);
					adiantamento.setUsuario(usuario);
					adiantamento.setDatInc(new Date());
					viagem.getAdiantamentos().add(adiantamento);
				}
				idViagem = (Integer) viagemDAO.insert(viagem);
			}
			String mensagem = "{success:true, msg: 'Cadastro realizado com sucesso', viagemId:"+ idViagem + "}";
			inputStream = new ByteArrayInputStream((mensagem).getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			inputStream = new ByteArrayInputStream(("{success:false, msg: 'CADASTRO NAO FOI REALIZADO'}").getBytes());
		}
		return "sucTxt";
	}

	/** Buscar viagem aberta para o colaborador **/
	public String findByDateCol() {
		try {
			Usuario usuario = usuarioDAO.loadById(((Usuario) util.recuperaSessao("Usuario")).getId());
			viagem.setUsuario(usuario);
			List<Viagem> viagens = viagemDAO.getViagensAbertas(viagem.getColaborador(), usuario);
			this.totalCount = String.valueOf(viagens.size());
			this.tipo = Viagem.class;
			setXmlModel(viagens);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "resultXML";
	}

	public void imprimirAcerto() {
		try {
			viagem = viagemDAO.loadById(this.viagem.getId());
			if (adiantamentoViagem != null)
				adiantamentoViagem = adiantamentoViagemDAO.loadById(new bean.AdiantamentoViagem.Id(viagem, adiantamentoViagem.getId().getId()));
			String path = servletRequest.getSession().getServletContext().getRealPath("/paginas/reports/");
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("SUBREPORT_DIR", path + "//");
			/** buscar apenas adiantamento selecionado **/
			List<AdiantamentoViagem> adiantamentos = new ArrayList<AdiantamentoViagem>();
			if (!viagem.getAdiantamentos().isEmpty())
				for (AdiantamentoViagem adiViagem : viagem.getAdiantamentos()) {
					if ((adiViagem.getId().getId() == adiantamentoViagem.getId().getId())
							&& (adiViagem.getId().getViagem().getId() == adiantamentoViagem.getId()
									.getViagem().getId())) {
						adiViagem.setDatAce(new Date());
						viagem.getAdiantamentos().add(adiViagem);
						adiantamentos.add(adiViagem);
					}
				}
			JRDataSource dsDetail2 = new JRBeanCollectionDataSource(adiantamentos);
			/** buscar apenas despesas que sejam do adiantamento selecionado **/
			List<DespesaViagem> despesas = new ArrayList<DespesaViagem>();

			if (!viagem.getDespesas().isEmpty())
				for (DespesaViagem despesaViagem : viagem.getDespesas()) {
					if (adiantamentoViagem != null) {
						if ((despesaViagem.getAdiantamentoViagem().getId().getId() == adiantamentoViagem
								.getId().getId())
								&& (despesaViagem.getAdiantamentoViagem().getId().getViagem().getId() == adiantamentoViagem
										.getId().getViagem().getId())) {
							despesaViagem.setDatAce(new Date());
							viagem.getDespesas().add(despesaViagem);
							despesas.add(despesaViagem);
						}
					} else {
						despesaViagem.setDatAce(new Date());
						viagem.getDespesas().add(despesaViagem);
						despesas.add(despesaViagem);
					}
				}

			JRDataSource dsDetail = new JRBeanCollectionDataSource(despesas);
			JRDataSource dsDetail3 = new JRBeanCollectionDataSource(viagem.getCentrosVisitados());
			parametros.put("DATA", dsDetail);
			parametros.put("DATA2", dsDetail2);
			parametros.put("DATA3", dsDetail3);
			List<Viagem> viagens = new ArrayList<Viagem>();
			viagens.add(viagem);
			viagemDAO.update(viagem);
			/** imprimir acerto **/
			util.geraRelatorio(viagens, "AcertoViagem.jasper", "Acerto"
					+ viagem.getColaborador().getNomFun() + new Date().getTime() + ".pdf", parametros,
					"pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Deprecated
	public String fecharViagem() throws Exception {
		double totalAdt = 0;
		double totalDes = 0;
		double totRee = 0;
		String resultado = "";
		Viagem viagem = viagemDAO.loadById(this.viagem.getId());
		for (AdiantamentoViagem adiantamentoViagem : viagem.getAdiantamentos()) {
			totalAdt += adiantamentoViagem.getVlrAdt();
		}
		for (DespesaViagem despesaViagem : viagem.getDespesas()) {
			totalDes += despesaViagem.getVlrDes();
		}

		totRee = totalDes - totalAdt;
		if (totRee != 0) {
			viagem.setVlrRee(totRee);
			viagem.setSituacao(Situacao.FE);
			viagemDAO.update(viagem);
			if (totRee >= 0)
				resultado = "{success:true, msg: 'VIAGEM FECHADA COM SUCESSO. REEMBOLSO: "
						+ String.valueOf(totRee) + "'  }";
			else
				resultado = "{success:true, msg: 'VIAGEM FECHADA COM SUCESSO. DEVOLU«√O: "
						+ String.valueOf(totRee) + "'  }";
		}

		inputStream = new ByteArrayInputStream((resultado).getBytes());
		return "sucTxt";
	}

	public String excluir() {
		try {
			viagem = viagemDAO.loadById(viagem.getId());
			viagemDAO.delete(viagem);
			inputStream = new ByteArrayInputStream(("Viagem excluida com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(("Viagem nao foi excluida.").getBytes());
		}
		return "sucTxt";
	}

	public void geraRelatorioAdtPendente() {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("titulo", "Adiantamentos Pendentes");
			Usuario usuario = (Usuario) util.recuperaSessao("Usuario");
			List<AdiantamentoViagem> adiantamentos = adiantamentoViagemDAO.listPendentes(usuario);
			if (adiantamentos.size() > 0) {
				util.geraRelatorio(adiantamentos, "AdiantamentosPendentes.jasper",
						"Adiantamentos pendentes" + new Date().getTime() + ".pdf", parametros, "pdf");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ViagemDAO getViagemDAO() {
		return viagemDAO;
	}

	public void setViagemDAO(ViagemDAO viagemDAO) {
		this.viagemDAO = viagemDAO;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public AdiantamentoViagemDAO getAdiantamentoViagemDAO() {
		return adiantamentoViagemDAO;
	}

	public void setAdiantamentoViagemDAO(AdiantamentoViagemDAO adiantamentoViagemDAO) {
		this.adiantamentoViagemDAO = adiantamentoViagemDAO;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}
}
