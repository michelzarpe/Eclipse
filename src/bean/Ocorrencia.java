package bean;

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
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Ocorrencia {
	@Id
	@GeneratedValue
	private int id;
	@Temporal(TemporalType.DATE)
	private Date datOcr;
	private String obsItm;
	private boolean cobCli;
	@ManyToOne
	@JoinColumn(name = "codEsc")
	private Escala novEsc;
	@Column(length = 5)
	private String horEnt1;
	@Column(length = 5)
	private String horSai1;
	@Column(length = 5)
	private String horEnt2;
	@Column(length = 5)
	private String horSai2;
	@ManyToOne
	@JoinColumns( { @JoinColumn(name = "codCar", nullable = true, referencedColumnName = "codCar"),
			@JoinColumn(name = "estCar", nullable = true, referencedColumnName = "estCar") })
	private Cargo novCar;
	@ManyToOne
	@JoinColumn(name = "usuLib")
	private Usuario usuLib;
	private String motLib;
	@Enumerated(EnumType.STRING)
	@Column(length = 2)
	private Situacao sitOcr;
	private boolean gerCob;
	@Temporal(TemporalType.TIMESTAMP)
	private Date datGer;
	@Temporal(TemporalType.TIMESTAMP)
	private Date datLib;
	@Temporal(TemporalType.DATE)
	private Date datTer;
	@Temporal(TemporalType.TIMESTAMP)
	private Date datAtu;
	@OneToMany(mappedBy = "ocorrencia", cascade = CascadeType.ALL)
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("id")
	private Set<Cobertura> coberturas = new HashSet<Cobertura>();
	@ManyToOne
	@JoinColumn(name = "codMot")
	private MotivoOcorrencia motivo;
	@ManyToOne
	@JoinColumn(name = "colaboradorId")
	private Colaborador colaborador;
	@ManyToOne
	@JoinColumns( { @JoinColumn(name = "numLoc", nullable = true),
			@JoinColumn(name = "tabOrg", nullable = true) })
	private Local novoLocal;
	@ManyToOne
	@JoinColumn(name = "stpMot")
	private SubTipoMotivo subTipoMotivo;
	@ManyToOne
	@JoinColumn(name = "usuarioId")
	private Usuario usuario;

	public Ocorrencia() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDatOcr() {
		return datOcr;
	}

	public void setDatOcr(Date datOcr) {
		this.datOcr = datOcr;
	}

	public String getObsItm() {
		return obsItm;
	}

	public void setObsItm(String obsItm) {
		this.obsItm = obsItm;
	}

	public boolean isCobCli() {
		return cobCli;
	}

	public void setCobCli(boolean cobCli) {
		this.cobCli = cobCli;
	}

	public Escala getNovEsc() {
		return novEsc;
	}

	public void setNovEsc(Escala novEsc) {
		this.novEsc = novEsc;
	}

	public String getHorEnt1() {
		return horEnt1;
	}

	public void setHorEnt1(String horEnt1) {
		this.horEnt1 = horEnt1;
	}

	public String getHorSai1() {
		return horSai1;
	}

	public void setHorSai1(String horSai1) {
		this.horSai1 = horSai1;
	}

	public String getHorEnt2() {
		return horEnt2;
	}

	public void setHorEnt2(String horEnt2) {
		this.horEnt2 = horEnt2;
	}

	public String getHorSai2() {
		return horSai2;
	}

	public void setHorSai2(String horSai2) {
		this.horSai2 = horSai2;
	}

	public Cargo getNovCar() {
		return novCar;
	}

	public void setNovCar(Cargo novCar) {
		this.novCar = novCar;
	}

	public Usuario getUsuLib() {
		return usuLib;
	}

	public void setUsuLib(Usuario usuLib) {
		this.usuLib = usuLib;
	}

	public String getMotLib() {
		return motLib;
	}

	public void setMotLib(String motLib) {
		this.motLib = motLib;
	}

	public Situacao getSitOcr() {
		return sitOcr;
	}

	public void setSitOcr(Situacao sitOcr) {
		this.sitOcr = sitOcr;
	}

	public boolean isGerCob() {
		return gerCob;
	}

	public void setGerCob(boolean gerCob) {
		this.gerCob = gerCob;
	}

	public Date getDatGer() {
		return datGer;
	}

	public void setDatGer(Date datGer) {
		this.datGer = datGer;
	}

	public Date getDatLib() {
		return datLib;
	}

	public void setDatLib(Date datLib) {
		this.datLib = datLib;
	}

	public Date getDatTer() {
		return datTer;
	}

	public void setDatTer(Date datTer) {
		this.datTer = datTer;
	}

	public Date getDatAtu() {
		return datAtu;
	}

	public void setDatAtu(Date datAtu) {
		this.datAtu = datAtu;
	}

	public Set<Cobertura> getCoberturas() {
		return coberturas;
	}

	public void setCoberturas(Set<Cobertura> coberturas) {
		this.coberturas = coberturas;
	}

	public MotivoOcorrencia getMotivo() {
		return motivo;
	}

	public void setMotivo(MotivoOcorrencia motivo) {
		this.motivo = motivo;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public Local getNovoLocal() {
		return novoLocal;
	}

	public void setNovoLocal(Local novoLocal) {
		this.novoLocal = novoLocal;
	}

	public SubTipoMotivo getSubTipoMotivo() {
		return subTipoMotivo;
	}

	public void setSubTipoMotivo(SubTipoMotivo subTipoMotivo) {
		this.subTipoMotivo = subTipoMotivo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
		Ocorrencia other = (Ocorrencia) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public enum Situacao {
		PL("Pendente de liberação"), AB("Aberto"), LB("Liberado"), PR("Processado"), CA("Cancelado");
		private String realName;

		public int getId() {
			return this.ordinal();
		}

		public String getRealName() {
			return realName;
		}

		public void setRealName(String realName) {
			this.realName = realName;
		}

		private Situacao(String realName) {
			this.realName = realName;
		}
	}
}
