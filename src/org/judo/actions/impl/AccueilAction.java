package org.judo.actions.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.judo.actions.GenericAction;
import org.judo.util.PageName;

public class AccueilAction extends GenericAction {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		return PageName.ACCUEIL ;
	}

}
