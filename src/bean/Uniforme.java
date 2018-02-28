package bean;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.opensymphony.xwork2.conversion.annotations.TypeConversion;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@Entity
@XStreamAlias("uniforme")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Uniforme implements Serializable {
	private static final long		serialVersionUID	= 1L;
	@Id
	@XStreamAlias("uniformeId")
	private int							id;
	@Column(length = 50, nullable = false)
	private String						desEpi;
	@Column(nullable = false)
	private int							diaVal;
	@Column(nullable = false)
	private int							qtdMax;
	@ManyToOne
	@JoinColumn(name = "tipoId")
	private TipoEPI					tipoEPI;
	@Column(length = 1)
	private String						sitEpi;
	@Column(length = 350)
	private String						cplDes;
	// CONFIGURANDO DESSA FORMA, O HIBERNATE GRAVA A CARACTERÍSTICA NO BANCO SE ESTA NÃO EXISTIR
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(updatable = true, insertable = true)
	@XStreamOmitField
	private Set<Caracteristica>	caracteristicas;

	public Uniforme() {

	}

	public Set<Caracteristica> getCaracteristicas() {
		return caracteristicas;
	}

	@TypeConversion(converter = "converter.CollectionConverter")
	public void setCaracteristicas(Set<Caracteristica> caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public String getCplDes() {
		return cplDes;
	}

	public void setCplDes(String cplDes) {
		this.cplDes = cplDes;
	}

	public String getSitEpi() {
		return sitEpi;
	}

	public void setSitEpi(String sitEpi) {
		this.sitEpi = sitEpi;
	}

	public Uniforme(int id) {
		this.id = id;
	}

	public String getDesEpi() {
		return desEpi;
	}

	public int getDiaVal() {
		return diaVal;
	}

	public int getId() {
		return id;
	}

	public int getQtdMax() {
		return qtdMax;
	}

	public TipoEPI getTipoEPI() {
		return tipoEPI;
	}

	public void setDesEpi(String desEpi) {
		this.desEpi = desEpi;
	}

	public void setDiaVal(int diaVal) {
		this.diaVal = diaVal;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setQtdMax(int qtdMax) {
		this.qtdMax = qtdMax;
	}

	public void setTipoEPI(TipoEPI tipoEPI) {
		this.tipoEPI = tipoEPI;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Uniforme other = (Uniforme) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Uniforme " + this.id;
	}
}
