package bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity
@XStreamAlias("nacionalidade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Nacionalidade implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Id
	@XStreamAlias("id")
	private int						id;
	@Column(length = 40, nullable = false)
	@XStreamAlias("name")
	private String					desNac;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesNac() {
		return desNac;
	}

	public void setDesNac(String desNac) {
		this.desNac = desNac;
	}

	@Override
	public String toString() {
		return String.valueOf(this.getId() + " - " + this.getDesNac());
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
		if (id != other.getId())
			return false;
		return true;
	}

}
