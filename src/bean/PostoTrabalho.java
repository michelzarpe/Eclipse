package bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity(name= "postoTrabalho")
@XStreamAlias("postoTrabalho")
@Table( uniqueConstraints = { @UniqueConstraint( columnNames = "posTra" ) } )
public class PostoTrabalho implements Serializable {
   private static final long serialVersionUID = 1L;
   
   @Id
   @GeneratedValue
   @XStreamAlias("idPosTrab")
	private int idPosTrab;
   
   @Column
   @XStreamAlias("posTra")
	 private String posTra;
   
   @Column
   @XStreamAlias("desPos")
	 private String desPos;
   
   @Column
   @XStreamAlias("estPos")
   private int estPos;

	public PostoTrabalho() {
	}   

   
	public PostoTrabalho(String posTra, String desPos, int estPos) {
		this.posTra = posTra;
		this.desPos = desPos;
		this.estPos = estPos;
	}

	public String getPosTra() {
		return posTra;
	}

	public void setPosTra(String posTra) {
		this.posTra = posTra;
	}

	public String getDesPos() {
		return desPos;
	}

	public void setDesPos(String desPos) {
		this.desPos = desPos;
	}

	public int getEstPos() {
		return estPos;
	}

	public void setEstPos(int estPos) {
		this.estPos = estPos;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((desPos == null) ? 0 : desPos.hashCode());
		result = prime * result + estPos;
		result = prime * result + ((posTra == null) ? 0 : posTra.hashCode());
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
		PostoTrabalho other = (PostoTrabalho) obj;
		if (desPos == null) {
			if (other.desPos != null)
				return false;
		} else if (!desPos.equals(other.desPos))
			return false;
		if (estPos != other.estPos)
			return false;
		if (posTra == null) {
			if (other.posTra != null)
				return false;
		} else if (!posTra.equals(other.posTra))
			return false;
		return true;
	}
   
}
