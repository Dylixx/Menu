package fr.iutfbleau.SAE;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * La classe <code>VueCamembert</code> affiche le camembert
 *  
 * @version 1.1
 * @author Oscar Williatte et Edouard Schnur et Hassan Meite
 */
public class VueCamembert {

     /**
     *  JFrame de VueCamembert
     */
    private JFrame frame;

     /**
     *  JPanel dans la frame
     */
    private JPanel cardPanel;

     /**
     *  Connexion de la bd
     */
    private Connection cnx;

     /**
     *  code aplhanumérique du protocole
     */
    private String code;

    /**
     *  Constructeur permettant de transférer les informations de la base de donnée
     */
    public VueCamembert(Connection cnx, String userStory, String code) {
        this.cnx = cnx;
        this.code = code;
        initializeUI(userStory);
    }

    /**
 * Crée une fenetre en y ajoutant les éléments visuels (camemberts) + boutons
 *
 * @param userStory la description que les utilisateurs devaient réalisés durant les tests
 */  
    private void initializeUI(String userStory) {
        frame = new JFrame("Camembert");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardPanel = new JPanel(new CardLayout());

        ArrayList<String> labelsAF = new ArrayList<>();
        ArrayList<Integer> valuesAF = new ArrayList<>();
	 ArrayList<String> labelsSM = new ArrayList<>();
        ArrayList<Integer> valuesSM = new ArrayList<>();
        //  Résultats de test
        int bonIndice = Remplissage.addValActionFinale(labelsAF, valuesAF, this.code, this.cnx);
        PieChartPanel resultsPanel = new PieChartPanel(labelsAF, valuesAF, bonIndice);
        cardPanel.add(resultsPanel, "resultsCard");

        /* Répartition du nombre de sous-menus*/ 
        int bonIndice2 = Remplissage.addValSousMenu(labelsSM, valuesSM, this.code, this.cnx);
	PieChartPanel resultsPanelSousMenu = new PieChartPanel(labelsSM, valuesSM, bonIndice2); 
        cardPanel.add(resultsPanelSousMenu, "sousMenuCard"); 
        CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
        JButton resultsButton = new JButton("Résultats de Test");
        JButton sousMenuButton = new JButton("Répartition des Sous-menus");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(resultsButton);
        buttonPanel.add(sousMenuButton);

        resultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "resultsCard");
            }
        });

        sousMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "sousMenuCard");
            }
        });

        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(cardPanel, BorderLayout.CENTER);
        frame.setSize(1080, 940);
        frame.setVisible(true);
        cardLayout.show(cardPanel, "resultsCard");
    }
}
