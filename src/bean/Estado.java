package bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("estado")
public class Estado {
	private String sigUFS;
	private String nomUFS;

	public String getSigUFS() {
		return sigUFS;
	}

	public void setSigUFS(String sigUFS) {
		this.sigUFS = sigUFS;
	}

	public String getNomUFS() {
		return nomUFS;
	}

	public void setNomUFS(String nomUFS) {
		this.nomUFS = nomUFS;
	}

	public Estado() {

	}

	public Estado(String sigUFS, String nomUFS) {
		this.sigUFS = sigUFS;
		this.nomUFS = nomUFS;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sigUFS == null) ? 0 : sigUFS.hashCode());
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
		Estado other = (Estado) obj;
		if (sigUFS == null) {
			if (other.sigUFS != null)
				return false;
		} else if (!sigUFS.equals(other.sigUFS))
			return false;
		return true;
	}

}
