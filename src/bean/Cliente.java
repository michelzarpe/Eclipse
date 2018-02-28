package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@XStreamAlias("cliente")
public class Cliente implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@EmbeddedId
	private Id id = new Id();
	@Column(length = 40, nullable = false)
	@XStreamAsAttribute
	private String razSoc;
	@ManyToOne
	@JoinColumn(name = "empresaId", insertable = false, updatable = false)
	private Empresa empresa;
	@Column(length = 1)
	private String tipFil;
	@Column(length = 18)
	@XStreamAsAttribute
	private String numCgc;
	@OneToMany(mappedBy = "cliente")
	@XStreamOmitField
	private List<Colaborador> colaboradores = new ArrayList<Colaborador>();
	@Column(length = 40)
	private String apeFil;
	@ManyToOne
	@JoinColumn(name="cidadeId", nullable=true)
	private Cidade cidade;

	public Cliente() {

	}

	public Cliente(Id id) {
		this.id = id;
	}

	public Cliente(Integer codFil, Integer empresaId) {
		this.id.codFil = codFil;
		this.id.empresaId = empresaId;
	}

	public String getApeFil() {
		return apeFil;
	}

	public List<Colaborador> getColaboradores() {
		return colaboradores;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public Id getId() {
		return id;
	}

	public String getNumCgc() {
		return numCgc;
	}

	public String getRazSoc() {
		return razSoc;
	}

	public String getTipFil() {
		return tipFil;
	}

	public void setApeFil(String apeFil) {
		this.apeFil = apeFil;
	}

	public void setColaboradores(List<Colaborador> colaboradores) {
		this.colaboradores = colaboradores;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public void setNumCgc(String numCgc) {
		this.numCgc = numCgc;
	}

	public void setRazSoc(String razSoc) {
		this.razSoc = razSoc;
	}

	public void setTipFil(String tipFil) {
		this.tipFil = tipFil;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	@Override
	public String toString() {
		return this.getRazSoc();
	}

	public static class Id implements Serializable {
		private static final long	serialVersionUID	= 1L;
		@XStreamAsAttribute
		private Integer codFil;
		@XStreamAsAttribute
		private Integer empresaId;

		public Id() {
		};

		public Id(Integer codFil, Integer empresaId) {
			this.codFil = codFil;
			this.empresaId = empresaId;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj != null && obj instanceof Id) {
				Id that = (Id) obj;
				return this.codFil.equals(that.codFil)
						&& this.empresaId.equals(that.empresaId);
			} else {
				return false;
			}
		}

		public Integer getCodFil() {
			return codFil;
		}

		public Integer getEmpresaId() {
			return empresaId;
		}

		@Override
		public int hashCode() {
			return codFil.hashCode() + empresaId.hashCode();
		}

		public void setCodFil(Integer codFil) {
			this.codFil = codFil;
		}

		public void setEmpresaId(Integer empresaId) {
			this.empresaId = empresaId;
		}

		@Override
		public String toString() {
			return "Cliente: " + codFil + " da empresa " + empresaId;
		}
	}
}
