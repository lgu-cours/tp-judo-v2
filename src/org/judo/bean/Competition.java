package org.judo.bean;

import java.util.Date;

public class Competition 
{
	private int    id ;
	private Date   date ; 
	private String nom ;
	private String ville ;
	private String adresse ;
	private String departement ;
	
	
	public Competition(int id, Date date, String nom, String ville,
			String adresse, String departement) {
		super();
		this.id = id;
		this.date = date;
		this.nom = nom;
		this.ville = ville;
		this.adresse = adresse;
		this.departement = departement;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	
	public String getDepartement() {
		return departement;
	}
	public void setDepartement(String departement) {
		this.departement = departement;
	}
	
	

}
