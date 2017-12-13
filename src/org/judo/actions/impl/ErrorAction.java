package org.judo.actions.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.judo.actions.GenericAction;
import org.judo.util.Attrib;
import org.judo.util.PageName;

public class ErrorAction extends GenericAction {

	private final String message ;
	
	public ErrorAction(String message) {
		super();
		this.message = message;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		//--- Expose model 
		request.setAttribute(Attrib.ERREUR, message);

		return PageName.ERREUR ;
	}

}
