package org.judo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.judo.actions.Action;
import org.judo.actions.ActionProvider2;
import org.judo.actions.impl.NotAuthenticatedAction;
import org.judo.bean.Grade;
import org.judo.services.Service;
import org.judo.util.Attrib;

/**
 * Servlet "action"<br>
 * 
 * Cette servlet est le point d'entrée de toutes les requêtes provenant du navigateur<br> 
 * Elle traite des requêtes du type : <br>
 * . http://host/webapp/action/accueil <br>
 * . http://host/webapp/action/login <br>
 * . http://host/webapp/action/competitions <br>
 * etc... <br>
 * La dernière partie de l'URL désigne l'action à réaliser <br>
 * et donc la page qui en résulte <br> 
 * 
 * @author L. Guerin
 *
 */
@WebServlet(urlPatterns="/action/*")
public class ActionServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	private String templateJSP = "template.jsp" ; 
	private String templateFullPath = "/WEB-INF/jsp/" + templateJSP ; 
       
	@Override
	public void init() throws ServletException {
		super.init();
		
		ServletConfig config = getServletConfig();
//		String s = config.getInitParameter("template");
//		if ( s != null )
//		{
//			template = s ;
//		}
//		
		//--- Récupération de la liste des grades et stockage au niveau application
		LinkedList<Grade> grades = Service.getGrades();
		ServletContext servletContext = config.getServletContext();
		servletContext.setAttribute(Attrib.GRADES, grades);
	}
	
	private void trace(String msg) {
		System.out.println("[TRACE] : " + msg );
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		trace("doGet");
		process(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		trace("doPost");
		process(request, response);
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String actionName = getActionName(request);		
		trace("process : actionName = '" + actionName + "'" );
		
		//Action action = ActionProvider.getAction(actionName);
		Action action = ActionProvider2.getAction(actionName);
		trace("process : action found : " + action.getClass().getCanonicalName() );
		
		// Execute the action (returns the destination page)
		String viewPage = executeAction(action, request, response);
		trace("process : action executed, destination page : '" + viewPage + "'" );
		
		setAttributesForView(request, viewPage);
		trace("process : attributes ready" );
		
		generateView(request, response);
		trace("process : view generated" );
	}
	
	/**
	 * Parses the request URI in order to retrieve the 'action name' 
	 * @param request
	 * @return
	 */
	private String getActionName(HttpServletRequest request) {
		//--- Récupération de la partie de l'URL qui détermine l'action
		String path = request.getPathInfo();
		
		//--- Path par défaut = "/accueil"
		if ( null == path || "".equals(path) || "/".equals(path) )
		{
			path="/accueil" ; 
		}
		
		//--- Nom de l'action = Path sans le "/" du début
		return path.substring(1);
	}
	
	/**
	 * Executes the given action and returns the destination page
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 */
	private String executeAction(Action action, HttpServletRequest request, HttpServletResponse response) {
		trace("executeAction : action " + action.getClass().getCanonicalName()  );
		Action actionToExecute = action ;
		if ( action.isAuthenticationRequired() && isUserAuthenticated(request) == false ) {
			// Force the action to the specific "not authenticated" action
			actionToExecute = new NotAuthenticatedAction();
		}
		trace("executeAction : action to execute " + actionToExecute.getClass().getCanonicalName()  );
		return actionToExecute.execute(request, response);
	}

	private boolean isUserAuthenticated(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if ( session != null ) {
			return  session.getAttribute(Attrib.LOGIN) != null ;
		}
		return false ;
	}
	
	private void setAttributesForView(HttpServletRequest request, String page) { 
		
		//--- Stockage de la page a afficher au niveau requête
		//    Cet attribut est utilisé par le template pour faire un "include"
		request.setAttribute(Attrib.PAGE, page) ;
		trace("process : page = '" + page + "' " );
		
		//--- Stockage de l'URI racine de la servlet action ( ex "/webapp/action" )
		String actionURI = request.getContextPath() + request.getServletPath();
		request.setAttribute(Attrib.ACTION_URI, actionURI);
		trace("process : actionURI = '" + actionURI + "' " );
	}
	
	/**
	 * Cette méthode fait un forward vers la page "template" qui va inclure la partie spécifique <br>
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void generateView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//--- Récupération du "ServletContext"
		ServletContext servletContext = getServletContext();
		
		//--- Récupération du "RequestDispatcher" correspondant à la cible 		
		//    voir <param-name>forwardto</param-name> dans le web.xml 
		//RequestDispatcher rd = servletContext.getNamedDispatcher( template );
		RequestDispatcher rd = servletContext.getRequestDispatcher(templateFullPath);
		if ( rd != null ) {
			//--- Forward vers la ressource configurée dans le web.xml
			rd.forward(request, response);
		}
		else {
			//--- Le "RequestDispatcher" est nul => la cible n'existe pas 
			PrintWriter out = response.getWriter() ;
			out.println("<html>");
			out.println("<head>");
			out.println("</head>");
			out.println("<body>");
			//-----------------------------------------		
			out.println("<h2>Erreur : pas de RequestDispatcher pour '" + templateFullPath + "' ! </h2>");
			//-----------------------------------------
			out.println("</body>");
			out.println("</html>");
		}
	}

}
