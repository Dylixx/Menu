package fr.iutfbleau.SAE;
import org.mariadb.jdbc.*;
import java.sql.*;
import javax.swing.*;
import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;

public class Arbre {
    public static JTree creationArbre(Connection cnx, int idPere) {
        JTree arbre;   
	try{
	    PreparedStatement ps = cnx.prepareStatement("SELECT id_Menu, nom_Menu, est_Dossier FROM SAE_Menu WHERE id_Menu=?");
            ps.setInt(1, idPere);
	    ResultSet rs = ps.executeQuery();
	    rs.next();
	    OptionJTree option = new OptionJTree(rs.getInt(1),rs.getString(2),true);  //idMenu, nomMenu, pasCliqué
	    DefaultMutableTreeNode version = new DefaultMutableTreeNode(option);

	    creationBranche(cnx, idPere, version);
	    arbre = new JTree(version);
	    return arbre;
	}catch(SQLException e){
	    e.printStackTrace();
	    return null;
	}
    }

    private static void creationBranche(Connection cnx, int idPere, DefaultMutableTreeNode toAdd) {
        try {
            PreparedStatement ps = cnx.prepareStatement("SELECT id_Menu, nom_Menu, est_Dossier FROM SAE_Menu WHERE Pere=? AND id_Menu != Pere");
            ps.setInt(1, idPere);
	    ResultSet rs = ps.executeQuery();
	            
            while (rs.next()) {	        
		OptionJTree option = new OptionJTree(rs.getInt(1),rs.getString(2),false);  //idMenu, nomMenu, pasCliqué
		DefaultMutableTreeNode entree = new DefaultMutableTreeNode(option);		
		toAdd.add(entree);
		if (rs.getInt(3) == 1) {
		    creationBranche(cnx, rs.getInt(1), entree);
		}
	    }
	}catch (SQLException e) {
	    e.printStackTrace();
	}
    }
}
/*public static int getIdMenuFromDatabase( String menuName, Connection cnx) {
  try {
  PreparedStatement ps = cnx.prepareStatement("SELECT id_Menu FROM SAE_Menu WHERE nom_Menu = ?");
      ps.setString(1, menuName);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
      idMenuRecherche = rs.getInt("id_Menu");
      return idMenuRecherche;
      }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
	}*/


		     
		
	
	

