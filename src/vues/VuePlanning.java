
/**
 * @author Emile Rossi
 */

package vues;

import modeles.Planning;
import modeles.Session;
import modeles.Epreuve;
import modeles.Action;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

/**
 * Vue pour afficher le planning des sessions et épreuves.
 */
public class VuePlanning extends JPanel implements Action {
	//--------------------------
	// ATTRIBUTS
	//--------------------------
	   
    private JLabel labelSession;
    private JPanel panelEpreuves; 
    private JButton _nextButton; 
    private JButton _previousButton;
    private JButton _retour; 

    //--------------------------
  	// CONSTRUCTEUR
  	//--------------------------
     
    /**
     * Constructeur de la classe VuePlanning.
     */
    public VuePlanning() {
        setLayout(new BorderLayout());
        setBackground(new Color(230, 230, 250)); 

        // Créer les composants de l'interface
        labelSession = new JLabel("Session :");
        labelSession.setFont(new Font("Arial", Font.BOLD, 30));
        labelSession.setHorizontalAlignment(SwingConstants.CENTER);
        labelSession.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        panelEpreuves = new JPanel();
        panelEpreuves.setLayout(new BoxLayout(panelEpreuves, BoxLayout.Y_AXIS));
        panelEpreuves.setBackground(Color.WHITE);

        _retour = createButton("Retour", "back-icon.png", new Color(155, 89, 182));

        _previousButton = new JButton("◄");
        _previousButton.setFont(new Font("Arial", Font.BOLD, 30));
        _previousButton.setBorder(BorderFactory.createEmptyBorder());
        _previousButton.setContentAreaFilled(false);

        _nextButton = new JButton("►");
        _nextButton.setFont(new Font("Arial", Font.BOLD, 30));
        _nextButton.setBorder(BorderFactory.createEmptyBorder());
        _nextButton.setContentAreaFilled(false);
        
        // Préparer la gestion des clics sur les boutons
        _nextButton.setActionCommand(ACTION_DROITE);
        _previousButton.setActionCommand(ACTION_GAUCHE);
        _retour.setActionCommand(ACTION_RETOUR);

        // Ajouter les composants au panneau
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.setBackground(new Color(230, 230, 250));
        panelTop.add(labelSession, BorderLayout.CENTER);
        panelTop.add(_retour, BorderLayout.EAST);

        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        panelBottom.setBackground(new Color(230, 230, 250));
        panelBottom.add(_previousButton);
        panelBottom.add(_nextButton);

        add(panelTop, BorderLayout.NORTH);
        add(new JScrollPane(panelEpreuves), BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);
    }
    
    //--------------------------
  	// METHODES
  	//--------------------------
     
    /**
     * Crée un bouton avec un texte, une icône et une couleur de fond.
     */
    private JButton createButton(String text, String iconName, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("San Francisco", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        // Configuration de l'icône du bouton
        URL iconURL = getClass().getResource(iconName);
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            Image image = icon.getImage();
            Image newimg = image.getScaledInstance(24, 24, java.awt.Image.SCALE_SMOOTH); // Redimensionner l'icône
            icon = new ImageIcon(newimg);
            button.setIcon(icon);
        } 
        else {
            System.err.println("Icon not found: " + iconName);
        }

        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setPreferredSize(new Dimension(130, 50));
        button.setIconTextGap(10);

        // Ajouter un effet de survol
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        // Ajouter un effet d'ombre
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            public void paint(Graphics g, JComponent c) {
                super.paint(g, c);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.BLACK);
                g2.setComposite(AlphaComposite.SrcOver.derive(0.1f));
                g2.fillRoundRect(3, 3, c.getWidth() - 6, c.getHeight() - 6, 10, 10);
                g2.dispose();
            }
        });
        
        return button;
    }

    /**
     * Affiche les informations d'une session et ses épreuves dans l'interface.
     */
    public void afficherSession(Session session) {
        if (session.getDate()!=null && session.getNom()!=null && session.getLieu()!=null) {
            labelSession.setText("Session : " + session.getNom() + " | " + session.getDate() + " | " + session.getLieu());
            panelEpreuves.removeAll();

            for (Epreuve epreuve : session.getSesEpreuves()) {
                JPanel panelEpreuve = new JPanel();
                panelEpreuve.setLayout(new BorderLayout());
                panelEpreuve.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                panelEpreuve.setBackground(new Color(255, 248, 220)); 
                panelEpreuve.setMaximumSize(new Dimension(1200, 100));

                JLabel labelNom = new JLabel("Épreuve : " + epreuve.getNom());
                labelNom.setFont(new Font("Arial", Font.BOLD, 20));

                JLabel labelDateHoraire = new JLabel("Date: " + epreuve.getDate() + " | Horaire : " + epreuve.getHoraire());
                labelDateHoraire.setFont(new Font("Arial", Font.PLAIN, 16));
                JLabel labelLieu = new JLabel("Lieu: " + epreuve.getLieu());
                labelLieu.setFont(new Font("Arial", Font.PLAIN, 16));

                JLabel labelDiscipline = new JLabel("Discipline : " + epreuve.getSaDiscipline().getNom());
                labelDiscipline.setFont(new Font("Arial", Font.ITALIC, 16));

                JPanel panelDetails = new JPanel();
                panelDetails.setLayout(new BoxLayout(panelDetails, BoxLayout.Y_AXIS));
                panelDetails.setBackground(new Color(255, 248, 220)); 
                panelDetails.add(labelNom);
                panelDetails.add(labelDateHoraire);
                panelDetails.add(labelLieu);
                panelDetails.add(labelDiscipline);

                panelEpreuve.add(panelDetails, BorderLayout.CENTER);
                panelEpreuves.add(panelEpreuve);
            }

            panelEpreuves.revalidate();
            panelEpreuves.repaint();
        } 
        else {
            afficherSessionVide();
        }
    }

    /**
     * Affiche un message indiquant qu'aucune session n'est sélectionnée.
     */
    public void afficherSessionVide() {
        labelSession.setText("Aucune session sélectionnée");
        panelEpreuves.removeAll();
        panelEpreuves.revalidate();
        panelEpreuves.repaint();
    }

    //--------------------------
  	// ACCESSEURS
  	//--------------------------
    
    public JButton get_previousButton() {
        return _previousButton;
    }

    public JButton get_nextButton() {
        return _nextButton;
    }

    public JButton get_retour() {
        return _retour;
    }
}
