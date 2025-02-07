
/**
 * @author Mathias Petibon-Gravier / Emile Rossi / Oumar Anhe / Adrien Ozon
 */

package modeles;

import java.io.Serializable;
import java.util.ArrayList;
import Liens.SessionManager;

/**
 * La classe Planning représente un planning de sessions avec une date et une durée.
 */
public class Planning implements Serializable {
	//--------------------------
	// ATTRIBUTS
	//--------------------------
		
	private static final long serialVersionUID = 1L;
    private SessionManager sessionManager;
	private int num; 
	private int duree;
	private String date; 
	private static int numGlobale = 0; 
	private int currentSessionIndex = 0; 

    //--------------------------
  	// CONSTRUCTEUR
  	//--------------------------
     
    /**
     * Constructeur de la classe Planning.
     */
	public Planning(String date, int duree) {
        sessionManager = SessionManager.getInstance(); 
		numGlobale++; 
		this.num = numGlobale;
		this.date = date;
		this.duree = duree;
	}
	
    //--------------------------
  	// METHODES
  	//--------------------------
     
    /**
     * Obtient la session courante du planning.
     */
    public Session getCurrentSession() {
        if (sessionManager.getSessions().isEmpty()) {
            return null;
        }
        return sessionManager.getSessions().get(currentSessionIndex);
    }

    /**
     * Définit l'index de la session courante.
     */
    public void setCurrentSessionIndex(int index) {
        if (index >= 0 && index < sessionManager.getSessions().size()) {
            currentSessionIndex = index;
        }
    }

    /**
     * Passe à la session suivante dans le planning.
     */
    public void nextSession() {
        if (currentSessionIndex < sessionManager.getSessions().size() - 1) {
            currentSessionIndex++;
        } 
        else {
            currentSessionIndex = 0; 
        }
    }

    /**
     * Revient à la session précédente dans le planning.
     */
    public void previousSession() {
        if (currentSessionIndex > 0) {
            currentSessionIndex--;
        } 
        else {
            currentSessionIndex = sessionManager.getSessions().size() - 1; 
        }
    }
    
    //--------------------------
  	// ACCESSEURS
  	//--------------------------
    
    public ArrayList<Session> getSesSessions() {
		return sessionManager.getSessions();
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}
}
