package bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.thoughtworks.xstream.annotations.XStreamConverter;

import converter.DateConverter;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdiantamentoViagem implements Serializable {
	private static final long	serialVersionUID	= 1L;
	
	@EmbeddedId
	protected Id id = new Id();

	private double vlrAdt;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	@XStreamConverter(DateConverter.class)
	private Date datAdt;
	
	@ManyToOne
	@JoinColumn(name = "usuarioId", nullable = false)
	private Usuario usuario;
	
	@Column(nullable = true)
	@XStreamConverter(DateConverter.class)
	private Date datInc;
	
	@Column(nullable = true)
	@XStreamConverter(DateConverter.class)
	private Date datAce;

	public AdiantamentoViagem() {
	}

	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public double getVlrAdt() {
		return vlrAdt;
	}

	public void setVlrAdt(double vlrAdt) {
		this.vlrAdt = vlrAdt;
	}

	public Date getDatAdt() {
		return datAdt;
	}

	public void setDatAdt(Date datAdt) {
		this.datAdt = datAdt;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getDatInc() {
		return datInc;
	}

	public void setDatInc(Date datInc) {
		this.datInc = datInc;
	}

	public Date getDatAce() {
		return datAce;
	}

	public void setDatAce(Date datAce) {
		this.datAce = datAce;
	}

	@Override
	public String toString() {
		return String.valueOf(this.getId().getId());
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
		AdiantamentoViagem other = (AdiantamentoViagem) obj;
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
		@GeneratedValue
		private Viagem viagem;
		
		private int id;

		public Viagem getViagem() {
			return viagem;
		}

		public void setViagem(Viagem viagem) {
			this.viagem = viagem;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public Id() {
		}

		public Id(Viagem viagem, int adiantamentoId) {
			this.viagem = viagem;
			this.id = adiantamentoId;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + id;
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
			if (id != other.id)
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
