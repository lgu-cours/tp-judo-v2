package org.judo.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class GenericAction implements Action {

	@Override
	public boolean authenticationRequired() {
		return false ;
	}
	
	@Override
	public void beforeAction(String method,  HttpServletRequest request, HttpServletResponse response) {
		// Default behavior : nothing to do 
	}
	
	@Override
	public void afterAction(String method,  HttpServletRequest request, HttpServletResponse response) {
		// Default behavior : nothing to do 
	}
}
