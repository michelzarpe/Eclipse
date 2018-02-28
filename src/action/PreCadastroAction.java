package action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.StreamResult;
import org.apache.struts2.views.tiles.TilesResult;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import util.Sexo;
import util.Util;
import bean.Afastamento;
import bean.Cargo;
import bean.Centro;
import bean.Colaborador;
import bean.Empresa;
import bean.Escala;
import bean.Hierarquia;
import bean.Local;
import bean.Usuario;

import com.opensymphony.xwork2.ActionSupport;

import dao.AfastamentoDAO;
import dao.CargoDAO;
import dao.CentroDAO;
import dao.ColaboradorDAO;
import dao.EmpresaDAO;
import dao.EscalaDAO;
import dao.HierarquiaDAO;
import dao.LocalDAO;

@Results({
		@Result(name = "success", value = "tile.preCadastro", type = TilesResult.class),
		@Result(name = "input", value = "tile.preCadastro", type = TilesResult.class),
		@Result(name = "listAll", value = "/paginas/jsp/listaPreCadastro.jsp"),
		@Result(name = "sucTxt", value = "inputStream", type = StreamResult.class, params = {
				"inputName", "inputStream" }) })
public class PreCadastroAction extends ActionSupport {
	private static final long	serialVersionUID	= 1L;
	private Colaborador			colaborador;
	private int						id;
	private String					unidadeLocId		= "";
	private String					clienteLocId		= "";
	private String					cidadeLocId			= "";
	private String					locCid;
	private String					locCli;
	private InputStream			inputStream;
	private String					cargo;
	private String					local;
	@Autowired
	@Qualifier("HCentroDAO")
	private CentroDAO				centroDAO;
	@Autowired
	@Qualifier("HEmpresaDAO")
	private EmpresaDAO			empresaDAO;
	@Autowired
	@Qualifier("HEscalaDAO")
	private EscalaDAO				escalaDAO;
	@Autowired
	@Qualifier("HCargoDAO")
	private CargoDAO				cargoDAO;
	@Autowired
	@Qualifier("HColaboradorDAO")
	private ColaboradorDAO		colaboradorDAO;
	@Autowired
	@Qualifier("HLocalDAO")
	private LocalDAO				localDAO;
	@Autowired
	@Qualifier("HAfastamentoDAO")
	private AfastamentoDAO		afastamentoDAO;
	@Autowired
	@Qualifier("HHierarquiaDAO")
	private HierarquiaDAO		hierarquiaDAO;
	@Autowired
	@Qualifier("Util")
	private Util					util;

	public CentroDAO getCentroDAO() {
		return centroDAO;
	}

	public void setCentroDAO(CentroDAO centroDAO) {
		this.centroDAO = centroDAO;
	}

	public EmpresaDAO getEmpresaDAO() {
		return empresaDAO;
	}

	public void setEmpresaDAO(EmpresaDAO empresaDAO) {
		this.empresaDAO = empresaDAO;
	}

	public EscalaDAO getEscalaDAO() {
		return escalaDAO;
	}

	public void setEscalaDAO(EscalaDAO escalaDAO) {
		this.escalaDAO = escalaDAO;
	}

	public CargoDAO getCargoDAO() {
		return cargoDAO;
	}

	public void setCargoDAO(CargoDAO cargoDAO) {
		this.cargoDAO = cargoDAO;
	}

	public ColaboradorDAO getColaboradorDAO() {
		return colaboradorDAO;
	}

	public void setColaboradorDAO(ColaboradorDAO colaboradorDAO) {
		this.colaboradorDAO = colaboradorDAO;
	}

	public LocalDAO getLocalDAO() {
		return localDAO;
	}

	public void setLocalDAO(LocalDAO localDAO) {
		this.localDAO = localDAO;
	}

	public AfastamentoDAO getAfastamentoDAO() {
		return afastamentoDAO;
	}

	public void setAfastamentoDAO(AfastamentoDAO afastamentoDAO) {
		this.afastamentoDAO = afastamentoDAO;
	}

	public HierarquiaDAO getHierarquiaDAO() {
		return hierarquiaDAO;
	}

	public void setHierarquiaDAO(HierarquiaDAO hierarquiaDAO) {
		this.hierarquiaDAO = hierarquiaDAO;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getUnidadeLocId() {
		return unidadeLocId;
	}

	public void setUnidadeLocId(String unidadeLocId) {
		this.unidadeLocId = unidadeLocId;
	}

	public String getClienteLocId() {
		return clienteLocId;
	}

	public void setClienteLocId(String clienteLocId) {
		this.clienteLocId = clienteLocId;
	}

	public String getCidadeLocId() {
		return cidadeLocId;
	}

	public void setCidadeLocId(String cidadeLocId) {
		this.cidadeLocId = cidadeLocId;
	}

	public String getLocCid() {
		return locCid;
	}

	public void setLocCid(String locCid) {
		this.locCid = locCid;
	}

	public String getLocCli() {
		return locCli;
	}

	public void setLocCli(String locCli) {
		this.locCli = locCli;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public Sexo[] getSexo() {
		return Sexo.values();
	}

	public List<Centro> getCentros() throws Exception {
		return centroDAO.listAll("nomCcu");
	}

	public List<Empresa> getEmpresas() throws Exception {
		return empresaDAO.listAll("nomEmp");
	}

	public List<Escala> getEscalas() throws Exception {
		return escalaDAO.listAll("nomEsc");
	}

	public List<Cargo> getCargos() throws Exception {
		return cargoDAO.listAll("titCar");
	}

	public String gravar() {
		try {
			/** normaliza o cpf do colaborador, tirando todos os caracteres não numéricos **/
			String cpf = this.colaborador.getNumCpf();
			cpf = cpf.replaceAll("[^0-9]", "");

			long cpfL = Long.parseLong(cpf);

			cpf = Long.toString(cpfL);

			if (colaboradorDAO.loadByCPF(cpf) == null) {

				this.colaborador.setNumCpf(cpf);
				this.colaborador.setDatInc(Calendar.getInstance().getTime());
				this.colaborador.setLocPai(util.geraLocalPai(this.colaborador.getLocal().getId()
						.getNumLoc()));
				this.colaborador.setSitAdm("I");// incompleta

				Usuario usuario = (Usuario) util.recuperaSessao("Usuario");
				this.colaborador.setUsuCad(usuario);

				Local local = new Local();
				local = localDAO.loadById(this.colaborador.getLocal().getId());
				this.colaborador.setLocal(local);

				Afastamento afastamento = new Afastamento();
				afastamento = afastamentoDAO.loadById(1000);
				this.colaborador.setAfastamento(afastamento);

				Cargo cargo = new Cargo();
				this.colaborador.getCargo().getId().setEstCar(1);
				cargo = cargoDAO.loadById(this.getColaborador().getCargo().getId());
				this.getColaborador().setCargo(cargo);

				/**Centro centro = new Centro();
				centro = centroDAO.loadById(this.colaborador.getCentro().getId());
				this.colaborador.setCentro(centro);**/

				if (this.colaborador.getId() == 0) {
					colaboradorDAO.insert(this.colaborador);
					System.out.println("Pré cadastro do colaborador inserido com sucesso: "
							+ this.colaborador.getNomFun() + " pelo usuario "
							+ this.getColaborador().getUsuCad().getNomFun());
				} else {
					colaboradorDAO.update(this.colaborador);
					System.out.println("Pré cadastro do colaborador alterado com sucesso: "
							+ this.colaborador.getNomFun() + " pelo usuario "
							+ this.getColaborador().getUsuCad().getNomFun());
				}

				inputStream = new ByteArrayInputStream(
						("{success:true, msg: 'PRE-CADASTRO REALIZADO COM SUCESSO'}").getBytes());
				this.colaborador = new Colaborador();
			} else {
				inputStream = new ByteArrayInputStream(
						("{success:false, msg: 'PRE-CADASTRO NAO REALIZADO. ERRO: Ja existe um colaborador cadastrado com este CPF.'}")
								.getBytes());
			}
		} catch (ConstraintViolationException e) {
			inputStream = new ByteArrayInputStream(
					("{success:false, msg: \"" + e.getCause().getLocalizedMessage() + "\"}").getBytes());
		} catch (Exception e) {
			String msg = "PRE-CADASTRO NAO REALIZADO. ERRO: " + e.getCause();
			inputStream = new ByteArrayInputStream(
					("{success:false, msg: \"" + msg + "\"}").getBytes());
		}
		return "sucTxt";
	}

	public String editar() throws Exception {
		this.colaborador = colaboradorDAO.loadById(this.id);
		Hierarquia hierarquia = new Hierarquia();
		for (Hierarquia hie : colaborador.getLocal().getHierarquias()) {
			hierarquia = hie;
		}
		this.clienteLocId = hierarquia.getCodLoc().substring(0, 10);
		this.locCli = hierarquiaDAO.loadByCodLoc(this.clienteLocId).getLocal().getNomLoc();
		this.cidadeLocId = hierarquia.getCodLoc().substring(0, 15);
		this.locCid = hierarquiaDAO.loadByCodLoc(this.cidadeLocId).getLocal().getNomLoc();
		return SUCCESS;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}
}
