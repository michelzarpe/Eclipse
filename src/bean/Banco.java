package bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity
@XStreamAlias("banco")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Banco implements Serializable {
	private static final long	serialVersionUID	= 1L;
	
	@Id
	private int		id;
	@Column(length = 30, nullable = false)
	@XStreamAlias("name")
	private String	nomBan;

	public Banco() {

	}

	public Banco(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getNomBan() {
		return nomBan;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNomBan(String nomBan) {
		this.nomBan = nomBan;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((nomBan == null) ? 0 : nomBan.hashCode());
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
		final Banco other = (Banco) obj;
		if (id != other.id)
			return false;
		if (nomBan == null) {
			if (other.nomBan != null)
				return false;
		} else if (!nomBan.equals(other.nomBan))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return ("Banco " + nomBan);
	}

}
