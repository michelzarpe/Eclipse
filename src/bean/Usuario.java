package bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.opensymphony.xwork2.conversion.annotations.Conversion;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@Entity
@Conversion
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
@XStreamAlias("colaborador")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Usuario extends Colaborador {
	private static final long	serialVersionUID	= 1L;
	@Column(length = 15, nullable = false)
	private String codUsu;
	@Column(length = 15, nullable = false)
	private String senUsu;
	@Enumerated(EnumType.STRING)
	@Column(length = 3)
	private TipoUsuario tipUsu;
	@Column(length = 50, nullable = false)
	private String email;
	private boolean recebeAviso;
	
	@ManyToMany
	@XStreamOmitField
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Centro> centrosPermitidos = new HashSet<Centro>();
	
	
	@ManyToOne
	@JoinColumn(name = "gerenteId")
	@XStreamOmitField
	private Usuario gerente;

	public Usuario() {

	}

	public Usuario getGerente() {
		return gerente;
	}

	public void setGerente(Usuario gerente) {
		this.gerente = gerente;
	}

	public String getCodUsu() {
		return codUsu;
	}

	public String getEmail() {
		return email;
	}

	public String getSenUsu() {
		return senUsu;
	}

	public boolean isRecebeAviso() {
		return recebeAviso;
	}

	public void setCodUsu(String codUsu) {
		this.codUsu = codUsu;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setRecebeAviso(boolean recebeAviso) {
		this.recebeAviso = recebeAviso;
	}

	public void setSenUsu(String senUsu) {
		this.senUsu = senUsu;
	}

	@Override
	public String toString() {
		return "Usuario Codigo = " + " Nome= " + this.getCodUsu();
	}

	public TipoUsuario getTipUsu() {
		return tipUsu;
	}

	@TypeConversion(converter = "converter.EnumTypeConverter")
	public void setTipUsu(TipoUsuario tipUsu) {
		this.tipUsu = tipUsu;
	}

	public Set<Centro> getCentrosPermitidos() {
		return centrosPermitidos;
	}

	@TypeConversion(converter = "converter.CollectionConverter")
	public void setCentrosPermitidos(Set<Centro> centrosPermitidos) {
		this.centrosPermitidos = centrosPermitidos;
	}

	public enum TipoUsuario {
		/**
		 * 0 = OAL, 1 = AAL, 2 = OAD, 3 = AAD, 4 = OUN, 5 = AUN, 6 = ASI, 7 =AAU
		 * 
		 */
		OAL("Operador Almoxarifado"), AAL("Administrador Almoxarifado"), OAD("Operador Admissão"), AAD(
				"Administrador Admissão"), OUN("Operador Unidade"), AUN("Administrador Unidade"), ASI(
				"Administrador Sistema"), AAU("Administrador de Autorização"), ACO(
				"Operador Comercial"), OCO("Operador de compras"), SGQ("Administrador do SGQ");
		private String descricao;

		public int getId() {
			return this.ordinal();
		}

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		public void setRealName(String descricao) {
			this.descricao = descricao;
		}

		private TipoUsuario(String descricao) {
			this.descricao = descricao;
		}
	}

}
