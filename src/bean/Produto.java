package bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.opensymphony.xwork2.conversion.annotations.TypeConversion;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity
@XStreamAlias("produto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Produto implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@EmbeddedId
	protected Id			id			= new Id();
	@Column(length = 100)
	private String			desPro;
	@Column(length = 50)
	private String			cplPro;
	@Enumerated(EnumType.STRING)
	@Column(length = 1)
	private Situacao		sitPro;
	@Column(length = 3)
	private String			uniMed;
	@Enumerated(EnumType.STRING)
	@Column(length = 2, nullable = true)
	private TipoProduto	tipPro;
	@ManyToOne
	@JoinColumn(name = "empresaId", updatable = false, insertable = false)
	private Empresa		empresa	= new Empresa();
	@Column(nullable = true, columnDefinition = "TINYINT(1) DEFAULT 1")
	private boolean		requisitavel;
	@Column(nullable = false, columnDefinition = "DECIMAL(10,2) DEFAULT 0.0")
	private double			preUni;

	public Produto() {}

	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public boolean isRequisitavel() {
		return requisitavel;
	}

	public void setRequisitavel(boolean requisitavel) {
		this.requisitavel = requisitavel;
	}

	public String getDesPro() {
		return desPro;
	}

	public void setDesPro(String desPro) {
		this.desPro = desPro;
	}

	public String getCplPro() {
		return cplPro;
	}

	public void setCplPro(String cplPro) {
		this.cplPro = cplPro;
	}

	public Situacao getSitPro() {
		return sitPro;
	}

	public void setSitPro(Situacao sitPro) {
		this.sitPro = sitPro;
	}

	public String getUniMed() {
		return uniMed;
	}

	public void setUniMed(String uniMed) {
		this.uniMed = uniMed;
	}

	@TypeConversion(converter = "converter.EnumTypeConverter")
	public TipoProduto getTipPro() {
		return tipPro;
	}

	public void setTipPro(TipoProduto tipPro) {
		this.tipPro = tipPro;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public enum Situacao {
		A, I;
	}

	public double getPreUni() {
		return preUni;
	}

	public void setPreUni(double preUni) {
		this.preUni = preUni;
	}

	public enum TipoProduto {
		ME("Material de Expediente"), MI("Material de Instalaçao"), MC("Material Carga de Posto"), LI("Material de Limpeza");
		@XStreamAlias("name")
		private String	descricao;

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		private TipoProduto(String descricao) {
			this.descricao = descricao;
		}
	}

	@Embeddable
	public static class Id implements Serializable {
		private static final long	serialVersionUID	= 1L;
		@ManyToOne
		@JoinColumn(name = "empresaId")
		private Empresa	empresa;
		@Column(length = 14)
		private String		codPro;

		public Id() {}

		public Id(Empresa empresa, String codPro) {
			this.empresa = empresa;
			this.codPro = codPro;
		}

		public Empresa getEmpresa() {
			return empresa;
		}

		public void setEmpresa(Empresa empresa) {
			this.empresa = empresa;
		}

		public String getCodPro() {
			return codPro;
		}

		public void setCodPro(String codPro) {
			this.codPro = codPro;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((codPro == null) ? 0 : codPro.hashCode());
			result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
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
			if (codPro == null) {
				if (other.codPro != null)
					return false;
			} else if (!codPro.equals(other.codPro))
				return false;
			if (empresa == null) {
				if (other.empresa != null)
					return false;
			} else if (!empresa.equals(other.empresa))
				return false;
			return true;
		}

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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
