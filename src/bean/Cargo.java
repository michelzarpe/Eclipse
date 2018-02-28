package bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@Entity
@XStreamAlias("cargo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cargo implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@EmbeddedId
	@XStreamOmitField
	private Id						id						= new Id();
	@Column(length = 60, nullable = false)
	@XStreamAlias("name")
	private String					titCar;
	@Column(insertable = false, updatable = false)
	@XStreamAlias("id")
	private String					codCar;

	public Cargo() {

	}

	public Cargo(Integer estCar, String codCar) {
		this.id.estCar = estCar;
		this.id.codCar = codCar;
	}

	public Cargo(Id id) {
		this.id = id;
	}

	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public String getTitCar() {
		return titCar;
	}

	public void setTitCar(String titCar) {
		this.titCar = titCar;
	}

	public String getCodCar() {
		return codCar;
	}

	public void setCodCar(String codCar) {
		this.codCar = codCar;
	}

	@Embeddable
	public static class Id implements Serializable {
		private static final long	serialVersionUID	= 1L;
		@Column(length = 24)
		private String					codCar;
		private Integer				estCar;

		public Id() {

		}

		public Id(Integer estCar, String codCar) {
			this.estCar = estCar;
			this.codCar = codCar;
		}

		public String getCodCar() {
			return codCar;
		}

		public Integer getEstCar() {
			return estCar;
		}

		public void setCodCar(String codCar) {
			this.codCar = codCar;
		}

		public void setEstCar(Integer estCar) {
			this.estCar = estCar;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj != null && obj instanceof Id) {
				Id that = (Id) obj;
				return this.estCar.equals(that.estCar) && this.codCar.equals(that.codCar);
			} else {
				return false;
			}
		}

		@Override
		public int hashCode() {
			return estCar.hashCode() + codCar.hashCode();
		}

	}
}
