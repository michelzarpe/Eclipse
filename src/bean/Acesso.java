package bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Acesso implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Id
	@GeneratedValue
	private int id;
	private String idSession;
	@Column(nullable = false)
	private Timestamp datAce;
	@Temporal(TemporalType.TIMESTAMP)
	private Date datSai;
	@ManyToOne
	@JoinColumn(name = "usuarioId", nullable = false)
	private Usuario usuario;

	public Acesso() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getDatAce() {
		return datAce;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setDatAce(Timestamp datAce) {
		this.datAce = datAce;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getIdSession() {
		return idSession;
	}

	public void setIdSession(String idSession) {
		this.idSession = idSession;
	}

	public Date getDatSai() {
		return datSai;
	}

	public void setDatSai(Date datSai) {
		this.datSai = datSai;
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
		Acesso other = (Acesso) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
