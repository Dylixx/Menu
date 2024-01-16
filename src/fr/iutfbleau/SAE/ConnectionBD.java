package fr.iutfbleau.SAE;
import org.mariadb.jdbc.*;
import java.sql.*;

/**
 * La classe <code>ConnectionBD</code> possède les méthodes en lien avec la connexion à une base de donneés
 *  
 * @version 1.1
 * @author Oscar Williatte et Edouard Schnur
 */

public class ConnectionBD {

    /**
     * Créer la connexion à la base de donnée
     *
     * @return la variable qui contient la connexion
     */
    
    public static Connection connect() {
	try {
	    Class.forName("org.mariadb.jdbc.Driver");
	} catch(ClassNotFoundException e) {
	    System.err.println("classe non trouvee");
	    return null;
	}
	try {
	    Connection cnx = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/williatt","williatt","OscarSQL92");
	    return cnx;
	} catch(SQLException e) {
	    System.err.println("la connection a echoue");
	    return null;
	}
    }   
}
