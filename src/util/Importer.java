package util;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.apache.poi.hslf.record.Sound;
import org.hibernate.NonUniqueResultException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import bean.Afastamento;
import bean.Bairro;
import bean.Banco;
import bean.Caracteristica;
import bean.Cargo;
import bean.Centro;
import bean.Centro.Situacao;
import bean.Cidade;
import bean.ClasseEscalaRonda;
import bean.Cliente;
import bean.Colaborador;
import bean.Colaborador.EstadoCivil;
import bean.Colaborador.TipoSalario;
import bean.Colaborador.TipoSanguineo;
import bean.CondicaoPagamento;
import bean.Deficiencia;
import bean.Empresa;
import bean.EscalaHorarioRondaMZ;
import bean.EscalaRonda;
import bean.EscalaVT;
import bean.Estoque;
import bean.Fornecedor;
import bean.GrauInstrucao;
import bean.Hierarquia;
import bean.HorarioEscalaRonda;
import bean.HorarioRonda;
import bean.ItemSolicitacao;
import bean.Local;
import bean.Motivo;
import bean.Nacionalidade;
import bean.NaturezaDespesa;
import bean.PostoTrabalho;
import bean.Produto;
import bean.Produto.TipoProduto;
import bean.SequenciaRegistro;
import bean.Sindicato;
import bean.Solicitacao;
import bean.TipoEPI;
import bean.TurmaHorarioRonda;
import bean.Uniforme;
import bean.Usuario;
import bean.Usuario.TipoUsuario;
import bean.Vinculo;
import dao.AfastamentoDAO;
import dao.BairroDAO;
import dao.BancoDAO;
import dao.CargoDAO;
import dao.CentroDAO;
import dao.CidadeDAO;
import dao.ClasseEscalaRondaDAO;
import dao.ClienteDAO;
import dao.ColaboradorDAO;
import dao.CondicaoPagamentoDAO;
import dao.DeficienciaDAO;
import dao.EmpresaDAO;
import dao.EscalaHorarioRondaMZDAO;
import dao.EscalaRondaDAO;
import dao.EscalaVTDAO;
import dao.EstoqueDAO;
import dao.FornecedorDAO;
import dao.GrauInstrucaoDAO;
import dao.HorarioEscalaRondaDAO;
import dao.HorarioRondaDAO;
import dao.ItemSolicitacaoDAO;
import dao.LocalDAO;
import dao.MotivoDAO;
import dao.NacionalidadeDAO;
import dao.NaturezaDespesaDAO;
import dao.PostoTrabalhoDAO;
import dao.ProdutoDAO;
import dao.SequenciaRegistroDAO;
import dao.SindicatoDAO;
import dao.SolicitacaoDAO;
import dao.TipoEPIDAO;
import dao.TurmaHorarioRondaDAO;
import dao.UniformeDAO;
import dao.UsuarioDAO;
import dao.VinculoDAO;
import dao.hibernate.HClasseEscalaRondaDAO;

@Service("Importer")
@Transactional
public class Importer {
	static ConnectSqlServer				connectSqlServer			= ConnectSqlServer.getInstance();
	static ConnectSqlServerSapiens	connectSqlServerSapiens	= ConnectSqlServerSapiens.getInstance();
	static Logger							logger						= Logger.getLogger(Importer.class);

	/*------ - - - - colocar os dao.hibernate aqui*/
	@Autowired
	@Qualifier("HBairroDAO")
	private BairroDAO		bairroDAO;
	
	@Autowired
	@Qualifier("HClasseEscalaRondaDAO")
	private ClasseEscalaRondaDAO		classeEscalaRondaDAO;

	@Autowired
	@Qualifier("HHorarioRondaDAO")
	private HorarioRondaDAO				horarioRondaDAO;

	@Autowired
	@Qualifier("HEscalaHorarioRondaMZDAO")
	private EscalaHorarioRondaMZDAO	escalaHorarioRondaMZDAO;

	@Autowired
	@Qualifier("HTurmaHorarioRondaDAO")
	private TurmaHorarioRondaDAO		turmaHorarioRondaDAO;

	@Autowired
	@Qualifier("HHorarioEscalaRondaDAO")
	private HorarioEscalaRondaDAO		horarioEscalaRondaDao;

	@Autowired
	@Qualifier("HSequenciaRegistroDAO")
	private SequenciaRegistroDAO		sequenciaRegistroDAO;
	
	@Autowired
	@Qualifier("HSindicatoDAO")
	private SindicatoDAO		sindicatoDAO;
	
	@Autowired
	@Qualifier("HDeficienciaDAO")
	private DeficienciaDAO		deficienciaDAO;

	@Autowired
	@Qualifier("HPostoTrabalhoDAO")
	private PostoTrabalhoDAO		postoTrabalhoDAO;
	
	@Autowired
	@Qualifier("HNaturezaDespesaDAO")
	private NaturezaDespesaDAO		naturezaDespesaDAO;
	
	
	@Autowired
	@Qualifier("HVinculoDAO")
	private VinculoDAO		vinculoDAO;

	@Autowired
	@Qualifier("HEscalaVTDAO")
	private EscalaVTDAO		escalaVTDAO;
	/*---------------------------------------------------*/
	
	@Autowired
	@Qualifier("HColaboradorDAO")
	private ColaboradorDAO				colaboradorDAO;
	@Autowired
	@Qualifier("HUsuarioDAO")
	private UsuarioDAO					usuarioDAO;
	@Autowired
	@Qualifier("HItemSolicitacaoDAO")
	private ItemSolicitacaoDAO			itemSolicitacaoDAO;
	@Autowired
	@Qualifier("HAfastamentoDAO")
	private AfastamentoDAO				afastamentoDAO;
	@Autowired
	@Qualifier("HBancoDAO")
	private BancoDAO						bancoDAO;
	@Autowired
	@Qualifier("HCargoDAO")
	private CargoDAO						cargoDAO;
	@Autowired
	@Qualifier("HCentroDAO")
	private CentroDAO						centroDAO;
	@Autowired
	@Qualifier("HCidadeDAO")
	private CidadeDAO						cidadeDAO;
	@Autowired
	@Qualifier("HClienteDAO")
	private ClienteDAO					clienteDAO;
	@Autowired
	@Qualifier("HCondicaoPagamentoDAO")
	private CondicaoPagamentoDAO		condicaoPagamentoDAO;
	@Autowired
	@Qualifier("HEmpresaDAO")
	private EmpresaDAO					empresaDAO;
	@Autowired
	@Qualifier("HSolicitacaoDAO")
	private SolicitacaoDAO				solicitacaoDAO;
	@Autowired
	@Qualifier("HEscalaRondaDAO")
	private EscalaRondaDAO				escalaRondaDAO;
	@Autowired
	@Qualifier("HEstoqueDAO")
	private EstoqueDAO					estoqueDAO;
	@Autowired
	@Qualifier("HFornecedorDAO")
	private FornecedorDAO				fornecedorDAO;
	@Autowired
	@Qualifier("HGrauInstrucaoDAO")
	private GrauInstrucaoDAO			grauInstrucaoDAO;
	@Autowired
	@Qualifier("HLocalDAO")
	private LocalDAO						localDAO;
	@Autowired
	@Qualifier("HMotivoDAO")
	private MotivoDAO						motivoDAO;
	@Autowired
	@Qualifier("HProdutoDAO")
	private ProdutoDAO					produtoDAO;
	@Autowired
	@Qualifier("HTipoEPIDAO")
	private TipoEPIDAO					tipoEPIDAO;
	@Autowired
	@Qualifier("HUniformeDAO")
	private UniformeDAO					uniformeDAO;
	@Autowired
	private Util							util;
	@Autowired
	@Qualifier("HNacionalidadeDAO")
	private NacionalidadeDAO			nacionalidadeDAO;

	public Importer() {
		super();
	}

	/**
	 * Atualiza os colaboradores alterados, inseridos, exclu�dos do Vetorh H� uma trigger na tabela R034Fun que registra essas a��es na tabela
	 * Usu_TSincWeb Essa fun��o faz uma varredura na tabela Usu_TSincWeb e atualiza na base da Web
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean atualizaColaboradores() throws Exception {

		boolean retorno = false;
		Connection connection = connectSqlServer.getConnection();
		try {
			util.normalizaCPF();
			removeNulos();
			importaCidades();
			importaEscalasRonda();
			importaCargos();
			importaLocais();
			importaClientes();
			importaBancos();
			processaDelete(connection);
			processaInsert(connection);
			processaUpdate(connection);
		} catch (SQLException e) {
			registraLogError("Erro de banco de dados:" + e.getSQLState() + " : " + e.getMessage());
			System.err.println("Erro de banco de dados:" + e.getSQLState() + " : " + e.getMessage());
			throw e;
		} catch (NonUniqueResultException e) {
			registraLogError("Erro :" + e.getMessage());
			System.err.println("Erro :" + e.getMessage());
			throw e;
		} catch (ConstraintViolationException e) {
			registraLogError("Erro :" + e.getCause().getMessage());
			System.err.println(e.getCause().getMessage());
			throw e;
		} catch (Exception e) {
			registraLogError("Erro :" + e.getMessage());
			System.err.println("Erro :" + e.getMessage());
			throw e;
		} finally {
			if (!connection.isClosed()) {
				connection.close();
			}
		}
		return retorno;
	}

	public void removeNulos() {
		String sql = "delete Usu_TSincWeb where USU_PKORI is null";
		Connection connection = connectSqlServer.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.err.println("Erro ao tentar efetuar rollback da transação.");
				System.err.println("Erro de banco de dados: " + e1.getSQLState());
				System.err.println(e1.getMessage());
			}
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (connection != null && !connection.isClosed())
					connection.close();
			} catch (SQLException e) {
				System.err
						.println("Erro de banco de dados:" + e.getSQLState() + " : " + e.getMessage());
			}
		}
	}

	/**
	 * Importa os colaboradores do Vetorh para o webOper Tabela no Vetorh = R034FUN não importa quando for CPF de Marcos Zanardo e da empresa Z & R
	 * 
	 * @return true se importou todos os registros
	 * @throws Exception
	 */
	public boolean atualizaColaboradores(Date datIni, Date datFim, int numEmp) throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		int idColaborador = 0;
		boolean retorno = false;
		long numCad = 0;
		try {
			conn = connectSqlServer.getConnection();
			String sql = "select f.numEmp, nomFun, datAdm, f.sitAfa, f.estCar, f.codCar, f.codFil,	"
					+ "f.tabOrg, f.numLoc, f.codCcu, numCpf, f.codEsc, f.numCad, Usu_DesLocPai, datInc, "
					+ "horInc, datNas, tipSex, numCtp, serCtp, numPis, ct.conBan, ct.codBan, ct.codAge, "
					+ "f.estCiv, graIns, usu_numDRT, usu_numDip, usu_orgExD, usu_crtVig, numCid, emiCid, "
					+ "numCnh, catCnh, numEle, cciNas, endRua, c.codCid, endCep, c.numTel, nomBai, usu_funCol, ct.digBan, "
					+ "(select digAge from R012Age where codBan=ct.codBan and codAge=ct.codAge) as digAge , c.endNum, c.endCpl, "
					+ "fl.razSoc, fl.tipFil, fl.numCgc, fl.nomFil, fl.codCid, car.titRed, es.nomEsc, lo.nomLoc, lo.datCri, lo.datExt, cc.nomCcu, s.desSit "
					+ "from R034FUN f "
					+ "left join R030Fil fl on (f.numemp=fl.numemp and f.codfil=fl.codfil) "
					+ "left join R034CPL c on (f.numEmp=c.numEmp and f.tipCol=c.tipCol and f.numCad=c.numCad) "
					+ "left join R074Bai b on (c.codCid=b.codCid and c.codBai=b.codBai) "
					+ "left join R024Car car on (f.estCar = car.estCar and f.codCar=car.codCar) "
					+ "left join R006Esc es on (f.codEsc = es.codEsc) "
					+ "left join R016Orn lo on (f.tabOrg = lo.tabOrg and f.numLoc=lo.numLoc) "
					+ "left join R018Ccu cc on (f.numEmp = cc.numEmp and f.codCcu=cc.codCcu) "
					+ "left join R034Con ct on (f.numEmp=ct.numEmp and f.tipCol=ct.tipCol and f.numCad=ct.numCad) "
					+ "left join R010Sit s on (f.sitAfa = s.codSit) " + "where f.numEmp = " + numEmp
					+ " and f.sitAfa <>7 and f.tipCol = 1 and (ct.datAlt = (SELECT MAX (DATALT) FROM R034CON TABELA002 "
					+ "WHERE (TABELA002.NUMEMP = ct.NUMEMP) AND (TABELA002.TIPCOL = ct.TIPCOL) AND (TABELA002.NUMCAD = ct.NUMCAD) "
					+ "AND (TABELA002.DATALT <= (select sysdate from dual)))) ";
			if (datIni != null) {
				sql = sql
						+ " and ((f.datAdm>=? and f.datAdm<=?) or (f.usu_datAtu>=? and f.usu_DatAtu<=?)) and f.numEmp=?  order by f.numEmp";
				stmt = conn.prepareStatement(sql);
				stmt.setDate(1, util.convDateParaSql(datIni));
				stmt.setDate(2, util.convDateParaSql(datFim));
				stmt.setDate(3, util.convDateParaSql(datIni));
				stmt.setDate(4, util.convDateParaSql(datFim));
				stmt.setInt(5, numEmp);
			} else {
				sql = sql + " order by f.numEmp";
				stmt = conn.prepareStatement(sql);
			}

			rs = stmt.executeQuery();

			while (rs.next()) {
				/** fazer rotina de atualiza��o dos dados do colaborador na web **/
				numCad = rs.getLong("numCad");
				idColaborador = colaboradorDAO.loadIdByNumCad(numCad, numEmp);
				long numCpf = 0;
				if (idColaborador == 0) {
					numCpf = rs.getLong("numCpf");
					try {
						idColaborador = colaboradorDAO.loadByCPF(String.valueOf(numCpf)).getId();
					} catch (NullPointerException e) {
						idColaborador = 0;
					}
				}
				if (idColaborador == 0) {
					Colaborador colaborador = new Colaborador();
					numCpf = rs.getLong("numCpf");
					colaborador = colaboradorDAO.loadByCPF(String.valueOf(numCpf));
					if (colaborador != null)
						idColaborador = colaborador.getId();
				}
				if (idColaborador > 0) {
					/** atualiza dados do colaborador na web **/
					try {
						atualizaDadosColaborador(idColaborador, rs);
					} catch (ConstraintViolationException e) {
						System.err.println("Não foi possível atualizar colaborador " + idColaborador
								+ ". Causa: " + e.getCause().getMessage());
						retorno = false;
						throw new Exception("Não foi possível atualizar colaborador " + idColaborador
								+ ". Causa: " + e.getCause().getMessage());
					} catch (Exception e) {
						System.err.println("Não foi possível atualizar colaborador " + idColaborador);
						retorno = false;
						throw e;
					}
				} else {
					/**
					 * insere dados do colaborador novo na web, sabendo que não encontrou colaborador pendente com o cpf dado.
					 */
					try {
						insereDadosColaborador(rs);
					} catch (BatchUpdateException bue) {
						System.err.println(
								"Não foi possível inserir colaborador " + rs.getString("nomFun"));
						continue;
					} catch (Exception e) {
						System.err.println(
								"Não foi possível inserir colaborador " + rs.getString("nomFun"));
						retorno = false;
					}
				}
			}
			connectSqlServer.closeConnection(conn, stmt, rs);

			logger.info("[R034FUN] Colaboradores importados/atualizados - " + new java.util.Date());
			return retorno;
		} catch (NonUniqueResultException e) {
			retorno = false;
			throw new Exception("Encontrado mais de um colaborador com o cadastro " + numCad);
		} catch (Exception e) {
			logger.fatal("COLABORADORES NãO FORAM IMPORTADOS. Causa: " + e.getMessage());
			Usuario usuario = new Usuario();
			usuario.setTipUsu(TipoUsuario.AAD);
			usuario.setAfastamento(new Afastamento(1));
			List<Usuario> usuAdmissao = usuarioDAO.listByExample(usuario);
			Util util = new Util();
			List<Usuario> usu = new ArrayList<Usuario>();
			usu.add(usuarioDAO.loadById(1036));
			usu.addAll(usuAdmissao);
			util.mandaEmail("weboper@grupozanardo.com.br", usu, "[webOper] " + "ERRO", e.getMessage(),
					"");
			e.printStackTrace();
			throw e;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
	}

	/** Atualiza os dados do colaborador na web com base em um registro do Vetorh **/
	public boolean atualizaDadosColaborador(int idColaborador, ResultSet rs) throws NumberFormatException, Exception {
		Colaborador colaborador = new Colaborador();
		boolean retorno = true;
		logger.info("Tentando atualizar colaborador " + rs.getInt("numCad") + " - "
				+ rs.getString("nomFun"));
		try {
			if (idColaborador > 0) {
				colaborador = colaboradorDAO.loadById(idColaborador);
			}

			colaborador.setDatAdm(rs.getDate("datAdm"));
			colaborador.setEmpresa(new Empresa(rs.getInt("numEmp")));
			colaborador.setNomFun(rs.getString("nomFun"));
			colaborador.setNumCpf(rs.getString("numCpf"));
			colaborador.setDatNas(rs.getDate("datNas"));
			colaborador.setSexo(Sexo.valueOf(rs.getString("tipSex")));
			colaborador.setNumCtp(rs.getLong("numCtp"));
			colaborador.setSerCtp(rs.getString("serCtp"));
			colaborador.setNumPis(rs.getLong("numPis"));
			colaborador.setConBan(rs.getLong("conBan"));
			if (rs.getInt("codBan") > 0)
				colaborador.setBanco(new Banco(rs.getInt("codBan")));
			else
				colaborador.setBanco(new Banco(9999));
			colaborador.setCodAge(rs.getString("codAge"));
			colaborador.setDigAge(rs.getString("digAge"));
			colaborador.setDigCon(rs.getString("digBan"));

			EstadoCivil estadoCivil = Colaborador.EstadoCivil.loadById(rs.getInt("estCiv"));
			if (estadoCivil != null)
				colaborador.setEstCiv(estadoCivil);
			else
				colaborador.setEstCiv(Colaborador.EstadoCivil.Outros);
			colaborador.setGrauInstrucao(new GrauInstrucao(rs.getShort("graIns")));

			// para vigilantes
			colaborador.setNumDRT(rs.getString("usu_numDRT"));
			colaborador.setNumDip(rs.getString("usu_numDip"));
			colaborador.setOrgDip(rs.getString("usu_orgExD"));
			colaborador.setDatFor(rs.getDate("usu_crtVig"));

			// ficha complementar
			colaborador.setNumCid(rs.getString("numCid"));
			colaborador.setEmiCid(rs.getString("emiCid"));
			colaborador.setNumCnh(rs.getString("numCnh"));
			colaborador.setCatCnh(rs.getString("catCnh"));
			colaborador.setNumEle(rs.getString("numEle"));
			colaborador.setCciNas(new Cidade(rs.getInt("cciNas")));
			colaborador.setEndRua(rs.getString("endRua"));
			colaborador.setCidEnd(new Cidade(rs.getInt("codCid")));
			colaborador.setCep(rs.getString("endCep"));
			colaborador.setNumTel(rs.getString("numTel"));
			colaborador.setTipSal(colaborador.getTipSal());
			colaborador.setTipSan(colaborador.getTipSan());

			colaborador.setNumCad(rs.getLong("numCad"));
			colaborador.setLocPai(rs.getString("Usu_DesLocPai"));
			colaborador.setDatInc(rs.getDate("datInc"));
			colaborador.setHorInc(rs.getInt("horInc"));
			colaborador.setEndNum(rs.getString("endNum"));// nro casa
			colaborador.setEndCpl(rs.getString("endCpl"));// complemento
			colaborador.setFuncao(rs.getInt("usu_funcol"));// fun��o

			Cliente cliente = new Cliente();
			cliente.setId(new Cliente.Id(rs.getInt("codFil"), rs.getInt("numEmp")));
			cliente.setRazSoc(rs.getString("razSoc"));
			cliente.setTipFil(rs.getString("tipFil"));
			cliente.setNumCgc(rs.getString("numCgc"));
			cliente.setApeFil(rs.getString("nomFil"));
			cliente.setCidade(new Cidade(rs.getInt("codCid")));

			/*
			EscalaRonda escalaRonda = new EscalaRonda(rs.getInt("codEsc"));
			escalaRonda.setId(rs.getInt("codEsc"));
			escalaRonda.setNomEsc(rs.getString("nomEsc"));
			colaborador.setEscalaRonda(escalaRonda);
         */
			
			
			Cargo cargo = new Cargo();
			cargo.setId(new Cargo.Id(rs.getInt("estCar"), rs.getString("codCar")));
			cargo.setTitCar(rs.getString("titRed"));
			cargo.setCodCar(rs.getString("codCar"));
			colaborador.setCargo(cargo);

			/** aqui adicionar uma busca na tabela R016HRS e R016HRO para pegar o c�digo antigo e o novo **/
			Connection conn = connectSqlServer.getConnection();
			String sql = "select * from R016Hrs where subori in (select codloc from r016hie where numloc=?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, rs.getInt("numLoc"));
			ResultSet rsEst = stmt.executeQuery();
			System.out.println(rs.getInt("numLoc"));
			String novoCodLoc = "";
			while (rsEst.next()) {
				novoCodLoc = rsEst.getString("subDes");
			}
			if (novoCodLoc == "") {
				
				sql = "select * from R016Hro where locOri in (select codloc from r016hie where numloc=?)";
				stmt.setInt(1, rs.getInt("numLoc"));
				rsEst = stmt.executeQuery();
				while (rsEst.next()) {
					novoCodLoc = rsEst.getString("locDes");
				}
			}

			int novoNumLoc = 0;
			if (novoCodLoc != "") {
				sql = "select * from r016hie where codLoc=?)";
				stmt.setString(1, novoCodLoc);
				rsEst = stmt.executeQuery();
				while (rsEst.next()) {
					novoNumLoc = rsEst.getInt("numLoc");
				}
			}

			stmt.close();
			rsEst.close();
			conn.close();

			Local local;

			if (novoNumLoc > 0) {
				local = new Local(rs.getInt("tabOrg"), novoNumLoc);
			} else {
				local = new Local(rs.getInt("tabOrg"), rs.getInt("numLoc"));
			}

			local.setDatCri(rs.getDate("datCri"));
			local.setDatExt(rs.getDate("datExt"));
			local.setNomLoc(rs.getString("nomLoc"));
			colaborador.setLocal(local);

			if (rs.getString("codCcu").equals(" ")) {
				throw new Exception("Centro de custo do colaborador (" + rs.getInt("numCad") + ") "
						+ rs.getString("nomFun") + " não informado.");
			}
			int codCcu = Integer.valueOf(rs.getString("codCcu"));
			Centro centro = new Centro(codCcu);
			centro.setNomCcu(rs.getString("nomCcu"));
			centro.setSitCcu(Situacao.A);
			colaborador.setCentro(centro);

			Afastamento afastamento = new Afastamento(rs.getInt("sitAfa"));
			afastamento.setDesSit(rs.getString("desSit"));
			colaborador.setAfastamento(afastamento);

			colaboradorDAO.update(colaborador);

			logger.info("Colaborador atualizado: " + colaborador.getNumCad() + " - "
					+ colaborador.getNomFun());

		} catch (Exception e) {
			retorno = false;
			throw e;
		}
		return retorno;
	}

	/**
	 * Atualiza as distribui��es que tiveram altera��o no banco de dados do SM para o webOper
	 */
	public void atualizaDistribuicao() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			conn = connectSqlServer.getConnection();
			String sql = "select numEmp, numCad, codEpi, datEnt, usu_NumSeq, usu_entAut, usu_datAtu, usu_datEnv, datDev from R096Die "
					+ "where numCad in(select numcad from r034fun where sitafa<>7) and usu_datAtu>=getDate()-10";

			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				ItemSolicitacao itemSolicitacao = itemSolicitacaoDAO.loadByChaveVetorh(rs.getInt(1),
						rs.getInt(2), rs.getInt(3), rs.getDate(4));
				if (itemSolicitacao == null) {
					logger.error("Item não encontrado no webOper. não foi importado. Número sequencial: "	+ rs.getInt(5));
				} else {
					itemSolicitacao.setDatAtu(rs.getDate(7));
					itemSolicitacao.setEntAut(rs.getString(6));
					itemSolicitacao.setDatEnv(rs.getDate(8));
					itemSolicitacao.setDatDev(rs.getDate("datDev"));
					Calendar calendar = Calendar.getInstance();
					calendar.set(1900, 12, 31);
					/**
					 * Verifica se a data de envio trazida do SM � maior que 31/12/1900 se for, ent�o atualiza a situa��o do item para EN = enviado
					 */
					if (rs.getDate(8) != null) {
						if (rs.getDate(8).compareTo(calendar.getTime()) > 0) {
							itemSolicitacao.setSitItm(bean.ItemSolicitacao.Situacao.EN);
						} else {
							if (itemSolicitacao.getSitItm().equals(bean.ItemSolicitacao.Situacao.EN)) {
								itemSolicitacao.setSitItm(bean.ItemSolicitacao.Situacao.PL);
							}
						}
					}
					itemSolicitacaoDAO.update(itemSolicitacao);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
	}

	/** Monta sql select de colaboradores usando como where as chaves passados como String **/
	public static String getSqlSelectColaboradorFromRegistro(String Usu_PkOri) throws SQLException {
		String sql;
		String[] chaves = Importer.desmembraChave(Usu_PkOri);
		/**
		 * sql = "select * from (R034Fun f left join (R034CPL c left join R074BAI b on c.codCid=b.codCid and c.codBai=b.codBai) " + "on
		 * f.numEmp=c.numEmp and f.tipCol=c.tipCol and f.numCad=c.numCad) " + "left join R012Age a on f.codBan=a.codBan and f.codAge=a.codAge left join
		 * R030Fil fl on fl.numemp=f.numemp and fl.codfil=f.codfil where ";
		 **/

		sql = "select f.numEmp, nomFun, datAdm, f.sitAfa, f.estCar, f.codCar, f.codFil,	"
				+ "f.tabOrg, f.numLoc, f.codCcu, numCpf, f.codEsc, f.numCad, Usu_DesLocPai, datInc, "
				+ "horInc, datNas, tipSex, numCtp, serCtp, numPis, f.conBan, f.codBan, f.codAge, "
				+ "f.estCiv, graIns, usu_numDRT, usu_numDip, usu_orgExD, usu_crtVig, numCid, emiCid, "
				+ "numCnh, catCnh, numEle, cciNas, endRua, c.codCid, endCep, c.numTel, nomBai, usu_funCol, f.digBan, "
				+ "(select digAge from R012Age where codBan=f.codBan and codAge=f.codAge) as digAge , c.endNum, c.endCpl, "
				+ "fl.razSoc, fl.tipFil, fl.numCgc, fl.nomFil, fl.codCid, car.titRed, es.nomEsc, lo.nomLoc, lo.datCri, lo.datExt, cc.nomCcu, s.desSit "
				+ "from R034FUN f "
				+ "left join R030Fil fl on (f.numemp=fl.numemp and f.codfil=fl.codfil) "
				+ "left join R034CPL c on (f.numEmp=c.numEmp and f.tipCol=c.tipCol and f.numCad=c.numCad) "
				+ "left join R074Bai b on (c.codCid=b.codCid and c.codBai=b.codBai) "
				+ "left join R024Car car on (f.estCar = car.estCar and f.codCar=car.codCar) "
				+ "left join R006Esc es on (f.codEsc = es.codEsc) "
				+ "left join R016Orn lo on (f.tabOrg = lo.tabOrg and f.numLoc=lo.numLoc)  "
				+ "left join R018Ccu cc on (f.numEmp = cc.numEmp and f.codCcu=cc.codCcu) "
				+ "left join R010Sit s on (f.sitAfa = s.codSit) " + "where ";

		for (int i = 0; i < chaves.length; i++) {
			if (i == 0)
				sql += "f." + chaves[i];
			else
				sql += " and " + "f." + chaves[i];
		}
		return sql;
	}

	/** Monta sql select de filiais/clientes usando como where as chaves passados como String **/
	public static String getSqlSelectFilialFromRegistro(String Usu_PkOri) throws SQLException {
		String sql;
		String[] chaves = Importer.desmembraChave(Usu_PkOri);
		sql = "select * from R030FIL f, R032OEM o where f.codOem=o.codOem and ";
		for (int i = 0; i < chaves.length; i++) {
			if (i == 0)
				sql += "f." + chaves[i].trim();
			else
				sql += " and " + "f." + chaves[i].trim();
		}
		return sql;
	}

	/** Monta sql select de local usando como where as chaves passados como String **/
	public static String getSqlSelectLocalFromRegistro(String Usu_PkOri) throws SQLException {
		String sql;
		String[] chaves = Importer.desmembraChave(Usu_PkOri);
		sql = "select * from R016ORN l, R016HIE h where (l.tabOrg=h.tabOrg and l.numLoc=h.numLoc) and ";
		for (int i = 0; i < chaves.length; i++) {
			if (i == 0)
				sql += "l." + chaves[i];
			else
				sql += " and " + "l." + chaves[i];
		}
		return sql;
	}

	/**
	 * Transforma a string com os campos chaves e seus valores em um array
	 * 
	 * @param chave
	 * @return String[]
	 */
	public static String[] desmembraChave(String chave) {
		String[] arr = chave.split(",");
		return arr;
	}

	/** Exclui o registro da tabela Usu_TSincWeb **/
	public static boolean excluiRegistroUsu_TSincWeb(String pkOri, String tipOpe, String nomTab) {
		String sql = "delete from Usu_TSincWeb where USU_PKORI=? and USU_TIPOPE=? and USU_NomTab=?";
		Connection connection = connectSqlServer.getConnection();
		boolean retorno = false;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, pkOri);
			stmt.setString(2, tipOpe);
			stmt.setString(3, nomTab);
			stmt.executeUpdate();
			connection.commit();
			retorno = true;
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				logger.error("Erro ao tentar efetuar rollback da transação.");
				logger.error("Erro de banco de dados: " + e1.getSQLState());
				logger.error(e1.getMessage());
				e.printStackTrace();
			}
		} finally {
			try {
				if ((stmt != null) && !stmt.isClosed())
					stmt.close();
				if (connection != null && !connection.isClosed())
					connection.close();
			} catch (SQLException e) {
				logger.error("Erro de banco de dados:" + e.getSQLState() + " : " + e.getMessage());
			}
		}
		return retorno;
	}

	/** Exclui o registro da tabela Usu_TSincWeb em casos onde a chave � null **/
	public static boolean excluiRegistroUsu_TSincWebNulo(String tipOpe, String nomTab) {
		String sql = "delete from Usu_TSincWeb where USU_TIPOPE=? and USU_NomTab=? and USU_PKORI is null";
		Connection connection = connectSqlServer.getConnection();
		boolean retorno = false;
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, tipOpe);
			stmt.setString(2, nomTab);
			stmt.executeUpdate();
			connection.commit();
			retorno = true;
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.err.println("Erro ao tentar efetuar rollback da transação.");
				System.err.println("Erro de banco de dados: " + e1.getSQLState());
				System.err.println(e1.getMessage());
			}
		} finally {
			try {
				if ((stmt != null) && !stmt.isClosed())
					stmt.close();
				if (connection != null && !connection.isClosed())
					connection.close();
			} catch (SQLException e) {
				System.err
						.println("Erro de banco de dados:" + e.getSQLState() + " : " + e.getMessage());
			}
		}
		return retorno;
	}

	/**
	 * Gera uma string com o comando sql de select para os registros alterados na @param tabela do sapiens
	 * 
	 * @param chaves
	 * @param tabela
	 * @return String
	 */
	public static String getSqlSelectFromRegistro(String[] chaves, String tabela) {
		String sql = "select distinct * from " + tabela + " where ";
		for (int i = 0; i < chaves.length; i++) {
			if (i == 0)
				sql += chaves[i];
			else
				sql += " and " + chaves[i];
		}
		return sql;
	}

	/**
	 * Importa as situa��es de afastamento do Vetorh para o webOper Tabela no Vetorh = R010Sit
	 * 
	 * @return true se importou todos os registros
	 */
	public boolean importaAfastamentos() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			conn = connectSqlServer.getConnection();
			String sql = "select codSit, desSit from R010Sit";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			List<Afastamento> afastamentos = new LinkedList<Afastamento>();
			while (rs.next()) {
				Afastamento afastamento = new Afastamento();
				afastamento.setId(rs.getInt(1));
				afastamento.setDesSit(rs.getString(2));
				afastamentos.add(afastamento);
			}
			afastamentoDAO.importar(afastamentos);
			logger.info("[R010SIT] Afastamentos importados/atualizados - " + new Date());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("AFASTAMENTOS não FORAM IMPORTADOS.");
			return false;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
	}

	public boolean importaBancos() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		boolean retorno = false;
		try {
			String sql = "select * from Usu_TSincWeb where usu_tipOpe in('I', 'U') and usu_nomtab='R012BAN'";
			conn = connectSqlServer.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			String pkOri = "";
			while (rs.next()) {
				pkOri = rs.getString("usu_pkori");
				String sqlGerado = getSqlSelectFromRegistro(desmembraChave(pkOri), "R012BAN");
				stmt = conn.prepareStatement(sqlGerado);
				ResultSet rsBan = stmt.executeQuery();
				if (rsBan.next()) {
					Banco banco = new Banco();
					banco.setId(rsBan.getInt("codBan"));
					banco.setNomBan(rsBan.getString("nomBan"));
					bancoDAO.saveOrUpdate(banco);
				}
				/** SE IMPORTAR SEM ERROS, PODE EXCLUIR OS REGISTROS DA TABELA AUXILIAR **/
				Importer.excluiRegistroUsu_TSincWeb(rs.getString("usu_pkori"), "I", "R012BAN");
				Importer.excluiRegistroUsu_TSincWeb(rs.getString("usu_pkori"), "U", "R012BAN");
			}
			logger.info("[R012Ban] Bancos importados/atualizados - " + new Date());
			retorno = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("BANCOS não FORAM IMPORTADOS");
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
		return retorno;
	}

	/**
	 * Importa os cargos do Vetorh para o webOper Tabela no Vetorh = R024CAR
	 * 
	 * @return true se importou todos os registros
	 * @throws Exception
	 */
	public boolean importaCargos() throws Exception {
		boolean retorno = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select * from Usu_TSincWeb where usu_tipOpe in('I', 'U') and upper(usu_nomtab)='R024CAR'";
			conn = connectSqlServer.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			String pkOri = "";
			while (rs.next()) {
				pkOri = rs.getString("usu_pkori");
				String sqlGerado = getSqlSelectFromRegistro(desmembraChave(pkOri), "R024CAR");
				stmt = conn.prepareStatement(sqlGerado);
				ResultSet rsCar = stmt.executeQuery();
				if (rsCar.next()) {
					Cargo cargo = new Cargo();
					cargo.setId(new Cargo.Id(rsCar.getInt("estCar"), rsCar.getString("codCar")));
					cargo.setTitCar(rsCar.getString("titCar"));
					cargoDAO.saveOrUpdate(cargo);
				}
				/** SE IMPORTAR SEM ERROS, PODE EXCLUIR OS REGISTROS DA TABELA AUXILIAR **/
				Importer.excluiRegistroUsu_TSincWeb(rs.getString("usu_pkori"), "I", "R024CAR");
				Importer.excluiRegistroUsu_TSincWeb(rs.getString("usu_pkori"), "U", "R024CAR");
			}
			retorno = true;
			logger.info("[R024CAR] Cargos importados/atualizados - " + new Date());
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("CARGOS não FORAM IMPORTADOS.");
			throw e;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
		return retorno;
	}

	public boolean sincronizaCargos() throws Exception {
		boolean retorno = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select * from R024CAR";
			conn = connectSqlServer.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Cargo cargo = new Cargo();
				cargo.setId(new Cargo.Id(rs.getInt("estCar"), rs.getString("codCar")));
				cargo.setTitCar(rs.getString("titCar"));
				cargoDAO.saveOrUpdate(cargo);
			}
			retorno = true;
			logger.info("[R024CAR] Cargos sincronizados - " + new Date());
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("CARGOS não FORAM SINCRONIZADOS.");
			throw e;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
		return retorno;
	}

	/**
	 * Importa os centros de custo do Vetorh para o webOper Tabela no Vetorh = R Como no vetorh os centros s�o separados por empresa, aqui pega
	 * apenas da empresa 1
	 * 
	 * @return true se importou todos os registros
	 */
	public boolean importaCentros() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			Centro centroZero = new Centro();
			centroZero.setId(0);
			centroZero.setNomCcu("NÃO INFORMADO");
			centroZero.setSitCcu(Situacao.A);
			// centroDAO.insert(centroZero);
			conn = connectSqlServer.getConnection();
			String sql = "select codCcu, nomCcu from R018CCU where numEmp=1";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			List<Centro> centros = new LinkedList<Centro>();
			while (rs.next()) {
				Centro centro = new Centro();
				centro.setId(rs.getInt(1));
				centro.setNomCcu(rs.getString(2));
				centro.setSitCcu(Situacao.A);
				centros.add(centro);
			}
			centroDAO.importar(centros);
			logger.info("[R018CCU] Centros importados/atualizados - " + new Date());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("CENTROS não FORAM IMPORTADOS.");
			return false;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
	}

	public boolean importaCidades() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean retorno = false;
		try {
			String sql = "select * from Usu_TSincWeb where usu_tipOpe in('I', 'U') and usu_nomtab='R074CID'";
			conn = connectSqlServer.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			String pkOri = "";
			while (rs.next()) {
				pkOri = rs.getString("usu_pkori");
				String sqlGerado = getSqlSelectFromRegistro(desmembraChave(pkOri), "R074Cid");
				stmt = conn.prepareStatement(sqlGerado);
				ResultSet rsCid = stmt.executeQuery();
				while (rsCid.next()) {
					Cidade cidade = new Cidade();
					cidade.setId(rsCid.getInt("codCid"));
					cidade.setNomCid(rsCid.getString("nomCid"));
					Cidade.Estado est = Cidade.Estado.valueOf(rsCid.getString("estCid"));
					cidade.setEstCid(est);
					cidadeDAO.saveOrUpdate(cidade);
				}
				/** SE IMPORTAR SEM ERROS, PODE EXCLUIR OS REGISTROS DA TABELA AUXILIAR **/
				Importer.excluiRegistroUsu_TSincWeb(rs.getString("usu_pkori"), "I", "R074CID");
				Importer.excluiRegistroUsu_TSincWeb(rs.getString("usu_pkori"), "D", "R074CID");
				Importer.excluiRegistroUsu_TSincWeb(rs.getString("usu_pkori"), "U", "R074CID");
			}
			retorno = true;
			logger.info("[R074CID] Cidades importadas/atualizadas - " + new Date());
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Cidades não foram importadas.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Cidades não foram importadas.");
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
		return retorno;
	}

	public boolean sincronizaCidades() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean retorno = false;
		try {
			String sql = "select * from R074CID";
			conn = connectSqlServer.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Cidade cidade = new Cidade();
				cidade.setId(rs.getInt("codCid"));
				cidade.setNomCid(rs.getString("nomCid"));
				Cidade.Estado est = Cidade.Estado.valueOf(rs.getString("estCid"));
				cidade.setEstCid(est);
				cidadeDAO.saveOrUpdate(cidade);
				retorno = true;
				logger.info("[R074CID] Cidades sincronizadas - " + new Date());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Cidades não foram sincronizadas.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Cidades não foram sincronizadas.");
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
		return retorno;
	}

	/**
	 * Importa os clientes / filiais do Vetorh para o webOper Tabela no Vetorh = R030FIL
	 * 
	 * @return true se importou todos os registros
	 * @throws Exception
	 */
	public boolean importaClientes() throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		boolean retorno = false;
		try {
			String sql = "select * from Usu_TSincWeb where usu_nomtab='R030FIL' order by usu_tipOpe";
			conn = connectSqlServer.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			String pkOri = "";
			String operacao = "";
			while (rs.next()) {
				pkOri = rs.getString("usu_pkori");
				operacao = rs.getString("usu_tipOpe");
				if (pkOri != null) {
					String sqlGerado = getSqlSelectFilialFromRegistro(pkOri);
					stmt = conn.prepareStatement(sqlGerado);
					ResultSet rsFil = stmt.executeQuery();
					while (rsFil.next()) {
						Cliente cliente = new Cliente();
						cliente.setId(new Cliente.Id(rsFil.getInt("codFil"), rsFil.getInt("numEmp")));
						cliente.setRazSoc(rsFil.getString("razSoc"));
						cliente.setTipFil(rsFil.getString("tipFil"));
						cliente.setNumCgc(rsFil.getString("numCgc"));
						cliente.setApeFil(rsFil.getString("nomFil"));
						cliente.setCidade(new Cidade(rsFil.getInt("codCid")));
						if (!operacao.equals("D"))
							clienteDAO.saveOrUpdate(cliente);
						else
							clienteDAO.delete(cliente);
						/** SE IMPORTAR SEM ERROS, PODE EXCLUIR OS REGISTROS DA TABELA AUXILIAR **/
						Importer.excluiRegistroUsu_TSincWeb(rs.getString("usu_pkori"), "D", "R030FIL");
						Importer.excluiRegistroUsu_TSincWeb(rs.getString("usu_pkori"), "I", "R030FIL");
						Importer.excluiRegistroUsu_TSincWeb(rs.getString("usu_pkori"), "U", "R030FIL");
					}
				} else {
					excluiRegistroUsu_TSincWebNulo("I", "R030FIL");
					excluiRegistroUsu_TSincWebNulo("U", "R030FIL");
					excluiRegistroUsu_TSincWebNulo("D", "R030FIL");
				}
				Importer.excluiRegistroUsu_TSincWeb(rs.getString("usu_pkori"), "D", "R030FIL");
				Importer.excluiRegistroUsu_TSincWeb(rs.getString("usu_pkori"), "I", "R030FIL");
				Importer.excluiRegistroUsu_TSincWeb(rs.getString("usu_pkori"), "U", "R030FIL");
			}
			logger.info("[R030FIL] Filiais/Clientes importados/atualizados - " + new Date());
			retorno = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("FILIAIS/CLIENTES não FORAM IMPORTADOS.");
			throw e;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
		return retorno;
	}

	public boolean sincronizaClientes() throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		boolean retorno = false;
		try {
			String sql = "select * from R030Fil";
			conn = connectSqlServer.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setId(new Cliente.Id(rs.getInt("codFil"), rs.getInt("numEmp")));
				cliente.setRazSoc(rs.getString("razSoc"));
				cliente.setTipFil(rs.getString("tipFil"));
				cliente.setNumCgc(rs.getString("numCgc"));
				cliente.setApeFil(rs.getString("nomFil"));
				cliente.setCidade(new Cidade(rs.getInt("codCid")));
				clienteDAO.saveOrUpdate(cliente);
			}
			logger.info("[R030FIL] Filiais/Clientes sincronizados - " + new Date());
			retorno = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("FILIAIS/CLIENTES não FORAM SINCRONIZADOS.");
			throw e;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
		return retorno;
	}

	public boolean importaCondicoesPagamento() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			conn = connectSqlServerSapiens.getConnection();
			String sql = "select codEmp, codCpg, desCpg, aplCpg, sitCpg from E028Cpg where sitCpg='A'";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			List<CondicaoPagamento> condicoes = new ArrayList<CondicaoPagamento>();
			while (rs.next()) {
				CondicaoPagamento condicaoPagamento = new CondicaoPagamento();
				condicaoPagamento.setId(rs.getString("codCpg"));
				condicaoPagamento.setEmpresa(empresaDAO.getEmpresaByEmpSap(rs.getInt("codEmp")));
				condicaoPagamento.setDesCpg(rs.getString("desCpg"));
				condicaoPagamento.setAplCpg(rs.getString("aplCpg"));
				condicaoPagamento.setSitCpg(rs.getString("sitCpg"));
				condicoes.add(condicaoPagamento);
			}
			condicaoPagamentoDAO.importar(condicoes);
			logger.info("[E028CPG] Condições de pagamento importados/atualizados - " + new Date());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("CONDIÇÕES DE PAGAMENTO não FORAM IMPORTADAS.");
			return false;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
	}

	/**
	 * atualiza os colaboradores demitidos, inserindo a data de demiss�o e a situa��o do colaborador. Utiliza a tabela de hist�rico de
	 * afastamentos = R038AFA não importa Marcos Zanarod(CPF) e empresa Z & R (14)
	 * 
	 * @return
	 */
	public boolean importaDemitidos() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			conn = connectSqlServer.getConnection();
			String sql = "select a.numEmp, a.numCad, a.datAfa, f.numCpf, f.datAdm, a.sitAfa "
					+ "from R038AFA a, R034FUN f "
					+ "where a.sitAfa=7 and a.tipCol=1 and a.numEmp<>14 and f.numCpf<>78054320906 and "
					+ "(a.numEmp=f.numEmp and a.numCad=f.numCad and a.tipCol=f.tipCol) and a.datAfa>=sysdate-365";

			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			List<Colaborador> demitidos = new LinkedList<Colaborador>();
			while (rs.next()) {
				Colaborador demitido = new Colaborador();
				try {
					demitido = colaboradorDAO.loadByNumCad(rs.getLong(2), rs.getInt(1));
					if (demitido != null) {

						logger.info("Atualizando demitido " + demitido.getNumCad() + " - "
								+ demitido.getNomFun());

						demitido.setDatDem(rs.getDate(3));

						Afastamento afastamento = new Afastamento();
						afastamento = afastamentoDAO.loadById(rs.getInt(6));

						demitido.setAfastamento(afastamento);

						demitidos.add(demitido);
					} else {
						logger.info("Colaborador demitido" + rs.getLong(2) + " da empresa " + rs.getInt(1)
								+ " não encontrado na base web.");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			colaboradorDAO.atualizar(demitidos);
			logger.info("[R038AFA] Colaboradores demitidos atualizados - " + new java.util.Date());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("COLABORADORES DEMITIDOS não FORAM IMPORTADOS.");
			return false;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
	}

	/**
	 * Busca todos os itens pendentes e verifica no SM se já foi entregue, atualizando data e situa��o
	 * 
	 * @throws Exception
	 */
	public void importaDistribuicoes() throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		List<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();
		List<bean.ItemSolicitacao.Situacao> situacoes = new ArrayList<bean.ItemSolicitacao.Situacao>();
		situacoes.add(bean.ItemSolicitacao.Situacao.SP);
		situacoes.add(bean.ItemSolicitacao.Situacao.SAA);
		situacoes.add(bean.ItemSolicitacao.Situacao.NA);
		situacoes.add(bean.ItemSolicitacao.Situacao.PL);
		situacoes.add(bean.ItemSolicitacao.Situacao.PP);
		solicitacoes = solicitacaoDAO.listPendentesEnvio(situacoes);
		try {
			conn = connectSqlServer.getConnection();
			for (Solicitacao solicitacao : solicitacoes) {
				if (solicitacao.getNumSeq() > 0) {
					for (ItemSolicitacao item : solicitacao.getItensSolicitacao()) {
						stmt = conn.prepareStatement(
								"select * from R096Die where usu_numSeq=? and codepi=? and usu_datEnv<>'1900-12-31'");
						stmt.setInt(1, solicitacao.getNumSeq());
						stmt.setInt(2, item.getUniforme().getId());
						rs = stmt.executeQuery();
						if (rs.next()) {
							item.setDatEnv(rs.getDate("usu_datEnv"));
							item.setDatAtu(new Date());
							item.setEntAut("S");
							item.setSitItm(bean.ItemSolicitacao.Situacao.EN);
							item.setDatDev(rs.getDate("datDev"));
							itemSolicitacaoDAO.update(item);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Erro atualizando itens na importa��o. Causa:" + e.getMessage());
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
	}

	/**
	 * Importa as empresas do Vetorh para o webOper Tabela no Vetorh = R030EMP
	 * 
	 * @return true se importou todos os registros
	 */
	public boolean importaEmpresas() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			conn = connectSqlServer.getConnection();
			String sql = "select numEmp, nomEmp from R030EMP";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			List<Empresa> empresas = new LinkedList<Empresa>();
			while (rs.next()) {
				Empresa empresa = new Empresa();
				empresa.setId(rs.getInt(1));
				empresa.setNomEmp(rs.getString(2));
				switch (rs.getInt(1)) {
					case 1:
						empresa.setCodLoc("01");
						empresa.setEmpSap(1);
						break;
					case 3:
						empresa.setCodLoc("05");
						empresa.setEmpSap(2);
						break;
					case 5:
						empresa.setCodLoc("04");
						empresa.setEmpSap(3);
						break;
					case 9:
						empresa.setCodLoc("07");
						empresa.setEmpSap(5);
						break;
					case 14:
						empresa.setCodLoc("06");
						break;
					case 16:
						empresa.setCodLoc("08");
						break;
					case 21:
						empresa.setCodLoc("03");
						empresa.setEmpSap(4);
						break;
					case 23:
						empresa.setCodLoc("02");
						empresa.setEmpSap(6);
						break;
					default:
						break;
				}
				empresas.add(empresa);
			}
			empresaDAO.importar(empresas);
			logger.info("[R030EMP] Empresas importadas/atualizadas - " + new Date());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("EMPRESAS não FORAM IMPORTADAS.");
			return false;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
	}

	/**
	 * Importa as escalas do Vetorh para consulta no webOper Tabela no Vetorh = R006ESC
	 * 
	 * @return true se conseguiu importar todos os objetos
	 * @throws Exception
	 */
	public boolean importaEscalasRonda() throws Exception {
		boolean retorno = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select * from Usu_TSincWeb where usu_nomtab='R006ESC' order by usu_tipOpe";
			conn = connectSqlServer.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			String pkOri = "";
			String operacao = "";
			while (rs.next()) {
				pkOri = rs.getString("usu_pkori");
				operacao = rs.getString("usu_tipOpe");
				String sqlGerado = getSqlSelectFromRegistro(desmembraChave(pkOri), "R006ESC");
				stmt = conn.prepareStatement(sqlGerado);
				ResultSet rsEsc = stmt.executeQuery();
				if (rsEsc.next()) {
					EscalaRonda escalaRonda = new EscalaRonda();
					escalaRonda.setId(rsEsc.getInt("codEsc"));
					escalaRonda.setNomEsc(rsEsc.getString("nomEsc"));
					if (!operacao.equals("D"))
						escalaRondaDAO.saveOrUpdate(escalaRonda);
					else
						escalaRondaDAO.delete(escalaRonda);
				}
				/** SE IMPORTAR SEM ERROS, PODE EXCLUIR OS REGISTROS DA TABELA AUXILIAR **/
				Importer.excluiRegistroUsu_TSincWeb(rs.getString("usu_pkori"), "D", "R006ESC");
				Importer.excluiRegistroUsu_TSincWeb(rs.getString("usu_pkori"), "I", "R006ESC");
				Importer.excluiRegistroUsu_TSincWeb(rs.getString("usu_pkori"), "U", "R006ESC");
			}
			retorno = true;
			logger.info("[R006ESC] Escalas importadas/atualizadas - " + new Date());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ESCALAS não FORAM IMPORTADAS");
			throw e;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
		return retorno;
	}

	public boolean sincronizaEscalasRonda() throws Exception {
		boolean retorno = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select * from R006Esc";
			conn = connectSqlServer.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				EscalaRonda escalaRonda = new EscalaRonda();
				escalaRonda.setId(rs.getInt("codEsc"));
				escalaRonda.setNomEsc(rs.getString("nomEsc"));
				escalaRonda.setSitEsc(rs.getString("usu_sitEsc"));
				escalaRondaDAO.saveOrUpdate(escalaRonda);

			}
			retorno = true;
			logger.info("[R006ESC] Escalas sincronizadas com Vetorh - " + new Date());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ESCALAS não FORAM IMPORTADAS");
			throw e;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
		return retorno;
	}

	/**
	 * Importa as quantidades de estoque de epis do sapiens
	 * 
	 * @throws Exception
	 */
	public void importaEstoques() throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		List<Estoque> estoques = new ArrayList<Estoque>();
		try {
			conn = connectSqlServerSapiens.getConnection();
			conn.setAutoCommit(false);
			stmt = conn
					.prepareStatement("select codEmp, codPro, qtdEst from e210est where coddep='101' "
							+ "and codpro in(select codpro from e075pro where codori=100 and sitPro='A') and codEmp in(1,2,3,4,5,6)");
			rs = stmt.executeQuery();
			conn.commit();
			while (rs.next()) {
				int empresa = 0;
				switch (rs.getInt("codEmp")) {
					case 1:
						empresa = 1;
						break;
					case 2:
						empresa = 3;
						break;
					case 3:
						empresa = 5;
						break;
					case 4:
						empresa = 21;
						break;
					case 5:
						empresa = 9;
						break;
					case 6:
						empresa = 23;
						break;
					default:
						break;
				}
				Estoque estoque = new Estoque(new Uniforme(Integer.valueOf(rs.getString("codPro"))),
						new Empresa(empresa));
				estoque.setQtdEst(rs.getInt("qtdEst"));
				estoques.add(estoque);
			}
			estoqueDAO.importar(estoques);
		} catch (Exception e) {
			logger.error("Não foi possível popular o estoque " + ":" + e.getMessage());
			throw e;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
	}

	public boolean importaFornecedores() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			conn = connectSqlServerSapiens.getConnection();
			String sql = "select codFor, nomFor, apeFor, tipFor, cgcCpf, endFor, insEst, "
					+ "insMun, cplEnd, baiFor, cepFor, cidFor, sigUfs, fonFor, faxFor, intNet, "
					+ "sitFor from E095For where sitFor='A'";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			List<Fornecedor> fornecedores = new ArrayList<Fornecedor>();
			while (rs.next()) {
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setCodFor(rs.getInt("codFor"));
				fornecedor.setNomFor(rs.getString("nomFor"));
				fornecedor.setApeFor(rs.getString("apeFor"));
				fornecedor.setTipFor(rs.getString("tipFor"));
				fornecedor.setCgcCpf(rs.getString("cgcCpf"));
				fornecedor.setEndFor(rs.getString("endFor"));
				fornecedor.setInsEst(rs.getString("insEst"));
				fornecedor.setInsMun(rs.getString("insMun"));
				fornecedor.setCplEnd(rs.getString("cplEnd"));
				fornecedor.setBaiFor(rs.getString("baiFor"));
				fornecedor.setCepFor(rs.getString("cepFor"));
				fornecedor.setCidFor(rs.getString("cidFor"));
				fornecedor.setSigUfs(rs.getString("sigUfs"));
				fornecedor.setFonFor(rs.getString("fonFor"));
				fornecedor.setFaxFor(rs.getString("faxFor"));
				fornecedor.setIntNet(rs.getString("intNet"));
				fornecedor.setSitFor(rs.getString("sitFor"));
				fornecedores.add(fornecedor);
			}
			fornecedorDAO.importar(fornecedores);
			logger.info("[E095FOR] Fornecedores importados/atualizados - " + new Date());
			return true;
		} catch (Exception e) {
			logger.fatal("FORNECEDORES não FORAM IMPORTADOS." + e.getMessage());
			return false;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
	}

	public boolean importaGrauInstrucao() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			conn = connectSqlServer.getConnection();
			String sql = "select graIns, desGra from R022GRA";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			List<GrauInstrucao> graus = new LinkedList<GrauInstrucao>();
			while (rs.next()) {
				GrauInstrucao grau = new GrauInstrucao();
				grau.setId(rs.getShort(1));
				grau.setDesGra(rs.getString(2));
				graus.add(grau);
			}

			grauInstrucaoDAO.importar(graus);
			logger.info("[R022Gra] Graus de instrução importados/atualizados - " + new Date());
			return true;
		} catch (Exception e) {
			logger.error("Graus de instrução não foram importados." + e.getMessage());
			return false;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
	}

	/**
	 * Importa os itens do banco de dados do SM.
	 * 
	 * @param solicitacoes
	 *           - informa de quais solicita��es gerar os itens. Informar null para importar todos os itens do banco de dados
	 */
	@Deprecated
	public void importaItens(List<Solicitacao> solicitacoes) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			conn = connectSqlServer.getConnection();
			if (solicitacoes == null) {
				solicitacoes = solicitacaoDAO.listAll("id");
			}
			Solicitacao solicitacao = new Solicitacao();
			ItemSolicitacao itemSolicitacao = new ItemSolicitacao();

			for (Iterator<Solicitacao> iterator = solicitacoes.iterator(); iterator.hasNext();) {
				solicitacao = (Solicitacao) iterator.next();
				stmt = conn.prepareStatement("select numEmp, numCad, codEpi, datEnt, "
						+ "qtdEnt, codMtv, obsDis, usu_numSeq, usu_entAut, usu_datEnv, usu_datAtu, datDev "
						+ "from R096Die where usu_numseq=?");
				stmt.setInt(1, solicitacao.getNumSeq());
				rs = stmt.executeQuery();
				while (rs.next()) {
					itemSolicitacao = new ItemSolicitacao();
					itemSolicitacao
							.setId(new ItemSolicitacao.Id(solicitacao, new Uniforme(rs.getInt(3))));
					itemSolicitacao.setDatEnv(rs.getDate(10));
					itemSolicitacao.setEntAut(rs.getString(9));
					itemSolicitacao.setObsDis(rs.getString(7));
					itemSolicitacao.setQtdEnt(rs.getInt(5));
					itemSolicitacao.setDatAtu(rs.getDate(11));
					itemSolicitacao.setDatDev(rs.getDate(12));
					solicitacao.getItensSolicitacao().add(itemSolicitacao);
					solicitacaoDAO.saveOrUpdate(solicitacao);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
	}

	/**
	 * Importa os locais e hierarquias do organograma do Vetorh para o webOper Tabela no Vetorh = R016ORN = locais e R016HIE = hierarquias
	 * 
	 * @return true se importou todos os registros
	 * @throws Exception
	 */
	public boolean importaLocais() throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean retorno = false;
		try {
			String sql = "select * from Usu_TSincWeb where usu_nomtab='R016ORN' order by usu_tipOpe";
			conn = connectSqlServer.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			String pkOri = "";
			String operacao = "";
			while (rs.next()) {
				pkOri = rs.getString("usu_pkori");
				operacao = rs.getString("usu_tipOpe");
				String sqlGerado = getSqlSelectLocalFromRegistro(pkOri);
				stmt = conn.prepareStatement(sqlGerado);
				ResultSet rsLoc = stmt.executeQuery();
				if (rsLoc.next()) {
					Local local = new Local();
					Hierarquia hierarquia = new Hierarquia();
					local.setId(new Local.Id(rsLoc.getInt("tabOrg"), rsLoc.getInt("numLoc")));
					local.setDatCri(rsLoc.getTimestamp("datCri"));
					local.setDatExt(rsLoc.getTimestamp("datExt"));

					if (rsLoc.getString("nomLoc") == null) {
						local.setNomLoc("SEM NOME - ERRO");
						System.out.println("CAMPO SEM NOME");
					} else {
						local.setNomLoc(rsLoc.getString("nomLoc"));
					}

					hierarquia.setId(new Hierarquia.Id(rsLoc.getInt("tabOrg"), rsLoc.getInt("numLoc"),
							rsLoc.getDate("datIni")));
					hierarquia.setCodLoc(rsLoc.getString("codLoc"));
					hierarquia.setDatFim(rsLoc.getDate("datFim"));
					hierarquia.setPosLoc(rsLoc.getString("posLoc"));
					hierarquia.setLocal(local);

					local.getHierarquias().add(hierarquia);
					if (!operacao.equals("D"))
						localDAO.saveOrUpdate(local);
					else
						localDAO.delete(local);
				}
				/** SE IMPORTAR SEM ERROS, PODE EXCLUIR OS REGISTROS DA TABELA AUXILIAR **/
				Importer.excluiRegistroUsu_TSincWeb(rs.getString("usu_pkori"), "D", "R016ORN");
				Importer.excluiRegistroUsu_TSincWeb(rs.getString("usu_pkori"), "I", "R016ORN");
				Importer.excluiRegistroUsu_TSincWeb(rs.getString("usu_pkori"), "U", "R016ORN");
			}
			retorno = true;
			logger.info("[R016ORN] Locais importados/atualizados - " + new Date());
		} catch (Exception e) {
			logger.fatal("LOCAIS não FORAM IMPORTADOS: " + e.getMessage());
			throw e;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
		return retorno;
	}

	public boolean sincronizaLocais() throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean retorno = false;
		try {
			String sql = "Select * from R016ORN l, R016HIE h where (l.tabOrg=h.tabOrg and l.numLoc=h.numLoc) and l.tabOrg = 2 order by l.numLoc";
			conn = connectSqlServer.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Local local = new Local();
				Hierarquia hierarquia = new Hierarquia();
				local.setId(new Local.Id(rs.getInt("tabOrg"), rs.getInt("numLoc")));
				local.setDatCri(rs.getTimestamp("datCri"));
				local.setDatExt(rs.getTimestamp("datExt"));
				if (rs.getString("nomLoc") == null) {
					local.setNomLoc("SEM NOME - ERRO");
					System.out.println("CAMPO SEM NOME");
				} else {
					local.setNomLoc(rs.getString("nomLoc"));
				}
				hierarquia.setId(new Hierarquia.Id(rs.getInt("tabOrg"), rs.getInt("numLoc"),
						rs.getDate("datIni")));
				hierarquia.setCodLoc(rs.getString("codLoc"));
				hierarquia.setDatFim(rs.getDate("datFim"));
				hierarquia.setPosLoc(rs.getString("posLoc"));
				hierarquia.setLocal(local);
				System.out.println(rs.getInt("numLoc"));
				local.getHierarquias().add(hierarquia);
				localDAO.saveOrUpdate(local);
				System.out.println(rs.getInt("numLoc"));
				System.out.println(rs.getString("codLoc"));

			}
			retorno = true;
			logger.info("[R016ORN] Locais sincronizados - " + new Date());
		} catch (Exception e) {
			logger.fatal("LOCAIS não FORAM SINCRONIZADOS: " + e.getMessage());
			throw e;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
		return retorno;
	}

	/**
	 * Importa os motivos de distribuição do Vetorh para o webOper Tabela no Vetorh = R096MTV
	 * 
	 * @return true se importou todos os registros
	 */
	@Deprecated
	public boolean importaMotivos() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			conn = connectSqlServer.getConnection();
			String sql = "select codMtv, desMtv from R096MTV where codMtv<>9";

			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			List<Motivo> motivos = new LinkedList<Motivo>();
			while (rs.next()) {
				Motivo motivo = new Motivo();
				motivo.setId(rs.getInt(1));
				motivo.setDesMtv(rs.getString(2));
				motivos.add(motivo);
			}
			motivoDAO.importar(motivos);
			logger.info("[R096MTV] Motivos importados/atualizados - " + new Date());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("MOTIVOS não FORAM IMPORTADOS.");
			return false;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
	}

	@Deprecated
	public void importaNovasDistribuicoes() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			conn = connectSqlServer.getConnection();
			String sql = "select distinct a.numEmp, a.numCad, a.datEnt, "
					+ "(select top 1 codMtv from R096die b where b.numEmp=a.numEmp and b.numCad=a.numcad and b.datEnt=a.datEnt) as motivo, "
					+ "(select top 1 usu_NumSeq from R096die c where c.numEmp=a.numEmp and c.numCad=a.numcad and c.datEnt=a.datEnt) as numSeq "
					+ "from R096Die a "
					+ "where numCad in(select numcad from r034fun where sitafa<>7) AND "
					+ "(DATEPART(DAY, usu_datger) = DATEPART(DAY, getDate()-1) AND "
					+ "DATEPART(MONTH, usu_datger) = DATEPART(MONTH, getDate()-1) AND "
					+ "DATEPART(YEAR, usu_datger) >= DATEPART(YEAR, getDate()-1)) ";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			List<Solicitacao> solicitacoes = new LinkedList<Solicitacao>();
			while (rs.next()) {
				int numEmp = rs.getInt(1);
				int numCad = rs.getInt(2);
				Date datEnt = rs.getDate(3);
				if (solicitacaoDAO.loadByChaveVetorh(numEmp, numCad, datEnt) == null) {
					logger.info("Carregando distribuição do colaborador: " + rs.getLong(2));
					Solicitacao solicitacao = new Solicitacao();
					solicitacao.setColaborador(
							new Colaborador(colaboradorDAO.loadIdByNumCad(rs.getLong(2), rs.getInt(1))));
					solicitacao.setDatEnt(rs.getDate(3));
					solicitacao.setMotivo(new Motivo(rs.getInt(4)));
					solicitacao.setNumSeq(rs.getInt(5));
					solicitacao.setViaWeb(false);
					solicitacoes.add(solicitacao);
				}
			}
			solicitacaoDAO.importar(solicitacoes);
			importaItens(solicitacoes);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
	}

	/**
	 * Importa materiais de expediente, material de instala��o/ferramentas e material de limpeza
	 * 
	 * @throws Exception
	 **/
	public void importaProdutos() throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			conn = connectSqlServerSapiens.getConnection();
			String sql = "select E075Pro.codEmp, E075Pro.codPro, E075Pro.desPro, E075Pro.cplPro, "
					+ "E075Pro.sitPro, E075Pro.codOri, E075Pro.uniMed, E075Der.preUen from E075Pro, E075Der "
					+ "where E075Pro.CodEmp=E075Der.CodEmp and E075Pro.CodPro=E075Der.CodPro and "
					+ "((E075Pro.codOri='600' and E075Pro.codFam='538' or (E075Pro.codOri='400' and (E075Pro.codEmp=1 or E075Pro.codEmp=6))) "
					+ "or (E075Pro.codemp=2 and codori='200' and (E075Pro.codfam='063' or E075Pro.codfam='062')) "
					+ "or (E075Pro.codEmp=6 and codOri='300') and E075Pro.claPro=1)";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			List<Produto> produtos = new LinkedList<Produto>();
			while (rs.next()) {
				Produto produto = new Produto();
				int codEmp = 0;
				switch (rs.getInt("codEmp")) {
					case 1:
						codEmp = 1;
						break;
					case 2:
						codEmp = 3;
						break;
					case 3:
						codEmp = 5;
						break;
					case 4:
						codEmp = 21;
						break;
					case 5:
						codEmp = 9;
						break;
					case 6:
						codEmp = 23;
						break;
					default:
						break;
				}
				bean.Produto.Id idProduto = new bean.Produto.Id(new Empresa(codEmp),
						rs.getString("codPro"));
				produto = produtoDAO.loadById(idProduto);
				try {
					produto.setId(idProduto);
				} catch (ObjectNotFoundException onfe) {
					produto = new Produto();
					produto.setId(idProduto);
					produto.setRequisitavel(true);
				}
				produto.setDesPro(rs.getString("desPro"));
				produto.setCplPro(rs.getString("cplPro"));
				produto.setSitPro(bean.Produto.Situacao.valueOf(rs.getString("sitPro")));
				produto.setUniMed(rs.getString("uniMed"));
				produto.setPreUni(rs.getDouble("preUen"));
				if (rs.getString("codOri").equals("600"))
					produto.setTipPro(TipoProduto.ME);// material de expediente
				else if (rs.getString("codOri").equals("400"))
					produto.setTipPro(TipoProduto.MC); // material carga de
				// posto
				else if (rs.getString("codOri").equals("200")) // material de
					// instala��o
					produto.setTipPro(TipoProduto.MI);
				else if (rs.getString("codOri").equals("300"))
					produto.setTipPro(TipoProduto.LI);
				produtos.add(produto);
			}
			produtoDAO.importar(produtos);
			logger.info("[E075PRO] Produtos importados/atualizados - " + new Date());
		} catch (Exception e) {
			logger.fatal("PRODUTOS não FORAM IMPORTADOS:" + e.getMessage());
			throw e;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
	}

	/**
	 * Importa os tipos de uniforme/EPI do Vetorh para o webOper Tabela no Vetorh = R096TIP
	 * 
	 * @return true se importou todos os registros
	 */
	public boolean importaTipos() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			conn = connectSqlServer.getConnection();
			String sql = "select tipEqu, desSvc from R096TIP";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			List<TipoEPI> tipos = new LinkedList<TipoEPI>();
			while (rs.next()) {
				TipoEPI tipo = new TipoEPI();
				tipo.setId(rs.getInt(1));
				tipo.setDesSvc(rs.getString(2));
				tipos.add(tipo);
			}
			tipoEPIDAO.importar(tipos);
			logger.info("[R096TIP] Tipos importados/atualizados - " + new Date());
			return true;
		} catch (Exception e) {
			logger.fatal("TIPOS não FORAM IMPORTADOS:" + e.getMessage());
			return false;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
	}

	/**
	 * Importa as nacionalides do vetorh para a web Tabela = R023NAC
	 * 
	 * @throws Exception
	 */
	public void importaNacionalidades() throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			conn = connectSqlServer.getConnection();
			String sql = "select codNac, desNac from R023Nac";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			List<Nacionalidade> nacionalidades = new LinkedList<Nacionalidade>();
			while (rs.next()) {
				Nacionalidade nacionalidade = new Nacionalidade();
				nacionalidade.setId(rs.getInt("codNac"));
				nacionalidade.setDesNac(rs.getString("desNac"));
				nacionalidades.add(nacionalidade);
			}
			nacionalidadeDAO.importar(nacionalidades);
			logger.info("[R023NAC] Nacionalidades importadas/atualizadas - " + new Date());
		} catch (Exception e) {
			logger.fatal("NACIONALIDADES não FORAM IMPORTADAS: " + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
	}

	/**
	 * Importa os uniformes do Vetorh para o webOper Tabela no Vetorh = R096EPI
	 * 
	 * @return true se importou todos os registros
	 * @throws Exception
	 */
	public void importaUniformes() throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			conn = connectSqlServerSapiens.getConnection();
			/**
			 * VERIFICAR NO SAPIENS QUAL O CAMPO QUE INDICA SE O PRODUTO PODE SER REQUISITADO OU não
			 */
			String sql = "select codPro, desPro, usu_diaval, usu_qtdmax, codFam, sitPro, cplPro, codEmp from E075Pro where (codOri='100' and codEmp=1 and indReq='S') "
					+ "or (codOri='100' and codEmp=6 and indReq='S' and codPro not in (select codPro from E075Pro where codOri='100' and codEmp=1 and indReq='S'))";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			List<Uniforme> uniformes = new LinkedList<Uniforme>();
			while (rs.next()) {
				Uniforme uniforme = new Uniforme();
				uniforme.setId(rs.getInt(1));
				uniforme.setDesEpi(rs.getString(2));
				uniforme.setDiaVal(rs.getInt(3));
				uniforme.setQtdMax(rs.getInt(4));
				uniforme.setTipoEPI(tipoEPIDAO.loadById(rs.getInt(5)));
				uniforme.setSitEpi(rs.getString("sitPro"));
				uniforme.setCplDes(rs.getString("cplPro"));

				/**
				 * BUSCAR AS CARACTER�STICAS DE CADA UNIFORME E GRAVAR NO SET
				 */
				String sqlCar = "select E075Cpr.CodCte as CodCte, E010Cte.DesCte as DesCte from E075Cpr, E010Cte where E075Cpr.CodEmp=E010Cte.CodEmp and "
						+ "E075Cpr.CodCte=E010Cte.CodCte and E075Cpr.CodPro='"
						+ String.format("%04d", rs.getInt(1)) + "' and E075Cpr.CodEmp="
						+ rs.getInt("codEmp");
				Statement stmtCar = conn.createStatement();
				ResultSet rsCar = stmtCar.executeQuery(sqlCar);
				Set<Caracteristica> caracteristicas = new HashSet<Caracteristica>();
				while (rsCar.next()) {
					Caracteristica caracteristica = new Caracteristica();
					caracteristica.setId(rsCar.getString("CodCte"));
					caracteristica.setDesCte(rsCar.getString("DesCte"));
					caracteristicas.add(caracteristica);
				}
				rsCar.close();
				stmtCar.close();
				uniforme.setCaracteristicas(caracteristicas);
				uniformes.add(uniforme);
			}
			uniformeDAO.importar(uniformes);
			logger.info("[E075PRO] Uniformes importados/atualizados - " + new Date());
		} catch (Exception e) {
			logger.fatal("UNIFORMES não FORAM IMPORTADOS: " + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			connectSqlServerSapiens.closeConnection(conn, stmt, rs);
		}
	}

	public boolean insereDadosColaborador(ResultSet rs) throws Exception {
		Colaborador colaborador = new Colaborador();
		boolean retorno = false;
		try {
			colaborador.setDatAdm(rs.getDate("datAdm"));
			colaborador.setEmpresa(new Empresa(rs.getInt("numEmp")));
			colaborador.setLocPai(rs.getString("Usu_DesLocPai"));
			colaborador.setNomFun(rs.getString("nomFun"));
			colaborador.setNumCad(rs.getLong("numCad"));
			colaborador.setNumCpf(rs.getString("numCpf"));
			colaborador.setDatInc(rs.getDate("datInc"));
			colaborador.setHorInc(rs.getInt("horInc"));
			colaborador.setDatNas(rs.getDate("datNas"));
			colaborador.setSexo(Sexo.valueOf(rs.getString("tipSex")));
			colaborador.setNumCtp(rs.getLong("numCtp"));
			colaborador.setSerCtp(rs.getString("serCtp"));
			colaborador.setNumPis(rs.getLong("numPis"));
			colaborador.setConBan(rs.getLong("conBan"));
			colaborador.setCodAge(rs.getString("codAge"));
			if (rs.getInt("codBan") > 0)
				colaborador.setBanco(new Banco(rs.getInt("codBan")));
			colaborador.setEstCiv(Colaborador.EstadoCivil.loadById(rs.getInt("estCiv")));
			colaborador.setGrauInstrucao(new GrauInstrucao(rs.getShort("graIns")));

			// para vigilantes
			colaborador.setNumDRT(rs.getString("usu_numDRT"));
			colaborador.setNumDip(rs.getString("usu_numDip"));
			colaborador.setOrgDip(rs.getString("usu_orgExD"));
			colaborador.setDatFor(rs.getDate("usu_crtVig"));
			// ficha complementar
			colaborador.setNumCid(rs.getString("numCid"));
			colaborador.setEmiCid(rs.getString("emiCid"));
			colaborador.setNumCnh(rs.getString("numCnh"));
			colaborador.setCatCnh(rs.getString("catCnh"));
			colaborador.setNumEle(rs.getString("numEle"));
			colaborador.setCciNas(new Cidade(rs.getInt("cciNas")));
			colaborador.setEndRua(rs.getString("endRua"));
			colaborador.setCidEnd(new Cidade(rs.getInt("codCid")));
			colaborador.setCep(rs.getString("endCep"));
			colaborador.setNumTel(rs.getString("numTel"));
			colaborador.setBaiEnd(rs.getString("nomBai"));
			colaborador.setTipSal(TipoSalario.Mensalista);
			colaborador.setTipSan(TipoSanguineo.ABN);
			colaborador.setFuncao(rs.getInt("usu_funcol"));
			colaborador.setDigCon(rs.getString("digBan"));
			colaborador.setDigAge(rs.getString("digAge"));
			colaborador.setEndNum(rs.getString("endNum"));
			colaborador.setEndCpl(rs.getString("endCpl"));

			/*
			EscalaRonda escalaRonda = new EscalaRonda(rs.getInt("codEsc"));
			escalaRonda.setId(rs.getInt("codEsc"));
			escalaRonda.setNomEsc(rs.getString("nomEsc"));
			colaborador.setEscalaRonda(escalaRonda);
*/
			Cargo cargo = new Cargo(new Cargo.Id(rs.getInt("estCar"), rs.getString("codCar")));
			cargo.setTitCar(rs.getString("titRed"));
			cargo.setCodCar(rs.getString("codCar"));
			colaborador.setCargo(cargo);

			Local local = new Local(rs.getInt("tabOrg"), rs.getInt("numLoc"));
			local.setDatCri(rs.getDate("datCri"));
			local.setDatExt(rs.getDate("datExt"));
			local.setNomLoc(rs.getString("nomLoc"));
			colaborador.setLocal(local);

			Cliente cliente = new Cliente();
			cliente.setId(new Cliente.Id(rs.getInt("codFil"), rs.getInt("numEmp")));
			cliente.setRazSoc(rs.getString("razSoc"));
			cliente.setTipFil(rs.getString("tipFil"));
			cliente.setNumCgc(rs.getString("numCgc"));
			cliente.setApeFil(rs.getString("nomFil"));
			cliente.setCidade(new Cidade(rs.getInt("codCid")));
			colaborador.setCliente(cliente);

			Centro centro = new Centro(rs.getInt("codCcu"));
			centro.setNomCcu(rs.getString("nomCcu"));
			centro.setSitCcu(Situacao.A);
			colaborador.setCentro(centro);

			Afastamento afastamento = new Afastamento(rs.getInt("sitAfa"));
			afastamento.setDesSit(rs.getString("desSit"));
			colaborador.setAfastamento(afastamento);

			colaboradorDAO.merge(colaborador);
		} catch (Exception e) {
			logger.error(
					"Erro no colaborador - " + rs.getLong("numCad") + " :" + e.getLocalizedMessage());
			retorno = false;
			throw e;
		}
		return retorno;
	}

	public boolean processaDelete(Connection conn) throws Exception {
		boolean retorno = true;
		String sql = "select * from Usu_TSincWeb where usu_tipOpe='D' and usu_nomtab='R034FUN'";
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		String pkOri = "";
		while (rs.next()) {
			/** Retorna cpf do colaborador inserido **/
			pkOri = rs.getString("usu_pkori");
			sql = getSqlSelectColaboradorFromRegistro(pkOri);
			stmt = conn.prepareStatement(sql);
			ResultSet rsCol = stmt.executeQuery();
			Colaborador colaborador = new Colaborador();
			try {
				if (rsCol.next()) {
					int idColaborador = colaboradorDAO.loadIdByNumCad(rsCol.getLong("numCad"),
							rsCol.getInt("numEmp"));
					if (idColaborador > 0)
						colaborador = colaboradorDAO.loadById(idColaborador);
					else
						colaborador = null;
				}
				if (colaborador != null) {
					try {
						removeColaborador(colaborador);
						Importer.excluiRegistroUsu_TSincWeb(pkOri, "I", "R034FUN");
						Importer.excluiRegistroUsu_TSincWeb(pkOri, "U", "R034FUN");
						Importer.excluiRegistroUsu_TSincWeb(pkOri, "D", "R034FUN");
					} catch (Exception e) {
						retorno = false;
						throw e;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			} finally {
				rsCol.close();
				connectSqlServer.closeConnection(stmt, rs);
			}
		}
		return retorno;
	}

	public boolean processaInsert(Connection conn) throws NumberFormatException, Exception {
		/** INSER��ES **/
		boolean retorno = true;
		String sql = "select * from Usu_TSincWeb where usu_tipOpe='I' and usu_nomtab='R034FUN'";
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		String numCpf = "";
		String pkOri = "";
		while (rs.next()) {
			/** Retorna cpf do colaborador inserido **/
			pkOri = rs.getString("usu_pkori");
			if (pkOri == null) {
				Importer.excluiRegistroUsu_TSincWebNulo("I", "R034FUN");
			} else {
				sql = getSqlSelectColaboradorFromRegistro(pkOri);
				stmt = conn.prepareStatement(sql);
				ResultSet rsCol = stmt.executeQuery();
				if (rsCol.next()) {
					numCpf = String.valueOf(rsCol.getLong("numCpf"));
				} else {// se não encontrou o registro vetorh, � pq foi exclu�do antes de importar para a web
					Importer.excluiRegistroUsu_TSincWeb(pkOri, "I", "R034FUN");
					Importer.excluiRegistroUsu_TSincWeb(pkOri, "U", "R034FUN");
					continue;
				}
				Colaborador colaborador = new Colaborador();
				try {
					colaborador = colaboradorDAO.loadByCPF(numCpf);
				} catch (NonUniqueResultException e) {
					System.err.println(
							"não foi possível atualizar colaborador. possível causa: colaborador já possui cadastro com cpf: "
									+ numCpf);
					retorno = false;
					throw e;
				}
				if (colaborador != null) {
					try {
						/** atualiza dados do colaborador na web **/
						atualizaDadosColaborador(colaborador.getId(), rsCol);
						/** remover registro da tabela usu_tsincweb **/
						Importer.excluiRegistroUsu_TSincWeb(pkOri, "I", "R034FUN");
						Importer.excluiRegistroUsu_TSincWeb(pkOri, "U", "R034FUN");
					} catch (Exception e) {
						System.err.println("não foi possível atualizar colaborador "
								+ colaborador.getNumCad() + " - " + colaborador.getNomFun());
						retorno = false;
						throw e;
					} finally {
						rsCol.close();
					}
				} else {
					/**
					 * insere dados do colaborador novo na web, sabendo que não encontrou colaborador pendente com o cpf dado.
					 */
					try {
						insereDadosColaborador(rsCol);
						/** remover registro da tabela usu_tsincweb **/
						Importer.excluiRegistroUsu_TSincWeb(pkOri, "I", "R034FUN");
						Importer.excluiRegistroUsu_TSincWeb(pkOri, "U", "R034FUN");
					} catch (Exception e) {
						System.err.println(
								"não foi possível inserir colaborador " + rsCol.getString("nomFun"));
						retorno = false;
						throw new Exception("não foi possível inserir colaborador "
								+ rsCol.getString("nomFun") + e.getMessage());
					} finally {
						rsCol.close();
					}
				}
			}
		}
		connectSqlServer.closeConnection(stmt, rs);
		return retorno;
	}

	public boolean processaUpdate(Connection connection) throws Exception {
		/** ATUALIZA��ES **/
		boolean retorno = false;
		int idColaborador = 0;
		String sql = "select * from Usu_TSincWeb where usu_tipOpe='U' and usu_nomtab='R034FUN'";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			ResultSet rs1 = null;
			PreparedStatement stmt2 = null;
			String pkOri;
			if (rs.getString("usu_pkori") != null) {
				String[] chaves = desmembraChave(rs.getString("usu_pkori"));
				pkOri = rs.getString("usu_pkori");
				sql = "select f.numEmp, nomFun, datAdm, f.sitAfa, f.estCar, f.codCar, f.codFil,	"
						+ "f.tabOrg, f.numLoc, f.codCcu, numCpf, f.codEsc, f.numCad, Usu_DesLocPai, datInc, "
						+ "horInc, datNas, tipSex, numCtp, serCtp, numPis, f.conBan, f.codBan, f.codAge, "
						+ "f.estCiv, graIns, usu_numDRT, usu_numDip, usu_orgExD, usu_crtVig, numCid, emiCid, "
						+ "numCnh, catCnh, numEle, cciNas, endRua, c.codCid, endCep, c.numTel, nomBai, usu_funCol, f.digBan, "
						+ "(select digAge from R012Age where codBan=f.codBan and codAge=f.codAge) as digAge , c.endNum, c.endCpl, "
						+ "fl.razSoc, fl.tipFil, fl.numCgc, fl.nomFil, fl.codCid, car.titRed, es.nomEsc, lo.nomLoc, lo.datCri, lo.datExt, cc.nomCcu, s.desSit "
						+ "from R034FUN f "
						+ "left join R030Fil fl on (f.numemp=fl.numemp and f.codfil=fl.codfil) "
						+ "left join R034CPL c on (f.numEmp=c.numEmp and f.tipCol=c.tipCol and f.numCad=c.numCad) "
						+ "left join R074Bai b on (c.codCid=b.codCid and c.codBai=b.codBai) "
						+ "left join R024Car car on (f.estCar = car.estCar and f.codCar=car.codCar) "
						+ "left join R006Esc es on (f.codEsc = es.codEsc) "
						+ "left join R016Orn lo on (f.tabOrg = lo.tabOrg and f.numLoc=lo.numLoc)  "
						+ "left join R018Ccu cc on (f.numEmp = cc.numEmp and f.codCcu=cc.codCcu) "
						+ "left join R010Sit s on (f.sitAfa = s.codSit) " + "where ";
				for (int i = 0; i < chaves.length; i++) {

					if (i == 0)
						sql += "f." + chaves[i].trim();
					else
						sql += " and " + "f." + chaves[i].trim();
				}
				stmt2 = connection.prepareStatement(sql);
				rs1 = stmt2.executeQuery();
				if (rs1.next()) {
					/** fazer rotina de atualiza��o dos dados do colaborador na web **/
					idColaborador = colaboradorDAO.loadIdByNumCad(rs1.getLong("numCad"),
							rs1.getInt("numEmp"));
					long numCpf = 0;
					if (idColaborador == 0) {
						if (rs1.getInt("sitAfa") == 7) {
							/** remover registro da tabela usu_tsincweb **/
							Importer.excluiRegistroUsu_TSincWeb(pkOri, "I", "R034FUN");
							Importer.excluiRegistroUsu_TSincWeb(pkOri, "U", "R034FUN");
							continue;
						} else
							numCpf = rs1.getLong("numCpf");
						try {
							idColaborador = colaboradorDAO.loadByCPF(String.valueOf(numCpf)).getId();
						} catch (NullPointerException e) {
							idColaborador = 0;
						} catch (NonUniqueResultException ne) {
							try {
								idColaborador = colaboradorDAO
										.loadByCPFEmp(String.valueOf(numCpf), rs1.getInt("numEmp")).getId();
							} catch (NullPointerException npe) {
								idColaborador = 0;
							}
						}
					}
					if (idColaborador == 0) {
						Colaborador colaborador = new Colaborador();
						String cpf = String.valueOf(Double.parseDouble(rs1.getString("numCpf")));
						colaborador = colaboradorDAO.loadByCPF(cpf);
						if (colaborador != null)
							idColaborador = colaborador.getId();
					}
					if (idColaborador > 0) {
						/** atualiza dados do colaborador na web **/
						try {
							atualizaDadosColaborador(idColaborador, rs1);
							/** remover registro da tabela usu_tsincweb **/
							Importer.excluiRegistroUsu_TSincWeb(pkOri, "I", "R034FUN");
							Importer.excluiRegistroUsu_TSincWeb(pkOri, "U", "R034FUN");
						} catch (ConstraintViolationException e) {
							System.err.println("não foi possível atualizar colaborador "
									+ idColaborador + ". Causa: " + e.getCause().getMessage());
							retorno = false;
							throw new Exception("não foi possível atualizar colaborador "
									+ idColaborador + ". Causa: " + e.getCause().getMessage());
						} catch (Exception e) {
							System.err
									.println("não foi possível atualizar colaborador " + idColaborador);
							retorno = false;
							throw e;
						}
					} else {
						/**
						 * insere dados do colaborador novo na web, sabendo que não encontrou colaborador pendente com o cpf dado.
						 */
						try {
							insereDadosColaborador(rs1);
							/** remover registro da tabela usu_tsincweb **/
							Importer.excluiRegistroUsu_TSincWeb(pkOri, "I", "R034FUN");
							Importer.excluiRegistroUsu_TSincWeb(pkOri, "U", "R034FUN");
						} catch (Exception e) {
							System.err.println(
									"não foi possível inserir colaborador " + rs1.getString("nomFun"));
							retorno = false;
							// throw e;
						}
					}
				}
			} else {
				Importer.excluiRegistroUsu_TSincWebNulo("I", "R034FUN");
			}
			connectSqlServer.closeConnection(stmt2, rs1);
		}
		connectSqlServer.closeConnection(stmt, rs);
		return retorno;
	}

	/*---------------------------------------------------------------------------------------------------------------------------------------*/
	/**
	 * Importa as classes das escalas do Vetorh para o webOper Tabela no Vetorh = R006cle
	 * 
	 * @return true se importou todos os registros
	 */
	public boolean importarClasseEscalaRonda() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		List<ClasseEscalaRonda> list_classeEscalaRonda = new LinkedList<ClasseEscalaRonda>();
		try {
			conn = connectSqlServer.getConnection();
			String sql = "select codcla, descla from r006cle";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();

			while (rs.next()) {
				list_classeEscalaRonda.add(new ClasseEscalaRonda(rs.getInt(1), rs.getString(2)));
			}

			classeEscalaRondaDAO.importar(list_classeEscalaRonda);

			logger.info("[R006CLE] Classe Escala importados/atualizados - " + new Date());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("CLASSE DAS ESCALAS não FORAM IMPORTADOS. " + e.toString());
			return false;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}

	}

	/**
	 * Importa as Escalas do Vetorh para o webOper Tabela no Vetorh = r006esc
	 * 
	 * @return true se importou todos os registros
	 */
	public boolean importarEscalaHorarioRonda() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		List<EscalaHorarioRondaMZ> list_EscalaHorarioRondaMZ = new LinkedList<EscalaHorarioRondaMZ>();

		try {
			conn = connectSqlServer.getConnection();
			String sql = "select codesc, claesc, horsem, hormes, nomesc from r006esc";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();

			while (rs.next()) {
				ClasseEscalaRonda classeEscalaRonda = classeEscalaRondaDAO.retClaEscBd(rs.getInt(2));
				EscalaHorarioRondaMZ escalaHorarioRondaMZ = new EscalaHorarioRondaMZ(rs.getInt(1),classeEscalaRonda, rs.getInt(3), rs.getInt(4), rs.getString(5));
				list_EscalaHorarioRondaMZ.add(escalaHorarioRondaMZ);
			}
			escalaHorarioRondaMZDAO.importar(list_EscalaHorarioRondaMZ);

			logger.info("[R006ESC] Escalas importados/atualizados - " + new Date());
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("AS ESCALAS não FORAM IMPORTADOS.");
			return false;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
	}

	/**
	 * Importa as Turmas das Escalas do Vetorh para o webOper Tabela no Vetorh = r006TMA
	 * 
	 * @return true se importou todos os registros
	 */

	public boolean importarTurmaHorarioRonda() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		List<TurmaHorarioRonda> list_TurmaHorarioRonda = new LinkedList<TurmaHorarioRonda>();
		try {
			conn = connectSqlServer.getConnection();
			String sql = "select codtma, datBas, codEsc from r006tma";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();

			while (rs.next())
				list_TurmaHorarioRonda.add(new TurmaHorarioRonda(rs.getInt(1), rs.getDate(2),	escalaHorarioRondaMZDAO.retEscBd(rs.getInt(3))));


 			turmaHorarioRondaDAO.importar(list_TurmaHorarioRonda);

			logger.info("[R006TMA] Turmas das Escalas do Vetorh importados/atualizados - " + new Date());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("TURMA DAS ESCALAS não FORAM IMPORTADOS. " + e.toString());
			return false;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}

	}

	/**
	 * Importa os horarios do Vetorh para o webOper Tabela no Vetorh = R004hor
	 * 
	 * @return true se importou todos os registros
	 */
	public boolean importarHorarioRonda() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		List<HorarioRonda> list_HorarioRonda = new LinkedList<HorarioRonda>();

		try {
			conn = connectSqlServer.getConnection();
			String sql = "select codhor, deshor from r004HOR";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();

			while (rs.next())
				list_HorarioRonda.add(new HorarioRonda(rs.getInt(1), rs.getString(2)));

			System.out.println("Total: " + list_HorarioRonda.size());
			horarioRondaDAO.importar(list_HorarioRonda);

			logger.info("[R004HOR] HORARIOS importados/atualizados - " + new Date());
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("HORARIOS não FORAM IMPORTADOS.");
			return false;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
	}

	/**
	 * Importa os horarios da escala do Vetorh para o webOper Tabela no Vetorh = R006hor
	 * 
	 * @return true se importou todos os registros
	 */
	public boolean importarHorarioEscalaRonda() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		List<HorarioEscalaRonda> list_HorarioEscalaRonda = new LinkedList<HorarioEscalaRonda>();

		try {
			this.importarSeqenciaRegistro();
			conn = connectSqlServer.getConnection();
			String sql = "select CODHOR, SEQREG, CODESC from R006HOR";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			horarioEscalaRondaDao.removerHorariosEscalaRonda();
			
			while (rs.next()) {
				    list_HorarioEscalaRonda.add(new HorarioEscalaRonda(escalaHorarioRondaMZDAO.retEscBd(rs.getInt(3)),sequenciaRegistroDAO.retSeqRegBd(rs.getInt(2)),horarioRondaDAO.retCodHorBd(rs.getInt(1))));
			}

				horarioEscalaRondaDao.importar(list_HorarioEscalaRonda);
			logger.info("[R004HOR] HORARIOS importados/atualizados - " + new Date());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("HORARIOS não FORAM IMPORTADOS.");
			return false;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
	}

	/*Importar os sindicatos*/
	public boolean importarSindicatos() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		List<Sindicato> listSindicato = new LinkedList<Sindicato>();

		try {
			conn = connectSqlServer.getConnection();
			String sql = "select CODSIN, NOMSIN from R014SIN";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) 
				    listSindicato.add(new Sindicato(rs.getInt(1),rs.getString(2)));
			
			sindicatoDAO.importar(listSindicato );
			logger.info("[R014SIN] Sindicatos importados/atualizados - " + new Date());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("SINDICATOS não FORAM IMPORTADOS.");
			return false;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
	}
	
	/*Importar deficiencias*/
	public boolean importarDeficiencias() {

		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;

		List<Deficiencia> listDeficiencia = new LinkedList<Deficiencia>();

		try {
			conn = connectSqlServer.getConnection();
			String sql = "select CODDEF, DESDEF from R022DEF";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) 
				listDeficiencia.add(new Deficiencia(rs.getInt(1),rs.getString(2)));
			
			deficienciaDAO.importar(listDeficiencia);
			logger.info("[R022DEF] Deficiencias importados/atualizados - " + new Date());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("DEFICIENCIAS não FORAM IMPORTADOS.");
			return false;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
	}
	
	/*Importar vinculos*/
	public boolean importarVinculo() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		List<Vinculo> listVinculo = new LinkedList<Vinculo>();

		try {
			conn = connectSqlServer.getConnection();
			String sql = "select CODVIN, DESVIN from R022VIN";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) 
				listVinculo.add(new Vinculo(rs.getInt(1),rs.getString(2)));
			
			vinculoDAO.importar(listVinculo);
			logger.info("[R022VIN] Vinculos importados/atualizados - " + new Date());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("VINCULOS não FORAM IMPORTADOS.");
			return false;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
	}
	
	/*Importar Bairros*/
	public boolean importarBairros() {
      bairroDAO.removerBairros();
      Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		List<Bairro> listBairro = new LinkedList<Bairro>();
    
		try {
			conn = connectSqlServer.getConnection();
			String sql = "select codBai, codCid, nomBai from R074BAI";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) 
				listBairro.add(new Bairro(rs.getInt(1), cidadeDAO.loadById(rs.getInt(2)), rs.getString(3)));
			
			bairroDAO.importar(listBairro);
			logger.info("[R074BAI] Bairros importados - " + new Date());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("BAIRROS NÃO FORAM REMOVIDOS E IMPORTADOS NOVAMENTE.");
			return false;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
	}
	
	/*Importar Escala vale Transporte	*/
	public boolean importaEscalaValeTransporte() {
    	Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		List<EscalaVT> listEscalaValeTransporte = new LinkedList<EscalaVT>();

		try {
			conn = connectSqlServer.getConnection();
			String sql = "select ESCVTR, NOMEVT from R028EVT";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while (rs.next()) 
				listEscalaValeTransporte.add(new EscalaVT(rs.getInt(1),rs.getString(2)));
			
			escalaVTDAO.importar(listEscalaValeTransporte);
			logger.info("[R028EVT] Escala Vale Transporte importados/atualizados - " + new Date());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("ESCALA VALE TRANSPORTE NÃO FORAM IMPORTADOS.");
			return false;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
	}

	/*Importar PostoTrabalho*/
	public boolean importarPostoTrabalho() {
		
	

		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		List<PostoTrabalho> listPostoTrabalho = new LinkedList<PostoTrabalho>();
		try {
			if(!postoTrabalhoDAO.listAll("posTra").isEmpty())
					postoTrabalhoDAO.removerPostoTrabalho();
			
			conn = connectSqlServer.getConnection();
			String sql = "select ESTPOS, POSTRA, DESPOS from R017POS WHERE ESTPOS = 2";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) 
				listPostoTrabalho.add(new PostoTrabalho(rs.getString(2),rs.getString(3),rs.getInt(1)));
			
			postoTrabalhoDAO.importar(listPostoTrabalho);
			logger.info("[R017POS] Posto de Trabalho importados/atualizados - " + new Date());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("POSTOS DE TRABALHO NãO FORAM REMOVIDO E IMPORTADOS NOVAMENTE.");
			return false;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
	}

	/*Importar naturezaDespesa*/
	public boolean importarNaturezaDespesa() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		List<NaturezaDespesa> listNaturezaDespesa = new LinkedList<NaturezaDespesa>();

		try {
			conn = connectSqlServer.getConnection();
			String sql = "select NATDES, NOMNAT from R048nat";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) 
				listNaturezaDespesa.add(new NaturezaDespesa(rs.getInt(1),rs.getString(2)));
			
			naturezaDespesaDAO.importar(listNaturezaDespesa);
			logger.info("[R048NAT] NaturezaDespesa importados/atualizados - " + new Date());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("NaturezaDespesa não FORAM IMPORTADOS.");
			return false;
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
	}
	
	public boolean importarSeqenciaRegistro() {
		List<SequenciaRegistro> list_SequenciaRegistro = new LinkedList<SequenciaRegistro>();
		int qtdReqreg = retMaiorSeqReg();
		for (int x = 1; x <= qtdReqreg; x++)
			list_SequenciaRegistro.add(new SequenciaRegistro(x));
		try {

			sequenciaRegistroDAO.importar(list_SequenciaRegistro);
			logger.info("SEQUENCIA importados/atualizados - " + new Date());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("SEQUENCIA NãO FORAM IMPORTADOS.");
			return false;
		}
	}

	private int retMaiorSeqReg() {
		int maiorSeqReg = 0;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			conn = connectSqlServer.getConnection();
			String sql = "select MAX(seqReg) from r006hor";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.next())
				maiorSeqReg = rs.getInt(1);

		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("NÃO FOI POSSIVEL RETORNAR O MAIOR NUMERO SEQUENCIAL PARA TABELA DE HORARIOS ESCALA");
		} finally {
			connectSqlServer.closeConnection(conn, stmt, rs);
		}
     
		return maiorSeqReg;
	}
	
	/*---------------------------------------------------------------------------------------------------------------------------------------*/
	private void registraLogError(String mensagem) {
		Logger logger = Logger.getLogger(Importer.class);
		logger.error(mensagem);
	}

	public void removeColaborador(Colaborador colaborador) throws Exception {
		colaboradorDAO.delete(colaborador);
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
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

	public AfastamentoDAO getAfastamentoDAO() {
		return afastamentoDAO;
	}

	public void setAfastamentoDAO(AfastamentoDAO afastamentoDAO) {
		this.afastamentoDAO = afastamentoDAO;
	}

	public BancoDAO getBancoDAO() {
		return bancoDAO;
	}

	public void setBancoDAO(BancoDAO bancoDAO) {
		this.bancoDAO = bancoDAO;
	}

	public CargoDAO getCargoDAO() {
		return cargoDAO;
	}

	public void setCargoDAO(CargoDAO cargoDAO) {
		this.cargoDAO = cargoDAO;
	}

	public CentroDAO getCentroDAO() {
		return centroDAO;
	}

	public void setCentroDAO(CentroDAO centroDAO) {
		this.centroDAO = centroDAO;
	}

	public CidadeDAO getCidadeDAO() {
		return cidadeDAO;
	}

	public void setCidadeDAO(CidadeDAO cidadeDAO) {
		this.cidadeDAO = cidadeDAO;
	}

	public ClienteDAO getClienteDAO() {
		return clienteDAO;
	}

	public void setClienteDAO(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}

	public CondicaoPagamentoDAO getCondicaoPagamentoDAO() {
		return condicaoPagamentoDAO;
	}

	public void setCondicaoPagamentoDAO(CondicaoPagamentoDAO condicaoPagamentoDAO) {
		this.condicaoPagamentoDAO = condicaoPagamentoDAO;
	}

	public EmpresaDAO getEmpresaDAO() {
		return empresaDAO;
	}

	public void setEmpresaDAO(EmpresaDAO empresaDAO) {
		this.empresaDAO = empresaDAO;
	}

	public SolicitacaoDAO getSolicitacaoDAO() {
		return solicitacaoDAO;
	}

	public void setSolicitacaoDAO(SolicitacaoDAO solicitacaoDAO) {
		this.solicitacaoDAO = solicitacaoDAO;
	}

	public EscalaRondaDAO getEscalaRondaDAO() {
		return escalaRondaDAO;
	}

	public void setEscalaRondaDAO(EscalaRondaDAO escalaRondaDAO) {
		this.escalaRondaDAO = escalaRondaDAO;
	}

	public EstoqueDAO getEstoqueDAO() {
		return estoqueDAO;
	}

	public void setEstoqueDAO(EstoqueDAO estoqueDAO) {
		this.estoqueDAO = estoqueDAO;
	}

	public FornecedorDAO getFornecedorDAO() {
		return fornecedorDAO;
	}

	public void setFornecedorDAO(FornecedorDAO fornecedorDAO) {
		this.fornecedorDAO = fornecedorDAO;
	}

	public GrauInstrucaoDAO getGrauInstrucaoDAO() {
		return grauInstrucaoDAO;
	}

	public void setGrauInstrucaoDAO(GrauInstrucaoDAO grauInstrucaoDAO) {
		this.grauInstrucaoDAO = grauInstrucaoDAO;
	}

	public LocalDAO getLocalDAO() {
		return localDAO;
	}

	public void setLocalDAO(LocalDAO localDAO) {
		this.localDAO = localDAO;
	}

	public MotivoDAO getMotivoDAO() {
		return motivoDAO;
	}

	public void setMotivoDAO(MotivoDAO motivoDAO) {
		this.motivoDAO = motivoDAO;
	}

	public ProdutoDAO getProdutoDAO() {
		return produtoDAO;
	}

	public void setProdutoDAO(ProdutoDAO produtoDAO) {
		this.produtoDAO = produtoDAO;
	}

	public TipoEPIDAO getTipoEPIDAO() {
		return tipoEPIDAO;
	}

	public void setTipoEPIDAO(TipoEPIDAO tipoEPIDAO) {
		this.tipoEPIDAO = tipoEPIDAO;
	}

	public UniformeDAO getUniformeDAO() {
		return uniformeDAO;
	}

	public void setUniformeDAO(UniformeDAO uniformeDAO) {
		this.uniformeDAO = uniformeDAO;
	}

	public NacionalidadeDAO getNacionalidadeDAO() {
		return nacionalidadeDAO;
	}

	public void setNacionalidadeDAO(NacionalidadeDAO nacionalidadeDAO) {
		this.nacionalidadeDAO = nacionalidadeDAO;
	}

	public ClasseEscalaRondaDAO getClasseEscalaRondaDAO() {
		return classeEscalaRondaDAO;
	}

	public void setClasseEscalaRondaDAO(ClasseEscalaRondaDAO classeEscalaRondaDAO) {
		this.classeEscalaRondaDAO = classeEscalaRondaDAO;
	}

	public HorarioRondaDAO getHorarioRondaDAO() {
		return horarioRondaDAO;
	}

	public void setHorarioRondaDAO(HorarioRondaDAO horarioRondaDAO) {
		this.horarioRondaDAO = horarioRondaDAO;
	}

	public EscalaHorarioRondaMZDAO getEscalaHorarioRondaMZDAO() {
		return escalaHorarioRondaMZDAO;
	}

	public void setEscalaHorarioRondaMZDAO(EscalaHorarioRondaMZDAO escalaHorarioRondaMZDAO) {
		this.escalaHorarioRondaMZDAO = escalaHorarioRondaMZDAO;
	}

	public HorarioEscalaRondaDAO getHorarioEscalaRondaDao() {
		return horarioEscalaRondaDao;
	}

	public void setHorarioEscalaRondaDao(HorarioEscalaRondaDAO horarioEscalaRondaDao) {
		this.horarioEscalaRondaDao = horarioEscalaRondaDao;
	}

	public TurmaHorarioRondaDAO getTurmaHorarioRondaDAO() {
		return turmaHorarioRondaDAO;
	}

	public void setTurmaHorarioRondaDAO(TurmaHorarioRondaDAO turmaHorarioRondaDAO) {
		this.turmaHorarioRondaDAO = turmaHorarioRondaDAO;
	}

	public SequenciaRegistroDAO getSequenciaRegistroDAO() {
		return sequenciaRegistroDAO;
	}

	public void setSequenciaRegistroDAO(SequenciaRegistroDAO sequenciaRegistroDAO) {
		this.sequenciaRegistroDAO = sequenciaRegistroDAO;
	}

}
