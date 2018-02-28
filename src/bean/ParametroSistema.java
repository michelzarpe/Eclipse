package bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity
@XStreamAlias("parametroSistema")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ParametroSistema {
	@Id
	private int						id;
	@Column(length = 40)
	private String					nomPar;

	public ParametroSistema() {

	}

	public ParametroSistema(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomPar() {
		return nomPar;
	}

	public void setNomPar(String nomPar) {
		this.nomPar = nomPar;
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
		ParametroSistema other = (ParametroSistema) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
