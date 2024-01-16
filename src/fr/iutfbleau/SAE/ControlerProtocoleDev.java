package fr.iutfbleau.SAE;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import org.mariadb.jdbc.*;
import java.sql.*;

/**
 * La classe <code>ControlerProtocoleDev</code> permet d'assigner des actions aux boutons de la classe VueProtocole
 *  
 * @version 1.4
 * @author Oscar Williatte et Edouard Schnur et Hassan Meite
 */

public class ControlerProtocoleDev implements ActionListener {
    /**
     *  JFrame de VueProtocole
     */
    private VueProtocoleDev f;
    /**
     *  JPanel à l'intérieur de la VueProtocole
     */
    private JPanel p;
    /**
     *  Champ de texte qui contiendra le code du protocole
     */
    private JTextField field;

    /**
     * Constructeur destiné à transférer les éléments utiliser lorsque l'utilisateur clique sur un bouton de VueProtocole
     */
    
    public ControlerProtocoleDev(VueProtocoleDev frame,JPanel panel,JTextField champ){  
	this.f=frame;
	this.p=panel;
	this.field=champ;
    }

    /**
     * Appelle la charge du camembert
     * 
     * @param e evenement 
     */
    public void actionPerformed(ActionEvent e){
	String source = e.getActionCommand();

	if(source=="Charger le menu"){
	    String mot=this.field.getText();
	    //System.out.println("coucou");
	    Connection cnx = ConnectionBDDev.connect();
	    if(cnx==null){
		this.f.affichageErreur("Erreur lié à la connexion de la BD");  
	    }else if(ModelProtocoleBDDev.check(mot, cnx)){  //verifie si le code existe dans la BD, renvoie 1 si inexistant 
		System.out.println("pas good");
		this.f.affichageErreur("Leprotocole "+mot+"n'existe pas");		
	    }else{
		int idPere = ModelProtocoleBDDev.getIdVersion(mot,cnx);
		if(idPere == -1){
		    this.f.affichageErreur("Aucun menu lié à ce protocole");
		    return;
		}
		String story = ModelProtocoleBDDev.getUserStory(mot, cnx);
	        VueCamembert camembert = new VueCamembert(cnx,story,mot);
		System.out.println("c'est good");
	    }
	}	
    }
}
