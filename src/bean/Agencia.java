package bean;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.thoughtworks.xstream.annotations.XStreamAlias;


@Entity(name = "agencia")
@XStreamAlias("agencia")
public class Agencia implements Serializable {
	private static final long	serialVersionUID	= 1L;

	@EmbeddedId
	@XStreamAlias("IdAgencia")
	private Id						id						= new Id();
	
	@Column
	@XStreamAlias("nomAge")
	private String					nomAge;

	@Column
	@XStreamAlias("digAge")
	private String					digAge;
	
	
	public Id getId() {
		return id;
	}


	public void setId(Id id) {
		this.id = id;
	}


	public String getNomAge() {
		return nomAge;
	}


	public void setNomAge(String nomAge) {
		this.nomAge = nomAge;
	}


	public String getDigAge() {
		return digAge;
	}


	public void setDigAge(String digAge) {
		this.digAge = digAge;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((digAge == null) ? 0 : digAge.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nomAge == null) ? 0 : nomAge.hashCode());
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
		Agencia other = (Agencia) obj;
		if (digAge == null) {
			if (other.digAge != null)
				return false;
		} else if (!digAge.equals(other.digAge))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nomAge == null) {
			if (other.nomAge != null)
				return false;
		} else if (!nomAge.equals(other.nomAge))
			return false;
		return true;
	}


	@Embeddable
	public static class Id implements Serializable {
		private static final long	serialVersionUID	= 1L;
	
		@ManyToOne
		@JoinColumn(name = "FK_CodBan", nullable = true)
		private Banco					codBan;

		@Column
		@XStreamAlias("codAge")
		private int						codAge;

		public Banco getCodBan() {
			return codBan;
		}

		public void setCodBan(Banco codBan) {
			this.codBan = codBan;
		}

		public int getCodAge() {
			return codAge;
		}

		public void setCodAge(int codAge) {
			this.codAge = codAge;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + codAge;
			result = prime * result + ((codBan == null) ? 0 : codBan.hashCode());
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
			if (codAge != other.codAge)
				return false;
			if (codBan == null) {
				if (other.codBan != null)
					return false;
			} else if (!codBan.equals(other.codBan))
				return false;
			return true;
		}

		
		
		
	}

}
