package bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity
@XStreamAlias("motivo")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Motivo implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Id
	private int id;
	@Column(length = 50, nullable = false)
	@XStreamAlias("name")
	private String desMtv;

	public Motivo() {
	}

	public Motivo(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Motivo))
			return false;
		final Motivo other = (Motivo) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getDesMtv() {
		return desMtv;
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	public void setDesMtv(String desMtv) {
		this.desMtv = desMtv;
	}

	public void setId(int id) {
		this.id = id;
	}

}
