package bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@Entity
public class SubTipoMotivo {
	@Id
	private int id;
	@ManyToOne
	@JoinColumn(name = "motivoId")
	@XStreamOmitField
	private MotivoOcorrencia motivo;
	@Column(length=40)
	@XStreamAlias("name")
	private String desSub;

	public SubTipoMotivo() {
	}

	public SubTipoMotivo(MotivoOcorrencia motivo, int id) {
		this.motivo = motivo;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MotivoOcorrencia getMotivo() {
		return motivo;
	}

	public void setMotivo(MotivoOcorrencia motivo) {
		this.motivo = motivo;
	}

	public String getDesSub() {
		return desSub;
	}

	public void setDesSub(String desSub) {
		this.desSub = desSub;
	}

	@Override
	public String toString() {
		return "SubTipoMotivo[motivoId:" + this.motivo.getId() + ", id: " + this.id
				+ ", desSub: " + this.desSub + "]";
	}

}
