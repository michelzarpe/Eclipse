package bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity(name= "naturezaDespesa")
@XStreamAlias("naturezaDespesa")
public class NaturezaDespesa implements Serializable {
   private static final long serialVersionUID = 1L;
   @Id
   @XStreamAlias("natDes")
	 private int natDes;
   
   @Column
   @XStreamAlias("nomNat")
	 private String nomNat;

	public NaturezaDespesa(int natDes, String nomNat) {
		this.natDes = natDes;
		this.nomNat = nomNat;
	}
	
	public NaturezaDespesa() {
	}

	public int getNatDes() {
		return natDes;
	}

	public void setNatDes(int natDes) {
		this.natDes = natDes;
	}

	public String getNomNat() {
		return nomNat;
	}

	public void setNomNat(String nomNat) {
		this.nomNat = nomNat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + natDes;
		result = prime * result + ((nomNat == null) ? 0 : nomNat.hashCode());
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
		NaturezaDespesa other = (NaturezaDespesa) obj;
		if (natDes != other.natDes)
			return false;
		if (nomNat == null) {
			if (other.nomNat != null)
				return false;
		} else if (!nomNat.equals(other.nomNat))
			return false;
		return true;
	}
   
}
