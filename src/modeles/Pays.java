
/**
 * @author Mathias Petibon-Gravier / Emile Rossi / Oumar Anhe / Adrien Ozon
 */

package modeles;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * La classe Pays représente un pays dans le contexte sportif.
 */
public class Pays implements Serializable {
	//--------------------------
	// ATTRIBUTS
	//--------------------------
	
    private static final long serialVersionUID = 1L;

    private ArrayList<Equipe> sesEquipes;
    private ArrayList<Athlete> sesAthletes; 
    private int num; 
    private String nom; 
    private static int numGlobale = 0; 

    //--------------------------
  	// CONSTRUCTEUR
  	//--------------------------
     
    /**
     * Constructeur de la classe Pays.
     */
    public Pays(String nom) {
        numGlobale++; 
        this.num = numGlobale;
        this.nom = nom;
    }

    //--------------------------
  	// ACCESSEURS
  	//--------------------------
    
    public ArrayList<Equipe> getSesEquipes() {
        return sesEquipes;
    }

    public void setSesEquipes(ArrayList<Equipe> sesEquipes) {
        this.sesEquipes = sesEquipes;
    }

    public ArrayList<Athlete> getSesAthletes() {
        return sesAthletes;
    }

    public void setSesAthletes(ArrayList<Athlete> sesAthletes) {
        this.sesAthletes = sesAthletes;
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
}
