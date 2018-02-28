package bean;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity(name = "bairro")
@XStreamAlias("bairro")
public class Bairro implements Serializable {
	private static final long	serialVersionUID	= 1L;
	
	@EmbeddedId
	private IdPK id = new IdPK();
	
	@Column
	@XStreamAlias("nomBai")
	private String nomBai;

	public Bairro(IdPK id, String nomBai) {
		this.id = id;
		this.nomBai = nomBai;
	}

	public Bairro(int codBai, Cidade cidade, String nomBai) {
		this.id.setCidade(cidade);
		this.id.setCodBai(codBai);
		this.nomBai = nomBai;
	}
	
	

	public Bairro() {

	}
	
	public IdPK getId() {
		return id;
	}

	public void setId(IdPK id) {
		this.id = id;
	}

	public String getNomBai() {
		return nomBai;
	}

	public void setNomBai(String nomBai) {
		this.nomBai = nomBai;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nomBai == null) ? 0 : nomBai.hashCode());
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
		Bairro other = (Bairro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nomBai == null) {
			if (other.nomBai != null)
				return false;
		} else if (!nomBai.equals(other.nomBai))
			return false;
		return true;
	}


	@Embeddable
	public static class IdPK implements Serializable {
		private static final long	serialVersionUID	= 1L;
		
		@ManyToOne
	   @JoinColumn(name = "codCid", referencedColumnName = "id", nullable = false)
		@XStreamAlias("codCid")
		private Cidade cidade;
		
		@Column(name = "codBai", nullable = false)
		@XStreamAlias("codBai")
		private int codBai;
	
		public Cidade getCidade() {
			return cidade;
		}

		public void setCidade(Cidade cidade) {
			this.cidade = cidade;
		}

		public int getCodBai() {
			return codBai;
		}

		public void setCodBai(int codBai) {
			this.codBai = codBai;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
			result = prime * result + codBai;
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
			IdPK other = (IdPK) obj;
			if (cidade == null) {
				if (other.cidade != null)
					return false;
			} else if (!cidade.equals(other.cidade))
				return false;
			if (codBai != other.codBai)
				return false;
			return true;
		}
	}
}
