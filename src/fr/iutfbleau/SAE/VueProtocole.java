package fr.iutfbleau.SAE;
import javax.swing.*;
import java.awt.*;
import java.lang.*;

/**
 * La classe <code>VueProtocole</code> affiche l'interface pour choisir son protocole
 *  
 * @version 1.4
 * @author Oscar Williatte et Edouard Schnur et Hassan Meite
 */

public class VueProtocole extends JFrame {
    /**
     *  panel dans lequel seront mis les éléments lié au lancement du programme
     */
    private JPanel panelProtocole;
    /**
     *  JLabel dans lequel sera écris l'erreur (s'il y en a une) à l'utilisateur
     */
    private JLabel codeErreur;
    /**

       /**
       * Constructeur destiné à créer les éléments principaux de la fenêtre
       */

    public VueProtocole(){

	this.panelProtocole = new JPanel();
	this.add(this.panelProtocole);	
	this.codeErreur = new JLabel();  //JLabel vide pour le remplir plus tard
	this.codeErreur.setForeground(Color.red);
	JButton charger = new JButton("Charger le menu");
	JTextField champ = new JTextField(50);
	JLabel saisir = new JLabel("Saisissez le code d'un protocole");

	ControlerProtocole ctrl = new ControlerProtocole(this,this.panelProtocole,champ);
	charger.addActionListener(ctrl);

	this.panelProtocole.add(saisir, BorderLayout.NORTH);
	this.panelProtocole.add(champ, BorderLayout.NORTH);
	this.panelProtocole.add(charger, BorderLayout.NORTH);
	this.panelProtocole.add(this.codeErreur, BorderLayout.SOUTH);
		
	this.setSize(1600, 900);
	this.setLocation(0, 0);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * écris du texte dans le JLabel codeErreur
     * 
     * @param code code alphanumérique du protocole
     */
    public void affichageErreur(String code){
	this.codeErreur.setText(code);
    }
}
