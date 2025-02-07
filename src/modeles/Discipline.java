
/**
 * @author Mathias Petibon-Gravier / Emile Rossi / Oumar Anhe / Adrien Ozon
 */

package modeles;

import java.io.Serializable;

/**
 * La classe Discipline représente une discipline sportive.
 */
public class Discipline implements Serializable {
	//--------------------------
	// ATTRIBUTS
	//--------------------------
	
    private static final long serialVersionUID = 1L;

    private int num; 
    private String nom; 
    static int numGlobale = 0; 
    
    //--------------------------
  	// CONSTRUCTEUR
  	//--------------------------
     
    /**
     * Constructeur de la classe Discipline.
     */
    public Discipline(String nom) {
        numGlobale++; 
        this.num = numGlobale;
        this.nom = nom;
    }
    
    //--------------------------
  	// ACCESSEURS
  	//--------------------------
    
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