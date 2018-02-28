package action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.StreamResult;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import result.BasicResult;
import util.Util;
import bean.Afastamento;
import bean.Bairro;
import bean.Banco;
import bean.Cargo;
import bean.Centro;
import bean.Colaborador;
import bean.Deficiencia;
import bean.Dependente;
import bean.EscalaHorarioRondaMZ;
import bean.EscalaVT;
import bean.GrauInstrucao;
import bean.Local;
import bean.NaturezaDespesa;
import bean.PostoTrabalho;
import bean.Sindicato;
import bean.Usuario;
import bean.Vinculo;
import com.opensymphony.xwork2.ActionSupport;
import dao.AfastamentoDAO;
import dao.BairroDAO;
import dao.CargoDAO;
import dao.CentroDAO;
import dao.ColaboradorDAO;
import dao.DeficienciaDAO;
import dao.EscalaHorarioRondaMZDAO;
import dao.EscalaVTDAO;
import dao.LocalDAO;
import dao.NaturezaDespesaDAO;
import dao.PostoTrabalhoDAO;
import dao.SindicatoDAO;
import dao.SolicitacaoDAO;
import dao.VinculoDAO;

@Results({@Result(name = "resultBasicCol", value = "xmlModel", type = BasicResult.class),
			 @Result(name = "sucTxt", value = "inputStream", type = StreamResult.class, params = {"inputName", "inputStream" }),
			 @Result(name = "input", value = "inputStream", type = StreamResult.class, params = {"inputName", "inputStream" }) })

public class ColaboradorAction extends ActionSupport {
	private static final long			serialVersionUID	= 1L;
	private int								id;
	private Colaborador					colaborador;
	private List<Colaborador>			colaboradores;
	private Object							xmlModel;
	private String							totalCount;
	private InputStream					inputStream;
	private Class							tipo					= Colaborador.class;
	private boolean						notFindDemitidos;
	private Date							datIni;
	private Date							datFin;
	private String							tipoArquivo;
	private Centro							centro;
	private List<Dependente> dependentes = new ArrayList<Dependente>();
	
	@Autowired
	@Qualifier("HColaboradorDAO")
	private ColaboradorDAO				colaboradorDAO;
	@Autowired
	@Qualifier("HSolicitacaoDAO")
	private SolicitacaoDAO				solicitacaoDAO;
	@Autowired
	@Qualifier("HLocalDAO")
	private LocalDAO						localDAO;
	@Autowired
	@Qualifier("HAfastamentoDAO")
	private AfastamentoDAO				afastamentoDAO;
	@Autowired
	@Qualifier("HCargoDAO")
	private CargoDAO						cargoDAO;
	@Autowired
	@Qualifier("HCentroDAO")
	private CentroDAO						centroDAO;
	@Autowired
	@Qualifier("Util")
	private Util							util;

	@Autowired
	@Qualifier("HEscalaHorarioRondaMZDAO")
	private EscalaHorarioRondaMZDAO	escalaHorarioRondaMZDAO;

	@Autowired
	@Qualifier("HSindicatoDAO")
	private SindicatoDAO	sindicatoDAO;
	
	@Autowired
	@Qualifier("HPostoTrabalhoDAO")
	private PostoTrabalhoDAO	postoTrabalhoDAO;
	
	@Autowired
	@Qualifier("HNaturezaDespesaDAO")
	private NaturezaDespesaDAO	naturezaDespesaDAO;
	
	@Autowired
	@Qualifier("HVinculoDAO")
	private VinculoDAO	vinculoDAO;
		
	@Autowired
	@Qualifier("HDeficienciaDAO")
	private DeficienciaDAO	deficienciaDAO;

	@Autowired
	@Qualifier("HEscalaVTDAO")
	private EscalaVTDAO	escalaVTDAO;
	
	@Autowired
	@Qualifier("HBairroDAO")
	private BairroDAO	bairroDAO;
	
	public List<Dependente> getDependentes() {
		return dependentes;
	}

	public void setDependentes(List<Dependente> dependentes) {
		this.dependentes = dependentes;
	}

	public Centro getCentro() {
		return centro;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
	}

	public Date getDatIni() {
		return datIni;
	}

	public String getTipoArquivo() {
		return tipoArquivo;
	}

	public void setTipoArquivo(String tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}

	public void setDatIni(Date datIni) {
		this.datIni = datIni;
	}

	public Date getDatFin() {
		return datFin;
	}

	public void setDatFin(Date datFin) {
		this.datFin = datFin;
	}

	public boolean isNotFindDemitidos() {
		return notFindDemitidos;
	}

	public void setNotFindDemitidos(boolean notFindDemitidos) {
		this.notFindDemitidos = notFindDemitidos;
	}

	public Class getTipo() {
		return tipo;
	}

	public void setTipo(Class tipo) {
		this.tipo = tipo;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public List<Colaborador> getColaboradores() {
		return colaboradores;
	}

	public int getId() {
		return id;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public Object getXmlModel() {
		return xmlModel;
	}

	public String preCadastro() {
		return "PRE";
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public void setColaboradores(List<Colaborador> colaboradores) {
		this.colaboradores = colaboradores;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public void setXmlModel(Object xmlModel) {
		this.xmlModel = xmlModel;
	}

	public ColaboradorDAO getColaboradorDAO() {
		return colaboradorDAO;
	}

	public void setColaboradorDAO(ColaboradorDAO colaboradorDAO) {
		this.colaboradorDAO = colaboradorDAO;
	}

	public SolicitacaoDAO getSolicitacaoDAO() {
		return solicitacaoDAO;
	}

	public void setSolicitacaoDAO(SolicitacaoDAO solicitacaoDAO) {
		this.solicitacaoDAO = solicitacaoDAO;
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

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public String excluir() {
		if (solicitacaoDAO.litAllByCol(this.id).size() > 0) {
			inputStream = new ByteArrayInputStream(("UNI").getBytes());
		} else {
			try {
				colaboradorDAO.delete(colaboradorDAO.loadById(this.id));
				inputStream = new ByteArrayInputStream(("OK").getBytes());
			} catch (Exception e) {
				inputStream = new ByteArrayInputStream(("ERR").getBytes());
			}
		}
		return "sucTxt";

	}
	
	public void listDemitidos() {
		try {
			List<Colaborador> colaboradores = colaboradorDAO.listDemitidos(datIni, datFin, centro);
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Map parametros = new HashMap();
			parametros.put("periodo", format.format(datIni) + " atÈ " + format.format(datFin));
			util.geraRelatorio(colaboradores, "Demitidos.jasper", "Demitidos", parametros, tipoArquivo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String listAllXML() throws Exception {

		Map<String, Object> parametros = new HashMap<String, Object>();
		if (colaborador.getId() > 0)
			parametros.put("id", colaborador.getId());

		if ((colaborador.getNomFun() != null) && (!colaborador.getNomFun().equals(""))) {
			parametros.put("nomFun", colaborador.getNomFun());
		}
		if (this.isNotFindDemitidos()) {// n√£o trazer demitidos com a situa√ß√£oo 7
			parametros.put("notFindDemitidos", 7);
		}
		parametros.put("usuario", ((Usuario) util.recuperaSessao("Usuario")).getId());
		colaboradores = colaboradorDAO.busca(parametros);
		setXmlModel(colaboradores);
		return "resultBasicCol";
	}

	public String integrar() {
		try {
			this.colaborador.setSitAdm("G");
			this.colaborador.setDatInc(Calendar.getInstance().getTime());
			
			// normaliza o cpf do colaborador, tirando todos os caracteres n√£o num√©ricos
			String cpf = this.colaborador.getNumCpf();
			cpf = cpf.replaceAll("[^0-9]", "");
			char i = cpf.charAt(0);
			if (i == '0') {
				cpf = cpf.replaceFirst("0", "");
			}
			this.colaborador.setNumCpf(cpf);
			
			// Se pagamento em cheque banco 9999
			if (this.colaborador.isPgtChq()) {
				this.colaborador.setBanco(new Banco(9999));
			}
			
			Usuario usuario = (Usuario) util.recuperaSessao("Usuario");
			this.colaborador.setUsuCad(usuario);
			
			// Motivo = 1 substituicao, configura o mesmo local para o novo colaborador
			if ((this.colaborador.getMotivo().getId() == 1) && (this.colaborador.isMesmoLocal())) {
				Colaborador colAnterior = colaboradorDAO.loadById(this.colaborador.getColabSubs().getId());
				this.colaborador.setLocal(colAnterior.getLocal());
			}
			// motivo -2 implanta√ß√£o
			if (this.colaborador.getMotivo().getId() == 2) {
				this.colaborador.setColabSubs(null);
			}
			this.colaborador.setLocPai(util.geraLocalPai(this.colaborador.getLocal().getId().getNumLoc()));

			//busca local cadastrado
			Local local = new Local();
			local = localDAO.loadById(this.colaborador.getLocal().getId());
			this.colaborador.setLocal(local);
			
			//busca afastamento cadastrado
			Afastamento afastamento = new Afastamento();
			afastamento = afastamentoDAO.loadById(1000);// afastamento 1000 aguardando adimissÔøΩo 
			this.colaborador.setAfastamento(afastamento);

			//busca cargo cadastrado
			Cargo cargo = new Cargo();
			this.colaborador.getCargo().getId().setEstCar(1);
			cargo = cargoDAO.loadById(this.getColaborador().getCargo().getId());
			this.getColaborador().setCargo(cargo);

			//busca escala cadastrada
			EscalaHorarioRondaMZ escMz = new EscalaHorarioRondaMZ();
			escMz = escalaHorarioRondaMZDAO.loadById(this.getColaborador().getEscalaHorarioRondaMZ().getId());
			this.getColaborador().setEscalaHorarioRondaMZ(escMz);

			//busca sindicato cadastrado
			Sindicato sindicato = new Sindicato();
			sindicato = sindicatoDAO.loadById(this.getColaborador().getSindicato().getCodSin());
			this.getColaborador().setSindicato(sindicato);
			  
			//Busca Deficiencia Cadastrado
			Deficiencia deficiencia = new Deficiencia();
			if(this.getColaborador().getDefFis().getId().equalsIgnoreCase("S")){
				deficiencia = deficienciaDAO.loadById(this.getColaborador().getDeficiencia().getCodDef());
				this.getColaborador().setDeficiencia(deficiencia);
			}

			//Busca Vinculo Cadastrado
			Vinculo vinculo = new Vinculo();
			vinculo = vinculoDAO.loadById(this.getColaborador().getVinculo().getCodVin());
			this.getColaborador().setVinculo(vinculo);

			//Busca Natureza de Despesa cadastrada
			NaturezaDespesa naturezaDespesa = new NaturezaDespesa();	
			naturezaDespesa = naturezaDespesaDAO.loadById(this.getColaborador().getNaturezaDespesa().getNatDes());
			this.getColaborador().setNaturezaDespesa(naturezaDespesa);
			
			//busca posto Trabalho cadastrado
			PostoTrabalho postoTrabalho = new PostoTrabalho();
			postoTrabalho = postoTrabalhoDAO.retPosTraBd(this.getColaborador().getPostoTrabalho().getPosTra());
			this.getColaborador().setPostoTrabalho(postoTrabalho);			  

			//busca bairro Colaborador
			Bairro bairro = new Bairro();
			bairro = bairroDAO.listAllByCid(this.colaborador.getCidEnd().getId(), this.colaborador.getBairro().getId().getCodBai());
			this.getColaborador().setBairro(bairro);
			
			//busca Escala VT
			if(this.getColaborador().getMeiTrans().equals("O")){
	      EscalaVT escalaVT = new EscalaVT();
	      escalaVT = escalaVTDAO.loadById(this.getColaborador().getEscalaVT().getEscVtr());
			this.getColaborador().setEscalaVT(escalaVT);
			}
			this.colaboradorDAO.update(this.colaborador);
			System.out.println("IntegraÁ„o de: " + this.colaborador.getNomFun()	+ " pelo usuario " + this.getColaborador().getUsuCad().getNomFun());

			inputStream = new ByteArrayInputStream(("{success:true, msg: 'REALIZADO INTEGRA«√O, AGUARDE A MATRIZ HOMOLOGAR A ADIMISS√ÉO'}").getBytes());
			this.colaborador = new Colaborador();
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(("{success:false, msg: 'ERRO NA INTEGRA«√O: " + e.getMessage() + "'}").getBytes());
		}
		return "sucTxt";
	}

	public String gravar() {
		try {			
			
			this.colaborador.setSitAdm("C");
			this.colaborador.setDatInc(Calendar.getInstance().getTime());

			// normaliza o cpf do colaborador, tirando todos os caracteres n√£o num√©ricos
			String cpf = this.colaborador.getNumCpf();
			
			cpf = cpf.replaceAll("[^0-9]", "");
			char i = cpf.charAt(0);
			if (i == '0') {
				cpf = cpf.replaceFirst("0", "");
			}
			this.colaborador.setNumCpf(cpf);
			
			// Se pagamento em cheque banco 9999
			if (this.colaborador.isPgtChq()) {
				this.colaborador.setBanco(new Banco(9999));
			}
			
			Usuario usuario = (Usuario) util.recuperaSessao("Usuario");
			this.colaborador.setUsuCad(usuario);
			
			// Motivo = 1 substituicao, configura o mesmo local para o novo colaborador
			if ((this.colaborador.getMotivo().getId() == 1) && (this.colaborador.isMesmoLocal())) {
				Colaborador colAnterior = colaboradorDAO.loadById(this.colaborador.getColabSubs().getId());
				this.colaborador.setLocal(colAnterior.getLocal());
			}
			// motivo -2 implanta√ß√£o
			if (this.colaborador.getMotivo().getId() == 2) {
				this.colaborador.setColabSubs(null);
			}
			this.colaborador.setLocPai(util.geraLocalPai(this.colaborador.getLocal().getId().getNumLoc()));

			//busca local cadastrado
			Local local = new Local();
			local = localDAO.loadById(this.colaborador.getLocal().getId());
			this.colaborador.setLocal(local);
			
			//busca afastamento cadastrado
			Afastamento afastamento = new Afastamento();
			afastamento = afastamentoDAO.loadById(1000);// afastamento 1000 aguardando adimissÔøΩo 
			this.colaborador.setAfastamento(afastamento);

			//busca cargo cadastrado
			Cargo cargo = new Cargo();
			this.colaborador.getCargo().getId().setEstCar(1);
			cargo = cargoDAO.loadById(this.getColaborador().getCargo().getId());
			this.getColaborador().setCargo(cargo);

			//busca escala cadastrada
			EscalaHorarioRondaMZ escMz = new EscalaHorarioRondaMZ();
			escMz = escalaHorarioRondaMZDAO.loadById(this.getColaborador().getEscalaHorarioRondaMZ().getId());
			this.getColaborador().setEscalaHorarioRondaMZ(escMz);

			//busca sindicato cadastrado
			Sindicato sindicato = new Sindicato();
			sindicato = sindicatoDAO.loadById(this.getColaborador().getSindicato().getCodSin());
			this.getColaborador().setSindicato(sindicato);
			  
			//Busca Deficiencia Cadastrado
			Deficiencia deficiencia = new Deficiencia();
			if(this.getColaborador().getDefFis().getId().equalsIgnoreCase("S")){
				deficiencia = deficienciaDAO.loadById(this.getColaborador().getDeficiencia().getCodDef());
				this.getColaborador().setDeficiencia(deficiencia);
			}

			//Busca Vinculo Cadastrado
			Vinculo vinculo = new Vinculo();
			vinculo = vinculoDAO.loadById(this.getColaborador().getVinculo().getCodVin());
			this.getColaborador().setVinculo(vinculo);

			//Busca Natureza de Despesa cadastrada
			NaturezaDespesa naturezaDespesa = new NaturezaDespesa();	
			naturezaDespesa = naturezaDespesaDAO.loadById(this.getColaborador().getNaturezaDespesa().getNatDes());
			this.getColaborador().setNaturezaDespesa(naturezaDespesa);
			
			//busca posto Trabalho cadastrado
			PostoTrabalho postoTrabalho = new PostoTrabalho();
			postoTrabalho = postoTrabalhoDAO.retPosTraBd(this.getColaborador().getPostoTrabalho().getPosTra());
			this.getColaborador().setPostoTrabalho(postoTrabalho);			  

			//busca bairro Colaborador
			Bairro bairro = new Bairro();
			bairro = bairroDAO.listAllByCid(this.colaborador.getCidEnd().getId(), this.colaborador.getBairro().getId().getCodBai());
			this.getColaborador().setBairro(bairro);
			
			//busca Escala VT
			if(this.getColaborador().getMeiTrans().equals("O")){
	      EscalaVT escalaVT = new EscalaVT();
	      escalaVT = escalaVTDAO.loadById(this.getColaborador().getEscalaVT().getEscVtr());
			this.getColaborador().setEscalaVT(escalaVT);
			}
			
			/*Adicionando Dependentes ao colaborador*/
			if(!dependentes.isEmpty()){
				for(Dependente dependente : dependentes){
					dependente.setColaborador(colaborador);/*
					System.out.println("\nNome Dependente:"+dependente.getNomDep());
					System.out.println("Nome Colaborador:"+dependente.getColaborador().getNomFun()+" DtAdm"+dependente.getColaborador().getDatAdm());
					System.out.println("Data Nascimento:"+dependente.getDatNas());
					System.out.println("Data Invalidez:"+dependente.getDatInv());
					System.out.println("Data Inicio Tutela:"+dependente.getIniTut());
					System.out.println("Data Fim Tutela:"+dependente.getDatTut());
					System.out.println("Nome Mae: "+dependente.getNomMae());
					System.out.println("Aviso Impresso: "+dependente.getAviImp());
					System.out.println("Auxilio Cre:"+dependente.getAuxCre());
					System.out.println("Pens„o Judicial: "+dependente.getPenJud());
					System.out.println("Sexo: "+dependente.getSexo());
					System.out.println("Estado Civil: "+dependente.getEstCiv());
					System.out.println("ESocial: "+dependente.getTipoDependenteESocial());
					System.out.println("Grau de Parentesco: "+dependente.getGrauParantesco());
					System.out.println("InstruÁ„o: "+dependente.getGrauInstrucao().getId());
					System.out.println("limIRF: "+dependente.getLimIrf()+", limSaf: "+dependente.getLimSaf()+", nomCre: "+dependente.getNomCre()+", numCpf: "+dependente.getNumCpf());
					System.out.println("");*/
					
					colaborador.getDependentes().add(dependente);					
				}
			}
			
			if (this.colaborador.getId() == 0) {
				colaboradorDAO.insert(this.colaborador);
				System.out.println("Colaborador inserido com sucesso: " + this.colaborador.getNomFun()	+ " pelo usuario " + this.getColaborador().getUsuCad().getNomFun());
			} else {
				colaboradorDAO.update(this.colaborador);
				System.out.println("Colaborador atualizado com sucesso: " + this.colaborador.getNomFun()	+ " pelo usuario " + this.getColaborador().getUsuCad().getNomFun());
			}
			
			inputStream = new ByteArrayInputStream(("{success:true, msg: 'CADASTRO REALIZADO COM SUCESSO'}").getBytes());
			this.colaborador = new Colaborador();
		} catch (ConstraintViolationException e) {
			inputStream = new ByteArrayInputStream(("{success:false, msg: 'JA EXISTE UM COLABORADOR COM ESTE CPF :"+e.getMessage()+"'}").getBytes());
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream(("{success:false, msg: 'CADASTRO NAO FOI REALIZADO : " + e.getMessage() + "'}").getBytes());
		}
		return "sucTxt";
	}

	public String listAllSupervisoresXML() throws Exception {
		List<Colaborador> supervisores = new ArrayList<Colaborador>();
		if (id > 0)
			supervisores.add(colaboradorDAO.loadById(id));
		else
			supervisores = colaboradorDAO.listSupervisores();
		this.totalCount = String.valueOf(supervisores.size());
		setXmlModel(supervisores);
		return "resultBasicCol";
	}

	@Override
	public void validate() {
		System.out.println(this.getColaborador().toString());
		
	}




	
	
	
	
}
