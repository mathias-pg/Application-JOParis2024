

/**
 * @author Mathias Petibon-Gravier / Adrien Ozon
 */

package controleurs;

import modeles.Epreuve;
import vues.VueEpreuve;
import modeles.Action;

import javax.swing.*;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Le contrôleur pour la gestion des épreuves.
 */
public class ControleurEpreuve implements ActionListener, Action {
	//--------------------------
	// ATTRIBUTS
	//--------------------------
	
    private static final String FILE_PATH = "epreuves.dat";
    private VueEpreuve _vue;
    private ArrayList<Epreuve> epreuves;

    //--------------------------
  	// CONSTRUCTEUR
  	//--------------------------

    /**
     * Constructeur du contrôleur de gestion des épreuves.
     */
    public ControleurEpreuve(VueEpreuve vue) {
        this._vue = vue;
        this.epreuves = new ArrayList<>();

        // Charger les épreuves depuis le fichier
        chargerEpreuves();

        // Ajouter les écouteurs d'événements aux boutons
        vue.get_cButton().addActionListener(this);
        vue.get_mButton().addActionListener(this);
        vue.get_sButton().addActionListener(this);
        vue.get_retour().addActionListener(this);
    }
    
    //--------------------------
  	// METHODES
  	//--------------------------
     
    /**
     * Méthode invoquée lorsqu'un événement d'action se produit.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ACTION_CREER)) {
            creerEpreuve();
        } 
        else if (e.getActionCommand().equals(ACTION_MODIFIER)) {
            modifierEpreuve();
        } 
        else if (e.getActionCommand().equals(ACTION_SUPPRIMER)) {
            supprimerEpreuve();
        } 
        else if (e.getActionCommand().equals(ACTION_RETOUR)) {
            Container parent = _vue.getParent();
            if (parent instanceof JPanel) {
                CardLayout cl = (CardLayout) parent.getLayout();
                cl.show(parent, "Accueil");
            }
        }
    }

    /**
     * Méthode pour créer une nouvelle épreuve.
     */
    private void creerEpreuve() {
        String nom = JOptionPane.showInputDialog("Nom de l'épreuve :");
        String date = JOptionPane.showInputDialog("Date de l'épreuve (JJ-MM-AAAA)");
        String horaire = JOptionPane.showInputDialog("Horaire de l'épreuve (HH:MM):");
        String lieu = JOptionPane.showInputDialog("Lieu de l'épreuve :");
        String disciplineNom = JOptionPane.showInputDialog("Nom de la discipline :");

        if (nom != null && date != null && horaire != null && lieu != null && disciplineNom != null) {
            if (isValidDate(date) && isValidTime(horaire)) {
                Epreuve nouvelleEpreuve = new Epreuve(nom, date, horaire, lieu, disciplineNom);
                epreuves.add(nouvelleEpreuve);
                _vue.ajouterEpreuve(nouvelleEpreuve);
                sauvegarderEpreuves(); // Sauvegarder après ajout
            } 
            else {
                JOptionPane.showMessageDialog(null, "La date doit être au format JJ-MM-AAAA et l'heure HH:MM.");
            }
        }
    }

    /**
     * Méthode pour modifier une épreuve existante.
     */
    private void modifierEpreuve() {
        JTable table = _vue.getTableEpreuves();
        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            Epreuve epreuve = epreuves.get(selectedRow);

            String nom = JOptionPane.showInputDialog("Nouveau nom de l'épreuve :", epreuve.getNom());
            String date = JOptionPane.showInputDialog("Nouvelle date de l'épreuve :", epreuve.getDate());
            String horaire = JOptionPane.showInputDialog("Nouvel horaire de l'épreuve :", epreuve.getHoraire());
            String lieu = JOptionPane.showInputDialog("Nouveau lieu de l'épreuve :", epreuve.getLieu());
            String disciplineNom = JOptionPane.showInputDialog("Nouveau nom de discipline :", epreuve.getSaDiscipline().getNom());

            if (nom != null && date != null && horaire != null && lieu != null) {
                if (isValidDate(date) && isValidTime(horaire)) {
                    epreuve.modifier(nom, date, horaire, lieu, disciplineNom);
                    _vue.modifierEpreuve(selectedRow, epreuve);
                    sauvegarderEpreuves(); // Sauvegarder après modification
                } 
                else {
                    JOptionPane.showMessageDialog(null, "La date doit être au format JJ-MM-AAAA et l'heure HH:MM.");
                }
            }
        } 
        else {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner une épreuve à modifier.");
        }
    }

    /**
     * Méthode pour supprimer une épreuve existante.
     */
    private void supprimerEpreuve() {
        JTable table = _vue.getTableEpreuves();
        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            Epreuve epreuve = epreuves.get(selectedRow);
            epreuve.supprimer();
            epreuves.remove(selectedRow);
            _vue.supprimerEpreuve(selectedRow);
            sauvegarderEpreuves(); // Sauvegarder après suppression
        } 
        else {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner une épreuve à supprimer.");
        }
    }

    /**
     * Vérifie si une date est valide.
     */
    private boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } 
        catch (ParseException e) {
            return false;
        }
    }

    /**
     * Vérifie si une heure est valide.
     */
    private boolean isValidTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setLenient(false);
        try {
            sdf.parse(time);
            return true;
        } 
        catch (ParseException e) {
            return false;
        }
    }

    /**
     * Sauvegarde les épreuves dans un fichier.
     */
    private void sauvegarderEpreuves() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(epreuves);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Méthode pour charger les épreuves depuis un fichier.
     */
    private void chargerEpreuves() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
                epreuves = (ArrayList<Epreuve>) ois.readObject();
                for (Epreuve epreuve : epreuves) {
                    _vue.ajouterEpreuve(epreuve);
                }
            } 
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}