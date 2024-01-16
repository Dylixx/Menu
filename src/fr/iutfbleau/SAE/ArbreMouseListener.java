package fr.iutfbleau.SAE;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ArbreMouseListener extends MouseAdapter {
    private JTree arbre;
    private Connection cnx;
    private String code;
    private ArrayList<Integer> historiqueClic = new ArrayList<>();
    private VueJTree fenetre;
    
    public ArbreMouseListener(JTree arbre, Connection cnx, String code, ArrayList<Integer> historiqueClic, VueJTree f) {
        this.arbre = arbre;
        this.cnx = cnx;
	this.code = code;
	this.historiqueClic = historiqueClic;
	this.fenetre = f;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1) {
	    
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) arbre.getLastSelectedPathComponent();
            if (node != null) {
		OptionJTree optionClique =(OptionJTree) node.getUserObject();
		
                int idMenuClique = optionClique.getId();
		historiqueClic.add(idMenuClique);
		
                if (!node.isLeaf()) { //entre dans un reportoire
		    System.out.println(idMenuClique);
                    arbre.expandRow(arbre.getRowForPath(arbre.getSelectionPath()));
		    if(!optionClique.isClicked()){  //on a jamais cliqué sur ce repertoire
			System.out.println("une premiere");
			optionClique.setIsClickedTrue();
			//méthode qui ajoute l'id à l'arraylist ici
			
		    }else{ //on a deja cliqué sur ce repertoire
			System.out.println("encore ?");
		    }
		    
                } else {
                    int reponse = JOptionPane.showConfirmDialog(null,
								"Vous avez trouvé le menu que vous recherchiez ?",
								"Confirmation",
								JOptionPane.YES_NO_OPTION);
                    if (reponse == JOptionPane.YES_OPTION) {
                        EnregistrementBD.enregistrerTest(cnx, historiqueClic, code);
                        //Arbre.historiqueClic.clear();	        
			this.fenetre.dispose();
			System.out.println("fenetre fermée");
			try{
			cnx.close();
			}catch(SQLException a){
			    System.err.println("erreur cnx.close");
			}
                    }
                }
            }
        }
	
    }

    @Override
    public void mouseEntered(MouseEvent e){
    }
    @Override
    public void mouseExited(MouseEvent e){
    }
    @Override
    public void mousePressed(MouseEvent e){
    }
    @Override
    public void mouseReleased(MouseEvent e){
    }
    
}
