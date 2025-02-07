
/**
 * @author Adrien Ozon
 */

package Liens;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import modeles.Session;

/**
 * Cette classe gère la sauvegarde et le chargement des sessions dans un fichier.
 */
public class SessionManager implements Serializable {
	//--------------------------
	// ATTRIBUTS
	//--------------------------
	
	private static final long serialVersionUID = 1L;
    private static final String FILE_NAME = "sessions.dat";
    private static SessionManager instance;
    private ArrayList<Session> sessions;

    //--------------------------
  	// CONSTRUCTEUR
  	//--------------------------
     
    private SessionManager() {
        sessions = new ArrayList<>();
        chargerSessions(FILE_NAME);
    }

    //--------------------------
  	// METHODES
  	//--------------------------
     
    /**
     * Récupère l'instance unique de SessionManager.
     */
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    /**
     * Ajoute une session à la liste des sessions.
     */
    public void addSession(Session session) {
        sessions.add(session);
    }

    /**
     * Supprime une session de la liste des sessions.
     */
    public void supprimerSession(Session session) {
        sessions.remove(session);
    }

    

    /**
     * Sauvegarde les sessions dans un fichier.
     */
    public void sauvegarderSessions(String nomFichier) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nomFichier))) {
            out.writeObject(sessions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Charge les sessions à partir d'un fichier.
     */
    public void chargerSessions(String nomFichier) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(nomFichier))) {
            sessions = (ArrayList<Session>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    //--------------------------
  	// ACCESSEURS
  	//--------------------------
    
    public ArrayList<Session> getSessions() {
        return new ArrayList<>(sessions);
    }
}
