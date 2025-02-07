
/**
 * @author Mathias Petibon-Gravier / Emile Rossi / Oumar Anhe / Adrien Ozon
 */

package modeles;

import java.io.Serializable;

/**
 * La classe Athlete représente un athlète participant à une épreuve sportive.
 */
public class Athlete implements Serializable {
	//--------------------------
	// ATTRIBUTS
	//--------------------------
	
    private static final long serialVersionUID = 1L;

    private int num; 
    private String prenom;
    private String nom; 
    private String nationalite; 
    private String discipline; 
    private String pays; 
    private Equipe sonEquipe; 
    static int numGlobale = 0; 

    //--------------------------
  	// CONSTRUCTEUR
  	//--------------------------
     
    /**
     * Constructeur de la classe Athlete.
     */
    public Athlete(String prenom, String nom, String nationalite, String discipline, String pays) {
        numGlobale++; 
        this.num = numGlobale;
        this.prenom = prenom;
        this.nom = nom;
        this.nationalite = nationalite;
        this.discipline = discipline;
        this.pays = pays;
    }
    
    //--------------------------
  	// METHODES
  	//--------------------------
     
    public String toString() {
        return "Athlete [num=" + num + ", prenom=" + prenom + ", nom=" + nom + ", nationalite=" + nationalite +
                ", discipline=" + discipline + ", pays=" + pays + "]";
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public Equipe getSonEquipe() {
        return sonEquipe;
    }

    public void setSonEquipe(Equipe sonEquipe) {
        this.sonEquipe = sonEquipe;
    }

}
