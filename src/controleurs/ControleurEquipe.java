
/**
 * @author Oumar Anhe
 */

package controleurs;

import modeles.Equipe;
import modeles.Action;
import modeles.Athlete;
import modeles.Discipline;
import modeles.Epreuve;
import modeles.Pays;
import modeles.Session;
import vues.VueEquipe;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 * Le contrôleur pour la gestion des équipes.
 */
public class ControleurEquipe implements ActionListener, Action {
	//--------------------------
	// ATTRIBUTS
	//--------------------------
	
    private static final String FILE_PATH = "equipes.dat";
    private VueEquipe _vue;
    private ArrayList<Equipe> equipes;
    private String[] codesCIO = {
            "AFG", "RSA", "ALB", "ALG", "GER", "AND", "ANG", "ANT", "KSA", "ARG",
            "ARM", "AUS", "AUT", "AZE", "BAH", "BRN", "BAN", "BAR", "BLR", "BEL",
            "BEN", "BHU", "BOL", "BIH", "BOT", "BRA", "BUL", "BFA", "BDI", "CAM",
            "CAN", "CPV", "CAF", "CHI", "CHN", "COL", "COM", "CGO", "CRC", "CIV",
            "CRO", "CUB", "CYP", "CZE", "DEN", "DJI", "DMA", "DOM", "ECU", "EGY",
            "ESA", "GEQ", "ERI", "EST", "SWZ", "ETH", "FIJ", "FIN", "FRA", "GAB",
            "GAM", "GEO", "GHA", "GBR", "GRE", "GRN", "GUA", "GUI", "GBS", "GUY",
            "HAI", "HON", "HKG", "HUN", "ISL", "IND", "INA", "IRI", "IRQ", "IRL",
            "ISR", "ITA", "JAM", "JPN", "JOR", "KAZ", "KEN", "KGZ", "KIR", "KOR",
            "KOS", "KWT", "LAO", "LAT", "LBN", "LES", "LBR", "LBY", "LIE", "LTU",
            "LUX", "MAD", "MAW", "MAS", "MDV", "MLI", "MLT", "MHL", "MTN", "MRI",
            "MEX", "MDA", "MON", "MNG", "MNE", "MAR", "MOZ", "MYA", "NAM", "NRU",
            "NEP", "NED", "NZL", "NCA", "NIG", "NOR", "OMA", "PAK", "PLW", "PAN",
            "PNG", "PAR", "PER", "PHI", "POL", "POR", "PUR", "QAT", "ROU", "RUS",
            "RWA", "LCA", "VIN", "SAM", "SMR", "STP", "KSA", "SEN", "SRB", "SEY",
            "SLE", "SIN", "SVK", "SLO", "SOL", "SOM", "RSA", "ESP", "SRI", "SUD",
            "SUR", "SWZ", "SWE", "SUI", "SYR", "TJK", "TAN", "THA", "TLS", "TOG",
            "TGA", "TRI", "TUN", "TUR", "TKM", "TUV", "UGA", "UKR", "UAE", "GBR",
            "USA", "URU", "UZB", "VAN", "VEN", "VIE", "YEM", "ZAM", "ZIM"
    };

    //--------------------------
  	// CONSTRUCTEUR
  	//--------------------------


    /**
     * Constructeur du contrôleur pour la gestion des équipes.
     */
    public ControleurEquipe(VueEquipe vue) {
        _vue = vue;
        this.equipes = new ArrayList<>();

        // Ajouter les écouteurs d'événements aux boutons
        _vue.get_cButton().addActionListener(this);
        _vue.get_mButton().addActionListener(this);
        _vue.get_sButton().addActionListener(this);
        _vue.get_ajouterAthleteButton().addActionListener(this);
        _vue.get_supprimerAthleteButton().addActionListener(this);
        _vue.get_retour().addActionListener(this);

        // Ajouter un écouteur de sélection pour la table
        _vue.getTableEquipes().getSelectionModel().addListSelectionListener(e -> updateAthletesTextArea());

        chargerEquipes();
    }
    
    //--------------------------
  	// METHODES
  	//--------------------------
     
    /**
     * Méthode invoquée lorsqu'un événement se produit.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ACTION_CREER)) {
            creerEquipe();
        } 
        else if (e.getActionCommand().equals(ACTION_MODIFIER)) {
            modifierEquipe();
        } 
        else if (e.getActionCommand().equals(ACTION_SUPPRIMER)) {
            supprimerEquipe();
        } 
        else if (e.getActionCommand().equals(ACTION_AJOUTER_ATHLETE)) {
        	ajouterAthleteEquipe();
        } 
        else if (e.getActionCommand().equals(ACTION_SUPPRIMER_ATHLETE)) {
        supprimerAthleteEquipe();
        } 
        else if (e.getActionCommand().equals(ACTION_RETOUR)) {
	        Container parent = _vue.getParent();
	        if (parent instanceof JPanel) {
		        CardLayout cl = (CardLayout) parent.getLayout();
		        cl.show(parent, "Accueil");
	        }
        }
        sauvegarderEquipes();
    }
    /**
     * Méthode pour créer une nouvelle équipe.
     */
    private void creerEquipe() {
        String nom = JOptionPane.showInputDialog("Nom de l'équipe :");
        String disciplineNom = JOptionPane.showInputDialog("Nom de la discipline :");

        JComboBox<String> comboBoxCIO = new JComboBox<>(codesCIO);
        int result = JOptionPane.showConfirmDialog(null, comboBoxCIO, "Sélectionnez le pays", JOptionPane.OK_CANCEL_OPTION);
        String paysNom = (result == JOptionPane.OK_OPTION) ? (String) comboBoxCIO.getSelectedItem() : null;
        if (nom != null && disciplineNom != null && paysNom != null) {
            Equipe nouvelleEquipe = new Equipe(nom, disciplineNom, paysNom);
            equipes.add(nouvelleEquipe);
            _vue.ajouterEquipe(nouvelleEquipe);
        }
    }

    /**
     * Méthode pour modifier une équipe existante.
     */
    private void modifierEquipe() {
        JTable table = _vue.getTableEquipes();
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Equipe equipe = equipes.get(selectedRow);
            String nom = JOptionPane.showInputDialog("Nouveau nom d'équipe :", equipe.getNom());
            String disciplineNom = JOptionPane.showInputDialog("Nouvelle nom de discipline :", equipe.getSaDiscipline().getNom());
            JComboBox<String> comboBoxCIO = new JComboBox<>(codesCIO);

            int result = JOptionPane.showConfirmDialog(null, comboBoxCIO, "Sélectionnez le pays", JOptionPane.OK_CANCEL_OPTION);
            String paysNom = (result == JOptionPane.OK_OPTION) ? (String) comboBoxCIO.getSelectedItem() : null;
            if (nom != null && disciplineNom != null && paysNom != null) {
                equipe.modifier(nom, disciplineNom, paysNom);
                _vue.modifierEquipe(selectedRow, equipe);
                _vue.updateAthletesTextArea(equipe);
            }
        } 
        else {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner une équipe à modifier.");
        }
    }

    /**
     * Méthode pour supprimer une équipe existante.
     */
    private void supprimerEquipe() {
        JTable table = _vue.getTableEquipes();
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Equipe equipe = equipes.get(selectedRow);
            equipe.supprimer();
            equipes.remove(selectedRow);
            _vue.supprimerEquipe(selectedRow);
            _vue.updateAthletesTextArea(null);
        } 
        else {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner une équipe à supprimer.");
        }
    }

    /**
     * Méthode pour ajouter un athlète à une équipe.
     */
    private void ajouterAthleteEquipe() {
        JTable table = _vue.getTableEquipes();
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Equipe equipe = equipes.get(selectedRow);
            String prenom = JOptionPane.showInputDialog("Prénom de l'athlète :");
            String nom = JOptionPane.showInputDialog("Nom de l'athlète :");
            String nationnalite = JOptionPane.showInputDialog("Nationnalite de l'athlète :");

            if (nom != null && prenom != null && nationnalite != null) {
                Athlete athlete = new Athlete(prenom, nom, nationnalite, equipe.getSaDiscipline().getNom(), equipe.getSonPays().getNom());
                equipe.ajouterAthleteEquipe(athlete);
                athlete.setSonEquipe(equipe);
                _vue.ajouterAthleteEquipe(selectedRow, equipe);
            }
        } 
        else {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner une équipe à laquelle ajouter un athlète.");
        }
    }

    /**
     * Méthode pour supprimer un athlète d'une équipe.
     */
    private void supprimerAthleteEquipe() {
        JTable table = _vue.getTableEquipes();
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Equipe equipe = equipes.get(selectedRow);
            String numAthleteStr = JOptionPane.showInputDialog("Numéro de l'athlète à supprimer :");
            try {
                int numAthlete = Integer.parseInt(numAthleteStr);
                Athlete athleteToRemove = null;
                for (Athlete athlete : equipe.getSesAthletes()) {
                    if (athlete.getNum() == numAthlete) {
                        athleteToRemove = athlete;
                        break;
                    }
                }
                if (athleteToRemove != null) {
                    equipe.supprimerAthleteEquipe(athleteToRemove);
                    _vue.supprimerAthleteEquipe(selectedRow, equipe);
                    sauvegarderEquipes();
                } else {
                    JOptionPane.showMessageDialog(null, "Athlete non trouvé.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Numéro d'athlète invalide.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner une équipe pour supprimer un athlète.");
        }
    }

    /**
     * Méthode pour mettre à jour la zone de texte des athlètes.
     */
    private void updateAthletesTextArea() {
        JTable table = _vue.getTableEquipes();
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Equipe equipe = equipes.get(selectedRow);
            _vue.updateAthletesTextArea(equipe);
        }
    }
    
    
    /**
     * Méthode pour sauvegarder les équipes dans un fichier.
     */
    private void sauvegarderEquipes() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(equipes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode pour charger les équipes depuis un fichier.
     */
    private void chargerEquipes() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
                equipes = (ArrayList<Equipe>) ois.readObject();
                for (Equipe equipe : equipes) {
                    _vue.ajouterEquipe(equipe);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}