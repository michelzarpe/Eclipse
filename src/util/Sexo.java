package util;


public enum Sexo {
	M("Masculino"), F("Feminino");
	
	private String descricao;
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	private Sexo(String descricao){
		this.descricao = descricao;
	}
}
