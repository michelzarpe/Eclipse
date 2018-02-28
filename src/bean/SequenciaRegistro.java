package bean;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@Entity(name="sequenciaRegistro")
@XStreamAlias("sequenciaRegistro")
public class SequenciaRegistro implements Serializable {
	private static final long	serialVersionUID	= 1L;

	@Id
	@XStreamAlias("id")
	private Integer				seqReg;

	public SequenciaRegistro() {}

	public SequenciaRegistro(Integer seqReg) {
		this.seqReg = seqReg;
	}

}
