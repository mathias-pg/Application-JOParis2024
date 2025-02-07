
/**
 * @author Oumar Anhe
 */

package vues;

import modeles.Equipe;
import modeles.Action;
import modeles.Athlete;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * Vue pour afficher et gérer les équipes.
 */
public class VueEquipe extends JPanel implements Action {
	//--------------------------
	// ATTRIBUTS
	//--------------------------
	
    private JTable tableEquipes; 
    private DefaultTableModel tableModel; 
    
    private JButton _cButton; 
    private JButton _mButton; 
    private JButton _sButton; 
    private JButton _ajouterAthleteButton; 
    private JButton _supprimerAthleteButton; 
    private JButton _retour; 
    private JTextArea athletesTextArea; 
    private JScrollPane sp; 
    private JScrollPane athletesScrollPane; 

    //--------------------------
  	// CONSTRUCTEUR
  	//--------------------------
     
    /**
     * Constructeur de la classe VueEquipe.
     */
    public VueEquipe() {
        // Création du modèle de table
        String[] columnNames = {"Nom", "Discipline", "Pays"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableEquipes = new JTable(tableModel);
        customizeTable(tableEquipes);
        sp = new JScrollPane(tableEquipes);
        customizeScrollPane(sp);

        // Création de la zone de texte pour afficher les athlètes
        athletesTextArea = new JTextArea(10, 30);
        athletesTextArea.setEditable(false);
        athletesTextArea.setFont(new Font("San Francisco", Font.PLAIN, 14));
        athletesTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        athletesScrollPane = new JScrollPane(athletesTextArea);
        athletesScrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        customizeScrollPane(athletesScrollPane);
        
        // Création des boutons
        _cButton = createButton("Créer", "plus-icon.png", new Color(52, 152, 219));
        _mButton = createButton("Modifier", "edit-icon.png", new Color(241, 196, 15));
        _sButton = createButton("Supprimer", "delete-icon.png", new Color(231, 76, 60));
        _retour = createButton("Retour", "back-icon.png", new Color(155, 89, 182));
        _ajouterAthleteButton = createButton("Ajouter Athlète", "add-event-icon.png", new Color(46, 204, 113));
        _supprimerAthleteButton = createButton("Supprimer Athlète", "remove-event-icon.png", new Color(230, 126, 34));

        // Préparer la gestion des clics sur les boutons
        _cButton.setActionCommand(ACTION_CREER);
        _mButton.setActionCommand(ACTION_MODIFIER);
        _sButton.setActionCommand(ACTION_SUPPRIMER);
        _ajouterAthleteButton.setActionCommand(ACTION_AJOUTER_ATHLETE);
        _supprimerAthleteButton.setActionCommand(ACTION_SUPPRIMER_ATHLETE);
        _retour.setActionCommand(ACTION_RETOUR);

        // Créer un "panneau de contrôle" et y ajouter les boutons
        JPanel buttons = new JPanel(new GridLayout(2, 3, 10, 10));
        buttons.add(_cButton);
        buttons.add(_mButton);
        buttons.add(_sButton);
        buttons.add(_ajouterAthleteButton);
        buttons.add(_supprimerAthleteButton);
        buttons.add(_retour); 

        // Définir la disposition de la vue
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        add(sp, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        add(athletesScrollPane, BorderLayout.EAST);
    }
    
    //--------------------------
  	// METHODES
  	//--------------------------
     
    /**
     * Personnalise le tableau des équipes.
     */
    private void customizeTable(JTable table) {
        table.setFont(new Font("San Francisco", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(210, 210, 210));
        table.getTableHeader().setFont(new Font("San Francisco", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(240, 240, 240));
    }

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
     * Personnalise le panneau de défilement.
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
     * Ajoute une équipe au tableau des équipes.
     */
    public void ajouterEquipe(Equipe equipe) {
        tableModel.addRow(new Object[]{equipe.getNom(), equipe.getSaDiscipline().getNom(), equipe.getSonPays().getNom()});
    }

    /**
     * Modifie une équipe dans le tableau des équipes.
     */
    public void modifierEquipe(int rowIndex, Equipe equipe) {
        tableModel.setValueAt(equipe.getNom(), rowIndex, 0);
        tableModel.setValueAt(equipe.getSaDiscipline().getNom(), rowIndex, 1);
        tableModel.setValueAt(equipe.getSonPays().getNom(), rowIndex, 2);
    }

    /**
     * Supprime une équipe du tableau des équipes.
     */
    public void supprimerEquipe(int rowIndex) {
        tableModel.removeRow(rowIndex);
    }

    /**
     * Ajoute un athlète à une équipe et met à jour la zone de texte des athlètes.
     */
    public void ajouterAthleteEquipe(int rowIndex, Equipe equipe) {
        updateAthletesTextArea(equipe);
    }

    /**
     * Supprime un athlète d'une équipe et met à jour la zone de texte des athlètes.
     */
    public void supprimerAthleteEquipe(int rowIndex, Equipe equipe) {
        updateAthletesTextArea(equipe);
    }

    /**
     * Met à jour la zone de texte des athlètes avec les informations d'une équipe.
     */
    public void updateAthletesTextArea(Equipe equipe) {
        if (equipe != null) {
            String athletesText = "Liste des athlètes : \n";
            for (Athlete athlete : equipe.getSesAthletes()) {
                athletesText += athlete.toString() + "\n";
            }
            athletesTextArea.setText(athletesText);
        } 
        else {
            athletesTextArea.setText("");
        }
    }

    //--------------------------
  	// ACCESSEURS
  	//--------------------------
    
    public JTable getTableEquipes() {
        return tableEquipes;
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

    public JButton get_ajouterAthleteButton() {
        return _ajouterAthleteButton;
    }

    public JButton get_supprimerAthleteButton() {
        return _supprimerAthleteButton;
    }

    public JButton get_retour() {
        return _retour;
    }
}
