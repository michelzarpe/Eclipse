package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import util.Sexo;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamImplicitCollection;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import converter.DateConverter;



@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "numCad", "afastamentoId", "numCpf","empresaId" }) })
@XStreamAlias("colaborador")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@BatchSize(size = 10)
public class Colaborador implements Serializable {
	
	private static final long	serialVersionUID	= 1L;


	/*Dados pessoais-----------------------------------------------------------------*/
	@Id
	@GeneratedValue
	@XStreamAlias("colaboradorId")
	private int						id;
	
	@ManyToOne
	@JoinColumn(name = "afastamentoId", nullable = false)
	private Afastamento			afastamento;
	
	@Column(length = 40, nullable = false)
	@XStreamAsAttribute
	private String					nomFun;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	@XStreamConverter(DateConverter.class)
	private Date					datNas;
	
	@Column(length = 80)
	private String					nomPai;
	
	@Column(length = 80)
	private String					nomMae;
	
	@ManyToOne
	@JoinColumn(name = "cidadeNascId", nullable = true)
	private Cidade					cciNas;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 1, nullable = false)
	@XStreamAsAttribute
	private Sexo					sexo;
	
	@Column(nullable = true)
	private EstadoCivil			estCiv;
	
	@ManyToOne
	@JoinColumn(name = "nacionalidadeId", nullable = true)
	private Nacionalidade		nacionalidade;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = true)
	private int						numDep;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 3)
	private TipoSanguineo		tipSan;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = true)
	private RacaCor				racCor;
	
	@Column
	private String emaCom;
	
	@Column
	private String emaPar;
	
	@ManyToOne
	@JoinColumn(name = "graInsId", nullable = true)
	private GrauInstrucao		grauInstrucao;
	
	@Column(length = 200)
	private String	nomCur; // nome do curso superior
	
	@Column(length = 200)
	private String	nomIns; // instituicao do curso superior
	
	@Temporal(TemporalType.DATE)
	@XStreamConverter(DateConverter.class)
	@Column(nullable = true)
	private Date	datCon; //  dat, termino de conclusao de curso
	
	@Column(nullable = true)
	private LitSimNao defFis;   // colaborador possui deficiência
	
	@ManyToOne
	@JoinColumn(name = "codDef_Fk", nullable = true)
	private Deficiencia deficiencia; // Código da deficiência
	
	@Column
	private EstCtp estNas;
	
	@Column
	private PaiNas paiNas;

	/*----------------------------------------------------------------------------------*/
	/*-Endereço-------------------------------------------------------------------------*/
	
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "FK_CodBai", referencedColumnName = "codBai", nullable = true),
		@JoinColumn(name = "FK_CodCid", referencedColumnName = "codCid", nullable = true) })
	private Bairro	bairro;	
	
	@Column(length = 25)
	private String					endCpl;
	
	@Column(length = 6)
	private String					endNum;
	
	@Column(length = 60)
	private String					endRua;
	
	@ManyToOne
	@JoinColumn(name = "cidadeEndId", nullable = true)
	private Cidade					cidEnd;
	
	@Column
	private String 				estCid;
	
	@Column(length = 60)
	private String					baiEnd;
	
	@Column(length = 9)
	private String					cep;
	
	@Column
	private EstCtp 				codEst;
	
	@Column(length = 20)
	private String					nomCon;
	
	@Column(length = 20)
	private String					fonCon;
	
	@Column(length = 20)
	private String	numCel;
	
	@Column
	private Integer dddTel;
	
	@Column
	private Integer numDdd2;
	
	@Column
	private String numTel2;
	
	@Column(length = 20)
	private String numTel;
	/*----------------------------------------------------------------------------------*/
	/*Documentos------------------------------------------------------------------------*/
	@Column(length = 14, nullable = false)
	private String	numCpf;

	@Column(length = 15)
	private String	numCid;		// numero da carteira de identidade
	
	@Column(length = 6)
	private String	emiCid;		//n emissor da carteira de
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	@XStreamConverter(DateConverter.class)
	private Date	dexCid;	

	private Long	numCtp;
	
	@Column(nullable = true)
	private String	serCtp;
	
	@Column(nullable = true, precision = 0)
	private Long	numPis;
	
	@Column(length = 13)
	private String	numRes; // certificado de reservista
	
	@Column(length = 12)
	private String	numEle; // titulo de eleitor
   
	@Column(length = 3)
	private String	zonEle;
	
	@Column(length = 4)
	private String	secEle;
   
	@Column(length = 20)
	private String	numCnh;
	
	@Column(length = 4)
	private String	catCnh;
	/*----------------------------------------------------------------------------------*/
	/*Dados de Admissao-----------------------------------------------------------------*/
	@ManyToOne
	@JoinColumn(name = "escVtr_Fk", nullable = true)
	private EscalaVT escalaVT; // Escala vale transporte
	
	@ManyToOne
	@JoinColumn(name = "empresaId")
	@XStreamAlias("empresa")
	private Empresa				empresa;
	
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "codCar", nullable = true),@JoinColumn(name = "estCar", nullable = true) })
	private Cargo					cargo;
	
	@ManyToOne
	@JoinColumn(name = "motivoId")
	private Motivo					motivo;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	@XStreamConverter(DateConverter.class)
	private Date					datAdm;
	
	private float					valSal;
	
	@Column(nullable = false, columnDefinition = "FLOAT DEFAULT 0")
	private float					vlrAdi;
	
	@ManyToOne
	@JoinColumn(name = "colabSubsId", nullable = true)
	private Colaborador			colabSubs;
	
	@Column(nullable = true)
	private Integer perJur; // Percentual de Juros FGTS
	
	@Column(nullable = true)
	private String locTraHlo; // local de trabalho na raiz
	
	@Column(nullable = false, columnDefinition = "INT(11) DEFAULT 0")
	private int						qvtMes;
	
	@Column(nullable = false, columnDefinition = "FLOAT DEFAULT 0")
	private float					vvtMes;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private MeioTransporte		meiTrans;
	
	@Column(nullable = false, columnDefinition = "INT(11) DEFAULT 0")
	private int						qvtKm;
	
	@Column(nullable = false, columnDefinition = "FLOAT DEFAULT 0")
	private float					vlrCbt;
	
	@Column(nullable = true, columnDefinition = "TINYINT(1) DEFAULT 0")
	private boolean	pagSin;
	
	@ManyToOne
	@JoinColumn(name = "codSinHsi_Fk", nullable = true)
	private Sindicato sindicato; // codigo do sindicato
	
	@ManyToOne
	@JoinColumn(name = "idPosTra_Fk", nullable = true)
	private PostoTrabalho postoTrabalho; // Posto de trabalho do colaborador

	@ManyToOne
	@JoinColumn(name = "natDes_Fk", nullable = true)
	private NaturezaDespesa naturezaDespesa; // Codigo de natureza de despesa
	
	@ManyToOne
	@JoinColumn(name = "codVin_Fk", nullable = true)
	private Vinculo vinculo; // codigo de vinculo
	
	/*esocial*/
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = true)
	private AdmeSo admeSo; // Tipo Admissão eSocial
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = true)
	private CateSo cateSo; // categoria eSocial 
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = true)
	private CatAnt catAnt;// Categoria Anterior {Se(cateSo=410 ou cateSo=401) -> Se(cateSo=410) validos: 101,301,302,306 se Não demais valores}
	
	@Column(nullable = true)
	private Integer cnpjAn;  //CNPJ anterior, Se(admeSo = 2,3,4, cateSo = 410, catAnt = 101,102,103,104,105,106)
	
	@Column(nullable = true)
	private String matAnt; // matricula anterios, Se(admeSo = 2,3,4, cateSo = 410, catAnt = 101,102,103,104,105,106) 
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	@XStreamConverter(DateConverter.class)
	private Date admAnt; //Data Admissão Anterior, Se(admeSo = 2,3,4, cateSo = 410, catAnt = 101,102,103,104,105,106) 
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	@XStreamConverter(DateConverter.class)
	private Date iniEvt; // inicio da escala de vale transporte
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	@XStreamConverter(DateConverter.class)
	private Date fimEvt; // Data final da Escala de vale Transporte.
	
	
	/*----------------------------------------------------------------------------------*/
	/*EscalaMZ--------------------------------------------------------------------------*/
	@ManyToOne
	@JoinColumn(name = "escalaHorarioRondaId")
	@XStreamAlias("escalaHorarioRondaMZ")
	private EscalaHorarioRondaMZ escalaHorarioRondaMZ;
	/*----------------------------------------------------------------------------------*/
	/*Local-----------------------------------------------------------------------------*/
	@XStreamAsAttribute
	private boolean				mesmoLocal;
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "numLoc", nullable = true), @JoinColumn(name = "tabOrg", nullable = true) })
	private Local					local;
	
	/*----------------------------------------------------------------------------------*/
	/*Dados bancarios-------------------------------------------------------------------*/
	@XStreamAsAttribute
	private boolean				pgtChq;
	@XStreamAsAttribute
	private boolean				conPou;
	@ManyToOne
	@JoinColumn(name = "bancoId", nullable = true)
	private Banco					banco;
	@Column(length = 10, nullable = true)
	private String					codAge;
	@Column(nullable = true)
	private Long					conBan;
	@Column(length = 3)
	private String					digCon;
	@Column(length = 2)
	private String					digAge;
	/*----------------------------------------------------------------------------------*/
	/*Dados Vigilantes------------------------------------------------------------------*/
	@Column(length = 30)
	private String					numDRT;
	@Column(length = 25)
	private String					numDip;
	@Column(length = 10)
	private String					orgDip;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	@XStreamConverter(DateConverter.class)
	private Date					vctCNV;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	@XStreamConverter(DateConverter.class)
	private Date					datFor;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	@XStreamConverter(DateConverter.class)
	private Date					datRec;
	
	@XStreamAsAttribute
	private boolean				temCNV;
	@Column(length = 20, nullable = true)
	private String					numCnv;
	/*----------------------------------------------------------------------------------*/
	/*ObservAção-----------------------------------------------------------------------*/
	@Column(length = 400)
	private String					obs;
	/*----------------------------------------------------------------------------------*/
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "codFil", nullable = true),@JoinColumn(name = "empIdCliente", nullable = true) })
	private Cliente				cliente;
	
	@ManyToOne
	@JoinColumn(name = "escalaId")
	private Escala					escala;
	@ManyToOne
	@JoinColumn(name = "centroId", nullable = true)
	private Centro					centro;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = true)
	private TipoSalario			tipSal;							// SDF, horista ou mensalista
		
	@Column(length = 5)
	private String					horIni;
	@Column(length = 5)
	private String					horFin;
	@Column(length = 5)
	private String					horIni2;
	@Column(length = 5)
	private String					horFin2;
	@Column(length = 5)
	private String					horIniSab;
	@Column(length = 5)
	private String					horFinSab;
	@Column(length = 5)
	private String					horIniSab2;
	@Column(length = 5)
	private String					horFinSab2;
	@ManyToOne
	@JoinColumn(name = "usuCadId")
	@XStreamOmitField
	private Usuario				usuCad;
	private long					numCad;
	@Column(length = 120, nullable = true)
	private String					locPai;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	@XStreamConverter(DateConverter.class)
	private Date					datInc;
	private int						horInc;
	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	@XStreamConverter(DateConverter.class)
	private Date					datDem;
	@ManyToOne
	@JoinColumn(name = "escalaRondaId", nullable = true)
	@XStreamOmitField
	private EscalaRonda			escalaRonda;
	@Column(nullable = true)
	private int						funcao;
	@OneToMany(mappedBy = "colaborador", cascade = CascadeType.REMOVE)
	@Fetch(FetchMode.SUBSELECT)
	@XStreamOmitField
	private Set<Solicitacao>	solicitacoes		= new HashSet<Solicitacao>();
	@XStreamAsAttribute
	private boolean				supervisor;
	@Column(length = 1)
	private String					sitAdm;
	@XStreamAsAttribute
	private boolean				extSegPes;
	@XStreamAsAttribute
	private boolean				extTnsVal;
	@Column(length = 5)
	private String					horDia;														// carga horï¿½ria diï¿½ria
	@Column(length = 5)
	private String					horSem;														// carga horï¿½ria semanal
	@Column(length = 6)
	private String					horMes;														// carga horï¿½ria mensal

	/*------------------------Não ESTAO EM TELA- FIXO------------------------------------------*/
	@Column(nullable = true)
	private String tipOpc = "S";
	@Column(nullable = true)
	private String tipOpe = "I";
	@Column(nullable = true)
	private Integer turInt = 1; 
	@Column(nullable = true)
	private String verInt = "A";
	@Column(nullable = true)
	private Integer insCur = 0;
	@Column(nullable = true)
	private Integer conRho = 2;
	@Column(nullable = true)
	private Integer codTap = 1;
	@Column(nullable = true)
	private Integer apuPonApu = 1;
	@Column(nullable = true)
	private Integer codEstHsa = 1;
	@Column(nullable = true)
	private String codFicFmd ="S";
	@Column(nullable = true)
	private Integer tipCol = 1;
	@Column(nullable = true)
	private String movSef = " ";
	@Column(nullable = true)
	private Integer motPos = 1;
	@Column
	private Integer ddiTel = 55;
   @Column
   private Integer numDdi2  = 55;
	@Column
	private Integer codPai = 1;
/*-----------------Dados Complementares -----------------------------------------------------*/
	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	@XStreamConverter(DateConverter.class)
	private Date datOpc;   // Informar data de OpÃ§Ã£o pelo FGTS
	@Column(nullable = true)
	private Integer conFgt; // numero da Conta FGTS
	@Column(nullable = true)
	private Integer depIrf; // Quantidade dependentes de IR
	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	@XStreamConverter(DateConverter.class)
	private Date dexCtp;  // data de expedição da carteira de trabalho
	@Column(nullable = true)
	private String digCar;  //digito verificador carteira de trabalho
	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private EstCtp estCtp;  // UF {Estados}  
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = true)
	private CatSef catSef;
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = true)
	private CodEtb codEtb;// Estabilidade
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = true)
	private TipAdmHfi tipAdmHfi; // Tipo Admissão
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = true)
	private IndAdm indAdm; // Indicativo de Admissão
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = true)
	private TipApo tipApo; // Aposentadoria
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = true)
	private TipCon tipCon; //TipContrato
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = true)
	private OnuSce onuSce; // ônus da cesSão
	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private LitSimNao lisRai; // Constar na RAIS
	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private LitSimNao ratEve; // Entra no rateio de eventos 
	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private LitSimNao rec13S; // Recebe 13 salario
	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private LitSimNao recAdi; // Recebe Adiantamento salarial
	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private LitSimNao resOnu; // Ressarcimento ônus quando onuSce = 2, responde precisa do campo
	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private LitSimNao socSinHsi; //Sindicalizado
	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private LitSimNao emiCar; // Emitir cartï¿½o ponto
	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	@XStreamConverter(DateConverter.class)
	private Date iniEtbHeb; // data de inicio da estabilidade
	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	@XStreamConverter(DateConverter.class)
	private Date fimEtbHeb; // Data de fim da estabilidade
	

	/*-------------------DEPENDENTES----------------------------------------------------------*/
	@OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	@XStreamImplicit
   private Set<Dependente> dependentes = new HashSet<Dependente>();
	/*------------------------------------------------------------------------------------------*/

	
	public Colaborador() {

	}
	
	
	public Set<Dependente> getDependentes() {
		return dependentes;
	}


	public void setDependentes(Set<Dependente> dependentes) {
		this.dependentes = dependentes;
	}



	public EscalaVT getEscalaVT() {
		return escalaVT;
	}


	public void setEscalaVT(EscalaVT escalaVT) {
		this.escalaVT = escalaVT;
	}


	public Bairro getBairro() {
		return bairro;
	}


	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public CatAnt getCatAnt() {
		return catAnt;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public void setCatAnt(CatAnt catAnt) {
		this.catAnt = catAnt;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public AdmeSo getAdmeSo() {
		return admeSo;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public void setAdmeSo(AdmeSo admeSo) {
		this.admeSo = admeSo;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public CatSef getCatSef() {
		return catSef;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public void setCatSef(CatSef catSef) {
		this.catSef = catSef;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public CodEtb getCodEtb() {
		return codEtb;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public void setCodEtb(CodEtb codEtb) {
		this.codEtb = codEtb;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public TipAdmHfi getTipAdmHfi() {
		return tipAdmHfi;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public void setTipAdmHfi(TipAdmHfi tipAdmHfi) {
		this.tipAdmHfi = tipAdmHfi;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public IndAdm getIndAdm() {
		return indAdm;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public void setIndAdm(IndAdm indAdm) {
		this.indAdm = indAdm;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public TipApo getTipApo() {
		return tipApo;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public void setTipApo(TipApo tipApo) {
		this.tipApo = tipApo;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public EstCtp getEstCtp() {
		return estCtp;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public void setEstCtp(EstCtp estCtp) {
		this.estCtp = estCtp;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public CateSo getCateSo() {
		return cateSo;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public void setCateSo(CateSo cateSo) {
		this.cateSo = cateSo;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public TipCon getTipCon() {
		return tipCon;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public void setTipCon(TipCon tipCon) {
		this.tipCon = tipCon;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public OnuSce getOnuSce() {
		return onuSce;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public void setOnuSce(OnuSce onuSce) {
		this.onuSce = onuSce;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public LitSimNao getLisRai() {
		return lisRai;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public void setLisRai(LitSimNao lisRai) {
		this.lisRai = lisRai;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public LitSimNao getRatEve() {
		return ratEve;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public void setRatEve(LitSimNao ratEve) {
		this.ratEve = ratEve;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public LitSimNao getRec13S() {
		return rec13S;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public void setRec13S(LitSimNao rec13s) {
		rec13S = rec13s;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public LitSimNao getRecAdi() {
		return recAdi;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public void setRecAdi(LitSimNao recAdi) {
		this.recAdi = recAdi;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public LitSimNao getResOnu() {
		return resOnu;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public void setResOnu(LitSimNao resOnu) {
		this.resOnu = resOnu;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public LitSimNao getSocSinHsi() {
		return socSinHsi;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public void setSocSinHsi(LitSimNao socSinHsi) {
		this.socSinHsi = socSinHsi;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public LitSimNao getEmiCar() {
		return emiCar;
	}


	@TypeConversion(converter = "converter.EnumTypeConverter")
	public void setEmiCar(LitSimNao emiCar) {
		this.emiCar = emiCar;
	}
	
	public Date getAdmAnt() {
		return admAnt;
	}

	public void setAdmAnt(Date admAnt) {
		this.admAnt = admAnt;
	}

	public String getMatAnt() {
		return matAnt;
	}

	public void setMatAnt(String matAnt) {
		this.matAnt = matAnt;
	}

	public Integer getCnpjAn() {
		return cnpjAn;
	}

	public void setCnpjAn(Integer cnpjAn) {
		this.cnpjAn = cnpjAn;
	}


	public Deficiencia getCodDef() {
		return deficiencia;
	}

	public void setCodDef(Deficiencia deficiencia) {
		this.deficiencia = deficiencia;
	}

	public Integer getPerJur() {
		return perJur;
	}

	public void setPerJur(Integer perJur) {
		this.perJur = perJur;
	}


	public String getLocTraHlo() {
		return locTraHlo;
	}

	public void setLocTraHlo(String locTraHlo) {
		this.locTraHlo = locTraHlo;
	}

	public Integer getMotPos() {
		return motPos;
	}

	public void setMotPos(Integer motPos) {
		this.motPos = motPos;
	}

	public String getMovSef() {
		return movSef;
	}

	public void setMovSef(String movSef) {
		this.movSef = movSef;
	}


	public Deficiencia getDeficiencia() {
		return deficiencia;
	}

	public void setDeficiencia(Deficiencia deficiencia) {
		this.deficiencia = deficiencia;
	}

	public Sindicato getSindicato() {
		return sindicato;
	}

	public void setSindicato(Sindicato sindicato) {
		this.sindicato = sindicato;
	}


	public NaturezaDespesa getNaturezaDespesa() {
		return naturezaDespesa;
	}

	public void setNaturezaDespesa(NaturezaDespesa naturezaDespesa) {
		this.naturezaDespesa = naturezaDespesa;
	}

	public Vinculo getVinculo() {
		return vinculo;
	}

	public void setVinculo(Vinculo vinculo) {
		this.vinculo = vinculo;
	}

	public Date getIniEtbHeb() {
		return iniEtbHeb;
	}

	public void setIniEtbHeb(Date iniEtbHeb) {
		this.iniEtbHeb = iniEtbHeb;
	}

	public Date getIniEvt() {
		return iniEvt;
	}

	public void setIniEvt(Date iniEvt) {
		this.iniEvt = iniEvt;
	}

	public Date getFimEtbHeb() {
		return fimEtbHeb;
	}

	public void setFimEtbHeb(Date fimEtbHeb) {
		this.fimEtbHeb = fimEtbHeb;
	}

	public Date getFimEvt() {
		return fimEvt;
	}

	public void setFimEvt(Date fimEvt) {
		this.fimEvt = fimEvt;
	}

	public Date getDatOpc() {
		return datOpc;
	}

	public void setDatOpc(Date datOpc) {
		this.datOpc = datOpc;
	}

	public LitSimNao getDefFis() {
		return defFis;
	}

	public void setDefFis(LitSimNao defFis) {
		this.defFis = defFis;
	}

	public Integer getDepIrf() {
		return depIrf;
	}

	public void setDepIrf(Integer depIrf) {
		this.depIrf = depIrf;
	}

	public Date getDexCtp() {
		return dexCtp;
	}

	public void setDexCtp(Date dexCtp) {
		this.dexCtp = dexCtp;
	}

	public String getDigCar() {
		return digCar;
	}

	public void setDigCar(String digCar) {
		this.digCar = digCar;
	}

	public Integer getConFgt() {
		return conFgt;
	}

	public void setConFgt(Integer conFgt) {
		this.conFgt = conFgt;
	}

	public String getTipOpc() {
		return tipOpc;
	}

	public void setTipOpc(String tipOpc) {
		this.tipOpc = tipOpc;
	}

	public String getTipOpe() {
		return tipOpe;
	}




	public void setTipOpe(String tipOpe) {
		this.tipOpe = tipOpe;
	}




	public Integer getTurInt() {
		return turInt;
	}




	public void setTurInt(Integer turInt) {
		this.turInt = turInt;
	}




	public String getVerInt() {
		return verInt;
	}




	public void setVerInt(String verInt) {
		this.verInt = verInt;
	}




	public Integer getInsCur() {
		return insCur;
	}




	public void setInsCur(Integer insCur) {
		this.insCur = insCur;
	}




	public Integer getConRho() {
		return conRho;
	}




	public void setConRho(Integer conRho) {
		this.conRho = conRho;
	}




	public Integer getCodTap() {
		return codTap;
	}




	public void setCodTap(Integer codTap) {
		this.codTap = codTap;
	}




	public Integer getApuPonApu() {
		return apuPonApu;
	}




	public void setApuPonApu(Integer apuPonApu) {
		this.apuPonApu = apuPonApu;
	}




	public Integer getCodEstHsa() {
		return codEstHsa;
	}




	public void setCodEstHsa(Integer codEstHsa) {
		this.codEstHsa = codEstHsa;
	}




	public String getCodFicFmd() {
		return codFicFmd;
	}




	public void setCodFicFmd(String codFicFmd) {
		this.codFicFmd = codFicFmd;
	}




	public String getNumCnv() {
		return numCnv;
	}

	public void setNumCnv(String numCnv) {
		this.numCnv = numCnv;
	}

	public String getNumRes() {
		return numRes;
	}

	public void setNumRes(String numRes) {
		this.numRes = numRes;
	}

	public String getZonEle() {
		return zonEle;
	}

	public void setZonEle(String zonEle) {
		this.zonEle = zonEle;
	}

	public String getSecEle() {
		return secEle;
	}

	public void setSecEle(String secEle) {
		this.secEle = secEle;
	}

	public boolean isPagSin() {
		return pagSin;
	}

	public void setPagSin(boolean pagSin) {
		this.pagSin = pagSin;
	}

	@TypeConversion(converter = "converter.EnumTypeConverter")
	public RacaCor getRacCor() {
		return racCor;
	}

	public void setRacCor(RacaCor racCor) {
		this.racCor = racCor;
	}

	@TypeConversion(converter = "converter.EnumTypeConverter")
	public MeioTransporte getMeiTrans() {
		return meiTrans;
	}

	public void setMeiTrans(MeioTransporte meiTrans) {
		this.meiTrans = meiTrans;
	}

	public float getVlrAdi() {
		return vlrAdi;
	}

	public void setVlrAdi(float vlrAdi) {
		this.vlrAdi = vlrAdi;
	}

	public int getQvtMes() {
		return qvtMes;
	}

	public void setQvtMes(int qvtMes) {
		this.qvtMes = qvtMes;
	}

	public float getVvtMes() {
		return vvtMes;
	}

	public void setVvtMes(float vvtMes) {
		this.vvtMes = vvtMes;
	}

	public String getNomCur() {
		return nomCur;
	}

	public void setNomCur(String nomCur) {
		this.nomCur = nomCur;
	}

	public String getNomIns() {
		return nomIns;
	}

	public void setNomIns(String nomIns) {
		this.nomIns = nomIns;
	}

	public Date getDatCon() {
		return datCon;
	}

	public void setDatCon(Date datCon) {
		this.datCon = datCon;
	}

	public String getHorDia() {
		return horDia;
	}

	public void setHorDia(String horDia) {
		this.horDia = horDia;
	}

	public String getHorSem() {
		return horSem;
	}

	public void setHorSem(String horSem) {
		this.horSem = horSem;
	}

	public String getHorMes() {
		return horMes;
	}

	public void setHorMes(String horMes) {
		this.horMes = horMes;
	}

	public String getNomPai() {
		return nomPai;
	}

	public void setNomPai(String nomPai) {
		this.nomPai = nomPai;
	}

	public String getNomMae() {
		return nomMae;
	}

	public void setNomMae(String nomMae) {
		this.nomMae = nomMae;
	}

	public Date getDexCid() {
		return dexCid;
	}

	public void setDexCid(Date dexCid) {
		this.dexCid = dexCid;
	}

	public String getEndNum() {
		return endNum;
	}

	public void setEndNum(String endNum) {
		this.endNum = endNum;
	}

	public String getEndCpl() {
		return endCpl;
	}

	public void setEndCpl(String endCpl) {
		this.endCpl = endCpl;
	}

	public boolean isTemCNV() {
		return temCNV;
	}

	public void setTemCNV(boolean temCNV) {
		this.temCNV = temCNV;
	}

	public Date getVctCNV() {
		return vctCNV;
	}

	public void setVctCNV(Date vctCNV) {
		this.vctCNV = vctCNV;
	}

	public boolean isExtSegPes() {
		return extSegPes;
	}

	public void setExtSegPes(boolean extSegPes) {
		this.extSegPes = extSegPes;
	}

	public boolean isExtTnsVal() {
		return extTnsVal;
	}

	public void setExtTnsVal(boolean extTnsVal) {
		this.extTnsVal = extTnsVal;
	}

	public String getHorIniSab() {
		return horIniSab;
	}

	public void setHorIniSab(String horIniSab) {
		this.horIniSab = horIniSab;
	}

	public String getHorFinSab() {
		return horFinSab;
	}

	public void setHorFinSab(String horFinSab) {
		this.horFinSab = horFinSab;
	}

	public String getHorIniSab2() {
		return horIniSab2;
	}

	public void setHorIniSab2(String horIniSab2) {
		this.horIniSab2 = horIniSab2;
	}

	public String getHorFinSab2() {
		return horFinSab2;
	}

	public void setHorFinSab2(String horFinSab2) {
		this.horFinSab2 = horFinSab2;
	}

	public String getSitAdm() {
		return sitAdm;
	}

	public void setSitAdm(String sitAdm) {
		this.sitAdm = sitAdm;
	}

	public float getValSal() {
		return valSal;
	}

	public void setValSal(float valSal) {
		this.valSal = valSal;
	}

	public int getFuncao() {
		return funcao;
	}

	public void setFuncao(int funcao) {
		this.funcao = funcao;
	}

	public Colaborador(int id) {
		this.id = id;
	}

	public Afastamento getAfastamento() {
		return afastamento;
	}

	public String getBaiEnd() {
		return baiEnd;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public String getCatCnh() {
		return catCnh;
	}

	public Cidade getCciNas() {
		return cciNas;
	}

	public Centro getCentro() {
		return centro;
	}

	public String getCep() {
		return cep;
	}

	public Cidade getCidEnd() {
		return cidEnd;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Long getConBan() {
		return conBan;
	}

	public Date getDatAdm() {
		return datAdm;
	}

	public Date getDatDem() {
		return datDem;
	}

	public Date getDatFor() {
		return datFor;
	}

	public Date getDatInc() {
		return datInc;
	}

	public Date getDatNas() {
		return datNas;
	}

	public Date getDatRec() {
		return datRec;
	}

	public String getEmiCid() {
		return emiCid;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public String getEndRua() {
		return endRua;
	}
	
	public Escala getEscala() {
		return escala;
	}

	public EscalaRonda getEscalaRonda() {
		return escalaRonda;
	}

	@TypeConversion(converter = "converter.EnumTypeConverter")
	public EstadoCivil getEstCiv() {
		return estCiv;
	}

	public GrauInstrucao getGrauInstrucao() {
		return grauInstrucao;
	}

	public String getHorFin() {
		return horFin;
	}

	public String getHorFin2() {
		return horFin2;
	}

	public int getHorInc() {
		return horInc;
	}

	public String getHorIni() {
		return horIni;
	}

	public String getHorIni2() {
		return horIni2;
	}

	public int getId() {
		return id;
	}

	public Local getLocal() {
		return local;
	}

	public String getLocPai() {
		return locPai;
	}

	public Motivo getMotivo() {
		return motivo;
	}

	public String getNomCon() {
		return nomCon;
	}

	public String getNomFun() {
		return nomFun;
	}

	public long getNumCad() {
		return numCad;
	}

	public String getNumCid() {
		return numCid;
	}

	public String getNumCnh() {
		return numCnh;
	}

	public String getNumCpf() {
		return numCpf;
	}

	public Long getNumCtp() {
		return numCtp;
	}

	public int getNumDep() {
		return numDep;
	}

	public String getNumDip() {
		return numDip;
	}

	public String getNumDRT() {
		return numDRT;
	}

	public String getNumEle() {
		return numEle;
	}

	public Long getNumPis() {
		return numPis;
	}

	public String getNumTel() {
		return numTel;
	}

	public String getOrgDip() {
		return orgDip;
	}

	public String getSerCtp() {
		return serCtp;
	}

	@TypeConversion(converter = "converter.EnumTypeConverter")
	public Sexo getSexo() {
		return sexo;
	}

	@TypeConversion(converter = "converter.EnumTypeConverter")
	public TipoSalario getTipSal() {
		return tipSal;
	}

	@TypeConversion(converter = "converter.EnumTypeConverter")
	public TipoSanguineo getTipSan() {
		return tipSan;
	}

	public Integer getTipCol() {
		return tipCol;
	}

	public void setTipCol(Integer tipCol) {
		this.tipCol = tipCol;
	}


	public EstCtp getCodEst() {
		return codEst;
	}

	public void setCodEst(EstCtp codEst) {
		this.codEst = codEst;
	}

	public Integer getCodPai() {
		return codPai;
	}

	public void setCodPai(Integer codPai) {
		this.codPai = codPai;
	}

	public Integer getDddTel() {
		return dddTel;
	}

	public void setDddTel(Integer dddTel) {
		this.dddTel = dddTel;
	}

	public Integer getDdiTel() {
		return ddiTel;
	}

	public void setDdiTel(Integer ddiTel) {
		this.ddiTel = ddiTel;
	}

	public String getEmaCom() {
		return emaCom;
	}

	public void setEmaCom(String emaCom) {
		this.emaCom = emaCom;
	}

	public String getEmaPar() {
		return emaPar;
	}

	public void setEmaPar(String emaPar) {
		this.emaPar = emaPar;
	}



	public String getEstCid() {
		return estCid;
	}

	public void setEstCid(String estCid) {
		this.estCid = estCid;
	}

	public EstCtp getEstNas() {
		return estNas;
	}

	public void setEstNas(EstCtp estNas) {
		this.estNas = estNas;
	}


	public Integer getNumDdd2() {
		return numDdd2;
	}

	public void setNumDdd2(Integer numDdd2) {
		this.numDdd2 = numDdd2;
	}

	public Integer getNumDdi2() {
		return numDdi2;
	}

	public void setNumDdi2(Integer numDdi2) {
		this.numDdi2 = numDdi2;
	}

	public String getNumTel2() {
		return numTel2;
	}

	public void setNumTel2(String numTel2) {
		this.numTel2 = numTel2;
	}

	public PaiNas getPaiNas() {
		return paiNas;
	}

	public void setPaiNas(PaiNas paiNas) {
		this.paiNas = paiNas;
	}

	public Usuario getUsuCad() {
		return usuCad;
	}

	public Set<Solicitacao> getSolicitacoes() {
		return solicitacoes;
	}

	public void setSolicitacoes(Set<Solicitacao> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}

	public boolean isMesmoLocal() {
		return mesmoLocal;
	}

	public void setMesmoLocal(boolean mesmoLocal) {
		this.mesmoLocal = mesmoLocal;
	}

	public String getDigCon() {
		return digCon;
	}

	public void setDigCon(String digCon) {
		this.digCon = digCon;
	}

	public String getDigAge() {
		return digAge;
	}

	public void setDigAge(String digAge) {
		this.digAge = digAge;
	}

	public void setAfastamento(Afastamento afastamento) {
		this.afastamento = afastamento;
	}

	public void setBaiEnd(String baiEnd) {
		this.baiEnd = baiEnd;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public void setCatCnh(String catCnh) {
		this.catCnh = catCnh;
	}

	public void setCciNas(Cidade cciNas) {
		this.cciNas = cciNas;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setCidEnd(Cidade cidEnd) {
		this.cidEnd = cidEnd;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setConBan(Long conBan) {
		this.conBan = conBan;
	}

	public void setDatAdm(Date datAdm) {
		this.datAdm = datAdm;
	}

	public void setDatDem(Date datDem) {
		this.datDem = datDem;
	}

	public void setDatFor(Date datFor) {
		this.datFor = datFor;
	}

	public void setDatInc(Date datInc) {
		this.datInc = datInc;
	}

	public void setDatNas(Date datNas) {
		this.datNas = datNas;
	}

	public void setDatRec(Date datRec) {
		this.datRec = datRec;
	}

	public void setEmiCid(String emiCid) {
		this.emiCid = emiCid;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public void setEndRua(String endRua) {
		this.endRua = endRua;
	}

	public void setEscala(Escala escala) {
		this.escala = escala;
	}

	public void setEscalaRonda(EscalaRonda escalaRonda) {
		this.escalaRonda = escalaRonda;
	}

	public void setEstCiv(EstadoCivil estCiv) {
		this.estCiv = estCiv;
	}

	public void setGrauInstrucao(GrauInstrucao grauInstrucao) {
		this.grauInstrucao = grauInstrucao;
	}

	public void setHorFin(String horFin) {
		this.horFin = horFin;
	}

	public void setHorFin2(String horFin2) {
		this.horFin2 = horFin2;
	}

	public void setHorInc(int horInc) {
		this.horInc = horInc;
	}

	public void setHorIni(String horIni) {
		this.horIni = horIni;
	}

	public void setHorIni2(String horIni2) {
		this.horIni2 = horIni2;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public void setLocPai(String locPai) {
		this.locPai = locPai;
	}

	public void setMotivo(Motivo motivo) {
		this.motivo = motivo;
	}

	public void setNomCon(String nomCon) {
		this.nomCon = nomCon;
	}

	public void setNomFun(String nomFun) {
		this.nomFun = nomFun;
	}

	public void setNumCad(long numCad) {
		this.numCad = numCad;
	}

	public void setNumCid(String numCid) {
		this.numCid = numCid;
	}

	public void setNumCnh(String numCnh) {
		this.numCnh = numCnh;
	}

	public void setNumCpf(String numCpf) {
		this.numCpf = numCpf;
	}

	public void setNumCtp(Long numCtp) {
		this.numCtp = numCtp;
	}

	public void setNumDep(int numDep) {
		this.numDep = numDep;
	}

	public void setNumDip(String numDip) {
		this.numDip = numDip;
	}

	public void setNumDRT(String numDRT) {
		this.numDRT = numDRT;
	}

	public void setNumEle(String numEle) {
		this.numEle = numEle;
	}

	public void setNumPis(Long numPis) {
		this.numPis = numPis;
	}

	public void setNumTel(String numTel) {
		this.numTel = numTel;
	}

	public void setOrgDip(String orgDip) {
		this.orgDip = orgDip;
	}

	public void setSerCtp(String serCtp) {
		this.serCtp = serCtp;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public void setTipSal(TipoSalario tipSal) {
		this.tipSal = tipSal;
	}

	public void setTipSan(TipoSanguineo tipSan) {
		this.tipSan = tipSan;
	}

	public void setUsuCad(Usuario usuCad) {
		this.usuCad = usuCad;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public String getNumCel() {
		return numCel;
	}

	public void setNumCel(String numCel) {
		this.numCel = numCel;
	}

	public String getFonCon() {
		return fonCon;
	}

	public PostoTrabalho getPostoTrabalho() {
		return postoTrabalho;
	}

	public void setPostoTrabalho(PostoTrabalho postoTrabalho) {
		this.postoTrabalho = postoTrabalho;
	}

	public void setFonCon(String fonCon) {
		this.fonCon = fonCon;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public String getCodAge() {
		return codAge;
	}

	public void setCodAge(String codAge) {
		this.codAge = codAge;
	}

	public boolean isPgtChq() {
		return pgtChq;
	}

	public void setPgtChq(boolean pgtChq) {
		this.pgtChq = pgtChq;
	}

	public boolean isConPou() {
		return conPou;
	}

	public void setConPou(boolean conPou) {
		this.conPou = conPou;
	}

	public Colaborador getColabSubs() {
		return colabSubs;
	}

	public void setColabSubs(Colaborador colabSubs) {
		this.colabSubs = colabSubs;
	}

	public boolean isSupervisor() {
		return supervisor;
	}

	public void setSupervisor(boolean supervisor) {
		this.supervisor = supervisor;
	}

	public EscalaHorarioRondaMZ getEscalaHorarioRondaMZ() {
		return escalaHorarioRondaMZ;
	}

	public void setEscalaHorarioRondaMZ(EscalaHorarioRondaMZ escalaHorarioRondaMZ) {
		this.escalaHorarioRondaMZ = escalaHorarioRondaMZ;
	}
	

	public int getQvtKm() {
		return qvtKm;
	}


	public void setQvtKm(int qvtKm) {
		this.qvtKm = qvtKm;
	}


	public float getVlrCbt() {
		return vlrCbt;
	}


	public void setVlrCbt(float vlrCbt) {
		this.vlrCbt = vlrCbt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Colaborador other = (Colaborador) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public enum EstadoCivil {
		Solteiro(1, "Solteiro"), 
		Casado(2, "Casado"), 
		Divorciado(3, "Divorciado"), 
		ViÃºvo(4, "Viúvo"), 
		Concubinato(5, "Concubinato"), 
		Separado(6, "Separado"), 
		Outros(9, "Outros"), 
		UniaoEstavel(7,"União Estável");
		@XStreamAlias("id")
		private int		id;
		private String	descricao;

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		public static EstadoCivil loadById(Integer id) {
			for (EstadoCivil estadoCivil : EstadoCivil.values()) {
				if (estadoCivil.getId() == id)
					return estadoCivil;
			}
			return null;
		}

		private EstadoCivil(int id, String descricao) {
			this.id = id;
			this.descricao = descricao;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

	}

	public enum TipoSalario {
		Mensalista(1,"Mensalista"), Horista(2,"Horista"), SDF(3,"SDF");
		
		@XStreamAlias("id")
		private int		id;
		private String	descricao;

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		public static TipoSalario loadById(Integer id) {
			for (TipoSalario tipoSalario : TipoSalario.values()) {
				if (tipoSalario.getId() == id)
					return tipoSalario;
			}
			return null;
		}

		private TipoSalario(int id, String descricao) {
			this.id = id;
			this.descricao = descricao;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

	}

	public Nacionalidade getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(Nacionalidade nacionalidade) {
		this.nacionalidade = nacionalidade;
	}
	
	public enum TipoSanguineo {
		AP("A+"), AN("A-"), BP("B+"), BN("B-"), OP("O+"), ON("O-"), ABP("AB+"), ABN("AB-");
		private String	descricao;

		private TipoSanguineo(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}
	}

	public enum RacaCor {
		NAO(0, "Não informado"),
		BRA(1, "Branca"), 
		PRE(2, "Preta"), 
		AMA(3, "Amarela"), 
		PAR(4, "Parda"), 
		IND(5, "Indígena"), 
		MAM(6, "Mameluco"), 
		MUL(7, "Mulato"), 
		CAF(8, "Cafuso");
		@XStreamAlias("id")
		private int		id;
		private String	descricao;

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		private RacaCor(int id, String descricao) {
			this.id = id;
			this.descricao = descricao;
		}

		public static RacaCor loadById(Integer id) {
			for (RacaCor racaCor : RacaCor.values()) {
				if (racaCor.getId() == id)
					return racaCor;
			}
			return null;
		}
	}

	public enum MeioTransporte {
		O("Ônibus"), C("Carro próprio");

		private String	descricao;

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		private MeioTransporte(String descricao) {
			this.descricao = descricao;
		}

	}


	public enum LitSimNao {
		S("S","Sim"), N("N","Não");

		private String		id;
		
		private String	descricao;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		
		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}
		private LitSimNao(String id, String descricao) {
			this.descricao = descricao;
			this.id = id;
		}

	}
	

	public enum OnuSce {
		Cedente(1,"Cedente"), 
		Cessionario(2,"Cessionário"), 
		CedenteCesionario(3,"Cedente e Cessionário");

		@XStreamAlias("idOnuSce")
		private int		id;
		
		private String	descricao;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		
		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}
		private OnuSce(int id, String descricao) {
			this.descricao = descricao;
			this.id = id;
		}

	}
	
public enum TipCon {
		EmpregadoNormal(1,"Empregado Normal"), 
		DiretorNaoEmpregado(2,"Diretor Não-Empregado"), 
		TrabalhadorRural(3,"Trabalhador Rural"), 
		Aposentado(4,"Aposentado"),
		Estagiario (5,"Estagiário"),
		MenorAprendiz(6,"Menor Aprendiz"), 
		PrazoDeterminado (7,"Prazo Determinado"), 
		DiretoraAposentado(8,"Diretor Aposentado"), 
		AgentePublico(9,"Agente Público"), 
		Professor(10,"Professor"), 
		Cooperado(11,"Cooperado"), 
		TrabalhadorDomestico(12,"Trabalhador Doméstico");

		@XStreamAlias("idTipCon")
		private int		id;
		
		private String	descricao;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		
		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}
		private TipCon(int id, String descricao) {
			this.descricao = descricao;
			this.id = id;
		}

	}	 	

	public enum CateSo {
	EmpGeral(101,"Empregado - Geral"), 
	TrabRual(102,"Empregado - Trabalhador Rural por Pequeno Prazo da Lei 11.718/2008"), 
	EmpApr(103,"Empregado - Aprendiz"), 
	EmpDom(104,"Empregado - Doméstico"), 
	EmpConFir(105,"Empregado - contrato a termo firmado nos termos da Lei 9601/98"), 
	EmpConPra(106,"Empregado - contrato por prazo determinado nos termos da Lei 6019/74"),
	TraNVin(107,"Trabalhador Não vinculado ao RGPS com direito ao FGTS"), 
	TraPor(201,"Trabalhador Avulso - Portuário"), 
	TraNPorInfSin(202,"Trabalhador Avulso - Não Portuário (Informação do Sindicato)"), 
	TraNPorInfCon(203,"Trabalhador Avulso - Não Portuário (Informação do Contratante)"), 
	SerCarEfe(301,"Servidor Público - Titular de Cargo Efetivo"), 
	SerCarExc(302,"Servidor Público - Ocupante de Cargo exclusivo em comissão"), 
	SerEsecMan(303,"Servidor Público - Exercente de Mandato Eletivo"), 
	SerAgePub(304,"Servidor Público - Agente Público"), 
	SerVinRPPS(305,"Servidor Público vinculado a RPPS indicado para conselho ou órgão representativo, na condição de representante do governo, órgão ou entidade ad administração pública."), 
	DirSind(401,"Dirigente Sindical - Em relAção a remuneração Recebida no Sindicato."), 
	ConIndAutonoEmpGer(701,"Contrib. Individual - autônomo contratado por Empresas em geral"), 
	ConIndAutonoPF(702,"Contrib. Individual - autônomo contratado por Contrib. Individual, por pessoa fisíca em geral, ou por missão diplomática e repartição consular de carreira estrangeiras"), 
	ConIndAutonoBA(703,"Contrib. Individual - autônomo contratado por Entidade Beneficente de Assistência Social isenta da cota patronal"), 
	Excluido(704,"Excluído."), 
	ConIndTranAutoEmprGeral(711,"Contrib. Individual - Transportador autônomo contratado por Empresas em geral"),
	ConIndtranAutPF(712,"Contrib. Individual - Transportador autônomo contratado por Contrib. Individual, por pessoa fisíca em geral, ou por missão diplomática e repartição consular de carreira estrangeiras"), 
	ConIndtranAutBF(713,"Contrib. Individual - Transportador autônomo contratado por Entidade Beneficente de Assistência Social isenta da cota patronal"), 
	ConIndDirNEmpComFGTS(721,"Contrib. Individual - Diretor Não empregado com FGTS"), 
	ConIndDirNEmpSemFGTS(722,"Contrib. Individual - Diretor Não empregado sem FGTS"), 
	ConIndCooEmpInt(731,"Contrib. Individual - Cooperado que presta serviços a empresa por intermédio de cooperativa de trabalho"), 
	ConIndCooEmpBF(732,"Contrib. Individual - Cooperado que presta serviços a Entidade Beneficente de Assistência Social isenta da cota patronal ou para pessoa fisíca"),
	ConIndCooEmpDir(733,"Contrib. Individual - Cooperado eleito para direção da Cooperativa"), 
	ConIndTransCoopSer(734,"Contrib. Individual - Transportador Cooperado que presta serviços a empresa por intermédio de cooperativa de trabalho"), 
	ConIndTranCooBF(735,"Contrib. Individual - Transportador Cooperado que presta serviços a Entidade Beneficente de Assistência Social isenta da cota patronal ou para pessoa fisíca"), 
	ConIndTranCooDir(736,"Contrib. Individual - Transportador Cooperado eleito para direção da Cooperativa."), 
	ConIndCooFil(741,"Contrib. Individual - Cooperado filiado a cooperativa de Produção."), 
	ConIndMicEmp(751,"Contrib. Individual - Micro Empreendedor Individual, quando contratado por PJ"), 
	Estagiario(901,"Estagiário"), 
	NAplica(999,"Não Aplica");
		
		@XStreamAlias("idCateSo")
		private int		id;
		
		private String	descricao;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		
		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}
		private CateSo(int id, String descricao) {
			this.descricao = descricao;
			this.id = id;
		}
		
	}

	
	public enum EstCtp {
		AC("AC","Acre"), 
		AL("AL","Alagoas"),
		AM("AM","Amazonas"),
		AP("AP","Amapá"), 
		BA("BA","Bahia"), 
		CE("CE","Ceará"), 
		DF("DF","Distrito Federal"),
		ES("ES","Espírito Santo"), 
		FN("FN","Fernando de Noronha"),
		GO("GO","Goiás"), 
		MA("MA","Maranhão"),
		MG("MG","Minas Gerais"),
		MS("MS","Mato Grosso do Sul"),
		MT("MT","Mato Grosso"),
		PA("PA","Pará"),
		PB("PB","Paraíba"),
		PE("PE","Pernambuco"),
		PI("PI","Piauí"),
		PR("PR","Paraná"),
		RJ("RJ","Rio de Janeiro"),
		RN("RN","Rio Grande do Norte"),
		RO("RO","Rondônia"),
		RR("RR","Roraima"), 
		RS("RS","Rio Grande do Sul"),
		SC("SC","Santa Catarina"),
		SE("SE","Sergipe"),
		SP("SP","São Paulo"),
		TO("TP","Tocantins"),
		HA("HAI","Haiti"),
		SEI("SEIN","Saint Denis"),
		XX("XX","Estrangeiros");

		@XStreamAlias("idEstCtp")
		private String		id;
		
		private String	descricao;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
		
		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}
		private EstCtp(String id, String descricao) {
			this.descricao = descricao;
			this.id = id;
		}

	}

	public enum TipApo {
	TempoServico(1,"Tempo Serviço"),
	TempoServicoProp(2," Tempo Serviço Proporcional"),
	Idade(3,"Idade"), 
	Invalidez(4,"Invalidez"), 
	InvalidezAciTra(5,"Invalidez Acidente Trabalho"),  
	InvalidezDoePro(6,"Invalidez doença Profissional"), 
	Compulsoria(7,"Compulsória"), 
	Especial(8,"Especial");

		@XStreamAlias("idTipApo")
		private int		id;
		
		private String	descricao;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
		
		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}
		private TipApo(int id, String descricao) {
			this.descricao = descricao;
			this.id = id;
		}
		
	}

	public enum IndAdm {
	Normal(1,"Normal"),
	DecorenteAcaoFiscal(2,"Decorrente de Ação Fiscal (celetista)"),
	DecorrenteDecisaoJudicial(3,"Decorrente de Decisão Judicial"), 
	PosseSemExercicio(4,"Tomou posse mas Não entrou exercicío (estatutário)");

		@XStreamAlias("idIndAdm")
		private int		id;
		
		private String	descricao;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		
		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}
		private IndAdm(int id, String descricao) {
			this.descricao = descricao;
			this.id = id;
		}
		
	}
	
	  
	 public enum TipAdmHfi {
			PrimeiroEmprego(1,"Primeiro Emprego"),
			Reemprego(2,"Reemprego"),
			TranferenciaComOnus(3,"Transferência com ônus"), 
			TransferenciaSemOnus(4,"Transferência sem ônus"),
			IncorporacaoFusaoCisaoOutros(5,"incorporação / fusão / cisão / Outros"),
			Reitegracao(6,"Reintegração"),
			Reconducao(7,"Recondução (Servidor Público)"),
			ReversaoReadaptacaoServidorPub(8,"Reversão / Readaptação (Servidor Público)");

				@XStreamAlias("idTipAdmHfi")
				private int		id;
				
				private String	descricao;

				public int getId() {
					return id;
				}

				public void setId(int id) {
					this.id = id;
				}

				
				public String getDescricao() {
					return descricao;
				}

				public void setDescricao(String descricao) {
					this.descricao = descricao;
				}
				private TipAdmHfi(int id, String descricao) {
					this.descricao = descricao;
					this.id = id;
				}
				
			}
	 
		 public enum CodEtb {
			NEstavel(0,"Não Estável"),
			Cipa(1,"Cipa"),
			AcidenteTrabalho(2,"Acidente de Trabalho"),
			AuxilioMaternidade(3,"Auxílio Maternidade"),
			ServicoMilitar(4,"Serviço Militar"),
			MandatoSindical(5,"Mandato Sindical"),
			ServidorPublico(6,"Servidor Público"),
			RetornoFerias(7,"Retorno Férias"),
			RetornoAuxilioDoenca(8,"Retorno Auxílio-doença"),
			TempoProximoAposentadoria(9,"Tempo Próximo Aposentadoria"),
			Dissidio(10,"Convenção Coletiva (Dissídio)"),
			TempoAnteriorFGTS(11,"Tempo Anterior Opção FGTS"),
			NOptanteFgts(12,"Não Optante FGTS"),
			CipaEmpregador(13,"CIPA Empregador"),
			MembroComissaoConciliacaoPrefia(14,"Membro da comissão de Conciliação Prévia"),
			MembroConselhoCuradorFGTS(15,"Membro do Conselho Curador do FGTS"),
			MembroConselhoNascionalPrevidenciaSocial(16,"Membro do Conselho Nacional da Previdência Social"),
			AuxilioEducacao(17,"Auxílio Educação"),
			CoopConsumo(18,"Cooperativa de Consumo"),
			CoopCredito(19,"Cooperativa de Crédito"),
			PCD(20,"Pessoa com deficiência(PcD)"),
			CoperativaSindicato(21,"Cooperativa do Sindicato C"),
			CIPATR(22,"CIPATR"),
			Outros(99,"Outros");

				@XStreamAlias("idCodEtb")
				private int		id;
				
				private String	descricao;

				public int getId() {
					return id;
				}

				public void setId(int id) {
					this.id = id;
				}

				
				public String getDescricao() {
					return descricao;
				}

				public void setDescricao(String descricao) {
					this.descricao = descricao;
				}
				private CodEtb(int id, String descricao) {
					this.descricao = descricao;
					this.id = id;
				}
				
			}
		 
		 public enum CatSef {
			  Empregado(1,"Empregado"), 
				TrabaAvul(2,"Trabalhador avulso"), 
				TrabNaoVinc(3,"Trabalhador Não vinculado ao RGPS, mas com direito ao FGTS"), 
				EmpreSobContra(4,"Empregado sob contrato de trabalho por prazo determinado (Lei n* 9.601/98)"), 
				ContriIndiv(5,"Contribuinte Individual - Diretor Não empregado com FGTS (Lei n° 8.036/90, art. 16)"), 
				EmpregadoDomestico(6,"Empregado Doméstico"), 
				MenorAprediz(7,"Menor Aprendiz ( Lei 10.097/2000)"), 
				ContIndvDirNaoEmp(11,"Contribuinte Individual - Diretor Não empregado e demais empresários sem FGTS"), 
				DmaisAgePub(12,"Demais Agentes Públicos"),
				ContIndvSbrRemu(13,"Contribuinte Individual - Trabalhador autônomo ou a este equiparado, inclusive o operador de máquina com contribuição sobre remuneração"), 
				ContIndvSbrSal(14,"Contribuinte Individual - Trabalhador autônomo ou a este equiparado, inclusive o operador de máquina com contribuição sobre salário-base"), 
				ContIndvTrsSbrRem(15,"Contribuinte Individual - Transportador autônomo com contribuição sobre remuneração"), 
				ContIndvTrsSbrSal(16,"Contribuinte Individual - Transportador autônomo com contribuição sobre salário-base"),
				ContIndvCoo(17,"Contribuinte Individual - Cooperado que presta serviços a empresas contratantes de Cooperativa de Trabalho"), 
				ContIndvTra(18,"Contribuinte Individual - Transportador Cooperado que presta serviços a empresas contratantes de Cooperativa de Trabalho"), 
				AgePoli(19,"Agente Político"),
				SerPublOcup(20,"Servidor Público Ocupante, exclusivamente, de cargo em comissão; Servidor Público Ocupante de cargo temporário"), 
				SerPublTit(21,"Servidor Público titular de cargo efetivo, magistrado, membro do Ministério Público e do Tribunal e Conselho de Contas");
			 
				@XStreamAlias("idCatSef")
				private int		id;
				
				private String	descricao;

				public int getId() {
					return id;
				}

				public void setId(int id) {
					this.id = id;
				}

				public String getDescricao() {
					return descricao;
				}

				public void setDescricao(String descricao) {
					this.descricao = descricao;
				}
				
				private CatSef(int id, String descricao) {
					this.descricao = descricao;
					this.id = id;
				}
		 }
	 
		
		 public enum AdmeSo {
			 Admissao(1,"Admissão"), 
			 TransferenciaEmpresaMesmoGrupo(2,"Transferência de empresa do mesmo grupo econômico"), 
			 TransferenciaEmpresaConsorciadaConsorcio(3,"Transferência de empresa consorciada ou de consórcio"), 
			 TransferenciaMotivoSucessao(4,"Transferência por motivo de sucesção, incorporação, cisão ou fusão"), 
			 TrabalhoCedido(9,"Trabalhador Cedido");
			 
			 @XStreamAlias("idAdmeSo")
				private int		id;
				
				private String	descricao;

				public int getId() {
					return id;
				}

				public void setId(int id) {
					this.id = id;
				}

				public String getDescricao() {
					return descricao;
				}

				public void setDescricao(String descricao) {
					this.descricao = descricao;
				}
				
				private AdmeSo(int id, String descricao) {
					this.descricao = descricao;
					this.id = id;
				}
		 }
		 
		 
		  
			

		 public enum CatAnt {
			 EmpregadoGeral(101,"Empregado - Geral, inclusive o empregado Público da administração direta ou indireta contratado pela CLT"), 
			 EmpregadoRual(102,"Empregado - Trabalhador Rural por Pequeno Prazo da Lei 11.718/2008"), 
			 EmpregadoAprendiz(103,"Empregado - Aprendiz"), 
			 EmpregadoDomestico(104,"Empregado - Doméstico"), 
			 EmpregadoContratoFirmado(105,"Empregado - contrato a termo firmado nos termos da Lei 9601/98"), 
			 EmpregadoContratoDeterminado(106,"Empregado - contrato por prazo determinado nos termos da Lei 6019/74"), 
			 TrabalhadorPortuario(201,"Trabalhador Avulso - Portuário"), 
			 TrabalhadorNPortuario(202,"Trabalhador Avulso - Não Portuário"), 
			 ServPubTitCar(301,"Servidor Público - Titular de Cargo Efetivo"),
			 ServPubCarExlc(302,"Servidor Público - Ocupante de Cargo exclusivo em comissão"), 
			 AgePoli(303,"Agente Político"), 
			 ServPubIndicado(305,"Servidor Público indicado para conselho ou órgão representativo, na condição de representante do governo, órgão ou entidade da administração pública"), 
			 ServPubTemp(306,"Servidor Público temporário, sujeito a regime administrativo especial definido em lei própria"), 
			 AgePub(309,"Agente Público - Outros"), 
			 DirSindical(401,"Dirigente Sindical - Informação prestada pelo Sindicato"), 
			 TrabalhadorCedido(410,"Trabalhador cedido - Informação prestada pelo Cessionário"), 
			 ContIndAut(701,"Contribuinte individual - autônomo em geral, exceto se enquadrado em uma das demais categorias de contribuinte individual"), 
			 ContIndTran(711,"Contribuinte individual - Transportador autônomo"), 
			 ContIndDirNEmpreComFGts(721,"Contribuinte individual - Diretor Não empregado com FGTS"), 
			 ContIndDirNEmpreSemFGts(722,"Contribuinte individual - Diretor Não empregado sem FGTS"), 
			 ContIndEmpre(723,"Contribuinte individual - empresários, sócios e membro de conselho de administração ou fiscal"), 
			 ContIndCooperado(731,"Contribuinte individual - Cooperado que presta serviços a empresa por intermédio de cooperativa de trabalho"), 
			 ContIndTrans(734,"Contribuinte individual - Transportador Cooperado que presta serviços por intermédio de cooperativa de trabalho"), 
			 ContIndCooperadoFiliado(738,"Contribuinte individual - Cooperado filiado a Cooperativa de Produção"), 
			 ContIndMicroEmpre(741,"Contribuinte individual - Micro Empreendedor Individual, quando contratado por PJ"), 
			 ContInd(751,"Contribuinte individual - aposentado de qualquer regime previdenciário, nomeado magistrado classista temporário da Justiça do Trabalho ou nomeado da Justiça Eleitoral"), 
			 ContIndAssociado(761,"Contribuinte individual - Associado eleito para direção de Cooperativa, associação ou entidade de classe de qualquer natureza ou finalidade"), 
			 ContIndConTut(771,"Contribuinte individual - Membro de conselho tutelar, nos termos da Lei n° 8.069, de 13 de julho de 1990"), 
			 Ministro(781,"Ministro de confissão religiosa ou membro de vida consagrada, de congregação ou de ordem religiosa"),
			 Estagiario(901,"Estagiário"),
			 Medico(902,"Médico Residente"), 
			 NAplica(999,"Não se aplica");
			
			 @XStreamAlias("idCatAnt")
				private int		id;
				
				private String	descricao;

				public int getId() {
					return id;
				}

				public void setId(int id) {
					this.id = id;
				}

				public String getDescricao() {
					return descricao;
				}

				public void setDescricao(String descricao) {
					this.descricao = descricao;
				}
				
				private CatAnt(int id, String descricao) {
					this.descricao = descricao;
					this.id = id;
				}
		 }
		  
			
		 public enum PaiNas {
			 Br(1,"Brasil"), 
			 Arg(2,"Argentina"), 
			 Par(3,"Paraguai"), 
			 Port(4,"Portugual"), 
			 Esp(5,"Espanha"),
			 Afr(6,"África"),
			 RepHai(7,"República Haiti"),
			 Fra(8,"França");
			 
			 @XStreamAlias("idPaiNas")
				private int		id;
				
				private String	descricao;

				public int getId() {
					return id;
				}

				public void setId(int id) {
					this.id = id;
				}

				public String getDescricao() {
					return descricao;
				}

				public void setDescricao(String descricao) {
					this.descricao = descricao;
				}
				
				private PaiNas(int id, String descricao) {
					this.descricao = descricao;
					this.id = id;
				}
		 }
		 
		 
		 
}
