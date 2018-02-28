package bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Cobertura {
	@Id
	private int id;
	@ManyToOne
	@JoinColumn(name = "ocorrenciaId")
	private Ocorrencia ocorrencia;
	@ManyToOne
	@JoinColumn(name = "colaboradorId")
	private Colaborador colaborador;
	private String obsCob;
	@Column(length = 5)
	private String horEnt1;
	@Column(length = 5)
	private String horSai1;
	@Column(length = 5)
	private String horEnt2;
	@Column(length = 5)
	private String horSai2;
	@Temporal(TemporalType.DATE)
	private Date datIni;
	@Temporal(TemporalType.DATE)
	private Date datTer;

	public Cobertura() {
	}

	public int getId() {
		return id;
	}

	public Ocorrencia getOcorrencia() {
		return ocorrencia;
	}

	public void setOcorrencia(Ocorrencia ocorrencia) {
		this.ocorrencia = ocorrencia;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public String getObsCob() {
		return obsCob;
	}

	public void setObsCob(String obsCob) {
		this.obsCob = obsCob;
	}

	public String getHorEnt1() {
		return horEnt1;
	}

	public void setHorEnt1(String horEnt1) {
		this.horEnt1 = horEnt1;
	}

	public String getHorSai1() {
		return horSai1;
	}

	public void setHorSai1(String horSai1) {
		this.horSai1 = horSai1;
	}

	public String getHorEnt2() {
		return horEnt2;
	}

	public void setHorEnt2(String horEnt2) {
		this.horEnt2 = horEnt2;
	}

	public String getHorSai2() {
		return horSai2;
	}

	public void setHorSai2(String horSai2) {
		this.horSai2 = horSai2;
	}

	public Date getDatIni() {
		return datIni;
	}

	public void setDatIni(Date datIni) {
		this.datIni = datIni;
	}

	public Date getDatTer() {
		return datTer;
	}

	public void setDatTer(Date datTer) {
		this.datTer = datTer;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toString() {
		return "Cobertura[id=" + id + "]";
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!this.getClass().equals(obj.getClass())) {
			return false;
		}
		Cobertura that = (Cobertura) obj;
		if (this.id != that.id) {
			return false;
		}
		return true;
	}

	public int hashCode() {
		return 5924 + id;
	}
}
