package bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity(name= "deficiencia")
@XStreamAlias("deficiencia")
public class Deficiencia implements Serializable {
   private static final long serialVersionUID = 1L;
   @Id
   @XStreamAlias("codDef")
	 private Integer codDef;
   
   @Column
   @XStreamAlias("desDef")
   private String desDef;

	public Deficiencia() {
	}
   
	public Deficiencia(Integer codDef, String desDef) {
		this.codDef = codDef;
		this.desDef = desDef;
	}

	public Integer getCodDef() {
		return codDef;
	}

	public void setCodDef(Integer codDef) {
		this.codDef = codDef;
	}

	public String getDesDef() {
		return desDef;
	}

	public void setDesDef(String desDef) {
		this.desDef = desDef;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codDef == null) ? 0 : codDef.hashCode());
		result = prime * result + ((desDef == null) ? 0 : desDef.hashCode());
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
		Deficiencia other = (Deficiencia) obj;
		if (codDef == null) {
			if (other.codDef != null)
				return false;
		} else if (!codDef.equals(other.codDef))
			return false;
		if (desDef == null) {
			if (other.desDef != null)
				return false;
		} else if (!desDef.equals(other.desDef))
			return false;
		return true;
	}

   
   
   
}
