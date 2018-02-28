package bean;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity(name ="horarioRonda")
@XStreamAlias("horarioRonda")
public class HorarioRonda implements Serializable {
private static final long serialVersionUID = 1L;

@Id    
@XStreamAlias("id")
private Integer codhor;

@Column
@XStreamAlias("name")
private String deshor;

@XStreamAlias("numSeq")
private int numSeq = 0;

@XStreamAlias("diaSem")
private String diaSem;

 public HorarioRonda() {
 
 }

    public HorarioRonda(Integer codhor, String deshor) {
        this.codhor = codhor;
        this.deshor = deshor;
       
    }
    
    public HorarioRonda(Integer codhor) {
       this.codhor = codhor;
    
   }


    public Integer getCodhor() {
        return codhor;
    }

    public void setCodhor(Integer codhor) {
        this.codhor = codhor;
    }

    public String getDeshor() {
        return deshor;
    }

    public String getDiaSem() {
 		
 		switch (this.getNumSeq()) {
 			case (1): case (8): case (15): case (22):
 				this.setDiaSem("Segunda - Feira");
 				break;
 			case (2): case (9): case (16): case (23):
 				this.setDiaSem("Terça - Feira");
 				break;
 			case (3): case (10): case (17): case (24):
 				this.setDiaSem("Quarta - Feira");
 				break;
 			case (4): case (11): case (18): case (25):
 				this.setDiaSem("Quinta - Feira");
 				break;
 			case (5): case (12): case (19): case (26):
 				this.setDiaSem("Sexta - Feira");
 				break;
 			case (6): case (13): case (20): case (27):
 				this.setDiaSem("Sábado");
 				break;
 			case (7): case (14): case (21): case (28):
 				this.setDiaSem("Domingo");
 				break;
 			default:
 				break;
 		}
 		
   	 return diaSem;
	}

	public void setDiaSem(String diaSem) {
		this.diaSem = diaSem;
	}

	public void setDeshor(String deshor) {
        this.deshor = deshor;
    }

	public int getNumSeq() {
		return numSeq;
	}

	public void setNumSeq(int numSeq) {
		this.numSeq = numSeq;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codhor == null) ? 0 : codhor.hashCode());
		result = prime * result + ((deshor == null) ? 0 : deshor.hashCode());
		result = prime * result + ((diaSem == null) ? 0 : diaSem.hashCode());
		result = prime * result + numSeq;
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
		HorarioRonda other = (HorarioRonda) obj;
		if (codhor == null) {
			if (other.codhor != null)
				return false;
		} else if (!codhor.equals(other.codhor))
			return false;
		if (deshor == null) {
			if (other.deshor != null)
				return false;
		} else if (!deshor.equals(other.deshor))
			return false;
		if (diaSem == null) {
			if (other.diaSem != null)
				return false;
		} else if (!diaSem.equals(other.diaSem))
			return false;
		if (numSeq != other.numSeq)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[codhor=" + codhor + ", deshor=" + deshor + ", numSeq=" + numSeq
				+ ", diaSem=" + diaSem + "]";
	}




    
    
}
