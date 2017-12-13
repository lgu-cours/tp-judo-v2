package org.judo.actions.impl;

import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.judo.actions.GenericAction;
import org.judo.bean.Competition;
import org.judo.services.Service;
import org.judo.util.Attrib;
import org.judo.util.PageName;

public class CompetitionsAction extends GenericAction {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		//--- Get model : competitions lists
		LinkedList<Competition> competitions = Service.getCompetitions();
		
		//--- Expose model 
		request.setAttribute(Attrib.COMPETITIONS, competitions);
		
		//--- Goto page 
		return PageName.COMPETITIONS ;
	}

}
