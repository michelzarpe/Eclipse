package bean;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Estoque implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@EmbeddedId
	protected Id id = new Id();
	@ManyToOne
	@JoinColumn(name = "uniformeId", updatable = false, insertable = false)
	private Uniforme uniforme;
	@ManyToOne
	@JoinColumn(name = "empresaId", updatable = false, insertable = false)
	private Empresa empresa;
	private int qtdEst;

	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public Estoque() {

	}

	public Estoque(Uniforme uniforme, Empresa empresa) {
		this.id.uniforme = uniforme;
		this.id.empresa = empresa;
	}

	public Uniforme getUniforme() {
		return uniforme;
	}

	public void setUniforme(Uniforme uniforme) {
		this.uniforme = uniforme;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public int getQtdEst() {
		return qtdEst;
	}

	public void setQtdEst(int qtdEst) {
		this.qtdEst = qtdEst;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result + ((uniforme == null) ? 0 : uniforme.hashCode());
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
		Estoque other = (Estoque) obj;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		if (uniforme == null) {
			if (other.uniforme != null)
				return false;
		} else if (!uniforme.equals(other.uniforme))
			return false;
		return true;
	}

	@Embeddable
	public static class Id implements Serializable {
		private static final long	serialVersionUID	= 1L;
		@ManyToOne
		@JoinColumn(name = "uniformeId")
		private Uniforme uniforme;
		@ManyToOne
		@JoinColumn(name = "empresaId")
		private Empresa empresa;

		public Uniforme getUniforme() {
			return uniforme;
		}

		public void setUniforme(Uniforme uniforme) {
			this.uniforme = uniforme;
		}

		public Empresa getEmpresa() {
			return empresa;
		}

		public void setEmpresa(Empresa empresa) {
			this.empresa = empresa;
		}

		public Id() {

		}

		public Id(Uniforme uniforme, Empresa empresa) {
			this.uniforme = uniforme;
			this.empresa = empresa;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
			result = prime * result + ((uniforme == null) ? 0 : uniforme.hashCode());
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
			Id other = (Id) obj;
			if (empresa == null) {
				if (other.empresa != null)
					return false;
			} else if (!empresa.equals(other.empresa))
				return false;
			if (uniforme == null) {
				if (other.uniforme != null)
					return false;
			} else if (!uniforme.equals(other.uniforme))
				return false;
			return true;
		}
	}

}
