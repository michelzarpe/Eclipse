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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import converter.DateConverter;

@Entity
@XStreamAlias("viagem")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Viagem implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Id
	@GeneratedValue
	@XStreamAlias("viagemId")
	private int id;
	@ManyToOne
	@JoinColumn(name = "colaboradorId", nullable = false)
	private Colaborador colaborador;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "solicitanteId", nullable = false)
	private Colaborador solicitante;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "usuarioId", nullable = false)
	private Usuario usuario;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "motivoId", nullable = false)
	private MotivoViagem motivo;
	@Column(length = 400)
	private String cplMtv;
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	@XStreamConverter(DateConverter.class)
	private Date datSol;
	@Column(length = 400)
	private String itnVgm;
	@Enumerated(EnumType.STRING)
	@Column(length = 2, nullable = false)
	private Situacao situacao;
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	@XStreamConverter(DateConverter.class)
	private Date datInc;
	
	@OneToMany(mappedBy = "id.viagem", cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	@OrderBy("id")
	@XStreamOmitField
	private Set<AdiantamentoViagem> adiantamentos = new HashSet<AdiantamentoViagem>();
		
	@OneToMany(mappedBy = "id.viagem", cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	@OrderBy("id")
	@XStreamOmitField
	private Set<DespesaViagem> despesas = new HashSet<DespesaViagem>();
	
	private Date datSai;
	private Date datChe;
	private double vlrRee;
	
	@ManyToMany
	@Fetch(FetchMode.SUBSELECT)
	@XStreamOmitField
	private Set<Centro> centrosVisitados = new HashSet<Centro>();
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "empresaId", nullable = false)
	private Empresa empresa;

	public Viagem() {
	}

	public Viagem(int id) {
		this.id = id;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Set<Centro> getCentrosVisitados() {
		return centrosVisitados;
	}

	public void setCentrosVisitados(Set<Centro> centrosVisitados) {
		this.centrosVisitados = centrosVisitados;
	}

	public String getCplMtv() {
		return cplMtv;
	}

	public void setCplMtv(String cplMtv) {
		this.cplMtv = cplMtv;
	}

	public Date getDatSai() {
		return datSai;
	}

	public void setDatSai(Date datSai) {
		this.datSai = datSai;
	}

	public Date getDatChe() {
		return datChe;
	}

	public void setDatChe(Date datChe) {
		this.datChe = datChe;
	}

	public double getVlrRee() {
		return vlrRee;
	}

	public void setVlrRee(double vlrRee) {
		this.vlrRee = vlrRee;
	}

	public Set<DespesaViagem> getDespesas() {
		return despesas;
	}

	public void setDespesas(Set<DespesaViagem> despesas) {
		this.despesas = despesas;
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

	public Colaborador getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Colaborador solicitante) {
		this.solicitante = solicitante;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public MotivoViagem getMotivo() {
		return motivo;
	}

	public void setMotivo(MotivoViagem motivo) {
		this.motivo = motivo;
	}

	public Date getDatSol() {
		return datSol;
	}

	public void setDatSol(Date datSol) {
		this.datSol = datSol;
	}

	public String getItnVgm() {
		return itnVgm;
	}

	public void setItnVgm(String itnVgm) {
		this.itnVgm = itnVgm;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public Date getDatInc() {
		return datInc;
	}

	public void setDatInc(Date datInc) {
		this.datInc = datInc;
	}

	public Set<AdiantamentoViagem> getAdiantamentos() {
		return adiantamentos;
	}

	public void setAdiantamentos(Set<AdiantamentoViagem> adiantamentos) {
		this.adiantamentos = adiantamentos;
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
		Viagem other = (Viagem) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public enum Situacao {
		FE("Fechada"), AB("Aberta");

		@XStreamAlias("id")
		private int id;
		private String descricao;

		private Situacao(String descricao) {
			this.descricao = descricao;
		}

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

	}
}
