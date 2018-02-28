package bean;

import java.io.Serializable;
import java.util.Date;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import converter.DateConverter;
import util.Sexo;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="dependente")
@XStreamAlias("dependente")
public class Dependente implements Serializable {
	private static final long		serialVersionUID	= 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@XStreamAlias("codDep")
	private Integer codDep;
	
	@ManyToOne
	@JoinColumn(name = "colaborador",referencedColumnName="id")
	@XStreamAlias("colaborador")
	private Colaborador			colaborador;
	
	@Column(length = 80)
	@XStreamAlias("nomDep")
   private String nomDep;

	@Column(length = 80)
	@XStreamAlias("nomMae")
	private String nomMae;
	
	@Column(length = 14, nullable = false)
	private String	numCpf;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	@XStreamConverter(DateConverter.class)
	private Date					datNas;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	@XStreamConverter(DateConverter.class)
	private Date					datInv;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	@XStreamConverter(DateConverter.class)
	private Date					iniTut;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	@XStreamConverter(DateConverter.class)
	private Date					datTut;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private LitSimNaoDependente aviImp;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private LitSimNaoDependente auxCre;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private LitSimNaoDependente penJud;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private Sexo sexo;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = true)
	private EstadoCivilDependente estCiv;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = true)
	private TipoDependenteESocial tipoDependenteESocial;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = true)
	private GrauParantesco grauParantesco;
		
	@Column
	private Integer limIrf;
	
	@Column
	private Integer limSaf;
	
	@Column
	private Integer nomCre;

	
	@ManyToOne
	@JoinColumn(name = "graInsId", nullable = true)
	private GrauInstrucao		grauInstrucao;
	
	
	public Dependente() {

	}
	
		
	public GrauInstrucao getGrauInstrucao() {
		return grauInstrucao;
	}

	public void setGrauInstrucao(GrauInstrucao grauInstrucao) {
		this.grauInstrucao = grauInstrucao;
	}

	@TypeConversion(converter = "converter.EnumTypeConverter")
	public GrauParantesco getGrauParantesco() {
		return grauParantesco;
	}

	@TypeConversion(converter = "converter.EnumTypeConverter")
	public void setGrauParantesco(GrauParantesco grauParantesco) {
		this.grauParantesco = grauParantesco;
	}

	@TypeConversion(converter = "converter.EnumTypeConverter")
	public TipoDependenteESocial getTipoDependenteESocial() {
		return tipoDependenteESocial;
	}

	@TypeConversion(converter = "converter.EnumTypeConverter")
	public void setTipoDependenteESocial(TipoDependenteESocial tipoDependenteESocial) {
		this.tipoDependenteESocial = tipoDependenteESocial;
	}

	@TypeConversion(converter = "converter.EnumTypeConverter")
	public EstadoCivilDependente getEstCiv() {
		return estCiv;
	}

	@TypeConversion(converter = "converter.EnumTypeConverter")
	public void setEstCiv(EstadoCivilDependente estCiv) {
		this.estCiv = estCiv;
	}

	@TypeConversion(converter = "converter.EnumTypeConverter")
	public Sexo getSexo() {
		return sexo;
	}
	
	@TypeConversion(converter = "converter.EnumTypeConverter")
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	@TypeConversion(converter = "converter.EnumTypeConverter")
	public LitSimNaoDependente getPenJud() {
		return penJud;
	}

	@TypeConversion(converter = "converter.EnumTypeConverter")
	public void setPenJud(LitSimNaoDependente penJud) {
		this.penJud = penJud;
	}

	@TypeConversion(converter = "converter.EnumTypeConverter")
	public LitSimNaoDependente getAuxCre() {
		return auxCre;
	}

	@TypeConversion(converter = "converter.EnumTypeConverter")
	public void setAuxCre(LitSimNaoDependente auxCre) {
		this.auxCre = auxCre;
	}

	@TypeConversion(converter = "converter.EnumTypeConverter")
	public LitSimNaoDependente getAviImp() {
		return aviImp;
	}
	@TypeConversion(converter = "converter.EnumTypeConverter")
	public void setAviImp(LitSimNaoDependente aviImp) {
		this.aviImp = aviImp;
	}


	public String getNomMae() {
		return nomMae;
	}

	public void setNomMae(String nomMae) {
		this.nomMae = nomMae;
	}

	public String getNumCpf() {
		return numCpf;
	}

	public void setNumCpf(String numCpf) {
		this.numCpf = numCpf;
	}
	

	public Date getIniTut() {
		return iniTut;
	}


	public void setIniTut(Date iniTut) {
		this.iniTut = iniTut;
	}


	public Date getDatTut() {
		return datTut;
	}


	public void setDatTut(Date datTut) {
		this.datTut = datTut;
	}


	public Date getDatInv() {
		return datInv;
	}

	public void setDatInv(Date datInv) {
		this.datInv = datInv;
	}

	public Date getDatNas() {
		return datNas;
	}

	public void setDatNas(Date datNas) {
		this.datNas = datNas;
	}

	public Integer getCodDep() {
		return codDep;
	}

	public void setCodDep(Integer codDep) {
		this.codDep = codDep;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}


	public String getNomDep() {
		return nomDep;
	}

	public void setNomDep(String nomDep) {
		this.nomDep = nomDep;
	}

	public Integer getLimIrf() {
		return limIrf;
	}

	public void setLimIrf(Integer limIrf) {
		this.limIrf = limIrf;
	}

	public Integer getLimSaf() {
		return limSaf;
	}

	public void setLimSaf(Integer limSaf) {
		this.limSaf = limSaf;
	}

	public Integer getNomCre() {
		return nomCre;
	}

	public void setNomCre(Integer nomCre) {
		this.nomCre = nomCre;
	}

	

	public enum EstadoCivilDependente {
		Solteiro(1, "Solteiro"), 
		Casado(2, "Casado"), 
		Divorciado(3, "Separado Judicialmente/Divorciado"), 
		Viuvo(4, "Viúvo"), 
		Concubinato(5, "Concubinato"), 
		Separado(6, "Separado"), 
		Outros(9, "Outros");
		
		@XStreamAlias("idEstCivDep")
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
		private EstadoCivilDependente(int id, String descricao) {
			this.descricao = descricao;
			this.id = id;
		}

	}
	
	public enum GrauParantesco {
		Filho(1,"Filho(a)"),
		Conjuge(2,"Cônjuge"),
		PM(3,"Pai / Mãe"), 
		Avos(4,"Avó / Avô"), 
		Bisa(5,"Bisavó / Bisavô"),  
		Sobrinho(6,"Sobrinho(a)"), 
		Tio(7,"Tio(a)"), 
		Neto(8,"Neto(a)"),
		Sogro(9,"Sogro(a)"),
		Genro(10,"Genro / Nora"),
		Enteado(11,"Enteado(a)"),
		Irmao(12,"Irmão(ã)"),
		FilhoAdotivo(14,"Filho(a) Adotivo(a)"),
		Pensionistas(15,"Pensionistas"),
		Companheiro(16,"Companheiro(a)"),
		Tutelado(17,"Tutelado"),
		MenorSobGuarda(18,"Menor Sob Guarda"),
		Madastra(19,"Madastra"),
		Padastro(20,"Padastro"),
		Tutor(21,"Tutor"),
		Curatelado(26,"Curatelado(a)"),
		Outros(99,"Outros");

			@XStreamAlias("id")
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
			private GrauParantesco(int id, String descricao) {
				this.descricao = descricao;
				this.id = id;
			}
			
		}
	
	public enum LitSimNaoDependente {
		S("S","Sim"), N("N","Não");
		@XStreamAlias("id")
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
		private LitSimNaoDependente(String id, String descricao) {
			this.descricao = descricao;
			this.id = id;
		}

	}
	
	public enum TipoDependenteESocial {
		NaoLevarDepESocial(0,"Não levar Dependente para eSocial"),
		Conjuge(1,"Cônjuge"),
		Companheiro(2,"Companheiro(a)"),
		FilhoEnteado(3,"Filho(a) / Enteado(a)"),
		IrmaoNetoBisneto(4,"Irmão(ã) / Neto(a) / Bisneto(a)"),
		Menor(4,"Menor"),
		PessoaIncapaz(4,"Pessoa Incapaz"),
		FilhoEnteado24(4,"Filho / Enteado até 24 anos"),
		ExcONnjuge(4,"Ex-Cônjuge"),
		Outros(99,"Agregado / Outros");

			@XStreamAlias("id")
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
			private TipoDependenteESocial(int id, String descricao) {
				this.descricao = descricao;
				this.id = id;
			}
			
		}

	
}


