package bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity
@XStreamAlias("grauInstrucao")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class GrauInstrucao implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Id
	private short id;
	@Column(length = 30)
	@XStreamAlias("name")
	private String desGra;

	public GrauInstrucao() {

	}

	public GrauInstrucao(short id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final GrauInstrucao other = (GrauInstrucao) obj;
		if (desGra == null) {
			if (other.desGra != null)
				return false;
		} else if (!desGra.equals(other.desGra))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	public String getDesGra() {
		return desGra;
	}

	public short getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((desGra == null) ? 0 : desGra.hashCode());
		result = prime * result + id;
		return result;
	}

	public void setDesGra(String desGra) {
		this.desGra = desGra;
	}

	public void setId(short id) {
		this.id = id;
	}

}
