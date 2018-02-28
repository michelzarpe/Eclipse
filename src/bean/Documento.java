package bean;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity
@XStreamAlias("documento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Documento implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Id
	@GeneratedValue
	private int						id;
	private String					nomDoc;
	@ManyToOne
	@JoinColumn(name = "usuarioId")
	private Usuario				usuario;
	private Date					datInc;
	private Date					datAtu;
	@ManyToOne
	@JoinColumn(name = "docPaiId", nullable = true)
	private Documento				docPai;
	@OneToMany(mappedBy = "docPai")
	private Set<Documento>		docFilhos			= new HashSet<Documento>();
	private String					nomeArquivo;
	@Column(columnDefinition = "LONGBLOB")
	private byte[]					arquivo;
	private String					formatoArquivo;

	public Documento() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomDoc() {
		return nomDoc;
	}

	public void setNomDoc(String nomDoc) {
		this.nomDoc = nomDoc;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getDatInc() {
		return datInc;
	}

	public void setDatInc(Date datInc) {
		this.datInc = datInc;
	}

	public Date getDatAtu() {
		return datAtu;
	}

	public void setDatAtu(Date datAtu) {
		this.datAtu = datAtu;
	}

	public Documento getDocPai() {
		return docPai;
	}

	public void setDocPai(Documento docPai) {
		this.docPai = docPai;
	}

	public Set<Documento> getDocFilhos() {
		return docFilhos;
	}

	public void setDocFilhos(Set<Documento> docFilhos) {
		this.docFilhos = docFilhos;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public byte[] getArquivo() {
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	public String getFormatoArquivo() {
		return formatoArquivo;
	}

	public void setFormatoArquivo(String formatoArquivo) {
		this.formatoArquivo = formatoArquivo;
	}
	
	@Override
	public String toString() {
		return this.getId() + " - " + this.getNomDoc();
	}

}
