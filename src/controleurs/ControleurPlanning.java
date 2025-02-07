
/**
 * @author Emile Rossi
 */

package controleurs;

import vues.VuePlanning;
import modeles.Planning;
import modeles.Action;
import modeles.Session;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JPanel;
import Liens.SessionManager;

/**
 * Le contrôleur pour la gestion du planning.
 */
public class ControleurPlanning implements ActionListener, Action {
	//--------------------------
	// ATTRIBUTS
	//--------------------------
	    
    private static final String FILE_NAME = "sessions.dat";
    private VuePlanning _vue;
    private Planning planning;
    private SessionManager sessionManager;

    //--------------------------
  	// CONSTRUCTEUR
  	//--------------------------

    /**
     * Constructeur du contrôleur du planning.
     */
    public ControleurPlanning(VuePlanning vue, Planning plan) {
        sessionManager = SessionManager.getInstance();
        this._vue = vue;
        this.planning = plan;
        
        _vue.get_nextButton().addActionListener(this);
        _vue.get_previousButton().addActionListener(this);
        _vue.get_retour().addActionListener(this);
    }
    
    //--------------------------
  	// METHODES
  	//--------------------------
     
    /**
     * Méthode déclenchée lorsqu'un événement est produit.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ACTION_DROITE)) {
            if (!planning.getSesSessions().isEmpty()) {
                planning.nextSession();
                _vue.afficherSession(planning.getCurrentSession());
                sauvegarderSessions();
            }
        } 
        else if (e.getActionCommand().equals(ACTION_GAUCHE)) {
            if (!planning.getSesSessions().isEmpty()) {
                planning.previousSession();
                _vue.afficherSession(planning.getCurrentSession());
                sauvegarderSessions();
            }
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
     * Méthode pour sauvegarder les sessions dans un fichier.
     */
    private void sauvegarderSessions() {
        sessionManager.sauvegarderSessions(FILE_NAME);
        
    }
}