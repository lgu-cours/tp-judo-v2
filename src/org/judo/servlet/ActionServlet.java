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
import org.judo.actions.ActionProvider;
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
	
	private String getActionName(HttpServletRequest request) throws ServletException, IOException  {
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
	
	private boolean userAuthenticated(HttpServletRequest request) throws ServletException, IOException  {
		HttpSession session = request.getSession(false);
		if ( session != null ) {
			return  session.getAttribute(Attrib.LOGIN) != null ;
		}
		return false ;
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String actionName = getActionName(request);		
		trace("process : actionName = '" + actionName + "'" );
		
		Action action = ActionProvider.getAction(actionName);
		
		trace("process : action class : " + action.getClass().getSimpleName() );
		
		if ( action.isAuthenticationRequired() ) {
			trace("process : authentication required "  );
			if ( userAuthenticated(request) ) {
				executeAction(action, request, response );
			}
			else {
				executeAction(new NotAuthenticatedAction(), request, response );
			}
		}
		else {
			trace("process : no authentication required "  );
			executeAction(action, request, response );
		}
	}
	
	private void executeAction(Action action, HttpServletRequest request, 
			HttpServletResponse response ) throws ServletException, IOException {
		
		trace("process : execute action '" + action.getClass().getSimpleName() + "'");

		String page = action.execute(request, response);

		trace("process : view page = '" + page + "'" );
		
		//-------------------------------------------------------------------------
		// 2) Les traitements sont faits, le "modèle" est à jour => on affiche la "vue"
		//-------------------------------------------------------------------------

		trace("process : action = '" + action + "' : génération de la vue..." );
		
		//--- Stockage de la page a afficher au niveau requête
		//    Cet attribut est utilisé par le template pour faire un "include"
		request.setAttribute(Attrib.PAGE, page) ;
		trace("process : page = '" + page + "' " );
		
		//--- Stockage de l'URI racine de la servlet action ( ex "/webapp/action" )
		String sActionURI = request.getContextPath() + request.getServletPath();
		request.setAttribute(Attrib.ACTION_URI, sActionURI);
		trace("process : actionURI = '" + sActionURI + "' " );
		
		//--- Génération de la "vue" ( page JSP )
		generateView( request, response);
		
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
