package bean;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity(name = "vinculo")
@XStreamAlias("vinculo")
public class Vinculo implements Serializable {
	private static final long	serialVersionUID	= 1L;

	@Id
	@XStreamAlias("codVin")
	private int						codVin;

	@Column
	@XStreamAlias("desVin")
	private String						desVin;

	public Vinculo() {}

	public Vinculo(int codVin, String desVin) {
		this.codVin = codVin;
		this.desVin = desVin;
	}

	public int getCodVin() {
		return codVin;
	}

	public void setCodVin(int codVin) {
		this.codVin = codVin;
	}

	public String getDesVin() {
		return desVin;
	}

	public void setDesVin(String desVin) {
		this.desVin = desVin;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codVin;
		result = prime * result + ((desVin == null) ? 0 : desVin.hashCode());
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
		Vinculo other = (Vinculo) obj;
		if (codVin != other.codVin)
			return false;
		if (desVin == null) {
			if (other.desVin != null)
				return false;
		} else if (!desVin.equals(other.desVin))
			return false;
		return true;
	}

}
