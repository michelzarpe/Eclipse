package bean;

import java.io.Serializable;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@Entity(name= "escalaHorarioRondaMZ")
@XStreamAlias("escalaHorarioRondaMZ")
public class EscalaHorarioRondaMZ implements Serializable {
	private static final long	serialVersionUID	= 1L;

	@Id
	@XStreamAlias("id")
	private Integer				id;
	
	@Column
	@XStreamAlias("horSem")
	private int horSem;
	
	@Column
	@XStreamAlias("horMes")
	private int horMes;
	
	@Column(length = 40)
	@XStreamAlias("nomesc")
	private String nomesc;
	
	@OneToOne
	@JoinColumn(name = "FK_claEsc")
	private ClasseEscalaRonda	classeEscalaRonda;
	
	public EscalaHorarioRondaMZ() {
		this.classeEscalaRonda = new ClasseEscalaRonda();
	}

	public EscalaHorarioRondaMZ(Integer id, ClasseEscalaRonda classeEscalaRonda, int horSem, int horMes, String nomesc) {
		this.classeEscalaRonda = new ClasseEscalaRonda();
		this.id = id;
		this.classeEscalaRonda = classeEscalaRonda;
		this.horMes = horMes;
		this.horSem = horSem;
		this.nomesc = nomesc;		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getHorSem() {
		return horSem;
	}

	public void setHorSem(int horSem) {
		this.horSem = horSem;
	}

	public int getHorMes() {
		return horMes;
	}

	public void setHorMes(int horMes) {
		this.horMes = horMes;
	}

	public String getNomesc() {
		return nomesc;
	}

	public void setNomesc(String nomesc) {
		this.nomesc = nomesc;
	}

	public ClasseEscalaRonda getClasseEscalaRonda() {
		return classeEscalaRonda;
	}

	public void setClasseEscalaRonda(ClasseEscalaRonda classeEscalaRonda) {
		this.classeEscalaRonda = classeEscalaRonda;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classeEscalaRonda == null) ? 0 : classeEscalaRonda.hashCode());
		result = prime * result + horMes;
		result = prime * result + horSem;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nomesc == null) ? 0 : nomesc.hashCode());
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
		EscalaHorarioRondaMZ other = (EscalaHorarioRondaMZ) obj;
		if (classeEscalaRonda == null) {
			if (other.classeEscalaRonda != null)
				return false;
		} else if (!classeEscalaRonda.equals(other.classeEscalaRonda))
			return false;
		if (horMes != other.horMes)
			return false;
		if (horSem != other.horSem)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nomesc == null) {
			if (other.nomesc != null)
				return false;
		} else if (!nomesc.equals(other.nomesc))
			return false;
		return true;
	}
	
	
}
