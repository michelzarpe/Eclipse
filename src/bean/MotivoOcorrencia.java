package bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@Entity
public class MotivoOcorrencia {
	@Id
	private int id;
	@Column(length = 40)
	@XStreamAlias("name")
	private String desMot;
	@Enumerated(EnumType.STRING)
	@Column(length = 2)
	private TipoMotivo tipMot;
	@Enumerated(EnumType.STRING)
	@Column(length = 2)
	private SubTipoMotivo subTip;
	private boolean gerCF;
	@OneToMany(mappedBy="motivo")
	@XStreamOmitField
	private Set<bean.SubTipoMotivo> subTiposMotivo = new HashSet<bean.SubTipoMotivo>();

	public MotivoOcorrencia() {
	}

	public MotivoOcorrencia(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesMot() {
		return desMot;
	}

	public void setDesMot(String desMot) {
		this.desMot = desMot;
	}

	public TipoMotivo getTipMot() {
		return tipMot;
	}

	public void setTipMot(TipoMotivo tipMot) {
		this.tipMot = tipMot;
	}

	public SubTipoMotivo getSubTip() {
		return subTip;
	}

	public void setSubTip(SubTipoMotivo subTip) {
		this.subTip = subTip;
	}

	public boolean isGerCF() {
		return gerCF;
	}

	public void setGerCF(boolean gerCF) {
		this.gerCF = gerCF;
	}

	public Set<bean.SubTipoMotivo> getSubTiposMotivo() {
		return subTiposMotivo;
	}

	public void setSubTiposMotivo(Set<bean.SubTipoMotivo> subTiposMotivo) {
		this.subTiposMotivo = subTiposMotivo;
	}

	@Override
	public String toString() {
		return "Motivo[id: " + this.id + ", desMot: " + this.desMot + "]";
	}

	public enum TipoMotivo {
		AP("Assiduidade e Pontualidade"), MV("Movimentação");
		private String realName;

		public int getId() {
			return this.ordinal();
		}

		public String getRealName() {
			return realName;
		}

		public void setRealName(String realName) {
			this.realName = realName;
		}

		private TipoMotivo(String realName) {
			this.realName = realName;
		}
	}

	public enum SubTipoMotivo {
		FJ("Falta Justificada"), OU("Outros"), TE("Troca de Escala"), TF("Troca de Função"), TH(
				"Troca de Horário"), TP("Troca de Posto"), SE("Serviço Extra no Cliente");
		private String realName;

		public int getId() {
			return this.ordinal();
		}

		public String getRealName() {
			return realName;
		}

		public void setRealName(String realName) {
			this.realName = realName;
		}

		private SubTipoMotivo(String realName) {
			this.realName = realName;
		}
	}
}
