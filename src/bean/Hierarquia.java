package bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@Entity
@XStreamAlias("hierarquia")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Hierarquia implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@EmbeddedId
	@XStreamOmitField
	private Id id = new Id();
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date datFim;
	@Column(length = 24, nullable = false)
	private String posLoc;
	@Column(length = 32, nullable = false)
	private String codLoc;
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumns( {
			@JoinColumn(name = "numLoc", referencedColumnName = "numLoc", insertable = false, updatable = false),
			@JoinColumn(name = "tabOrg", referencedColumnName = "tabOrg", insertable = false, updatable = false) })
	private Local local;

	public Hierarquia() {
	}

	public String getCodLoc() {
		return codLoc;
	}

	public Date getDatFim() {
		return datFim;
	}

	public Id getId() {
		return id;
	}

	public String getPosLoc() {
		return posLoc;
	}

	public void setCodLoc(String codLoc) {
		this.codLoc = codLoc;
	}

	public void setDatFim(Date datFim) {
		this.datFim = datFim;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public void setPosLoc(String posLoc) {
		this.posLoc = posLoc;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	@Embeddable
	public static class Id implements Serializable {
		private static final long	serialVersionUID	= 1L;
		private Integer tabOrg;
		private Integer numLoc;
		@Temporal(TemporalType.DATE)
		private Date datIni;

		public Id() {
		}

		public Id(int tabOrg, int numLoc, Date datIni) {
			this.tabOrg = tabOrg;
			this.numLoc = numLoc;
			this.datIni = datIni;
		}

		public Date getDatIni() {
			return datIni;
		}

		public void setDatIni(Date datIni) {
			this.datIni = datIni;
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
			result = prime * result + ((datIni == null) ? 0 : datIni.hashCode());
			result = prime * result + ((numLoc == null) ? 0 : numLoc.hashCode());
			result = prime * result + ((tabOrg == null) ? 0 : tabOrg.hashCode());
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
			if (datIni == null) {
				if (other.datIni != null)
					return false;
			} else if (!datIni.equals(other.datIni))
				return false;
			if (numLoc == null) {
				if (other.numLoc != null)
					return false;
			} else if (!numLoc.equals(other.numLoc))
				return false;
			if (tabOrg == null) {
				if (other.tabOrg != null)
					return false;
			} else if (!tabOrg.equals(other.tabOrg))
				return false;
			return true;
		}

	}
}
