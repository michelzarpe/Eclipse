package bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

@Entity
public class SolicitacaoCompra {
	@GeneratedValue
	@Id
	private int id;
	@ManyToOne
	@JoinColumn(name = "empresaId", nullable = false)
	private Empresa empresa;
	private int numSol;
	private int seqSol;
	private double qtdSol;
	@Column(length = 250)
	private String cplPro;
	private double preSol;
	@Column(length = 250)
	private String obsSol;
	@ManyToOne
	@JoinColumn(name = "usuarioSol", nullable = false)
	private Usuario usuSol;
	@Temporal(TemporalType.DATE)
	private Date datPrv;
	@Temporal(TemporalType.TIMESTAMP)
	private Date datSol;
	@OneToMany(mappedBy = "solicitacaoCompra", cascade = CascadeType.ALL)
	@OrderBy("id")
	@XStreamOmitField
	private Set<Cotacao> cotacoes = new HashSet<Cotacao>();
	@Temporal(TemporalType.TIMESTAMP)
	private Date datAlt;
	@ManyToOne
	@JoinColumn(name = "usuarioAlt", nullable = false)
	private Usuario usuAlt;

	public SolicitacaoCompra() {
	}

	public Set<Cotacao> getCotacoes() {
		return cotacoes;
	}

	public void setCotacoes(Set<Cotacao> cotacoes) {
		this.cotacoes = cotacoes;
	}

	public Date getDatAlt() {
		return datAlt;
	}

	public void setDatAlt(Date datAlt) {
		this.datAlt = datAlt;
	}

	public Usuario getUsuAlt() {
		return usuAlt;
	}

	public void setUsuAlt(Usuario usuAlt) {
		this.usuAlt = usuAlt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public int getNumSol() {
		return numSol;
	}

	public void setNumSol(int numSol) {
		this.numSol = numSol;
	}

	public int getSeqSol() {
		return seqSol;
	}

	public void setSeqSol(int seqSol) {
		this.seqSol = seqSol;
	}

	public double getQtdSol() {
		return qtdSol;
	}

	public void setQtdSol(double qtdSol) {
		this.qtdSol = qtdSol;
	}

	public String getCplPro() {
		return cplPro;
	}

	public void setCplPro(String cplPro) {
		this.cplPro = cplPro;
	}

	public double getPreSol() {
		return preSol;
	}

	public void setPreSol(double preSol) {
		this.preSol = preSol;
	}

	public String getObsSol() {
		return obsSol;
	}

	public void setObsSol(String obsSol) {
		this.obsSol = obsSol;
	}

	public Usuario getUsuSol() {
		return usuSol;
	}

	public void setUsuSol(Usuario usuSol) {
		this.usuSol = usuSol;
	}

	public Date getDatPrv() {
		return datPrv;
	}

	public void setDatPrv(Date datPrv) {
		this.datPrv = datPrv;
	}

	public Date getDatSol() {
		return datSol;
	}

	public void setDatSol(Date datSol) {
		this.datSol = datSol;
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
		SolicitacaoCompra other = (SolicitacaoCompra) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
