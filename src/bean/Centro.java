package bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity
@XStreamAlias("centro")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Centro implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Id
	private int id;
	@Column(length = 80, nullable = false)
	private String nomCcu;
	@Enumerated(EnumType.STRING)
	@Column(length = 1)
	private Situacao sitCcu;

	public Centro() {

	}

	public Centro(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getNomCcu() {
		return nomCcu;
	}

	public Situacao getSitCcu() {
		return sitCcu;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNomCcu(String nomCcu) {
		this.nomCcu = nomCcu;
	}

	public void setSitCcu(Situacao sitCcu) {
		this.sitCcu = sitCcu;
	}

	@Override
	public String toString() {
		return String.valueOf(this.getId());
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
		Centro other = (Centro) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public enum Situacao {A, I;}
}
