package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

import converter.LocalConverter;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "tabOrg", "numLoc" }) })
@XStreamAlias("local")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Local implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@EmbeddedId
	private Id id = new Id();
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date datCri;
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date datExt;
	@Column(length = 60, nullable = false)
	private String nomLoc;
	@OneToMany(mappedBy = "local", cascade = CascadeType.ALL)
	@Fetch(FetchMode.SUBSELECT)
	@XStreamConverter(LocalConverter.class)
	private Set<Hierarquia> hierarquias = new HashSet<Hierarquia>();
	@Column(updatable = false, insertable = false)
	private Integer tabOrg;
	@Column(updatable = false, insertable = false)
	private Integer numLoc;

	public Local() {
	}

	public Local(int tabOrg, int numLoc) {
		this.id.tabOrg = tabOrg;
		this.id.numLoc = numLoc;
	}

	public Date getDatCri() {
		return datCri;
	}

	public Date getDatExt() {
		return datExt;
	}

	public Set<Hierarquia> getHierarquias() {
		return hierarquias;
	}

	public Id getId() {
		return id;
	}

	public String getNomLoc() {
		return nomLoc;
	}

	public void setDatCri(Date datCri) {
		this.datCri = datCri;
	}

	public void setDatExt(Date datExt) {
		this.datExt = datExt;
	}

	public void setHierarquias(Set<Hierarquia> hierarquias) {
		this.hierarquias = hierarquias;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public void setNomLoc(String nomLoc) {
		this.nomLoc = nomLoc;
	}

	public Integer getTabOrg() {
		return tabOrg;
	}

	public void setTabOrg(Integer tabOrg) {
		this.tabOrg = tabOrg;
	}

	public Integer getNumLoc() {
		return numLoc;
	}

	public void setNumLoc(Integer numLoc) {
		this.numLoc = numLoc;
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
		Local other = (Local) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Local: " + this.nomLoc;
	}

	@Embeddable
	public static class Id implements Serializable {
		private static final long	serialVersionUID	= 1L;
		private int tabOrg;
		private int numLoc;

		public Id() {
		}

		public Id(int tabOrg, int numLoc) {
			this.tabOrg = tabOrg;
			this.numLoc = numLoc;
		}

		public int getTabOrg() {
			return tabOrg;
		}

		public void setTabOrg(int tabOrg) {
			this.tabOrg = tabOrg;
		}

		public int getNumLoc() {
			return numLoc;
		}

		public void setNumLoc(int numLoc) {
			this.numLoc = numLoc;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + numLoc;
			result = prime * result + tabOrg;
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
			if (numLoc != other.numLoc)
				return false;
			if (tabOrg != other.tabOrg)
				return false;
			return true;
		}

		

	}
}
