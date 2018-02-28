package bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity
@XStreamAlias("despesa")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Despesa implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Id
	private int id;
	@Column(length = 200)
	private String desDes;

	public Despesa() {
	}

	public Despesa(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesDes() {
		return desDes;
	}

	public void setDesDes(String desDes) {
		this.desDes = desDes;
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
		Despesa other = (Despesa) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.valueOf(this.id);
	}
}
