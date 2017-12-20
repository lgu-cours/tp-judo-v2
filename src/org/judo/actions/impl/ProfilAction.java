package org.judo.actions.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.judo.actions.GenericAction;
import org.judo.bean.Judoka;
import org.judo.util.Attrib;
import org.judo.util.Msg;
import org.judo.util.PageName;

public class ProfilAction extends GenericAction {
	
	@Override
	public boolean isAuthenticationRequired() {
		return true ;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		if ( session != null ) {
			Object obj = session.getAttribute( Attrib.JUDOKA );
			if ( obj != null && obj instanceof Judoka ) {
					Judoka judoka = (Judoka) obj ;
					String v ;

					//--- Mise a jour du bean (MODELE) avec les paramètres de la requête
					v = request.getParameter("nom");
					if ( v != null )  judoka.setNom(v);

					v = request.getParameter("prenom");
					if ( v != null )  judoka.setPrenom(v);

					v = request.getParameter("codeGrade");
					if ( v != null )  judoka.setCodeGrade(v);

					v = request.getParameter("login");
					if ( v != null )  judoka.setLogin(v);

					v = request.getParameter("dateNais");
					if ( v != null ) {
						Date dateNais;
						try {
							//--- NB : Controle "basic" de date pour TP ( incomplet ! )
							SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
							dateNais = df.parse(v);
							judoka.setDateNais(dateNais);
						} catch (ParseException e) {
							// TODO : gérer l'erreur
							Msg.setMessage( request, "Date de naissance incorrecte !" );
						}
					}
					
					v = request.getParameter("poids");
					if ( v != null ) {
						int poids;
						try {
							poids = Integer.parseInt(v);
							judoka.setPoids(poids);
						} catch (NumberFormatException e) {
							// TODO : gérer l'erreur
							Msg.setMessage( request, "Poids incorrect !" );
							//judoka.setPoids(0);
						}
					}
			}
		}
		
		return PageName.PROFIL ;
	}

}
