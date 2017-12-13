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

public class ActionProvider {

	private static final Map<String,Action> map = new HashMap<>();
	
	static {
		map.put("accueil",  new AccueilAction());
		map.put("login",  new LoginAction());
		map.put("logout", new LogoutAction());
		map.put("profil", new ProfilAction());
		map.put("update-profil", new UpdateProfilAction());
		map.put("coordonnees", new CoordonneesAction());
		map.put("competitions", new CompetitionsAction());
	}
	
	public static final Action getAction(String actionName) {
		Action action = map.get(actionName);
		if ( action != null ) {
			return action ;
		}
		else {
			throw new IllegalStateException("Action '" + actionName + "' not found");
			//return new ErrorAction("Action '" + actionName + "' not found");
		}
	}
}
