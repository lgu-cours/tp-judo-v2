package org.judo.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {

	/**
	 * Action pre-processing
	 * @param method
	 * @param request
	 * @param response
	 */
	void beforeAction(String method,  HttpServletRequest request, HttpServletResponse response) ;
	
	/**
	 * Default processing method for an action <br>
	 * Called when the action is called without method name <br>
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	String execute( HttpServletRequest request, HttpServletResponse response) ;

	/**
	 * Action post-processing
	 * @param method
	 * @param request
	 * @param response
	 */
	void afterAction(String method,  HttpServletRequest request, HttpServletResponse response) ;

	/**
	 * Returns true if authentication is required for this action
	 * @return
	 */
	boolean authenticationRequired() ;

}
