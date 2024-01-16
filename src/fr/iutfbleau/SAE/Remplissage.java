package fr.iutfbleau.SAE;
import org.mariadb.jdbc.*;
import java.sql.*;
import java.util.*;

/**
 * La classe <code>Remplissage</code> Rempli les tableaux pour dessiner le camembert des actions finales
 *  
 * @version 1.1
 * @author Oscar Williatte et Edouard Schnur et Hassan Meite
 */
public class Remplissage {

    /**
     * Remplie les tableaux en argument et détermine l'action correct
     *
     * @param labels tableaux des nom des menus
     * @param values tableaux du nombre de clique sur chaque menu
     * @param code code alphanumérique du protocole
     * @param cnx connexion à la base de donnée
     * 
     * @return l'indice du menu correct
     */  
    public static int addValActionFinale(ArrayList<String> labels, ArrayList<Integer> values,String code,Connection cnx) {
	int indice=0,bonIndice=-1;
	try {
	    PreparedStatement selectionStatement = cnx.prepareStatement("SELECT id_Menu,nom_Menu,action_Correcte,COUNT(id_Menu) FROM SAE_Menu m JOIN SAE_Action a ON m.id_Menu=a.id_Menu_Cliqué NATURAL JOIN SAE_Protocole WHERE code=? AND dernier_Clic=1 GROUP BY id_Menu,action_Correcte;");
	    selectionStatement.setString(1,code);
	    ResultSet rs = selectionStatement.executeQuery();
	    while (rs.next()) {
		labels.add(rs.getString(2));
		values.add(rs.getInt(4));
		if (rs.getInt(1)==rs.getInt(3)) {
		    bonIndice = indice;
		}
		indice++;
	    }
	}catch (SQLException e) {
            e.printStackTrace();
	}
	return bonIndice;
    }


    /**
     * Remplie les tableaux en argument pour le camembert des sous Menus
     *
     * @param labels tableaux des nom des catégories
     * @param values tableaux du nombre de sous menu déployés
     * @param code code alphanumérique du protocole
     * @param cnx connexion à la base de donnée
     * 
     * @return -1 pour signifier qu'il n'y a pas d'action correcte
     */   
    public static int addValSousMenu(ArrayList<String> labels, ArrayList<Integer> values,String code,Connection cnx) {
	try {
	    PreparedStatement createViewStatement = cnx.prepareStatement("CREATE TABLE CntSsMenu AS SELECT id_Test,COUNT(id_Menu) AS cpt FROM SAE_Menu m JOIN SAE_Action a ON m.id_Menu=a.id_Menu_Cliqué NATURAL JOIN SAE_Protocole WHERE code=? AND est_Dossier=1 GROUP BY id_Test;");
	    createViewStatement.setString(1,code);
	    createViewStatement.executeQuery();

	    createViewStatement.close();
	    
	    PreparedStatement countStatement = cnx.prepareStatement("SELECT cpt,COUNT(cpt) FROM CntSsMenu GROUP BY cpt;");
	    ResultSet rs = countStatement.executeQuery();
	    while (rs.next()) {
		labels.add(Integer.toString(rs.getInt(1)));
		values.add(rs.getInt(2));
	    }

	    countStatement.close();
	    rs.close();
	    
	    PreparedStatement dropViewStatement = cnx.prepareStatement("DROP TABLE CntSsMenu;");
	    dropViewStatement.executeQuery();


	    dropViewStatement.close();

	    
	}catch (SQLException e) {
            e.printStackTrace();
        }

	return -1;

	}
}  
