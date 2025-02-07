
/**
 * @author Mathias Petibon-Gravier / Emile Rossi / Oumar Anhe / Adrien Ozon
 */

package modeles;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * La classe Equipe représente une équipe sportive.
 */
public class Equipe implements Serializable {
	//--------------------------
	// ATTRIBUTS
	//--------------------------
	
    private static final long serialVersionUID = 1L;

    private ArrayList<Epreuve> sesEpreuvesParticipes; 
    private Pays sonPays; 
    private ArrayList<Athlete> sesAthletes; 
    private Discipline saDiscipline; 
    private int num; 
    private String nom; 
    private static int numGlobale = 0; 

    //--------------------------
  	// CONSTRUCTEUR
  	//--------------------------
     
    /**
     * Constructeur de la classe Equipe.
     */
    public Equipe(String nom, String disciplineNom, String paysNom) {
        numGlobale++;
        this.num = numGlobale;
        this.nom = nom;
        this.sesAthletes = new ArrayList<>();
        this.saDiscipline = new Discipline(disciplineNom);
        this.sonPays = new Pays(paysNom);
    }

    //--------------------------
  	// METHODES
  	//--------------------------
     
    /**
     * Modifie les informations de l'équipe.
     */
    public void modifier(String nom, String disciplineNom, String paysNom) {
        this.nom = nom;
        this.saDiscipline.setNom(disciplineNom);
        this.sonPays.setNom(paysNom);
    }

    /**
     * Supprime les informations de l'équipe.
     */
    public void supprimer() {
        this.nom = null;
    }

    public String toString() {
        return "Equipe [num= " + num + ", nom=" + nom + "]";
    }

    /**
     * Associe un pays à l'équipe.
     */
    public void associerPaysEquipe(Pays pays) {
        this.sonPays = pays;
    }

    /**
     * Associe une discipline à l'équipe.
     */
    public void associerDisciplineEquipe(Discipline discipline) {
        this.saDiscipline = discipline;
    }

    /**
     * Ajoute un athlète à l'équipe.
     */
    public void ajouterAthleteEquipe(Athlete athlete) {
        sesAthletes.add(athlete);
    }

    /**
     * Supprime un athlète de l'équipe.
     */
    public void supprimerAthleteEquipe(Athlete athlete) {
        sesAthletes.removeIf(a -> a.equals(athlete));
    }

    /**
     * Vérifie si la discipline de l'équipe correspond à la discipline donnée.
     */
    public boolean verifierDisciplineEquipe(Discipline discipline) {
        return saDiscipline.equals(discipline);
    }

    /**
     * Vérifie si le pays de l'équipe correspond au pays donné.
     */
    public boolean verifierPaysEquipe(Pays pays) {
        return sonPays.equals(pays);
    }

    //--------------------------
  	// ACCESSEURS
  	//--------------------------
    
    public ArrayList<Epreuve> getSesEpreuvesParticipes() {
        return sesEpreuvesParticipes;
    }

    public void setSesEpreuvesParticipes(ArrayList<Epreuve> sesEpreuvesParticipes) {
        this.sesEpreuvesParticipes = sesEpreuvesParticipes;
    }

    public Pays getSonPays() {
        return sonPays;
    }

    public void setSonPays(Pays sonPays) {
        this.sonPays = sonPays;
    }

    public ArrayList<Athlete> getSesAthletes() {
        return sesAthletes;
    }

    public void setSesAthletes(ArrayList<Athlete> sesAthletes) {
        this.sesAthletes = sesAthletes;
    }

    public Discipline getSaDiscipline() {
        return saDiscipline;
    }

    public void setSaDiscipline(Discipline saDiscipline) {
        this.saDiscipline = saDiscipline;
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
