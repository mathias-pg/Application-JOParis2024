
/**
 * @author Mathias Petibon-Gravier / Adrien Ozon
 */

package vues;

import modeles.Session;
import modeles.Action;
import modeles.Epreuve;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * Vue pour afficher et gérer les sessions.
 */
public class VueSession extends JPanel implements Action {
	//--------------------------
	// ATTRIBUTS
	//--------------------------
	
    private JTable tableSessions;
    private DefaultTableModel tableModel;
    private JButton _cButton;
    private JButton _mButton;
    private JButton _sButton;
    private JButton _ajouterEpreuveButton;
    private JButton _supprimerEpreuveButton;
    private JButton _retour;
    private JTextArea epreuvesTextArea;
    private JScrollPane sp;
    private JScrollPane epreuvesScrollPane;

    //--------------------------
  	// CONSTRUCTEUR
  	//--------------------------
     
    /**
     * Constructeur par défaut.
     */
    public VueSession() {
    	// Création du modèle de table
        String[] columnNames = {"Nom", "Date", "Lieu"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableSessions = new JTable(tableModel);
        tableSessions.setFont(new Font("San Francisco", Font.PLAIN, 14));
        tableSessions.setRowHeight(30);
        tableSessions.setShowGrid(false);
        tableSessions.setIntercellSpacing(new Dimension(0, 0));
        tableSessions.setSelectionBackground(new Color(210, 210, 210));
        tableSessions.getTableHeader().setFont(new Font("San Francisco", Font.BOLD, 14));
        tableSessions.getTableHeader().setBackground(new Color(240, 240, 240));
        sp = new JScrollPane(tableSessions);
        customizeScrollPane(sp);

        // Création de la zone de texte pour afficher les épreuves
        epreuvesTextArea = new JTextArea(10, 30);
        epreuvesTextArea.setEditable(false);
        epreuvesTextArea.setFont(new Font("San Francisco", Font.PLAIN, 14));
        epreuvesTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        epreuvesScrollPane = new JScrollPane(epreuvesTextArea);
        epreuvesScrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        customizeScrollPane(epreuvesScrollPane);

        // Création des boutons
        _cButton = createButton("Créer", "plus-icon.png", new Color(52, 152, 219));
        _mButton = createButton("Modifier", "edit-icon.png", new Color(241, 196, 15));
        _sButton = createButton("Supprimer", "delete-icon.png", new Color(231, 76, 60));
        _ajouterEpreuveButton = createButton("Ajouter Epreuve", "add-event-icon.png", new Color(46, 204, 113));
        _supprimerEpreuveButton = createButton("Supprimer Epreuve", "remove-event-icon.png", new Color(230, 126, 34));
        _retour = createButton("Retour", "back-icon.png", new Color(155, 89, 182));

        // Préparer la gestion des clics sur les boutons
        _cButton.setActionCommand(ACTION_CREER);
        _mButton.setActionCommand(ACTION_MODIFIER);
        _sButton.setActionCommand(ACTION_SUPPRIMER);
        _ajouterEpreuveButton.setActionCommand(ACTION_AJOUTER_EPREUVE);
        _supprimerEpreuveButton.setActionCommand(ACTION_SUPPRIMER_EPREUVE);
        _retour.setActionCommand(ACTION_RETOUR);

        // Créer un "panneau de contrôle" et y ajouter les boutons
        JPanel buttons = new JPanel(new GridLayout(2, 3, 10, 10));
        buttons.add(_cButton);
        buttons.add(_mButton);
        buttons.add(_sButton);
        buttons.add(_ajouterEpreuveButton);
        buttons.add(_supprimerEpreuveButton);
        buttons.add(_retour);

        // Définir la disposition de la vue
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        add(sp, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        add(epreuvesScrollPane, BorderLayout.EAST);
    }

    //--------------------------
  	// METHODES
  	//--------------------------
     
    /**
     * Crée un bouton avec un texte, une icône et une couleur spécifiques.
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
        button.setPreferredSize(new Dimension(210, 50));
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
     * Personnalise la barre de défilement.
     */
    private void customizeScrollPane(JScrollPane scrollPane) {
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {

            protected void configureScrollBarColors() {
                this.thumbColor = new Color(100, 100, 100);
                this.trackColor = new Color(240, 240, 240);
            }

            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }

            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                g.setColor(trackColor);
                g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
            }

            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
                    return;
                }
                int w = thumbBounds.width;
                int h = thumbBounds.height;
                g.setColor(thumbColor);
                g.fillRect(thumbBounds.x, thumbBounds.y, w, h);
            }
        });
    }

    /**
     * Ajoute une session à la table des sessions.
     */
    public void ajouterSession(Session session) {
        tableModel.addRow(new Object[]{session.getNom(), session.getDate(), session.getLieu()});
    }

    /**
     * Modifie une session dans la table des sessions.
     */
    public void modifierSession(int rowIndex, Session session) {
        tableModel.setValueAt(session.getNom(), rowIndex, 0);
        tableModel.setValueAt(session.getDate(), rowIndex, 1);
        tableModel.setValueAt(session.getLieu(), rowIndex, 2);
    }

    /**
     * Supprime une session de la table des sessions.
     */
    public void supprimerSession(int rowIndex) {
        tableModel.removeRow(rowIndex);
    }

    /**
     * Ajoute une épreuve à la session sélectionnée.
     */
    public void ajouterEpreuveSession(int rowIndex, Session session) {
        updateEpreuvesTextArea(session);
    }

    /**
     * Supprime une épreuve de la session sélectionnée.
     */
    public void supprimerEpreuveSession(int rowIndex, Session session) {
        updateEpreuvesTextArea(session);
    }
    
    /**
     * Met à jour la zone de texte affichant les épreuves de la session.
     */
    public void updateEpreuvesTextArea(Session session) {
        if (session != null) {
            String epreuvesText = "Liste des épreuves : \n";
            for (Epreuve epreuve : session.getSesEpreuves()) {
                epreuvesText += epreuve.toString() + "\n";
            }
            epreuvesTextArea.setText(epreuvesText);
        } 
        else {
            epreuvesTextArea.setText("");
        }
    }

    //--------------------------
  	// ACCESSEURS
  	//--------------------------
    
    public JTable getTableSessions() {
        return tableSessions;
    }

    public JButton get_cButton() {
        return _cButton;
    }

    public JButton get_mButton() {
        return _mButton;
    }
    
    public JButton get_sButton() {
        return _sButton;
    }

    public JButton get_ajouterEpreuveButton() {
        return _ajouterEpreuveButton;
    }

    public JButton get_supprimerEpreuveButton() {
        return _supprimerEpreuveButton;
    }

    public JButton get_retour() {
        return _retour;
    }
}

