package bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity
@XStreamAlias("motivo")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class MotivoViagem {
	@Id
	private int id;
	@Column(length = 100)
	private String desMtv;

	public MotivoViagem() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesMtv() {
		return desMtv;
	}

	public void setDesMtv(String desMtv) {
		this.desMtv = desMtv;
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
		MotivoViagem other = (MotivoViagem) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
