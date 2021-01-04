package com.mdcarmo.cursomc.domain.enums;

public enum TipoCliente {

	PESSOA_FISICA(1, "Pessoa Física"),
	PESSOA_JURIDICA(2, "Pessoa Jurídica");
	
	private int cod;
	private String descricao;
	
	private TipoCliente(int cod, String desc) {
		this.cod = cod;
		this.descricao = desc;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static TipoCliente toEnum(Integer cod) {
		if(cod == null)
			return null;
		
		for (TipoCliente c  : TipoCliente.values()) {
			if(cod.equals(c.getCod())) 
				return c;
		}
		
		throw new IllegalArgumentException("Id: " + cod + " não encontrado!");
	}
	
}
