package bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@Entity
@XStreamAlias("tipo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TipoEPI implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Id
	private int id;
	@Column(length = 30, nullable = false)
	private String desSvc;
	@OneToMany(mappedBy = "tipoEPI", cascade = { CascadeType.ALL })
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("desEpi")
	@XStreamOmitField
	private Set<Uniforme> uniformes = new HashSet<Uniforme>();

	public TipoEPI() {
	}

	public String getDesSvc() {
		return desSvc;
	}

	public int getId() {
		return id;
	}

	public Set<Uniforme> getUniformes() {
		return uniformes;
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
		TipoEPI other = (TipoEPI) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public void setDesSvc(String desSvc) {
		this.desSvc = desSvc;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUniformes(Set<Uniforme> uniformes) {
		this.uniformes = uniformes;
	}

	@Override
	public String toString() {
		return this.desSvc;
	}
}
