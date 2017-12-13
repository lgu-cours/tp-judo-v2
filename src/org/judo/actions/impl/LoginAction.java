package org.judo.actions.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.judo.actions.GenericAction;
import org.judo.bean.Judoka;
import org.judo.services.Service;
import org.judo.util.Attrib;
import org.judo.util.Msg;
import org.judo.util.PageName;

public class LoginAction extends GenericAction {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		if ( login != null && password != null )
		{
			if ( password.equalsIgnoreCase("ok") )
			{
				//--- Connexion : activation d'une session
				HttpSession session = request.getSession();
				session.setAttribute( Attrib.LOGIN, login );
				
				//--- Chargement du profil du judoka 
				Judoka judoka = Service.getJudoka(login);
				//--- Stockage du profil dans la session 
				session.setAttribute( Attrib.JUDOKA, judoka );
			}
			else
			{
				//--- Login incorrect !
				Msg.setMessage( request, "Login incorrect !" );
			}
		}
		
		return PageName.ACCUEIL ;
	}

}
