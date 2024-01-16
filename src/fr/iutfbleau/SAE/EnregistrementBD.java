package fr.iutfbleau.SAE;
import org.mariadb.jdbc.*;
import java.sql.*;
import java.util.*;
    /**
    * La classe <code>EnregistrementBD</code> possède une méthode d'enregistrement des tests vers la base de données
    *  
    * @version 1.1
    * @author Oscar Williatte et Edouard Schnur et Hassan Meite
    */
public class EnregistrementBD {

    /**
     * Enregistre les valeurs générées par un test dans la base de données
     *
     * @return rien
     */
    public static void enregistrerTest(Connection cnx, ArrayList<Integer> historiqueClic, String code) {
	int id_test;
        try {
	    PreparedStatement idtestStatement = cnx.prepareStatement("SELECT MAX(id_Test)+1 FROM SAE_Test;");
	    ResultSet rs1 = idtestStatement.executeQuery();
	    rs1.next();
	    id_test=rs1.getInt(1);
            PreparedStatement testStatement = cnx.prepareStatement("INSERT INTO SAE_Test VALUES (?,(SELECT id_Protocole FROM SAE_Protocole WHERE code=?));");
	    testStatement.setInt(1,id_test);
            testStatement.setString(2,code);
            testStatement.executeQuery();
            testStatement.close();
            PreparedStatement actionStatement = cnx.prepareStatement("INSERT INTO SAE_Action VALUES (?, ?, ?, ?)");
            for (int i = 0; i < historiqueClic.size(); i++) {
		System.out.println("|||||||"+historiqueClic.get(i));
                actionStatement.setInt(1, id_test);
                actionStatement.setInt(2, i + 1);
                actionStatement.setInt(3, historiqueClic.get(i));
		actionStatement.setInt(4,0);
		if (i==historiqueClic.size()-1) {
		    actionStatement.setInt(4,1);
		}
                actionStatement.executeQuery();
            }
            actionStatement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
		
