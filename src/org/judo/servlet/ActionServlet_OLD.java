package org.judo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.judo.bean.Competition;
import org.judo.bean.Grade;
import org.judo.bean.Judoka;
import org.judo.services.Service;
import org.judo.util.Attrib;
import org.judo.util.Msg;

/**
 * Servlet "action"<br>
 * 
 * Cette servlet est le point d'entr�e de toutes les requ�tes provenant du navigateur<br> 
 * Elle traite des requ�tes du type : <br>
 * . http://host/webapp/action/accueil <br>
 * . http://host/webapp/action/login <br>
 * . http://host/webapp/action/competitions <br>
 * etc... <br>
 * La derni�re partie de l'URL d�signe l'action � r�aliser <br>
 * et donc la page qui en r�sulte <br> 
 * 
 * @author L. Guerin
 *
 */
public class ActionServlet_OLD extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	private String template = "template" ; 
       
	@Override
	public void init() throws ServletException {
		super.init();
		
		ServletConfig config = getServletConfig();
		String s = config.getInitParameter("template");
		if ( s != null )
		{
			template = s ;
		}
		
		//--- R�cup�ration de la liste des grades et stockage au niveau application
		LinkedList<Grade> grades = Service.getGrades();
		ServletContext servletContext = config.getServletContext();
		servletContext.setAttribute(Attrib.GRADES, grades);
	}
	
	private void trace(String msg)
	{
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
		trace("process : " + request.getMethod() + " " + request.getRequestURL() );
		
		//--- R�cup�ration de la partie de l'URL qui d�termine l'action
		String path = request.getPathInfo();
		
		//--- Path par d�faut = "/accueil"
		if ( null == path || "".equals(path) || "/".equals(path) )
		{
			path="/accueil" ; 
		}
		
		//--- Nom de l'action = Path sans le "/" du d�but
		String action = path.substring(1);

		//--- Par d�faut, page de destination = Nom de l'action 
		String page = action ;

		trace("process : action = '" + action + "' : traitements..." );
		
		//-------------------------------------------------------------------------
		// 1) Le "controleur" r�alise les traitements et met � jour le "mod�le"
		//-------------------------------------------------------------------------
		if ( "login".equalsIgnoreCase(action) )
		{
			//--- Traitement : tentative de login
			login (request);
			page = "accueil";
			
		}
		else if ( "logout".equalsIgnoreCase(action) )
		{
			//--- Traitement : logout
			logout (request);
			page = "accueil";
		}
		else if ( "profil".equalsIgnoreCase(action) ) 
		{
			//--- Traitement : affichage ou mise � jour du profil
			if ( "post".equalsIgnoreCase( request.getMethod() ) )
			{
				//--- POST => Mise � jour du profil
				modifProfil(request);
			}
			else
			{
				//--- GET => Simple affichage du profil
				// D�j� en session
			}
		}
		else if ( "competitions".equalsIgnoreCase(action) ) 
		{
			if ( "post".equalsIgnoreCase( request.getMethod() ) )
			{
				//--- POST => S�lection d'une comp�tition dans la liste
				//selectionCompetition(request);
			}
			else
			{
				//--- GET => Simple affichage de la liste des comp�titions
				
				//--- Chargement de la liste des comp�tions 
				LinkedList<Competition> competitions = chargerCompetitions() ;
				//--- Stockage de la liste des comp�tions au niveau "REQUEST"
				request.setAttribute( Attrib.COMPETITIONS, competitions ) ;
			}
		}
		
		//-------------------------------------------------------------------------
		// 2) Les traitements sont faits, le "mod�le" est � jour => on affiche la "vue"
		//-------------------------------------------------------------------------

		trace("process : action = '" + action + "' : g�n�ration de la vue..." );
		
		//--- Stockage de la page a afficher au niveau requ�te
		//    Cet attribut est utilis� par le template pour faire un "include"
		request.setAttribute(Attrib.PAGE, page) ;
		trace("process : page = '" + page + "' " );
		
		//--- Stockage de l'URI racine de la servlet action ( ex "/webapp/action" )
		String sActionURI = request.getContextPath() + request.getServletPath();
		request.setAttribute(Attrib.ACTION_URI, sActionURI);
		trace("process : actionURI = '" + sActionURI + "' " );
		
		//--- G�n�ration de la "vue" ( page JSP )
		genererPageReponse( request, response);
		
	}
	
	/**
	 * Cette m�thode fait un forward vers la page "template" qui va inclure la partie sp�cifique <br>
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void genererPageReponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//--- R�cup�ration du "ServletContext"
		ServletContext servletContext = getServletContext();
		
		//--- R�cup�ration du "RequestDispatcher" correspondant � la cible 		
		//    voir <param-name>forwardto</param-name> dans le web.xml 
		RequestDispatcher rd = servletContext.getNamedDispatcher( template );
		if ( rd != null )
		{
			//--- Forward vers la ressource configur�e dans le web.xml
			rd.forward(request, response);
		}
		else
		{
			//--- Le "RequestDispatcher" est nul => la cible n'existe pas 
			PrintWriter out = response.getWriter() ;
			out.println("<html>");
			out.println("<head>");
			out.println("</head>");
			out.println("<body>");
			//-----------------------------------------		
			out.println("<h2>Erreur : pas de RequestDispatcher pour servlet '" + template + "' ! </h2>");
			//-----------------------------------------
			out.println("</body>");
			out.println("</html>");
		}
	}
	
	/**
	 * Action "login" : l'utilisateur tente de se connecter ( identification login + password )
	 * @param request
	 * @return
	 */
	private boolean login( HttpServletRequest request )
	{
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
				Judoka judoka = chargerProfil( login );
				//--- Stockage du profil dans la session 
				session.setAttribute( Attrib.JUDOKA, judoka );
				return true ;
			}
			else
			{
				//--- Login incorrect !
				//request.setAttribute("msg", "Login incorrect !");
				Msg.setMessage( request, "Login incorrect !" );
			}
		}
		return false ;
	}

	/**
	 * Action "logout" : l'utilisateur se d�connecte
	 * @param request
	 */
	private void logout( HttpServletRequest request )
	{
		//--- Deconnexion : fin de session 
		HttpSession session = request.getSession();
		session.invalidate();
	}

	/**
	 * Chargement du profil du judoka � partir de son identifiant ( login )
	 * Simulation d'un acc�s � la base de donn�es
	 * @param login
	 * @return
	 */
	private Judoka chargerProfil( String login )
	{
		Judoka judoka = new Judoka();
		judoka.setLogin(login);
		
		judoka.setCodeGrade("B");
		judoka.setNom("Dupont");
		judoka.setPrenom("Jean");
		judoka.setPoids(75);
		judoka.setDateNais( new Date() );
		
		return judoka ;
	}

	private LinkedList<Competition> chargerCompetitions()
	{
		return Service.getCompetitions();
	}

	/**
	 * Modification du profil du judoka 
	 * Simulation d'une mise � jour dans la base de donn�es
	 * @param request
	 */
	private void modifProfil( HttpServletRequest request )
	{
		trace("--- modifProfil");
		
		HttpSession session = request.getSession(false);
		if ( session != null )
		{
			Object obj = session.getAttribute( Attrib.JUDOKA );
			if ( obj != null )
			{
				if ( obj instanceof Judoka )
				{
					Judoka judoka = (Judoka) obj ;
					String v ;

					//--- Mise a jour du bean (MODELE) avec les param�tres de la requ�te
					v = request.getParameter("nom");
					if ( v != null )  judoka.setNom(v);

					v = request.getParameter("prenom");
					if ( v != null )  judoka.setPrenom(v);

					v = request.getParameter("codeGrade");
					if ( v != null )  judoka.setCodeGrade(v);

					v = request.getParameter("login");
					if ( v != null )  judoka.setLogin(v);

					v = request.getParameter("dateNais");
					if ( v != null )  
					{
						Date dateNais;
						try {
							//--- NB : Controle "basic" de date pour TP ( incomplet ! )
							SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
							dateNais = df.parse(v);
							judoka.setDateNais(dateNais);
						} catch (ParseException e) {
							// TODO : g�rer l'erreur
							Msg.setMessage( request, "Date de naissance incorrecte !" );
						}
					}
					
					v = request.getParameter("poids");
					if ( v != null )  
					{
						int poids;
						try {
							poids = Integer.parseInt(v);
							judoka.setPoids(poids);
						} catch (NumberFormatException e) {
							// TODO : g�rer l'erreur
							Msg.setMessage( request, "Poids incorrect !" );
							//judoka.setPoids(0);
						}
					}
				}
			}
		}
		
	}

}
