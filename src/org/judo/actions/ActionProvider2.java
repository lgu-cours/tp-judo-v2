package org.judo.actions;

import java.util.HashMap;
import java.util.Map;

import org.judo.actions.impl.AccueilAction;
import org.judo.actions.impl.CompetitionsAction;
import org.judo.actions.impl.CoordonneesAction;
import org.judo.actions.impl.ErrorAction;
import org.judo.actions.impl.LoginAction;
import org.judo.actions.impl.LogoutAction;
import org.judo.actions.impl.ProfilAction;
import org.judo.actions.impl.UpdateProfilAction;

/**
 * Action provider 
 * with a new instance for each 'getAction()'
 *
 */
public class ActionProvider2 {

	private static final Map<String,Class<? extends Action>> map = new HashMap<>();
	
	static {
		map.put("accueil",  AccueilAction.class);
		map.put("login",  LoginAction.class);
		map.put("logout", LogoutAction.class);
		map.put("profil", ProfilAction.class);
		map.put("update-profil", UpdateProfilAction.class);
		map.put("coordonnees", CoordonneesAction.class);
		map.put("competitions", CompetitionsAction.class);
	}
	
	public static final Action getAction(String actionName) {
		Class<? extends Action> clazz = map.get(actionName);
		if ( clazz != null ) {
			Action action;
			try {
				action = clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				return new ErrorAction("Cannot create action '" + actionName + "' instance");
			}
			return action ;
		}
		else {
			//throw new IllegalStateException("Action '" + actionName + "' not found");
			return new ErrorAction("Action '" + actionName + "' not found");
		}
	}
}
