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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import converter.DateConverter;

@Entity
@XStreamAlias("solicitacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Solicitacao implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Id
	@GeneratedValue
	@XStreamAlias("solicitacaoId")
	private int id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "colaboradorId")
	private Colaborador colaborador;
	@ManyToOne
	@JoinColumn(name = "motivoId")
	private Motivo motivo;
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	@XStreamConverter(DateConverter.class)
	private Date datEnt;
	private int numSeq;
	@OneToMany(mappedBy = "id.solicitacao", cascade = CascadeType.ALL)
	@OrderBy("id")
	@XStreamOmitField
	private Set<ItemSolicitacao> itensSolicitacao = new HashSet<ItemSolicitacao>();
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "solicitanteId")
	private Usuario solicitante;
	private boolean viaWeb;
	private boolean assPro;
	@ManyToOne
	@JoinColumn(name = "supervisorId")
	@XStreamAlias("super")
	private Colaborador supervisor;
	@Temporal(TemporalType.DATE)
	@XStreamConverter(DateConverter.class)
	private Date datInc;
	@Enumerated(EnumType.STRING)
	@Column(length = 2)
	private Situacao situacao;

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

	public Colaborador getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Colaborador supervisor) {
		this.supervisor = supervisor;
	}

	public boolean isAssPro() {
		return assPro;
	}

	public void setAssPro(boolean assPro) {
		this.assPro = assPro;
	}

	public boolean isViaWeb() {
		return viaWeb;
	}

	public void setViaWeb(boolean viaWeb) {
		this.viaWeb = viaWeb;
	}

	public Solicitacao() {
	}

	public Solicitacao(int idSol) {
		this.id = idSol;
	}
	
	public Colaborador getColaborador() {
		return colaborador;
	}

	public Date getDatEnt() {
		return datEnt;
	}

	public int getId() {
		return id;
	}

	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Set<ItemSolicitacao> getItensSolicitacao() {
		return itensSolicitacao;
	}

	public Motivo getMotivo() {
		return motivo;
	}

	public int getNumSeq() {
		return numSeq;
	}

	public Usuario getSolicitante() {
		return solicitante;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public void setDatEnt(Date datEnt) {
		this.datEnt = datEnt;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setItensSolicitacao(Set<ItemSolicitacao> itensSolicitacao) {
		this.itensSolicitacao = itensSolicitacao;
	}

	public void setMotivo(Motivo motivo) {
		this.motivo = motivo;
	}

	public void setNumSeq(int numSeq) {
		this.numSeq = numSeq;
	}

	public void setSolicitante(Usuario solicitante) {
		this.solicitante = solicitante;
	}

	@Override
	public String toString() {
		return "Distribuição: " + this.getId();
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
		Solicitacao other = (Solicitacao) obj;
		if (this.getId() != other.getId())
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
