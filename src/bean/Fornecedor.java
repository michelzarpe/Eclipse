package bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "codFor", "cgcCpf" }) })
public class Fornecedor implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Id
	@GeneratedValue
	private int id;
	private int codFor;
	@Column(length = 100)
	private String nomFor;
	@Column(length = 50)
	private String apeFor;
	@Column(length = 1)
	private String tipFor;
	@Column(length=18)
	private String cgcCpf;
	@Column(length = 100)
	private String endFor;
	@Column(length = 25)
	private String insEst;
	@Column(length = 16)
	private String insMun;
	@Column(length = 20)
	private String cplEnd;
	@Column(length = 75)
	private String baiFor;
	@Column(length = 9)
	private String cepFor;
	@Column(length = 60)
	private String cidFor;
	@Column(length = 2)
	private String sigUfs;
	@Column(length = 20)
	private String fonFor;
	@Column(length = 20)
	private String faxFor;
	@Column(length = 100)
	private String intNet;
	@Column(length = 1)
	private String sitFor;

	public Fornecedor() {

	}

	public int getCodFor() {
		return codFor;
	}

	public void setCodFor(int codFor) {
		this.codFor = codFor;
	}

	public String getSigUfs() {
		return sigUfs;
	}

	public void setSigUfs(String sigUfs) {
		this.sigUfs = sigUfs;
	}

	public String getTipFor() {
		return tipFor;
	}

	public void setTipFor(String tipFor) {
		this.tipFor = tipFor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomFor() {
		return nomFor;
	}

	public void setNomFor(String nomFor) {
		this.nomFor = nomFor;
	}

	public String getApeFor() {
		return apeFor;
	}

	public void setApeFor(String apeFor) {
		this.apeFor = apeFor;
	}

	

	public String getEndFor() {
		return endFor;
	}

	public void setEndFor(String endFor) {
		this.endFor = endFor;
	}

	public String getInsEst() {
		return insEst;
	}

	public void setInsEst(String insEst) {
		this.insEst = insEst;
	}

	public String getInsMun() {
		return insMun;
	}

	public void setInsMun(String insMun) {
		this.insMun = insMun;
	}

	public String getCplEnd() {
		return cplEnd;
	}

	public void setCplEnd(String cplEnd) {
		this.cplEnd = cplEnd;
	}

	public String getBaiFor() {
		return baiFor;
	}

	public void setBaiFor(String baiFor) {
		this.baiFor = baiFor;
	}

	public String getCepFor() {
		return cepFor;
	}

	public void setCepFor(String cepFor) {
		this.cepFor = cepFor;
	}

	public String getCidFor() {
		return cidFor;
	}

	public void setCidFor(String cidFor) {
		this.cidFor = cidFor;
	}

	public String getFonFor() {
		return fonFor;
	}

	public void setFonFor(String fonFor) {
		this.fonFor = fonFor;
	}

	public String getFaxFor() {
		return faxFor;
	}

	public void setFaxFor(String faxFor) {
		this.faxFor = faxFor;
	}

	public String getIntNet() {
		return intNet;
	}

	public void setIntNet(String intNet) {
		this.intNet = intNet;
	}

	public String getSitFor() {
		return sitFor;
	}

	public void setSitFor(String sitFor) {
		this.sitFor = sitFor;
	}

	public String getCgcCpf() {
		return cgcCpf;
	}

	public void setCgcCpf(String cgcCpf) {
		this.cgcCpf = cgcCpf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cgcCpf == null) ? 0 : cgcCpf.hashCode());
		result = prime * result + codFor;
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
		Fornecedor other = (Fornecedor) obj;
		if (cgcCpf == null) {
			if (other.cgcCpf != null)
				return false;
		} else if (!cgcCpf.equals(other.cgcCpf))
			return false;
		if (codFor != other.codFor)
			return false;
		return true;
	}

	

}
