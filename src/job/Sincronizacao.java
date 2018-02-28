package job;

import java.net.MalformedURLException;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.ConnectSqlServer;
import util.Importer;
import util.Util;
import bean.Colaborador;
import bean.ItemSolicitacao;
import bean.ItemSolicitacao.Situacao;
import bean.Solicitacao;
import bean.Usuario;
import bean.Usuario.TipoUsuario;
import dao.ColaboradorDAO;
import dao.ItemSolicitacaoDAO;
import dao.SolicitacaoDAO;
import dao.UsuarioDAO;

@Service("Sincronizacao")
@Transactional
public class Sincronizacao {
	private Logger					logger				= Logger.getLogger(Sincronizacao.class);
	private ConnectSqlServer	connectSqlServer	= ConnectSqlServer.getInstance();
	@Autowired
	@Qualifier("HColaboradorDAO")
	private ColaboradorDAO		colaboradorDAO;
	@Autowired
	@Qualifier("HUsuarioDAO")
	private UsuarioDAO			usuarioDAO;
	@Autowired
	@Qualifier("HItemSolicitacaoDAO")
	private ItemSolicitacaoDAO	itemSolicitacaoDAO;
	@Autowired
	@Qualifier("HSolicitacaoDAO")
	private SolicitacaoDAO		solicitacaoDAO;
	@Autowired
	private Importer				importer;
	@Autowired
	private Util util;
	
	public Sincronizacao() {
		super();
	}

	/**
	 * Lista todos os colaboradores que estão com situação 1000 (Aguardando admissão) e envia e-mail para o responsável pelas admissões no vetorh
	 * 
	 * @throws Exception
	 * 
	 * @throws MalformedURLException
	 * @throws Exception
	 */
	public void notificaAdmissoes(String path) throws Exception {
		String texto = "Em anexo relação de admissões que ainda não foram lançadas no Rubi.";
		List<Colaborador> novosColaboradoes = colaboradorDAO.listBySituacao(1000);
		if (!novosColaboradoes.isEmpty()) {
			util.geraRelatorioArquivo(novosColaboradoes, path + "//AvisoAdmissao.jasper", path
					+ "//Aviso.pdf", new HashMap<String, String>());
			List<Usuario> receptoresEmail = usuarioDAO.listReceptoresEmail("OAD");
			receptoresEmail.addAll(usuarioDAO.listReceptoresEmail("AAD"));
			receptoresEmail.addAll(usuarioDAO.listReceptoresEmail("ASI"));
			util.mandaEmail("weboper@grupozanardo.com.br", receptoresEmail,
					"[webOper] Admissões pendentes", texto, path + "//Aviso.pdf");
		}
	}

	/**
	 * Gera o texto que será enviado por e-mail, dependendo do tipo de solicitação e de usuário.
	 * 
	 * @param itensSolicitacao
	 * @param tipUsu
	 * @throws Exception
	 */
	public void notificaSolicitacoes(List<Solicitacao> itensSolicitacao, String tipUsu, String path)
			throws Exception {
		String texto = "";
		String titulo = "";
		String nomeArquivo = "";
		Map<String, String> parametros = new HashMap<String, String>();
		if (tipUsu.equals("OAL")) {
			texto = texto + "Em anexo novas solicitação de uniformes\n";
			titulo = "Novas solicitações de uniforme";
			nomeArquivo = "//NovasSolicitacoes" + (new Date()).getTime() + ".pdf";
			parametros.put("titulo", "Solicitações de Uniformes / EPI");
		} else if (tipUsu.equals("AAL")) {
			texto = texto + "Em anexo uma relação de solicitações que precisam de liberação.\n";
			titulo = "Solicitações pendentes de liberação";
			nomeArquivo = "//Solicitacoes_de_Liberação" + (new Date()).getTime() + ".pdf";
			parametros.put("titulo", "Solicitações com Pendência de Liberação");
		}

		parametros.put("SUBREPORT_DIR", path + "//");
		parametros.put("report_name", "Itens.jasper");

		util.geraRelatorioArquivo(itensSolicitacao, path + "//AvisoSolicitacao.jasper", path
				+ nomeArquivo, parametros);
		List<Usuario> destinatarios = usuarioDAO.listReceptoresEmail(tipUsu);
		util.mandaEmail("weboper@grupozanardo.com.br", destinatarios, "[webOper] " + titulo, texto,
				path + nomeArquivo);

	}

	/**
	 * Envia uma notificação por e-mail com uma solicitação individual
	 * 
	 * @param itens
	 * @param tipUsu
	 * @param path
	 * @throws JRException
	 */
	public void notificaSolicitacao(List<ItemSolicitacao> itens, String tipUsu, String path)
			throws JRException {
		String texto = "Em anexo, nova solicitacao de uniformes.";
		String titulo = "Nova solicitacao";
		String nomeArquivo = "//NovaSolicitacao.pdf";
		Map<String, String> parametros = new HashMap<String, String>();
		parametros.put("titulo", "Solicitacao de Uniformes / EPI");
		util.geraRelatorioArquivo(itens, path + "//AvisoIndividual.jasper", path + nomeArquivo,
				parametros);
		List<Usuario> destinatarios = usuarioDAO.listReceptoresEmail(tipUsu);
		util.mandaEmail("weboper@grupozanardo.com.br", destinatarios, "[webOper] " + titulo, texto,
				path + nomeArquivo);
	}

	public void notificaSolicitacao(List<ItemSolicitacao> itens, Usuario usuario, String path,
			String texto, String titulo, String nomeArquivo) throws JRException {
		Map<String, String> parametros = new HashMap<String, String>();
		parametros.put("titulo", titulo);
		util.geraRelatorioArquivo(itens, path + "//AvisoIndividual.jasper", path + nomeArquivo,
				parametros);
		List<Usuario> destinatarios = new ArrayList<Usuario>();
		destinatarios.add(usuario);
		util.mandaEmail("weboper@grupozanardo.com.br", destinatarios, "[webOper] " + titulo, texto,
				path + nomeArquivo);
	}

	/**
	 * TODO Buscar todos os registros do tipo "I" na tabela Usu_TSincWeb e verificar se tem admissão pendente na web através do CPF. Se encontrar,
	 * atualizar com os dados do Vetorh.
	 * 
	 * @param path
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	@Deprecated
	public void verificaAdmissaoEfetivadaVetorh(String path) throws NumberFormatException, Exception {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		conn = connectSqlServer.getConnection();
		String sql = "select * from Usu_TSincWeb where usu_tipOpe='I' and usu_nomtab='R034FUN'";
		stmt = conn.prepareStatement(sql);
		rs = stmt.executeQuery();
		String numCpf = "";
		String pkOri = "";
		while (rs.next()) {
			/** Retorna cpf do colaborador inserido **/
			pkOri = rs.getString("usu_pkori");
			if (pkOri == null) {
				Importer.excluiRegistroUsu_TSincWebNulo("I", "R034FUN");
			} else {
				sql = Importer.getSqlSelectColaboradorFromRegistro(pkOri);
				stmt = conn.prepareStatement(sql);
				ResultSet rsCol = stmt.executeQuery();
				if (rsCol.next()) {
					numCpf = String.valueOf(rsCol.getLong("numCpf"));
				}
				Colaborador colaborador = colaboradorDAO.loadByCPF(numCpf);
				if (colaborador != null) {
					/** atualiza dados do colaborador na web **/
					if (!importer.atualizaDadosColaborador(0, rsCol)) {
						System.err.println("Não foi possível efetivar admissão do colaborador "
								+ colaborador.getNumCad() + " - " + colaborador.getNomFun());
					} else {
						/** remover registro da tabela usu_tsincweb **/
						Importer.excluiRegistroUsu_TSincWeb(pkOri, "I", "R034FUN");
						Importer.excluiRegistroUsu_TSincWeb(pkOri, "U", "R034FUN");
					}
				} else {
					/**
					 * TODO insere dados do colaborador novo na web, sabendo que não encontrou colaborador pendente com o cpf dado.
					 */
					if (!importer.insereDadosColaborador(rsCol)) {
						System.err.println("Não foi possível importar colaborador "
								+ rsCol.getString("nomFun"));
					} else {
						/** remover registro da tabela usu_tsincweb **/
						Importer.excluiRegistroUsu_TSincWeb(pkOri, "I", "R034FUN");
						Importer.excluiRegistroUsu_TSincWeb(pkOri, "U", "R034FUN");
					}
				}
			}
		}
	}

	/**
	 * Busca todos os colaboradores com situação 1000(Aguardando admissão) e verifica pelo CPF de cada um deles se já foi inserido no Vetorh. Se já
	 * estiver sido admitido no vetorh, atualiza os dados no webOper
	 * 
	 * @throws Exception
	 */
	@Deprecated
	public void verificaAdmissaoEfetivada(String path) throws BatchUpdateException,
			ConstraintViolationException, Exception {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		List<Colaborador> colaboradores = colaboradorDAO.listBySituacao(1000);
		conn = connectSqlServer.getConnection();
		for (Colaborador colaborador : colaboradores) {
			String sql = "select f.numEmp as numEmp, nomFun, datAdm, sitAfa, estCar, codCar, codFil, "
					+ "tabOrg, numLoc, codCcu, numCpf, codEsc, f.numCad as numCad, Usu_DesLocPai, datInc, "
					+ "horInc, datNas, tipSex, numCtp, serCtp, numPis, conBan, codBan, codAge, "
					+ "estCiv, graIns, usu_numDRT, usu_numDip, usu_orgExD, usu_crtVig, numCid, emiCid, "
					+ "numCnh, catCnh, numEle, cciNas, endRua, codCid, endCep, numTel, endCpl, endNum "
					+ "from R034FUN f left join R034CPL c "
					+ " on f.numEmp=c.numEmp and f.tipCol=c.tipCol and f.numCad=c.numCad ";
			sql = sql + "where numCpf=? and sitAfa<>7 and f.tipCol = 1 and f.numEmp=? ";
			stmt = conn.prepareStatement(sql);
			String cpf = colaborador.getNumCpf().replace(".", "");
			cpf = cpf.replace("-", "");
			stmt.setDouble(1, Double.parseDouble(cpf));
			stmt.setInt(2, colaborador.getEmpresa().getId());
			rs = stmt.executeQuery();

			while (rs.next()) {
				importer.atualizaDadosColaborador(0, rs);
			}
		}
	}

	/**
	 * Grava solicitações pendentes no SM
	 * 
	 * @param solicitacoes
	 *           (já deve vir com número sequencial)
	 * @throws SQLException
	 */
	public void exportaSolicitacoesPendentes(List<Solicitacao> solicitacoes) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		conn = connectSqlServer.getConnection();
		for (Iterator<Solicitacao> iterator = solicitacoes.iterator(); iterator.hasNext();) {
			Solicitacao solicitacao = (Solicitacao) iterator.next();
			for (ItemSolicitacao itemSolicitacao : solicitacao.getItensSolicitacao()) {
				if (itemSolicitacao.getSitItm().equals(Situacao.PP)) {
					try {
						stmt = conn
								.prepareStatement("insert into USU_T096AUTEPI "
										+ "(usu_numEmp, usu_tipCol, usu_numCad, usu_codEpi, usu_datEnt, "
										+ "usu_datVen, usu_autEpi, usu_usuAut, usu_numSeq, usu_mtvSol) values (?,?,?,?,?,?,?,?,?,?)");
						stmt.setInt(1, solicitacao.getColaborador().getEmpresa().getId());
						stmt.setInt(2, 1);
						stmt.setLong(3, solicitacao.getColaborador().getNumCad());
						stmt.setInt(4, itemSolicitacao.getId().getUniforme().getId());
						stmt.setDate(5, (java.sql.Date) solicitacao.getDatEnt());
						stmt.setDate(6, (java.sql.Date) itemSolicitacao.getDatVen());
						stmt.setString(7, "N");
						stmt.setInt(8, 0);
						stmt.setInt(9, solicitacao.getNumSeq());
						stmt.setString(10, itemSolicitacao.getMtvSol());
						stmt.executeUpdate();
						conn.commit();
					} catch (Exception e) {
						conn.rollback();
						e.printStackTrace();
						logger.error("Erro tentando gravar item pendente na tabela USU_T096AUTEPI. Detalhes: "
								+ e.getMessage());
					}
				}
			}
		}
	}

	public void notificaSolicitacaoNovosColaboradores(String path) throws Exception {
		List<ItemSolicitacao> solicitacoes = itemSolicitacaoDAO.listPendentesAdmissao();
		if (!solicitacoes.isEmpty()) {
			util.geraRelatorioArquivo(solicitacoes, path + "//AvisoImplantacoesUniforme.jasper",
					path + "//Implantacao.pdf", new HashMap<String, String>());
			List<Usuario> receptoresEmail = usuarioDAO.listReceptoresEmail("OAL");
			if (receptoresEmail != null)
				util.mandaEmail("weboper@grupozanardo.com.br", receptoresEmail,
						"[webOper] Novas solicitacoes de implantacao",
						"Em anexo as solicitacoes de implantacao ainda nao processadas.", path
								+ "//Implantacao.pdf");
			else
				logger.error("NENHUM USUÁRIO CADASTRADO COM TIPO 'OAL' - Operador de Almoxarifado");
		}
	}

	public void geraRelatorioPendencias(List<ItemSolicitacao> itens, String path) throws JRException {
		String texto = "";
		texto = texto + "Em anexo solicitação pendentes de envio \n";
		Map<String, String> parametros = new HashMap<String, String>();
		parametros.put("titulo", "Solicitações com Pendência de Envio");
		util.geraRelatorioArquivo(itens, path + "//AvisoPendencias.jasper", path
				+ "//PendenciasEnvio.pdf", parametros);
		List<Usuario> destinatarios = usuarioDAO.listReceptoresEmail("OAL");
		destinatarios.addAll(usuarioDAO.listReceptoresEmail("AAL"));
	}

	/**
	 * Atualiza o estoque e verifica se algum item está como NA - Não atendido por falta de estoque e ainda há estoque disponível, alterando a situação
	 * para SL - Solicitado Liberado
	 * 
	 * @throws Exception
	 */
	public void verificaEstoque() throws Exception {
		// atualiza estoque
		importer.importaEstoques();
		// lista solicitações não atendidas
		List<Situacao> situacoes = new ArrayList<Situacao>();
		situacoes.add(Situacao.NA);
		situacoes.add(Situacao.SL);
		situacoes.add(Situacao.SAA);
		situacoes.add(Situacao.LB);
		situacoes.add(Situacao.PL);
		// configura pra ordenar pelo nro da solicitação, para pegar sempre a
		// que foi lançada primeiro
		List<Order> ordens = new ArrayList<Order>();
		ordens.add(Order.asc("id"));
		List<Solicitacao> solicitacoes = solicitacaoDAO.listByPrevDate(situacoes, 1, ordens);
		Date dataAtual = Calendar.getInstance().getTime();
		boolean resultado = false;
		for (Solicitacao solicitacao : solicitacoes) {
			for (ItemSolicitacao item : solicitacao.getItensSolicitacao()) {
				// se existir estoque para o item, então atualiza a situação
				// para liberado
				if ((!item.getSitItm().equals(Situacao.EN)) && (!item.getSitItm().equals(Situacao.EAA))) {
					resultado = util.existeEstoque(item.getUniforme(), item, true, true);
					if (resultado == true) {// tem estoque
						if (item.getSitItm().equals(Situacao.NA)) {// e está não
							// atendido
							if (solicitacao.getColaborador().getAfastamento().getId() == 1000)
								item.setSitItm(Situacao.SAA);
							else
								item.setSitItm(Situacao.SL);
						}
					} else {// não tem estoque
						if ((!item.getSitItm().equals(Situacao.EN))// SE NÃO
								// TIVER
								// SIDO ENVIADO
								// AINDA, MUDA
								// PARA NÃO
								// ATENDIDO
								&& ((!item.getSitItm().equals(Situacao.EAA)))) {
							item.setSitItm(Situacao.NA);
						}
					}
					item.setDatAtu(dataAtual);
					itemSolicitacaoDAO.update(item);
				}
			}
		}
	}

	public ColaboradorDAO getColaboradorDAO() {
		return colaboradorDAO;
	}

	public void setColaboradorDAO(ColaboradorDAO colaboradorDAO) {
		this.colaboradorDAO = colaboradorDAO;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
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

	public Importer getImporter() {
		return importer;
	}

	public void setImporter(Importer importer) {
		this.importer = importer;
	}

	/**
	 * Gera o relatório por usuário e envia apenas as liberações pertinentes a ele, para aos administradores de almoxarifado e para os operadores de
	 * almoxarifado
	 * 
	 * @param path
	 * @throws Exception
	 */
	public List<ItemSolicitacao> geraRelatorioLiberacoes(String path, boolean mandaEmail)
			throws Exception {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		if (mandaEmail) {
			usuarios = usuarioDAO.listReceptoresEmail(TipoUsuario.AAU.name());
			usuarios.addAll(usuarioDAO.listReceptoresEmail(TipoUsuario.AAL.name()));
			usuarios.addAll(usuarioDAO.listReceptoresEmail(TipoUsuario.OAL.name()));
		} else {
			Usuario usuario = usuarioDAO.loadById(((Usuario) util.recuperaSessao("Usuario"))
					.getId());
			usuarios.add(usuario);
		}
		List<ItemSolicitacao> itensBloqueados = new ArrayList<ItemSolicitacao>();
		for (Usuario usuario : usuarios) {
			List<Order> ordens = new ArrayList<Order>();
			List<Situacao> situacoes = new ArrayList<Situacao>();
			situacoes.add(Situacao.SP);
			itensBloqueados = itemSolicitacaoDAO.listByPrevDate(situacoes, 1, ordens, usuario);
			if (mandaEmail)
				if (!itensBloqueados.isEmpty()) {
					notificaSolicitacao(itensBloqueados, usuario, path,
							"Em anexo, solicitacoes que necessitam liberacao.", "Solicitacoes a liberar",
							"//Solicitacoes_liberar.pdf");
				}
		}
		return itensBloqueados;
	}

	/**
	 * Gera e envia relatório de itens com falta de estoque
	 * 
	 * @param path
	 * @throws Exception
	 */
	public List<ItemSolicitacao> geraRelatorioNaoAtendidos(String path) throws Exception {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios = usuarioDAO.listReceptoresEmail(TipoUsuario.AAL.name());
		usuarios.addAll(usuarioDAO.listReceptoresEmail(TipoUsuario.OAL.name()));
		List<ItemSolicitacao> itensNaoAtendidos = itemSolicitacaoDAO.listNaoAtendidos();
		return itensNaoAtendidos;
	}

	public int atualizaSituacoes() throws Exception {
		int result = 0;
		try {
			List<Situacao> situacoes = new ArrayList<Situacao>();
			situacoes.add(Situacao.PL);
			situacoes.add(Situacao.SL);
			situacoes.add(Situacao.LB);
			situacoes.add(Situacao.EAA);
			situacoes.add(Situacao.SAA);
			situacoes.add(Situacao.EN);
			List<Integer> motivos = new ArrayList<Integer>();
			motivos.add(1);
			motivos.add(2);
			motivos.add(3);
			List<ItemSolicitacao> itens = itemSolicitacaoDAO.listBySituacao(situacoes, motivos, false);
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			conn = connectSqlServer.getConnection();

			for (ItemSolicitacao itemSolicitacao : itens) {
				if (itemSolicitacao.getSolicitacao().getColaborador().getNumCad() > 0) {
					String sql = "select * from R096die where numCad=? and datEnt=? and codEpi=?";
					stmt = conn.prepareStatement(sql);
					stmt.setLong(1, itemSolicitacao.getSolicitacao().getColaborador().getNumCad());
					stmt.setDate(2, (java.sql.Date) itemSolicitacao.getSolicitacao().getDatEnt());
					stmt.setInt(3, itemSolicitacao.getUniforme().getId());
					rs = stmt.executeQuery();
					if (!rs.next()) {
						itemSolicitacao.setSitItm(Situacao.SL);
						itemSolicitacaoDAO.update(itemSolicitacao);
						result++;
					} else {
						Calendar cal = Calendar.getInstance();
						cal.set(2000, 12, 31);
						Date data = rs.getDate("usu_datEnv");
						if (data != null) {
							if (data.compareTo(cal.getTime()) > 0) {
								itemSolicitacao.setDatEnv(data);
								itemSolicitacao.setSitItm(Situacao.EN);
								itemSolicitacaoDAO.update(itemSolicitacao);
								result++;
							} else {
								itemSolicitacao.setDatEnv(null);
								itemSolicitacao.setSitItm(Situacao.PL);
								itemSolicitacaoDAO.update(itemSolicitacao);
								result++;
							}
						} else {
							if (itemSolicitacao.getDatEnv() != null) {
								itemSolicitacao.setSitItm(Situacao.EN);
								sql = "update R096Die set usu_datEnv=? where numCad=? and datEnt=? and codEpi=?";
								stmt = conn.prepareStatement(sql);
								stmt.setDate(1, (java.sql.Date) itemSolicitacao.getDatEnv());
								stmt.setLong(2, itemSolicitacao.getSolicitacao().getColaborador()
										.getNumCad());
								stmt.setDate(3, (java.sql.Date) itemSolicitacao.getSolicitacao()
										.getDatEnt());
								stmt.setInt(4, itemSolicitacao.getUniforme().getId());
								stmt.executeUpdate();
								try {
									conn.commit();
									itemSolicitacaoDAO.update(itemSolicitacao);
								} catch (Exception e) {
									logger.error("Erro gravando data de envio do item no SM");
									conn.rollback();
								}
							}
						}
						if (itemSolicitacao.getSolicitacao().getNumSeq() == 0) {
							itemSolicitacao.getSolicitacao().setNumSeq(rs.getInt("USU_NumSeq"));
							itemSolicitacao.setDatExp(rs.getDate("USU_datImp"));
							itemSolicitacao.setSitItm(Situacao.PL);
							itemSolicitacaoDAO.update(itemSolicitacao);
							solicitacaoDAO.update(itemSolicitacao.getSolicitacao());
							result++;
						} else {
							if ((itemSolicitacao.getSitItm().equals(Situacao.SL))
									|| (itemSolicitacao.getSitItm().equals(Situacao.SAA))) {
								itemSolicitacao.setSitItm(Situacao.PL);
								itemSolicitacao.setDatExp(rs.getDate("USU_datImp"));
								itemSolicitacaoDAO.update(itemSolicitacao);
								result++;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}
}
