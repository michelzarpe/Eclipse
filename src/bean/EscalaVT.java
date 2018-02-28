package bean;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity(name ="escalaVT")
@XStreamAlias("escalaVT")
public class EscalaVT implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id    
	@XStreamAlias("escVtr")
	private int escVtr;
	
	
	@Column
	@XStreamAlias("nomEvt")
	private String nomEvt;

	
	
	public EscalaVT() {
	}


	public EscalaVT(int escVtr, String nomEvt) {
		this.escVtr = escVtr;
		this.nomEvt = nomEvt;
	}


	public int getEscVtr() {
		return escVtr;
	}


	public void setEscVtr(int escVtr) {
		this.escVtr = escVtr;
	}


	public String getNomVte() {
		return nomEvt;
	}


	public void setNomVte(String nomEvt) {
		this.nomEvt = nomEvt;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + escVtr;
		result = prime * result + ((nomEvt == null) ? 0 : nomEvt.hashCode());
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
		EscalaVT other = (EscalaVT) obj;
		if (escVtr != other.escVtr)
			return false;
		if (nomEvt == null) {
			if (other.nomEvt != null)
				return false;
		} else if (!nomEvt.equals(other.nomEvt))
			return false;
		return true;
	}
	
	
	
	
}
