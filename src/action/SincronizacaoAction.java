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
			inputStream = new ByteArrayInputStream(("Importa��o de Afastamentos n�o realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}

	public String atualizaBancos() {
		try {
			importer.importaBancos();
			inputStream = new ByteArrayInputStream(("Bancos importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(("Importa��o de bancos n�o realizada. Causa: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	public String atualizaCargos() {
		try {
			importer.importaCargos();
			inputStream = new ByteArrayInputStream(("Cargos importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(("Importa��o de cargos n�o realizada. Causa: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	public String sincronizaCargos() {
		try {
			importer.sincronizaCargos();
			inputStream = new ByteArrayInputStream(("Cargos importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importa��o de cargos n�o realizada. Causa: " + e.getMessage()).getBytes());
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
					("Importa��o de centros n�o realizada. Causa: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	public String atualizaCidades() {
		try {
			importer.importaCidades();
			
			inputStream = new ByteArrayInputStream(("Cidades importadas com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importa��o de cidades n�o realizada. Causa: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	
	public String sincronizaCidades() {
		try {
			importer.sincronizaCidades();
			importer.importarBairros();
			
			inputStream = new ByteArrayInputStream(("Cidades e Bairros importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(("Importa��o de cidades n�o realizada. Causa: " + e.getMessage()).getBytes());
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
					("Importa��o de nacionalidades n�o realizada. Causa: " + e.getMessage())
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
					("Importa��o de clientes n�o realizada. Causa: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	public String sincronizaClientes() {
		try {
			importer.sincronizaClientes();
			inputStream = new ByteArrayInputStream(("Clientes importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importa��o de clientes n�o realizada. Causa: " + e.getMessage()).getBytes());
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
					("Importa��o de colaboradores n�o realizada. Causa: " + e.getMessage())
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
					("Importa��o de colaboradores n�o realizada. Causa: " + e.getMessage())
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
					("Importa��o de demitidos n�o realizada. Causa: " + e.getMessage())
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
					("Distribui��es atualizadas com sucesso").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importa��o de Distribui��es n�o realizada. Causa: " + e.getMessage())
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
					("Importa��o de empresas n�o realizada. Causa: " + e.getMessage()).getBytes());
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
			inputStream = new ByteArrayInputStream(("Importa��o de EscalasRonda n�o realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}
	
	public String atualizaSindicato() {
		try {
			importer.importarSindicatos();
			
			inputStream = new ByteArrayInputStream(("Sindicatos importadas com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(("Importa��o de importadas n�o realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}
	
	
	public String atualizaDeficiencia() {
		try {
			importer.importarDeficiencias();
			
			inputStream = new ByteArrayInputStream(("Deficiencias importadas com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(("Importa��o de importadas n�o realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}
	
	public String atualizaPostoTrabalho() {
		try {
			importer.importarPostoTrabalho();
			
			inputStream = new ByteArrayInputStream(("Postos de Trabalho importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(("Importa��o de postos de trabalho n�o realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}
	
	public String atualizaNaturezaDespesa() {
		try {
			importer.importarNaturezaDespesa();
			
			inputStream = new ByteArrayInputStream(("Natureza de despesas, importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(("Importa��o de natureza de despesa n�o realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}
	
	
	
	public String atualizaVinculo() {
		try {
			importer.importarVinculo();
			
			inputStream = new ByteArrayInputStream(("Vinculo, importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(("Importa��o de vinculo n�o realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}
	
	
	public String atualizaEscaValTransporte() {
		try {
			importer.importaEscalaValeTransporte();
			
			inputStream = new ByteArrayInputStream(("Escala Vale Transporte, importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(("Importa��o de Escala Vale Transporte n�o realizada. Causa: " + e.getMessage())
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
					("Sincroniza��o de EscalasRonda n�o realizada. Causa: " + e.getMessage())
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
					("Importa��o de estoques n�o realizada. Causa: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	public String atualizaGrauInstrucao() {
		try {
			importer.importaGrauInstrucao();
			inputStream = new ByteArrayInputStream(
					("Graus de instru��o importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importa��o de graus de instru��o n�o realizada. Causa: "
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
					("Importa��o de locais n�o realizada. Causa: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	public String sincronizaLocais() {
		try {
			importer.sincronizaLocais();
			inputStream = new ByteArrayInputStream(("Locais importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importa��o de locais n�o realizada. Causa: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	public String atualizaMotivos() {
		try {
			// Importer.importaMotivos();
			inputStream = new ByteArrayInputStream(("Motivos importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importa��o de motivos n�o realizada. Causa: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	/**
	 * Verifica se os itens com situa��o PL na web est�o no SM, se nao estiver, muda a situacao para SL.
	 * 
	 * @return
	 */
	public String atualizaSituacoes() {
		try {
			sincronizacao.atualizaSituacoes();
			inputStream = new ByteArrayInputStream(
					("Situa��o dos itens atualizada com sucesso").getBytes());
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
					("Importa��o de Tipos de EPI n�o realizada. Causa: " + e.getMessage())
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
					("Importa��o de uniformes n�o realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}

	/**
	 * gera um relat�rio com os itens de solicita��o de uniforme que est�o pendentes de libera��o
	 * 
	 * @throws Exception
	 */
	public void geraRelatorioLiberacoes() throws Exception {
		String path = servletRequest.getSession().getServletContext().getRealPath("/paginas/reports/");
		try {
			Map<String, String> parametros = new HashMap<String, String>();
			parametros.put("titulo", "Itens � liberar");
			util.geraRelatorio(sincronizacao.geraRelatorioLiberacoes(path, mandaEmail),
					"AvisoIndividual.jasper", "Libera��o" + new Date().getTime() + ".pdf",
					parametros, "pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Envio de relat�rio de estoques em falta para atendimento de solicita��es
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
			parametros.put("titulo", "Pend�ncias de Envio - Implanta��o e Substitui��o");
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
			parametros.put("titulo", "Implanta��o com Pend�ncia de Envio");
			util.geraRelatorio(itensPendentes, "AvisoPendencias.jasper",
					"Pend�ncias" + new Date().getTime() + ".pdf", parametros, "pdf");
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
					("Distribui��es importadas com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importa��o de Distribui��es n�o realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}

	public String notificaAdmissao() {
		String path = servletRequest.getSession().getServletContext()
				.getRealPath("/paginas/reports/");
		try {
			sincronizacao.notificaAdmissoes(path);
			inputStream = new ByteArrayInputStream(("Notifica��o enviada com sucesso").getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			inputStream = new ByteArrayInputStream(
					("Notifica��o n�o foi enviada. Causa: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	public String notificaSolicitacaoAdm() {
		String path = servletRequest.getSession().getServletContext()
				.getRealPath("/paginas/reports/");
		try {
			sincronizacao.notificaSolicitacaoNovosColaboradores(path);
			inputStream = new ByteArrayInputStream(("Notifica��o enviada com sucesso").getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			inputStream = new ByteArrayInputStream(
					("Notifica��o n�o foi enviada. Causa: " + e.getMessage()).getBytes());
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
	 * Sincronizacao.verificaAdmissaoEfetivada(path); inputStream = new ByteArrayInputStream(("Verifica��o conclu�da com sucesso.").getBytes());
	 * } catch (BatchUpdateException e) { inputStream = new ByteArrayInputStream( ("Exporta��o n�o realizada. Causa: " +
	 * e.getCause()).getBytes()); } catch (ConstraintViolationException ex) { inputStream = new ByteArrayInputStream(
	 * ("Exporta��o n�o realizada. Causa: " + ex.getSQLException().getLocalizedMessage()).getBytes()); } catch (Exception e) { inputStream = new
	 * ByteArrayInputStream( ("Exporta��o n�o realizada. Causa: " + e.getMessage()).getBytes()); } return "sucTxt"; }
	 */

	/**
	 * public String verificaAdmissaoEfetivada() { String path = ServletActionContext.getServletContext().getRealPath("/paginas/reports/"); try {
	 * util.Util.normalizaCPF(); Sincronizacao.verificaAdmissaoEfetivadaVetorh(path); inputStream = new ByteArrayInputStream(("Verifica��o
	 * conclu�da com sucesso.").getBytes()); } catch (BatchUpdateException e) { inputStream = new ByteArrayInputStream( ("Exporta��o n�o
	 * realizada. Causa: " + e.getCause()).getBytes()); } catch (ConstraintViolationException ex) { inputStream = new
	 * ByteArrayInputStream(("Exporta��o n�o realizada. Causa: " + ex .getSQLException().getLocalizedMessage()).getBytes()); } catch (Exception
	 * e) { inputStream = new ByteArrayInputStream( ("Exporta��o n�o realizada. Causa: " + e.getMessage()).getBytes()); e.printStackTrace(); }
	 * return "sucTxt"; }
	 **/

	public String verificaEstoque() {
		try {
			sincronizacao.verificaEstoque();
			inputStream = new ByteArrayInputStream(("Estoque atualizado com sucesso").getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			inputStream = new ByteArrayInputStream(
					("Estoque n�o foi atualizado. Erro: " + e.getMessage()).getBytes());
		}
		return "sucTxt";
	}

	/**
	 * importa material de expediente, material carga de posto, material de instala��o
	 **/
	public String atualizaProduto() {
		try {
			importer.importaProdutos();
			inputStream = new ByteArrayInputStream(("Produtos importados com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importa��o de produtos n�o realizada. Causa: " + e.getMessage()).getBytes());
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
					("Importa��o de fornecedores n�o realizada. Causa: " + e.getMessage())
							.getBytes());
		}
		return "sucTxt";
	}

	public String atualizaCondicaoPagamento() {
		try {
			importer.importaCondicoesPagamento();
			inputStream = new ByteArrayInputStream(
					("Condi��o de pagamento atualizadas com sucesso.").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Importa��o de Condi��o de pagamento n�o realizada. Causa: "
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
						("Backup n�o foi realizado. Causa:" + e.getMessage()).getBytes());
			} catch (IOException e) {
				inputStream = new ByteArrayInputStream(
						("Backup n�o foi realizado. Causa:" + e.getMessage()).getBytes());
			} catch (InterruptedException e) {
				inputStream = new ByteArrayInputStream(
						("Backup n�o foi realizado. Causa:" + e.getMessage()).getBytes());
			}
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(
					("Backup n�o foi realizado. Causa:" + e.getMessage()).getBytes());
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
