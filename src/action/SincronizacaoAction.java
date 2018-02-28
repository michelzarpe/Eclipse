package action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import job.Sincronizacao;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.StreamResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import util.BackupMysql;
import util.Importer;
import util.Util;
import bean.ItemSolicitacao;
import bean.ItemSolicitacao.Situacao;

import com.opensymphony.xwork2.ActionSupport;

import dao.ItemSolicitacaoDAO;

@Results({ @Result(name = "sucTxt", value = "inputStream", type = StreamResult.class, params = {
		"inputName", "inputStream" }) })
public class SincronizacaoAction extends ActionSupport implements ServletRequestAware {
	private static final long	serialVersionUID	= 1L;
	private int						idSol;
	private String					path;
	private int						codCcu;
	private boolean				mandaEmail;
	private InputStream			inputStream;
	private int						numEmp;
	@Autowired
	@Qualifier("HItemSolicitacaoDAO")
	private ItemSolicitacaoDAO	itemSolicitacaoDAO;
	@Autowired
	@Qualifier("Importer")
	private Importer				importer;
	@Autowired
	@Qualifier("Sincronizacao")
	private Sincronizacao		sincronizacao;
	@Autowired
	@Qualifier("Util")
	private Util					util;
	private HttpServletRequest	servletRequest;

	public int getNumEmp() {
		return numEmp;
	}

	public void setNumEmp(int numEmp) {
		this.numEmp = numEmp;
	}

	public boolean isMandaEmail() {
		return mandaEmail;
	}

	public void setMandaEmail(boolean mandaEmail) {
		this.mandaEmail = mandaEmail;
	}

	public String atualizaAfastamentos() {
		try {
			importer.importaAfastamentos();
			inputStream = new ByteArrayInputStream(
					("Afastamentos importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(("Importação de Afastamentos não realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}

	public String atualizaBancos() {
		try {
			importer.importaBancos();
			inputStream = new ByteArrayInputStream(("Bancos importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(("Importação de bancos não realizada. Causa: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	public String atualizaCargos() {
		try {
			importer.importaCargos();
			inputStream = new ByteArrayInputStream(("Cargos importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(("Importação de cargos não realizada. Causa: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	public String sincronizaCargos() {
		try {
			importer.sincronizaCargos();
			inputStream = new ByteArrayInputStream(("Cargos importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importação de cargos não realizada. Causa: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	public String atualizaCentros() {
		try {
			importer.importaCentros();
			inputStream = new ByteArrayInputStream(
					("Centros de custo importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importação de centros não realizada. Causa: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	public String atualizaCidades() {
		try {
			importer.importaCidades();
			
			inputStream = new ByteArrayInputStream(("Cidades importadas com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importação de cidades não realizada. Causa: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	
	public String sincronizaCidades() {
		try {
			importer.sincronizaCidades();
			importer.importarBairros();
			
			inputStream = new ByteArrayInputStream(("Cidades e Bairros importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(("Importação de cidades não realizada. Causa: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}
	

	public String sincronizaNacionalidades() {
		try {
			importer.importaNacionalidades();
			inputStream = new ByteArrayInputStream(
					("Nacionalidades importadas com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importação de nacionalidades não realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}

	public String atualizaClientes() {
		try {
			importer.importaClientes();
			inputStream = new ByteArrayInputStream(("Clientes importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importação de clientes não realizada. Causa: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	public String sincronizaClientes() {
		try {
			importer.sincronizaClientes();
			inputStream = new ByteArrayInputStream(("Clientes importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importação de clientes não realizada. Causa: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	public String atualizaTodosColaboradores() {
		try {
			importer.atualizaColaboradores(null, null, this.getNumEmp());
			inputStream = new ByteArrayInputStream(
					("Colaboradores importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importação de colaboradores não realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}

	public String atualizaColaboradores() {
		try {
			importer.atualizaColaboradores();
			inputStream = new ByteArrayInputStream(
					("Colaboradores importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importação de colaboradores não realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}

	public String atualizaDemitidos() {
		try {
			importer.importaDemitidos();
			inputStream = new ByteArrayInputStream(("Demitidos importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importação de demitidos não realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}

	public String atualizaDistribuicao() {
		try {
			importer.atualizaDistribuicao();
			// Importer.importaNovasDistribuicoes();
			importer.importaDistribuicoes();
			inputStream = new ByteArrayInputStream(
					("Distribuições atualizadas com sucesso").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importação de Distribuições não realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}

	public String atualizaEmpresas() {
		try {
			importer.importaEmpresas();
			inputStream = new ByteArrayInputStream(("Empresas importadas com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importação de empresas não realizada. Causa: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	/*----------------------------------------------------------------------------------------------------*/
	public String atualizaEscalasRonda() {
		try {
			importer.importarClasseEscalaRonda();
			importer.importarEscalaHorarioRonda();
			importer.importarHorarioRonda();
			importer.importarTurmaHorarioRonda();
			importer.importarHorarioEscalaRonda();
			/*importer.importaEscalasRonda();*/
			
			inputStream = new ByteArrayInputStream(("EscalasRonda importadas com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(("Importação de EscalasRonda não realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}
	
	public String atualizaSindicato() {
		try {
			importer.importarSindicatos();
			
			inputStream = new ByteArrayInputStream(("Sindicatos importadas com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(("Importação de importadas não realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}
	
	
	public String atualizaDeficiencia() {
		try {
			importer.importarDeficiencias();
			
			inputStream = new ByteArrayInputStream(("Deficiencias importadas com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(("Importação de importadas não realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}
	
	public String atualizaPostoTrabalho() {
		try {
			importer.importarPostoTrabalho();
			
			inputStream = new ByteArrayInputStream(("Postos de Trabalho importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(("Importação de postos de trabalho não realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}
	
	public String atualizaNaturezaDespesa() {
		try {
			importer.importarNaturezaDespesa();
			
			inputStream = new ByteArrayInputStream(("Natureza de despesas, importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(("Importação de natureza de despesa não realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}
	
	
	
	public String atualizaVinculo() {
		try {
			importer.importarVinculo();
			
			inputStream = new ByteArrayInputStream(("Vinculo, importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(("Importação de vinculo não realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}
	
	
	public String atualizaEscaValTransporte() {
		try {
			importer.importaEscalaValeTransporte();
			
			inputStream = new ByteArrayInputStream(("Escala Vale Transporte, importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(("Importação de Escala Vale Transporte não realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}

	/*----------------------------------------------------------------------------------------------------*/
	public String sincronizaEscalasRonda() {
		try {
			importer.sincronizaEscalasRonda();
			inputStream = new ByteArrayInputStream(
					("EscalasRonda sincronizadas com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Sincronização de EscalasRonda não realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}
	
	
	public String atualizaEstoques() {
		try {
			importer.importaEstoques();
			inputStream = new ByteArrayInputStream(("Estoques importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importação de estoques não realizada. Causa: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	public String atualizaGrauInstrucao() {
		try {
			importer.importaGrauInstrucao();
			inputStream = new ByteArrayInputStream(
					("Graus de instrução importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importação de graus de instrução não realizada. Causa: "
							+ e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	public String atualizaLocais() {
		try {
			importer.importaLocais();
			inputStream = new ByteArrayInputStream(("Locais importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importação de locais não realizada. Causa: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	public String sincronizaLocais() {
		try {
			importer.sincronizaLocais();
			inputStream = new ByteArrayInputStream(("Locais importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importação de locais não realizada. Causa: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	public String atualizaMotivos() {
		try {
			// Importer.importaMotivos();
			inputStream = new ByteArrayInputStream(("Motivos importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importação de motivos não realizada. Causa: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	/**
	 * Verifica se os itens com situaàào PL na web estào no SM, se nao estiver, muda a situacao para SL.
	 * 
	 * @return
	 */
	public String atualizaSituacoes() {
		try {
			sincronizacao.atualizaSituacoes();
			inputStream = new ByteArrayInputStream(
					("Situaàào dos itens atualizada com sucesso").getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			inputStream = new ByteArrayInputStream(
					("Atualizacao nao efetuada." + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	public String atualizaTipos() {
		try {
			importer.importaTipos();
			inputStream = new ByteArrayInputStream(
					("Tipos de EPI importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importação de Tipos de EPI não realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}

	public String atualizaUniformes() {
		try {
			importer.importaUniformes();
			inputStream = new ByteArrayInputStream(("Uniformes importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importação de uniformes não realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}

	/**
	 * gera um relatàrio com os itens de solicitaàào de uniforme que estào pendentes de liberaàào
	 * 
	 * @throws Exception
	 */
	public void geraRelatorioLiberacoes() throws Exception {
		String path = servletRequest.getSession().getServletContext().getRealPath("/paginas/reports/");
		try {
			Map<String, String> parametros = new HashMap<String, String>();
			parametros.put("titulo", "Itens à liberar");
			util.geraRelatorio(sincronizacao.geraRelatorioLiberacoes(path, mandaEmail),
					"AvisoIndividual.jasper", "Liberação" + new Date().getTime() + ".pdf",
					parametros, "pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Envio de relatàrio de estoques em falta para atendimento de solicitaààes
	 * 
	 * @return
	 */
	public void geraRelatorioNaoAtendidos() {
		String path = servletRequest.getSession().getServletContext().getRealPath("/paginas/reports/");
		try {
			util.geraRelatorio(sincronizacao.geraRelatorioNaoAtendidos(path), "EstoqueFalta.jasper",
					"FaltaEstoque" + new Date().getTime() + ".pdf", new HashMap<String, String>(),
					"pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void geraRelatorioPendencias() {
		try {
			Map<String, String> parametros = new HashMap<String, String>();
			parametros.put("titulo", "Pendências de Envio - Implantação e Substituição");
			List<ItemSolicitacao> itens = itemSolicitacaoDAO.listPendentesEnvio(codCcu);
			util.geraRelatorio(itens, "AvisoPendencias.jasper",
					"Pendencias de Envio" + new Date().getTime() + ".pdf", parametros, "pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void geraRelatorioPendImpl() {
		try {
			List<Situacao> situacoes = new ArrayList<Situacao>();
			situacoes.add(Situacao.SAA);
			situacoes.add(Situacao.NA);
			situacoes.add(Situacao.SL);
			List<Integer> motivos = new ArrayList<Integer>();
			motivos.add(2);
			List<ItemSolicitacao> itensPendentes = itemSolicitacaoDAO.listBySituacao(situacoes,
					motivos, true);
			Map<String, String> parametros = new HashMap<String, String>();
			parametros.put("titulo", "Implantação com Pendência de Envio");
			util.geraRelatorio(itensPendentes, "AvisoPendencias.jasper",
					"Pendências" + new Date().getTime() + ".pdf", parametros, "pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getCodCcu() {
		return codCcu;
	}

	public int getIdSol() {
		return idSol;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public String getPath() {
		return path;
	}

	public String importaDistribuicoes() {
		try {
			importer.importaDistribuicoes();
			inputStream = new ByteArrayInputStream(
					("Distribuições importadas com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importação de Distribuições não realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}

	public String notificaAdmissao() {
		String path = servletRequest.getSession().getServletContext()
				.getRealPath("/paginas/reports/");
		try {
			sincronizacao.notificaAdmissoes(path);
			inputStream = new ByteArrayInputStream(("Notificação enviada com sucesso").getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			inputStream = new ByteArrayInputStream(
					("Notificação não foi enviada. Causa: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	public String notificaSolicitacaoAdm() {
		String path = servletRequest.getSession().getServletContext()
				.getRealPath("/paginas/reports/");
		try {
			sincronizacao.notificaSolicitacaoNovosColaboradores(path);
			inputStream = new ByteArrayInputStream(("Notificação enviada com sucesso").getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			inputStream = new ByteArrayInputStream(
					("Notificação não foi enviada. Causa: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	public void setCodCcu(int codCcu) {
		this.codCcu = codCcu;
	}

	public void setIdSol(int idSol) {
		this.idSol = idSol;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/*
	 * public String verificaAdmissaoEfetivada() { String path = ServletActionContext.getServletContext().getRealPath("/paginas/reports/"); try {
	 * Sincronizacao.verificaAdmissaoEfetivada(path); inputStream = new ByteArrayInputStream(("Verificaàào concluàda com sucesso.").getBytes());
	 * } catch (BatchUpdateException e) { inputStream = new ByteArrayInputStream( ("Exportaàào não realizada. Causa: " +
	 * e.getCause()).getBytes()); } catch (ConstraintViolationException ex) { inputStream = new ByteArrayInputStream(
	 * ("Exportaàào não realizada. Causa: " + ex.getSQLException().getLocalizedMessage()).getBytes()); } catch (Exception e) { inputStream = new
	 * ByteArrayInputStream( ("Exportaàào não realizada. Causa: " + e.getMessage()).getBytes()); } return "sucTxt"; }
	 */

	/**
	 * public String verificaAdmissaoEfetivada() { String path = ServletActionContext.getServletContext().getRealPath("/paginas/reports/"); try {
	 * util.Util.normalizaCPF(); Sincronizacao.verificaAdmissaoEfetivadaVetorh(path); inputStream = new ByteArrayInputStream(("Verificaàào
	 * concluàda com sucesso.").getBytes()); } catch (BatchUpdateException e) { inputStream = new ByteArrayInputStream( ("Exportaàào não
	 * realizada. Causa: " + e.getCause()).getBytes()); } catch (ConstraintViolationException ex) { inputStream = new
	 * ByteArrayInputStream(("Exportaàào não realizada. Causa: " + ex .getSQLException().getLocalizedMessage()).getBytes()); } catch (Exception
	 * e) { inputStream = new ByteArrayInputStream( ("Exportaàào não realizada. Causa: " + e.getMessage()).getBytes()); e.printStackTrace(); }
	 * return "sucTxt"; }
	 **/

	public String verificaEstoque() {
		try {
			sincronizacao.verificaEstoque();
			inputStream = new ByteArrayInputStream(("Estoque atualizado com sucesso").getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			inputStream = new ByteArrayInputStream(
					("Estoque não foi atualizado. Erro: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	/**
	 * importa material de expediente, material carga de posto, material de instalaàào
	 **/
	public String atualizaProduto() {
		try {
			importer.importaProdutos();
			inputStream = new ByteArrayInputStream(("Produtos importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importação de produtos não realizada. Causa: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	public String atualizaFornecedor() {
		try {
			importer.importaFornecedores();
			inputStream = new ByteArrayInputStream(
					("Fornecedores atualizados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importação de fornecedores não realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}

	public String atualizaCondicaoPagamento() {
		try {
			importer.importaCondicoesPagamento();
			inputStream = new ByteArrayInputStream(
					("Condição de pagamento atualizadas com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importação de Condição de pagamento não realizada. Causa: "
							+ e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	public String backup() {
		try {
			try {
				BackupMysql.doBackup("root", "grpzjba", "gzOper");
				BackupMysql.doBackup("root", "grpzjba", "operacional");
				inputStream = new ByteArrayInputStream(
						("Backup realizado com sucesso. Verifique em /opt/ultraBackup").getBytes());
			} catch (RemoteException e) {
				inputStream = new ByteArrayInputStream(
						("Backup não foi realizado. Causa:" + e.getMessage()).getBytes());
			} catch (IOException e) {
				inputStream = new ByteArrayInputStream(
						("Backup não foi realizado. Causa:" + e.getMessage()).getBytes());
			} catch (InterruptedException e) {
				inputStream = new ByteArrayInputStream(
						("Backup não foi realizado. Causa:" + e.getMessage()).getBytes());
			}
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Backup não foi realizado. Causa:" + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	public ItemSolicitacaoDAO getItemSolicitacaoDAO() {
		return itemSolicitacaoDAO;
	}

	public void setItemSolicitacaoDAO(ItemSolicitacaoDAO itemSolicitacaoDAO) {
		this.itemSolicitacaoDAO = itemSolicitacaoDAO;
	}

	public Importer getImporter() {
		return importer;
	}

	public void setImporter(Importer importer) {
		this.importer = importer;
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
