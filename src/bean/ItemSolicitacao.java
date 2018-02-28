package bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.opensymphony.xwork2.conversion.annotations.Conversion;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

import converter.DateConverter;

@Entity
@Conversion()
@XStreamAlias("itemSolicitacao")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class ItemSolicitacao implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@EmbeddedId
	protected Id id = new Id();
	@ManyToOne
	@JoinColumn(name = "usuarioLibId")
	private Usuario usuarioLiberacao;
	@Temporal(TemporalType.DATE)
	@XStreamConverter(DateConverter.class)
	private Date datEnv;
	@Column(length = 1)
	private String entAut;
	@Column(length = 80)
	private String obsDis;
	@Column(nullable = false)
	private int qtdEnt;
	@Temporal(TemporalType.DATE)
	@XStreamConverter(DateConverter.class)
	private Date datVen;
	@Temporal(TemporalType.TIMESTAMP)
	// @XStreamConverter(DateConverter.class)
	private Date datAtu;
	@Enumerated(EnumType.STRING)
	@Column(length = 3)
	private Situacao sitItm;
	@Column(length = 200)
	private String mtvSol;
	@ManyToOne
	@JoinColumn(name = "solicitacaoId", updatable = false, insertable = false)
	private Solicitacao solicitacao;
	@ManyToOne
	@JoinColumn(name = "uniformeId", updatable = false, insertable = false)
	private Uniforme uniforme;
	@Column(length = 200)
	private String motLib;
	@Temporal(TemporalType.TIMESTAMP)
	// @XStreamConverter(DateConverter.class)
	private Date datLib;
	@Temporal(TemporalType.TIMESTAMP)
	// @XStreamConverter(DateConverter.class)
	private Date datNeg;
	@Temporal(TemporalType.TIMESTAMP)
	// @XStreamConverter(DateConverter.class)
	private Date datAte;
	@Temporal(TemporalType.TIMESTAMP)
	// @XStreamConverter(DateConverter.class)
	private Date datExp;
	@Temporal(TemporalType.DATE)
	@XStreamConverter(DateConverter.class)
	private Date datPro;
	@Temporal(TemporalType.DATE)
	@XStreamConverter(DateConverter.class)
	private Date datDev;
	private boolean cobCol;
	@Temporal(TemporalType.TIMESTAMP)
	private Date datInc;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "usuAltId")
	private Usuario usuAlteracao;
	@Temporal(TemporalType.DATE)
	@XStreamConverter(DateConverter.class)
	private Date datUltEn;

	public Usuario getUsuAlteracao() {
		return usuAlteracao;
	}

	public void setUsuAlteracao(Usuario usuAlteracao) {
		this.usuAlteracao = usuAlteracao;
	}

	public Date getDatInc() {
		return datInc;
	}

	public void setDatInc(Date datInc) {
		this.datInc = datInc;
	}

	public boolean isCobCol() {
		return cobCol;
	}

	public void setCobCol(boolean cobCol) {
		this.cobCol = cobCol;
	}

	public ItemSolicitacao() {
	}

	public Date getDatPro() {
		return datPro;
	}

	public Date getDatDev() {
		return datDev;
	}

	public void setDatDev(Date datDev) {
		this.datDev = datDev;
	}

	public void setDatPro(Date datPro) {
		this.datPro = datPro;
	}

	public Date getDatExp() {
		return datExp;
	}

	public void setDatExp(Date datExp) {
		this.datExp = datExp;
	}

	public Date getDatAte() {
		return datAte;
	}

	public Date getDatNeg() {
		return datNeg;
	}

	public void setDatNeg(Date datNeg) {
		this.datNeg = datNeg;
	}

	public void setDatAte(Date datAte) {
		this.datAte = datAte;
	}

	public Date getDatLib() {
		return datLib;
	}

	public void setDatLib(Date datLib) {
		this.datLib = datLib;
	}

	public String getMotLib() {
		return motLib;
	}

	public void setMotLib(String motLib) {
		this.motLib = motLib;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public Date getDatEnv() {
		return datEnv;
	}

	public String getEntAut() {
		return entAut;
	}

	public Id getId() {
		return id;
	}

	public String getMtvSol() {
		return mtvSol;
	}

	public String getObsDis() {
		return obsDis;
	}

	public int getQtdEnt() {
		return qtdEnt;
	}

	public Situacao getSitItm() {
		return sitItm;
	}

	public Usuario getUsuarioLiberacao() {
		return usuarioLiberacao;
	}

	public void setDatEnv(Date datEnv) {
		this.datEnv = datEnv;
	}

	public void setEntAut(String entAut) {
		this.entAut = entAut;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public void setMtvSol(String mtvSol) {
		this.mtvSol = mtvSol;
	}

	public void setObsDis(String obsDis) {
		this.obsDis = obsDis;
	}

	public void setQtdEnt(int qtdEnt) {
		this.qtdEnt = qtdEnt;
	}

	@TypeConversion(converter = "converter.EnumTypeConverter")
	public void setSitItm(Situacao sitItm) {
		this.sitItm = sitItm;
	}

	public void setUsuarioLiberacao(Usuario usuarioLiberacao) {
		this.usuarioLiberacao = usuarioLiberacao;
	}

	public Date getDatVen() {
		return datVen;
	}

	public void setDatVen(Date datVen) {
		this.datVen = datVen;
	}

	public Date getDatAtu() {
		return datAtu;
	}

	public void setDatAtu(Date datAtu) {
		this.datAtu = datAtu;
	}

	public Uniforme getUniforme() {
		return uniforme;
	}

	public void setUniforme(Uniforme uniforme) {
		this.uniforme = uniforme;
	}

	public Date getDatUltEn() {
		return datUltEn;
	}

	public void setDatUltEn(Date datUltEn) {
		this.datUltEn = datUltEn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public String toString() {
		return "Item: Solicitacao " + this.getId().getSolicitacao().getId()  + " e Uniforme " + this.getId().getUniforme().getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemSolicitacao other = (ItemSolicitacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Embeddable
	public static class Id implements Serializable {
		private static final long	serialVersionUID	= 1L;
		@ManyToOne
		@JoinColumn(name = "solicitacaoId")
		private Solicitacao solicitacao;
		@ManyToOne
		@JoinColumn(name = "uniformeId")
		private Uniforme uniforme;

		public Id() {

		}

		public Id(Solicitacao solicitacao, Uniforme uniforme) {
			this.solicitacao = solicitacao;
			this.uniforme = uniforme;
		}

		public Solicitacao getSolicitacao() {
			return solicitacao;
		}

		public Uniforme getUniforme() {
			return uniforme;
		}

		public void setSolicitacao(Solicitacao solicitacao) {
			this.solicitacao = solicitacao;
		}

		public void setUniforme(Uniforme uniforme) {
			this.uniforme = uniforme;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((solicitacao == null) ? 0 : solicitacao.hashCode());
			result = prime * result + ((uniforme == null) ? 0 : uniforme.hashCode());
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
			Id other = (Id) obj;
			if (solicitacao == null) {
				if (other.solicitacao != null)
					return false;
			} else if (!solicitacao.equals(other.solicitacao))
				return false;
			if (uniforme == null) {
				if (other.uniforme != null)
					return false;
			} else if (!uniforme.equals(other.uniforme))
				return false;
			return true;
		}

	}

	public enum Situacao {
		SL("Solicitado"), SP("Bloqueado"), PL("Processado - Aguardando envio"), PP(
				"Processado - Aguardando liberação"), NE("Negado, não será enviado"), EN("Enviado"), SAA(
				"Solicitado, Aguardando admissão"), EAA("Enviado - Aguardando Admissão"), LB("Liberado"), NA("Não atendido - falta estoque");
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
