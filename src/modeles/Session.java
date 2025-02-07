
/**
 * @author Mathias Petibon-Gravier / Emile Rossi / Oumar Anhe / Adrien Ozon
 */

package modeles;

import java.io.Serializable;
import java.util.ArrayList;
import Liens.SessionManager;

/**
 * La classe Session représente une session avec un nom, une date et un lieu, et contient des épreuves.
 */
public class Session implements Serializable {
	//--------------------------
	// ATTRIBUTS
	//--------------------------
		
	private static final long serialVersionUID = 1L;
    private SessionManager sessionManager; 
	private ArrayList<Epreuve> sesEpreuves; 
	
	private int num; 
	private String nom; 
	private String date;
	private String lieu;
	private static int numGlobale = 0; 

    //--------------------------
  	// CONSTRUCTEUR
  	//--------------------------
     
    /**
     * Constructeur de la classe Session.
     */
	public Session(String nom, String date, String lieu) {
		numGlobale++; 
		this.num = numGlobale;
		this.nom = nom;
		this.date = date;
		this.lieu = lieu;
		this.sesEpreuves = new ArrayList<Epreuve>();
        sessionManager = SessionManager.getInstance(); 
        sessionManager.addSession(this); 
	}
	
    //--------------------------
  	// METHODES
  	//--------------------------
     
    /**
     * Constructeur par défaut de la classe Session.
     */
	public Session() {
        sessionManager = SessionManager.getInstance(); 
	}

    /**
     * Modifie les détails de la session.
     */
	public void modifier(String nom, String date, String lieu) {
		this.nom = nom;
		this.date = date;
		this.lieu = lieu;
	}

    /**
     * Supprime la session en effaçant ses détails.
     */
	public void supprimer() {
		this.nom = null;
		this.date = null;
		this.lieu = null;
	}

	public String toString() {
		return "Session [num= " + num + ", nom=" + nom + ", date=" + date + ", lieu=" + lieu + "]";
	}

	/**
     * Ajoute une épreuve à la session.
     */
	public void ajouterEpreuveSession(Epreuve epreuve) {
		sesEpreuves.add(epreuve);
	}

	/**
     * Ajoute une épreuve à la session.
     */
	public void supprimerEpreuveSession(Epreuve epreuve) {
		sesEpreuves.removeIf(e -> e.equals(epreuve));
	}

    //--------------------------
  	// ACCESSEURS
  	//--------------------------
    
	public ArrayList<Epreuve> getSesEpreuves() {
		return sesEpreuves;
	}

	public void setSesEpreuves(ArrayList<Epreuve> sesEpreuves) {
		this.sesEpreuves = sesEpreuves;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLieu() {
		return lieu;
	}

	public void setLieu(String lieu) {
		this.lieu = lieu;
	}
}
