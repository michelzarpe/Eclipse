package bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity
@XStreamAlias("escala")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Escala implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Id
	private int	id;
	@Column(length = 30, nullable = false)
	@XStreamAlias("name")
	private String nomEsc;
	
	public Escala() {
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Escala))
			return false;
		final Escala other = (Escala) obj;
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
