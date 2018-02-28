package bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@Entity
@XStreamAlias("condicaoPagamento")
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "id", "codEmp" }) })
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class CondicaoPagamento implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Id
	private String id;
	@ManyToOne
	@JoinColumn(name = "codEmp")
	@XStreamOmitField
	private Empresa empresa;
	@Column(length = 50)
	@XStreamAlias("name")
	private String desCpg;
	@Column(length = 1)
	private String aplCpg;
	@Column(length = 1)
	private String sitCpg;

	public CondicaoPagamento() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getDesCpg() {
		return desCpg;
	}

	public void setDesCpg(String desCpg) {
		this.desCpg = desCpg;
	}

	public String getAplCpg() {
		return aplCpg;
	}

	public void setAplCpg(String aplCpg) {
		this.aplCpg = aplCpg;
	}

	public String getSitCpg() {
		return sitCpg;
	}

	public void setSitCpg(String sitCpg) {
		this.sitCpg = sitCpg;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		CondicaoPagamento other = (CondicaoPagamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
