package org.judo.actions.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.judo.actions.GenericAction;
import org.judo.util.PageName;

public class LogoutAction extends GenericAction {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		//--- Deconnexion : fin de session 
		HttpSession session = request.getSession();
		session.invalidate();
		
		return PageName.ACCUEIL ;
	}

}
