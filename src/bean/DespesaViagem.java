package bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DespesaViagem implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@EmbeddedId
	protected Id id = new Id();
	private double vlrDes;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "usuarioId", nullable = false)
	private Usuario usuario;
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumns( {
			@JoinColumn(name = "adiantamentoId", nullable = true, referencedColumnName = "id"),
			@JoinColumn(name = "viagemIdAdt", nullable = true, referencedColumnName = "viagemId") })
	private AdiantamentoViagem adiantamentoViagem;
	private Date datAce;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public DespesaViagem() {
	}

	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public double getVlrDes() {
		return vlrDes;
	}

	public void setVlrDes(double vlrDes) {
		this.vlrDes = vlrDes;
	}

	public AdiantamentoViagem getAdiantamentoViagem() {
		return adiantamentoViagem;
	}

	public void setAdiantamentoViagem(AdiantamentoViagem adiantamentoViagem) {
		this.adiantamentoViagem = adiantamentoViagem;
	}

	public Date getDatAce() {
		return datAce;
	}

	public void setDatAce(Date datAce) {
		this.datAce = datAce;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		DespesaViagem other = (DespesaViagem) obj;
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
		@JoinColumn(name = "viagemId")
		private Viagem viagem;
		@ManyToOne
		@JoinColumn(name = "despesaId")
		private Despesa despesa;
		@ManyToOne
		@JoinColumn(name = "centroId")
		private Centro centro;

		public Id() {
		}

		public Centro getCentro() {
			return centro;
		}

		public void setCentro(Centro centro) {
			this.centro = centro;
		}

		public Despesa getDespesa() {
			return despesa;
		}

		public void setDespesa(Despesa despesa) {
			this.despesa = despesa;
		}

		public Viagem getViagem() {
			return viagem;
		}

		public void setViagem(Viagem viagem) {
			this.viagem = viagem;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((centro == null) ? 0 : centro.hashCode());
			result = prime * result + ((despesa == null) ? 0 : despesa.hashCode());
			result = prime * result + ((viagem == null) ? 0 : viagem.hashCode());
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
			if (centro == null) {
				if (other.centro != null)
					return false;
			} else if (!centro.equals(other.centro))
				return false;
			if (despesa == null) {
				if (other.despesa != null)
					return false;
			} else if (!despesa.equals(other.despesa))
				return false;
			if (viagem == null) {
				if (other.viagem != null)
					return false;
			} else if (!viagem.equals(other.viagem))
				return false;
			return true;
		}

	}
}
