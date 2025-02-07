
/**
 * @author Mathias Petibon-Gravier / Emile Rossi / Oumar Anhe / Adrien Ozon
 */



package application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controleurs.ControleurEpreuve;
import controleurs.ControleurEquipe;
import controleurs.ControleurPlanning;
import controleurs.ControleurSession;
import modeles.Action;
import modeles.Planning;
import vues.*;

/**
 * Cette classe représente l'application principale des Jeux Olympiques.
 */
public class Application implements ActionListener, Action{
	//--------------------------
	// ATTRIBUTS
	//--------------------------

    // Fenêtre principale et gestion des vues
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Planning planning;

    // Vues
    private VueEpreuve vueEpreuve;
    private VueSession vueSession;
    private VueEquipe vueEquipe;
    private VuePlanning vuePlanning;

    // Contrôleurs
    private ControleurEpreuve controleurEpreuve;
    private ControleurSession controleurSession;
    private ControleurEquipe controleurEquipe;
    private ControleurPlanning controleurPlanning;
    
    //--------------------------
  	// CONSTRUCTEUR
  	//--------------------------


    /**
     * Constructeur de l'application.
     */
    public Application() {
        // Initialisation de la fenêtre principale
        frame = new JFrame("Application des Jeux Olympiques");
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Page d'accueil stylisée
        JPanel accueilPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dessiner une image de fond
                ImageIcon icon = new ImageIcon(getClass().getResource("Fond_ecran_accueil.png"));
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        accueilPanel.setLayout(new GridBagLayout());
        accueilPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Création de boutons stylisés
        JButton btnGestionEpreuves = createStyledButton("Gestion des Épreuves");
        JButton btnGestionSessions = createStyledButton("Gestion des Sessions");
        JButton btnGestionEquipes = createStyledButton("Gestion des Équipes");
        JButton btnGestionPlanning = createStyledButton("Gestion du Planning");

        // Attribution des actions aux boutons
        btnGestionEpreuves.setActionCommand(ACTION_GESTION_EPREUVES);
        btnGestionSessions.setActionCommand(ACTION_GESTION_SESSIONS);
        btnGestionEquipes.setActionCommand(ACTION_GESTION_EQUIPES);
        btnGestionPlanning.setActionCommand(ACTION_GESTION_PLANNING);

        // Ajout des boutons à la page d'accueil
        accueilPanel.add(btnGestionEpreuves, gbc);
        gbc.gridy++;
        accueilPanel.add(btnGestionSessions, gbc);
        gbc.gridy++;
        accueilPanel.add(btnGestionEquipes, gbc);
        gbc.gridy++;
        accueilPanel.add(btnGestionPlanning, gbc);

        // Ajout de la page d'accueil au panneau principal
        mainPanel.add(accueilPanel, "Accueil");

        // Initialisation des vues et des contrôleurs
        initVuesEtControleurs();

        // Ajout des vues au panneau principal
        mainPanel.add(vueEpreuve, "GestionEpreuves");
        mainPanel.add(vueSession, "GestionSessions");
        mainPanel.add(vueEquipe, "GestionEquipes");
        mainPanel.add(vuePlanning, "GestionPlanning");

        // Configuration des actions des boutons
        btnGestionEpreuves.addActionListener(this);
        btnGestionSessions.addActionListener(this);
        btnGestionEquipes.addActionListener(this);
        btnGestionPlanning.addActionListener(this);

        // Configuration de la fenêtre principale
        frame.add(mainPanel);
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    //--------------------------
  	// METHODES
  	//--------------------------
     
    /**
     * Méthode invoquée lorsqu'un bouton est cliqué.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ACTION_GESTION_EPREUVES)) {
            cardLayout.show(mainPanel, "GestionEpreuves");
        } else if (e.getActionCommand().equals(ACTION_GESTION_SESSIONS)) {
            cardLayout.show(mainPanel, "GestionSessions");
        } else if (e.getActionCommand().equals(ACTION_GESTION_EQUIPES)) {
            cardLayout.show(mainPanel, "GestionEquipes");
        } else if (e.getActionCommand().equals(ACTION_GESTION_PLANNING)) {
            cardLayout.show(mainPanel, "GestionPlanning");
            if (!planning.getSesSessions().isEmpty()) {
                planning.setCurrentSessionIndex(0);
                vuePlanning.afficherSession(planning.getCurrentSession());
            } else {
                vuePlanning.afficherSessionVide();
            }
        }
    }

    /**
     * Initialise les vues et les contrôleurs.
     */
    public void initVuesEtControleurs() {
        vueEpreuve = new VueEpreuve();
        controleurEpreuve = new ControleurEpreuve(vueEpreuve);

        vueSession = new VueSession();
        controleurSession = new ControleurSession(vueSession);

        vueEquipe = new VueEquipe();
        controleurEquipe = new ControleurEquipe(vueEquipe);

        vuePlanning = new VuePlanning();
        planning = new Planning("26-07-2024 / 11/08/2024", 17);
        controleurPlanning = new ControleurPlanning(vuePlanning, planning);
    }

    /**
     * Crée un bouton stylisé avec le texte donné.
     */
    public JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setFocusPainted(false);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(300, 60));
        return button;
    }

    /**
     * Méthode principale pour lancer l'application.
    */
    public static void main(String[] args) {
    	Application app = new Application();
    }
}