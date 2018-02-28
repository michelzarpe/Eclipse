package bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@Entity(name= "sindicato")
@XStreamAlias("sindicato")
public class Sindicato implements Serializable {
   private static final long serialVersionUID = 1L;
   @Id
   @XStreamAlias("codSin")
	 private Integer codSin;
   
   @Column
   @XStreamAlias("nomSin")
	 private String nomSin;

   public Sindicato() {

	}
   
	public Sindicato(Integer codSin, String nomSin) {
		this.codSin = codSin;
		this.nomSin = nomSin;
	}

	public Integer getCodSin() {
		return codSin;
	}

	public void setCodSin(Integer codSin) {
		this.codSin = codSin;
	}

	public String getNomSin() {
		return nomSin;
	}

	public void setNomSin(String nomSin) {
		this.nomSin = nomSin;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codSin == null) ? 0 : codSin.hashCode());
		result = prime * result + ((nomSin == null) ? 0 : nomSin.hashCode());
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
		Sindicato other = (Sindicato) obj;
		if (codSin == null) {
			if (other.codSin != null)
				return false;
		} else if (!codSin.equals(other.codSin))
			return false;
		if (nomSin == null) {
			if (other.nomSin != null)
				return false;
		} else if (!nomSin.equals(other.nomSin))
			return false;
		return true;
	}
   
   
   
   
}
