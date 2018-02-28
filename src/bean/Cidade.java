package bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.opensymphony.xwork2.conversion.annotations.TypeConversion;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity
@XStreamAlias("cidade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cidade implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Id
	private int id;
	@Column(length = 20)
	private String nomCid;
	@Enumerated(EnumType.STRING)
	@Column(length = 2)
	private Estado estCid;

	public Cidade() {

	}

	public Cidade(int id) {
		this.id = id;
	}

	@TypeConversion(converter = "converter.EnumTypeConverter")
	public Estado getEstCid() {
		return estCid;
	}

	public int getId() {
		return id;
	}

	public String getNomCid() {
		return nomCid;
	}

	public void setEstCid(Estado estCid) {
		this.estCid = estCid;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNomCid(String nomCid) {
		this.nomCid = nomCid;
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
		Cidade other = (Cidade) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public enum Estado {
		AC("Acre"), 
		AL("Alagoas"),
		AM("Amazonas"),
		AP("Amapá"), 
		BA("Bahia"), 
		CE("Ceará"), 
		DF("Distrito Federal"),
		ES("Espírito Santo"), 
		FN("Fernando de Noronha"),
		GO("Goiás"), 
		MA("Maranhão"),
		MG("Minas Gerais"),
		MS("Mato Grosso do Sul"),
		MT("Mato Grosso"),
		PA("Pará"),
		PB("Paraíba"),
		PE("Pernambuco"),
		PI("Piauí"),
		PR("Paraná"),
		RJ("Rio de Janeiro"),
		RN("Rio Grande do Norte"),
		RO("Rondônia"),
		RR("Roraima"), 
		RS("Rio Grande do Sul"),
		SC("Santa Catarina"),
		SE("Sergipe"),
		SP("São Paulo"),
		TO("Tocantins"),
		XX("Estrangeiros");
	
		private String descricao;

		private Estado(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

	  
	}

}
