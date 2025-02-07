
/**
 * @author Mathias Petibon-Gravier / Adrien Ozon
 */

package controleurs;

import modeles.Epreuve;
import modeles.Action;
import modeles.Session;
import vues.VueSession;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import Liens.SessionManager;

/**
 * Le contrôleur pour la gestion des sessions.
 */
public class ControleurSession implements ActionListener, Action {
	//--------------------------
	// ATTRIBUTS
	//--------------------------
	
    private static final String FILE_NAME = "sessions.dat";
    private VueSession _vue;
    private ArrayList<Session> sessions;
    private SessionManager sessionManager;

    //--------------------------
  	// CONSTRUCTEUR
  	//--------------------------

    /**
     * Constructeur du contrôleur de session.
     */
    public ControleurSession(VueSession vue) {
        _vue = vue;
        sessionManager = SessionManager.getInstance();
        chargerSessions(); // Charger les sessions lors de la création du contrôleur
        this.sessions = sessionManager.getSessions();

        // Ajouter les écouteurs d'événements aux boutons
        _vue.get_cButton().addActionListener(this);
        _vue.get_mButton().addActionListener(this);
        _vue.get_sButton().addActionListener(this);
        _vue.get_ajouterEpreuveButton().addActionListener(this);
        _vue.get_supprimerEpreuveButton().addActionListener(this);
        _vue.get_retour().addActionListener(this);

        // Ajouter un écouteur de sélection pour la table
        _vue.getTableSessions().getSelectionModel().addListSelectionListener(e -> updateEpreuvesTextArea());
    }
    
    //--------------------------
  	// METHODES
  	//--------------------------
     
    /**
     * Charge les sessions depuis le fichier de sauvegarde.
     */
    private void chargerSessions() {
        sessionManager.chargerSessions(FILE_NAME);
        ArrayList<Session> sessions = sessionManager.getSessions();
        for (Session session : sessions) {
            _vue.ajouterSession(session);
        }
    }

    /**
     * Méthode déclenchée lorsqu'un événement est produit.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ACTION_CREER)) {
            creerSession();
        } 
        else if (e.getActionCommand().equals(ACTION_MODIFIER)) {
            modifierSession();
        } 
        else if (e.getActionCommand().equals(ACTION_SUPPRIMER)) {
            supprimerSession();
        } 
        else if (e.getActionCommand().equals(ACTION_AJOUTER_EPREUVE)) {
            ajouterEpreuveSession();
        }
        else if (e.getActionCommand().equals(ACTION_SUPPRIMER_EPREUVE)) {
            supprimerEpreuveSession();
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
     * Crée une nouvelle session.
     */
    private void creerSession() {
        String nom = JOptionPane.showInputDialog("Nom de la session :");
        String date = JOptionPane.showInputDialog("Date de la session (JJ-MM-AAAA):");
        String lieu = JOptionPane.showInputDialog("Lieu de la session :");
        if (nom != null && date != null && lieu != null) {
            if (isValidDate(date)) {
                Session nouvelleSession = new Session(nom, date, lieu);
                sessions.add(nouvelleSession);
                _vue.ajouterSession(nouvelleSession);
                sauvegarderSessions();
            } else {
                JOptionPane.showMessageDialog(null, "La date doit être au format JJ-MM-AAAA.");
            }
        }
    }

    /**
     * Modifie une session existante.
     */
    private void modifierSession() {
        JTable table = _vue.getTableSessions();
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Session session = sessions.get(selectedRow);
            String nom = JOptionPane.showInputDialog("Nouveau nom de la session :", session.getNom());
            String date = JOptionPane.showInputDialog("Nouvelle date de la session (JJ-MM-AAAA):", session.getDate());
            String lieu = JOptionPane.showInputDialog("Nouveau lieu de la session :", session.getLieu());
            if (nom != null && date != null && lieu != null) {
                if (isValidDate(date)) {
                    session.modifier(nom, date, lieu);
                    _vue.modifierSession(selectedRow, session);
                    _vue.updateEpreuvesTextArea(session);
                    sauvegarderSessions();
                } else {
                    JOptionPane.showMessageDialog(null, "La date doit être au format JJ-MM-AAAA.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner une session à modifier.");
        }
    }

    /**
     * Supprime une session.
     */
    private void supprimerSession() {
        JTable table = _vue.getTableSessions();
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Session session = sessions.get(selectedRow);
            session.supprimer();
            sessionManager.supprimerSession(session);
            sessions.remove(selectedRow);
            _vue.supprimerSession(selectedRow);
            _vue.updateEpreuvesTextArea(null);
            sauvegarderSessions();
        } else {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner une session à supprimer.");
        }
    }

    /**
     * Ajoute une épreuve à une session.
     */
    private void ajouterEpreuveSession() {
        JTable table = _vue.getTableSessions();
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Session session = sessions.get(selectedRow);
            String nom = JOptionPane.showInputDialog("Nom de l'épreuve :");
            String date = JOptionPane.showInputDialog("Date de l'épreuve (JJ-MM-AAAA):");
            String horaire = JOptionPane.showInputDialog("Horaire de l'épreuve :");
            String lieu = JOptionPane.showInputDialog("Lieu de l'épreuve :");
            String disciplineNom = JOptionPane.showInputDialog("Nom de la discipline :");

            if (nom != null && date != null && horaire != null && lieu != null && disciplineNom != null) {
            	if (isValidDate(date) && isValidTime(horaire)) {
                    Epreuve epreuve = new Epreuve(nom, date, horaire, lieu, disciplineNom);
                    session.ajouterEpreuveSession(epreuve);
                    epreuve.setSaSession(session);
                    _vue.ajouterEpreuveSession(selectedRow, session);
                    sauvegarderSessions();
                } else {
                    JOptionPane.showMessageDialog(null, "La date doit être au format JJ-MM-AAAA et l'heure HH:MM.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner une session à laquelle ajouter une épreuve.");
        }
    }

    /**
     * Supprime une épreuve d'une session.
     */
    private void supprimerEpreuveSession() {
        JTable table = _vue.getTableSessions();
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Session session = sessions.get(selectedRow);
            String numEpreuveStr = JOptionPane.showInputDialog("Numéro de l'épreuve à supprimer :");
            try {
                int numEpreuve = Integer.parseInt(numEpreuveStr);
                Epreuve epreuveToRemove = null;
                for (Epreuve epreuve : session.getSesEpreuves()) {
                    if (epreuve.getNum() == numEpreuve) {
                        epreuveToRemove = epreuve;
                        break;
                    }
                }
                if (epreuveToRemove != null) {
                    session.supprimerEpreuveSession(epreuveToRemove);
                    _vue.supprimerEpreuveSession(selectedRow, session);
                    sauvegarderSessions();
                } else {
                    JOptionPane.showMessageDialog(null, "Epreuve non trouvée.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Numéro d'épreuve invalide.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner une session pour supprimer une épreuve.");
        }
    }

    /**
     * Met à jour la zone de texte des épreuves en fonction de la session sélectionnée.
     */
    private void updateEpreuvesTextArea() {
        JTable table = _vue.getTableSessions();
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Session session = sessions.get(selectedRow);
            _vue.updateEpreuvesTextArea(session);
        } else {
            _vue.updateEpreuvesTextArea(null);
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
        } catch (ParseException e) {
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
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Sauvegarde les sessions dans un fichier.
     */
    private void sauvegarderSessions() {
    	sessionManager.sauvegarderSessions(FILE_NAME);
    }
}
            