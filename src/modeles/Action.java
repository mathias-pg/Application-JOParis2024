
/**
 * @author Mathias Petibon-Gravier
 */

package modeles;

/**
 * Cette interface définit les différentes actions pouvant être effectuées dans l'application.
 */
public interface Action {
	//--------------------------
	// ATTRIBUTS
	//--------------------------
	
	static public final String ACTION_MODIFIER = "MODIFIER";
    static public final String ACTION_CREER = "CREER";
    static public final String ACTION_SUPPRIMER = "SUPPRIMER";
    static public final String ACTION_AJOUTER_ATHLETE = "AJOUTER_ATHLETE";
    static public final String ACTION_SUPPRIMER_ATHLETE = "SUPPRIMER_ATHLETE";
    static public final String ACTION_DROITE = "DROITE";
    static public final String ACTION_GAUCHE = "GAUCHE";
    static public final String ACTION_AJOUTER_EPREUVE = "AJOUTER_EPREUVE";
    static public final String ACTION_SUPPRIMER_EPREUVE = "SUPPRIMER_EPREUVE";
    static public final String ACTION_RETOUR = "RETOUR";
    static public final String ACTION_GESTION_EPREUVES = "GESTION_EPREUVES";
    static public final String ACTION_GESTION_SESSIONS = "GESTION_SESSIONS";
    static public final String ACTION_GESTION_EQUIPES = "GESTION_EQUIPES";
    static public final String ACTION_GESTION_PLANNING = "GESTION_PLANNING";
}