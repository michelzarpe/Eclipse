/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 *
 * @author mcl
 */
@Entity(name= "classeEscalaRonda")
@XStreamAlias("classeEscalaRonda")
public class ClasseEscalaRonda implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @XStreamAlias("id")
 	 private Integer claEsc;
    
    @Column
    @XStreamAlias("name")
 	 private String desCla;

    public ClasseEscalaRonda() {
    }

    public ClasseEscalaRonda(int claesc, String descla) {
        this.claEsc = claesc;
        this.desCla = descla;
    }

	public Integer getClaEsc() {
		return claEsc;
	}

	public void setClaEsc(Integer claEsc) {
		this.claEsc = claEsc;
	}

	public String getDesCla() {
		return desCla;
	}

	public void setDesCla(String desCla) {
		this.desCla = desCla;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((claEsc == null) ? 0 : claEsc.hashCode());
		result = prime * result + ((desCla == null) ? 0 : desCla.hashCode());
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
		ClasseEscalaRonda other = (ClasseEscalaRonda) obj;
		if (claEsc == null) {
			if (other.claEsc != null)
				return false;
		} else if (!claEsc.equals(other.claEsc))
			return false;
		if (desCla == null) {
			if (other.desCla != null)
				return false;
		} else if (!desCla.equals(other.desCla))
			return false;
		return true;
	}

   
    
    
}
