package bean;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity (name="horarioescalaronda")
@XStreamAlias("horarioescalaronda")
public class HorarioEscalaRonda implements Serializable {
	private static final long		serialVersionUID	= 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XStreamAlias("id")
	private Integer					id;

	@OneToOne
	@JoinColumn(name = "FK_codEsc", nullable = false)
	@XStreamAlias("escalaHorarioRondaMZ")
	private EscalaHorarioRondaMZ	codEsc;

	@OneToOne
	@JoinColumn(name = "FK_seqReg", nullable = false)
	@XStreamAlias("sequenciaRegistro")
	private SequenciaRegistro		seqReg;

	@OneToOne
	@JoinColumn(name = "FK_codHor", nullable = false)
	@XStreamAlias("horarioRonda")
	private HorarioRonda				horarioRonda;
	
	

	public HorarioEscalaRonda() {

	}

	public HorarioEscalaRonda(EscalaHorarioRondaMZ codEsc, SequenciaRegistro seqReg,
			HorarioRonda horarioRonda) {
		this.codEsc = codEsc;
		this.seqReg = seqReg;
		this.horarioRonda = horarioRonda;
	}

	public EscalaHorarioRondaMZ getCodEsc() {
		return codEsc;
	}

	public void setCodEsc(EscalaHorarioRondaMZ codEsc) {
		this.codEsc = codEsc;
	}

	public SequenciaRegistro getSeqReg() {
		return seqReg;
	}

	public void setSeqReg(SequenciaRegistro seqReg) {
		this.seqReg = seqReg;
	}

	public HorarioRonda getHorarioRonda() {
		return horarioRonda;
	}

	public void setHorarioRonda(HorarioRonda horarioRonda) {
		this.horarioRonda = horarioRonda;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codEsc == null) ? 0 : codEsc.hashCode());
		result = prime * result + ((horarioRonda == null) ? 0 : horarioRonda.hashCode());
		result = prime * result + ((seqReg == null) ? 0 : seqReg.hashCode());
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
		HorarioEscalaRonda other = (HorarioEscalaRonda) obj;
		if (codEsc == null) {
			if (other.codEsc != null)
				return false;
		} else if (!codEsc.equals(other.codEsc))
			return false;
		if (horarioRonda == null) {
			if (other.horarioRonda != null)
				return false;
		} else if (!horarioRonda.equals(other.horarioRonda))
			return false;
		if (seqReg == null) {
			if (other.seqReg != null)
				return false;
		} else if (!seqReg.equals(other.seqReg))
			return false;
		return true;
	}

}
