
/**
 * @author Mathias Petibon-Gravier / Emile Rossi / Oumar Anhe / Adrien Ozon
 */

package modeles;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * La classe Epreuve représente une épreuve sportive.
 */
public class Epreuve implements Serializable {
	//--------------------------
	// ATTRIBUTS
	//--------------------------
	
    private static final long serialVersionUID = 1L;

    private Session saSession;
    private Discipline saDiscipline; 
    private ArrayList<Equipe> sesEquipesParticipantes; 
    private int num; 
    private String nom; 
    private String date; 
    private String horaire; 
    private String lieu; 
    static int numGlobale = 0;

    //--------------------------
  	// CONSTRUCTEUR
  	//--------------------------
     
    /**
     * Constructeur de la classe Epreuve.
     */
    public Epreuve(String nom, String date, String horaire, String lieu, String nomDiscipline) {
        numGlobale++; 
        this.num = numGlobale;
        this.nom = nom;
        this.date = date;
        this.horaire = horaire;
        this.lieu = lieu;
        this.saDiscipline = new Discipline(nomDiscipline);
    }
    
    //--------------------------
  	// METHODES
  	//--------------------------

    /**
     * Modifie les informations de l'épreuve.
     */
    public void modifier(String nom, String date, String horaire, String lieu, String nomDiscipline) {
        this.nom = nom;
        this.date = date;
        this.horaire = horaire;
        this.lieu = lieu;
        this.saDiscipline.setNom(nomDiscipline);
    }

    /**
     * Supprime les informations de l'épreuve.
     */
    public void supprimer() {
        this.nom = null;
        this.date = null;
        this.horaire = null;
        this.lieu = null;
    }

    public String toString() {
        return "Epreuve [num= " + num + ", nom=" + nom + ", date=" + date + ", horaire=" + horaire + ", lieu=" + lieu + ", discipline=" + saDiscipline.getNom() + "]";
    }
    
    //--------------------------
  	// ACCESSEURS
  	//--------------------------
    
    public Session getSaSession() {
        return saSession;
    }

    public void setSaSession(Session saSession) {
        this.saSession = saSession;
    }

    public Discipline getSaDiscipline() {
        return saDiscipline;
    }

    public void setSaDiscipline(Discipline saDiscipline) {
        this.saDiscipline = saDiscipline;
    }

    public ArrayList<Equipe> getSesEquipesParticipantes() {
        return sesEquipesParticipantes;
    }

    public void setSesEquipesParticipantes(ArrayList<Equipe> sesEquipesParticipantes) {
        this.sesEquipesParticipantes = sesEquipesParticipantes;
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

    public String getHoraire() {
        return horaire;
    }

    public void setHoraire(String horaire) {
        this.horaire = horaire;
    }


    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }
}
