package bean;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@Entity
@XStreamAlias("empresa")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Empresa implements Serializable {
	private static final long	serialVersionUID	= 1L;
	@Id
	private int id;
	@Column(length = 40, nullable = false)
	@XStreamAlias("name")
	private String nomEmp;
	
	@OneToMany(mappedBy = "empresa")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("nomFun")
	@XStreamOmitField
	private List<Colaborador> colaboradores = new LinkedList<Colaborador>();
	
	
	@OneToMany(mappedBy = "empresa")
	@Fetch(FetchMode.SUBSELECT)
	@XStreamOmitField
	private List<Cliente> clientes = new LinkedList<Cliente>();
	
	
	@Column(length=2)
	private String codLoc;
	@Column(columnDefinition = "INT(11) DEFAULT 0")
	private int empSap;

	public Empresa() {

	}

	public int getEmpSap() {
		return empSap;
	}

	public void setEmpSap(int empSap) {
		this.empSap = empSap;
	}

	public Empresa(int id) {
		this.id = id;
	}

	public String getCodLoc() {
		return codLoc;
	}

	public void setCodLoc(String codLoc) {
		this.codLoc = codLoc;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Empresa))
			return false;
		final Empresa other = (Empresa) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public List<Colaborador> getColaboradores() {
		return colaboradores;
	}

	public int getId() {
		return id;
	}

	public String getNomEmp() {
		return nomEmp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public void setColaboradores(List<Colaborador> colaboradores) {
		this.colaboradores = colaboradores;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNomEmp(String nomEmp) {
		this.nomEmp = nomEmp;
	}

}
