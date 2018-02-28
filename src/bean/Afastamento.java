package bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Afastamento implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Id
	private int	id;
	@Column(length = 30, nullable = false)
	private String desSit;
	
	public Afastamento() {
		
	}
	
	public Afastamento(int id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Afastamento))
			return false;
		final Afastamento other = (Afastamento) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public String getDesSit() {
		return desSit;
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
	
	public void setDesSit(String desSit) {
		this.desSit = desSit;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
}
