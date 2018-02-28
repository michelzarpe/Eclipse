package bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * @author alessandra
 * Característica dos uniformes , cadastrado no sapiens
 */
@Entity
@XStreamAlias("caracteristica")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Caracteristica implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Id
	private String					id;
	@Column(length = 30, nullable = false)
	@XStreamAlias("name")
	private String					desCte;

	public Caracteristica() {

	}

	public Caracteristica(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDesCte() {
		return desCte;
	}

	public void setDesCte(String desCte) {
		this.desCte = desCte;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Caracteristica other = (Caracteristica) obj;
		if (id != other.id)
			return false;
		if (desCte == null) {
			if (other.desCte != null)
				return false;
		} else if (!desCte.equals(other.desCte))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Característica " + this.desCte;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((desCte == null) ? 0 : desCte.hashCode());
		return result;
	}

}
