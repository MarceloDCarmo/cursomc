package com.mdcarmo.sprgbappbackend.domain.enums;

public enum Perfil {

	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");
	
	private int cod;
	private String descricao;
	
	private Perfil(int cod, String desc) {
		this.cod = cod;
		this.descricao = desc;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static Perfil toEnum(Integer cod) {
		if(cod == null)
			return null;
		
		for (Perfil c  : Perfil.values()) {
			if(cod.equals(c.getCod())) 
				return c;
		}
		
		throw new IllegalArgumentException("Id: " + cod + " não encontrado!");
	}
	
}
