package bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Cotacao implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Id
	@GeneratedValue
	private int id;
	@ManyToOne
	@JoinColumn(name = "fornecedorId", nullable = false)
	private Fornecedor fornecedor;
	private int qtdCot;
	private double preCot;
	private Date przEnt;
	private String obsCot;
	@ManyToOne
	@JoinColumn(name = "solicitacaoId", nullable = false)
	private SolicitacaoCompra solicitacaoCompra;
	@ManyToOne
	@JoinColumn(name = "codCpg", nullable = false)
	private CondicaoPagamento condicaoPagamento;
	private int numCot;
	private int seqCot;

	public CondicaoPagamento getCondicaoPagamento() {
		return condicaoPagamento;
	}

	public void setCondicaoPagamento(CondicaoPagamento condicaoPagamento) {
		this.condicaoPagamento = condicaoPagamento;
	}

	public SolicitacaoCompra getSolicitacaoCompra() {
		return solicitacaoCompra;
	}

	public void setSolicitacaoCompra(SolicitacaoCompra solicitacaoCompra) {
		this.solicitacaoCompra = solicitacaoCompra;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public int getQtdCot() {
		return qtdCot;
	}

	public void setQtdCot(int qtdCot) {
		this.qtdCot = qtdCot;
	}

	public double getPreCot() {
		return preCot;
	}

	public void setPreCot(double preCot) {
		this.preCot = preCot;
	}

	public Date getPrzEnt() {
		return przEnt;
	}

	public void setPrzEnt(Date przEnt) {
		this.przEnt = przEnt;
	}

	public String getObsCot() {
		return obsCot;
	}

	public void setObsCot(String obsCot) {
		this.obsCot = obsCot;
	}

	public int getNumCot() {
		return numCot;
	}

	public void setNumCot(int numCot) {
		this.numCot = numCot;
	}

	public int getSeqCot() {
		return seqCot;
	}

	public void setSeqCot(int seqCot) {
		this.seqCot = seqCot;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fornecedor == null) ? 0 : fornecedor.hashCode());
		result = prime * result + id;
		result = prime * result + numCot;
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
		Cotacao other = (Cotacao) obj;
		if (fornecedor == null) {
			if (other.fornecedor != null)
				return false;
		} else if (!fornecedor.equals(other.fornecedor))
			return false;
		if (id != other.id)
			return false;
		if (numCot != other.numCot)
			return false;
		return true;
	}

}
