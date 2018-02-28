package bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.opensymphony.xwork2.conversion.annotations.TypeConversion;

@Entity
public class RequisicaoEstoque {
	@Id
	@GeneratedValue
	private int id;
	private int numEme;
	private int seqEme;
	private double qtdEme;
	@Column(length = 250)
	private String obsEme;
	@Temporal(TemporalType.TIMESTAMP)
	private Date datReq;
	@Temporal(TemporalType.DATE)
	private Date datPrv;
	@ManyToOne
	@JoinColumns( { @JoinColumn(name = "empresaId", referencedColumnName = "empresaId"),
			@JoinColumn(name = "produtoId", referencedColumnName = "codPro") })
	private Produto produto;
	@Enumerated(EnumType.STRING)
	@Column(length = 1, nullable = true)
	private MotivoRequisicao motReq;
	@ManyToOne
	@JoinColumn(name = "supervisorId")
	private Colaborador supervisor;
	@ManyToOne
	@JoinColumn(name = "usuarioSolId")
	private Usuario usuSol;
	@ManyToOne
	@JoinColumns( { @JoinColumn(name = "clienteId", referencedColumnName = "codFil"),
			@JoinColumn(name = "empresaClienteId", referencedColumnName = "empresaId") })
	private Cliente cliente;
	@Column(length = 50)
	private String conCli;
	private String cmpReq;
	private double qtdApr;
	@Temporal(TemporalType.TIMESTAMP)
	private Date datApr;
	@ManyToOne
	@JoinColumn(name = "usuarioAutId")
	private Usuario usuApr;
	@Enumerated(EnumType.STRING)
	@Column(length = 21, nullable = false)
	private Situacao sitEme;
	@ManyToOne
	@JoinColumn(name = "usuarioPrcId")
	private Usuario usuPrc;
	@Temporal(TemporalType.TIMESTAMP)
	private Date datPrc;
	@Enumerated(EnumType.STRING)
	@Column(length = 4, nullable = true)
	private Setor setor;
	@ManyToOne
	@JoinColumn(name = "centroId", nullable = false)
	private Centro centro;
	@Column(nullable = false, columnDefinition = "DECIMAL(10,2) DEFAULT 0.0")
	private double vlrUni;

	public RequisicaoEstoque() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public double getVlrUni() {
		return vlrUni;
	}

	public void setVlrUni(double vlrUni) {
		this.vlrUni = vlrUni;
	}

	public Centro getCentro() {
		return centro;
	}
	
	public void setCentro(Centro centro) {
		this.centro = centro;
	}

	public int getNumEme() {
		return numEme;
	}

	public void setNumEme(int numEme) {
		this.numEme = numEme;
	}

	public int getSeqEme() {
		return seqEme;
	}

	public void setSeqEme(int seqEme) {
		this.seqEme = seqEme;
	}

	public double getQtdEme() {
		return qtdEme;
	}

	public void setQtdEme(double qtdEme) {
		this.qtdEme = qtdEme;
	}

	public String getObsEme() {
		return obsEme;
	}

	public void setObsEme(String obsEme) {
		this.obsEme = obsEme;
	}

	public Date getDatReq() {
		return datReq;
	}

	public void setDatReq(Date datReq) {
		this.datReq = datReq;
	}

	public Date getDatPrv() {
		return datPrv;
	}

	public void setDatPrv(Date datPrv) {
		this.datPrv = datPrv;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@TypeConversion(converter = "converter.EnumTypeConverter")
	public MotivoRequisicao getMotReq() {
		return motReq;
	}

	public void setMotReq(MotivoRequisicao motReq) {
		this.motReq = motReq;
	}

	public Colaborador getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Colaborador supervisor) {
		this.supervisor = supervisor;
	}

	public Usuario getUsuSol() {
		return usuSol;
	}

	public void setUsuSol(Usuario usuSol) {
		this.usuSol = usuSol;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getConCli() {
		return conCli;
	}

	public void setConCli(String conCli) {
		this.conCli = conCli;
	}

	public double getQtdApr() {
		return qtdApr;
	}

	public void setQtdApr(double qtdApr) {
		this.qtdApr = qtdApr;
	}

	public Date getDatApr() {
		return datApr;
	}

	public void setDatApr(Date datApr) {
		this.datApr = datApr;
	}

	public Usuario getUsuApr() {
		return usuApr;
	}

	public void setUsuApr(Usuario usuApr) {
		this.usuApr = usuApr;
	}

	public Situacao getSitEme() {
		return sitEme;
	}

	public void setSitEme(Situacao sitEme) {
		this.sitEme = sitEme;
	}

	@TypeConversion(converter = "converter.EnumTypeConverter")
	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	public Usuario getUsuPrc() {
		return usuPrc;
	}

	public void setUsuPrc(Usuario usuPrc) {
		this.usuPrc = usuPrc;
	}

	public Date getDatPrc() {
		return datPrc;
	}

	public void setDatPrc(Date datPrc) {
		this.datPrc = datPrc;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequisicaoEstoque other = (RequisicaoEstoque) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getCmpReq() {
		return cmpReq;
	}

	public void setCmpReq(String cmpReq) {
		this.cmpReq = cmpReq;
	}

	public enum MotivoRequisicao {
		I("Implantação"), S("Substituição"), H("Serviço Extra"), A("Alteração");
		private String descricao;

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		private MotivoRequisicao(String descricao) {
			this.descricao = descricao;
		}
	}

	public enum Setor {
		ADM("Administrativo"), ALM("Almoxarifado"), COM("Comercial"), CTB("Contabilidade"), FIN(
				"Financeiro"), FAT("Faturamento"), INF("Informática"), JUR("Jurídico"), MON(
				"Monitoramento"), OPE("Operacional"), RHU("Recursos Humanos"), C21("TV Cidade"), VIV("Vivo"), LIC("Licitações") ;
		private String descricao;

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		private Setor(String descricao) {
			this.descricao = descricao;
		}

	}

	public enum Situacao {
		Digitado(1, "Digitado"), Aprovado(2, "Aprovado"), Compras(3, "Processo Compras"), Atendido(
				4, "Atendido"), ConfirmadoRecebimento(5, "Confirmado Recebimento"), Cancelado(9,
				"Cancelado");
		private int id;
		private String descricao;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		public static Situacao loadById(Integer id) {
			for (Situacao situacao : Situacao.values()) {
				if (situacao.getId() == id)
					return situacao;
			}
			return null;
		}

		private Situacao(int id, String descricao) {
			this.id = id;
			this.descricao = descricao;
		}
	}
}
