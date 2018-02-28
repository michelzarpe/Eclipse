package bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EscalaRonda implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Id
	private int						id;
	@Column(length = 30, nullable = false)
	private String					nomEsc;
	private String					sitEsc;

	public String getSitEsc() {
		return sitEsc;
	}

	public void setSitEsc(String sitEsc) {
		this.sitEsc = sitEsc;
	}

	public EscalaRonda() {

	}

	public EscalaRonda(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof EscalaRonda))
			return false;
		final EscalaRonda other = (EscalaRonda) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int getId() {
		return id;
	}

	public String getNomEsc() {
		return nomEsc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNomEsc(String nomEsc) {
		this.nomEsc = nomEsc;
	}

}
