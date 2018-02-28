package bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;



import converter.DateConverter;

@Entity(name = "historicoContaCorrenteWS")
@XStreamAlias("historicoContaCorrenteWS")
public class HistoricoContaCorrenteWS implements Serializable {

	private static final long	serialVersionUID	= 1L;
	@EmbeddedId
	@XStreamAlias("IdAgencia")
	private Id						id						= new Id();

	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = true)
	private TipBan					tipBan;

	@Column
	@XStreamAlias("digBan")
	private String					digBan;

	@Column
	@XStreamAlias("perCon")
	private double					perCon;

	@ManyToOne
	@JoinColumn(name = "FK_IdCol")
	@XStreamAlias("colaborador")
	private Colaborador			colaborador;

	@ManyToOne
	@JoinColumn(name = "FK_numEmp")
	@XStreamAlias("empresa")
	private Empresa				empresa;

	public HistoricoContaCorrenteWS() {

	}
	

	public Id getId() {
		return id;
	}


	public void setId(Id id) {
		this.id = id;
	}


	public TipBan getTipBan() {
		return tipBan;
	}


	public void setTipBan(TipBan tipBan) {
		this.tipBan = tipBan;
	}


	public String getDigBan() {
		return digBan;
	}


	public void setDigBan(String digBan) {
		this.digBan = digBan;
	}


	public double getPerCon() {
		return perCon;
	}


	public void setPerCon(double perCon) {
		this.perCon = perCon;
	}


	public Colaborador getColaborador() {
		return colaborador;
	}


	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}


	public Empresa getEmpresa() {
		return empresa;
	}


	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colaborador == null) ? 0 : colaborador.hashCode());
		result = prime * result + ((digBan == null) ? 0 : digBan.hashCode());
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		long temp;
		temp = Double.doubleToLongBits(perCon);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((tipBan == null) ? 0 : tipBan.hashCode());
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
		HistoricoContaCorrenteWS other = (HistoricoContaCorrenteWS) obj;
		if (colaborador == null) {
			if (other.colaborador != null)
				return false;
		} else if (!colaborador.equals(other.colaborador))
			return false;
		if (digBan == null) {
			if (other.digBan != null)
				return false;
		} else if (!digBan.equals(other.digBan))
			return false;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(perCon) != Double.doubleToLongBits(other.perCon))
			return false;
		if (tipBan != other.tipBan)
			return false;
		return true;
	}


	public enum TipBan {
		ContCorrente(1, "Conta Corrente"), ContaPoupanca(2, "Conta Poupança"), ContaSalario(3,"Conta Salário"), Outros(4, "Outros");
		@XStreamAlias("id")
		private int		id;
		private String	descricao;

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		public static TipBan loadById(Integer id) {
			for (TipBan tipBan : TipBan.values()) {
				if (tipBan.getId() == id)
					return tipBan;
			}
			return null;
		}

		private TipBan(int id, String descricao) {
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

	@Embeddable
	public static class Id implements Serializable {
		private static final long	serialVersionUID	= 1L;

		@ManyToOne
		@JoinColumns({ @JoinColumn(name = "FK_codBan", nullable = true),
				@JoinColumn(name = "FK_codAge", nullable = true) })
		private Agencia codAge;
		
		@Column
		@XStreamAlias("seqCon")
		private int						seqCon;

		
		@Column
		@XStreamAlias("conBan")
		private Long					conBan;

		@Temporal(TemporalType.DATE)
		@Column(nullable = false)
		@XStreamConverter(DateConverter.class)
		private Date					datAlt;

		public Agencia getCodAge() {
			return codAge;
		}

		public void setCodAge(Agencia codAge) {
			this.codAge = codAge;
		}

		public int getSeqCon() {
			return seqCon;
		}

		public void setSeqCon(int seqCon) {
			this.seqCon = seqCon;
		}

		public Long getConBan() {
			return conBan;
		}

		public void setConBan(Long conBan) {
			this.conBan = conBan;
		}

		public Date getDatAlt() {
			return datAlt;
		}

		public void setDatAlt(Date datAlt) {
			this.datAlt = datAlt;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((codAge == null) ? 0 : codAge.hashCode());
			result = prime * result + ((conBan == null) ? 0 : conBan.hashCode());
			result = prime * result + ((datAlt == null) ? 0 : datAlt.hashCode());
			result = prime * result + seqCon;
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
			if (codAge == null) {
				if (other.codAge != null)
					return false;
			} else if (!codAge.equals(other.codAge))
				return false;
			if (conBan == null) {
				if (other.conBan != null)
					return false;
			} else if (!conBan.equals(other.conBan))
				return false;
			if (datAlt == null) {
				if (other.datAlt != null)
					return false;
			} else if (!datAlt.equals(other.datAlt))
				return false;
			if (seqCon != other.seqCon)
				return false;
			return true;
		}

		
		
	}

}
