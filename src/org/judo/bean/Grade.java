package org.judo.bean;

public class Grade {

	private String code ;
	private String libelle ;
	
	
	public Grade(String code, String libelle) {
		super();
		this.code = code;
		this.libelle = libelle;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	
}
