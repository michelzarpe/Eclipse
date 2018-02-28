/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

import converter.DateConverter;

/**
 *
 * @author mcl
 */

@Entity(name="turmaHorarioRonda")
@XStreamAlias("turmaHorarioRonda")
public class TurmaHorarioRonda implements Serializable {
	private static final long		serialVersionUID	= 1L;

	@Id
	@XStreamAlias("id")
	private int							tmahor;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	@XStreamConverter(DateConverter.class)
	private Date						data;

	@OneToOne
	@JoinColumn(name = "FK_CodEsc")
	@XStreamAlias("escalaHorarioRondaMZ")
	private EscalaHorarioRondaMZ	ehrmz;

	public TurmaHorarioRonda() {

	}

	public TurmaHorarioRonda(int tmahor, Date data, EscalaHorarioRondaMZ ehrmz) {
		this.ehrmz = new EscalaHorarioRondaMZ();
		this.tmahor = tmahor;
		this.data = data;
		this.ehrmz = ehrmz;
	}

	public int getTmahor() {
		return tmahor;
	}

	public void setTmahor(int tmahor) {
		this.tmahor = tmahor;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public EscalaHorarioRondaMZ getEhrmz() {
		return ehrmz;
	}

	public void setEhrmz(EscalaHorarioRondaMZ ehrmz) {
		this.ehrmz = ehrmz;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((ehrmz == null) ? 0 : ehrmz.hashCode());
		result = prime * result + tmahor;
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
		TurmaHorarioRonda other = (TurmaHorarioRonda) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (ehrmz == null) {
			if (other.ehrmz != null)
				return false;
		} else if (!ehrmz.equals(other.ehrmz))
			return false;
		if (tmahor != other.tmahor)
			return false;
		return true;
	}

}
