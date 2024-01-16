package fr.iutfbleau.SAE;
import org.mariadb.jdbc.*;
import java.sql.*;

/**
 * La classe <code>ModelProtocoleBDDev</code> créer et envoie toutes les requêtes à la base de donnée concernant les protocoles
 *  
 * @version 1.3
 * @author Oscar Williatte et Edouard Schnur et Hassan Meite
 */

public class ModelProtocoleBDDev {
    /**
     * Verifie si le code du protocole existe dans la base de donnée
     *
     * @param code le String alphanumérique du protocole
     * @param cnx qui possède la connexion à la base de donnée
     * @return true si le code n'existe pas
     * @return false si le code existe
     */
    public static boolean check(String code, Connection cnx){
	try{
	    PreparedStatement ps = cnx.prepareStatement("Select code FROM SAE_Protocole WHERE code=?");  //demande si le code existe
	    ps.setString(1,code);
	    ResultSet rs = ps.executeQuery();
	    if(rs.next()){
		ps.close();
		rs.close();			
		return false; //le code existe 
	    }
	    ps.close();
	    rs.close();
	    return true;  //le code n'existe pas
	}catch(SQLException e){
	    System.err.println("erreur modelProto");
	    return true;
	}
    }

    /**
     * Récupère la user story que l'utilisateur doit faire
     *
     * @param code le String alphanumérique du protocole
     * @param cnx qui possède la connexion à la base de donnée
     * @return le String de la user story
     */
    public static String getUserStory(String code, Connection cnx){
	try{
	    PreparedStatement ps = cnx.prepareStatement("Select user_Story FROM SAE_Protocole WHERE code=?");  //demande la description
	    ps.setString(1,code);
	    ResultSet rs = ps.executeQuery();				
	    rs.next();
	    String userStory = rs.getString("user_Story");   //le résultat de la commande est mis dans la variable
	    return userStory;

	}catch(SQLException e){
	    System.err.println("erreur modelProto");
	    return "bizarre";
	}
}

    /**
     * Récupère l'id du menu testé par un protocole
     *
     * @param code le String alphanumérique du protocole
     * @param cnx qui possède la connexion à la base de donnée
     * @return le int qui contient l'id du menu testé
     */  
public static int getIdVersion(String mot, Connection cnx) {
	try{
	    PreparedStatement ps = cnx.prepareStatement("Select id_Menu_Teste FROM SAE_Protocole WHERE code=?");  //demande la description
	    ps.setString(1,mot);
	    ResultSet rs = ps.executeQuery();				
	    rs.next();
	    int idPere = rs.getInt(1);   //le résultat de la commande est mis dans la variable
	    return idPere;

	}catch(SQLException e){
		System.err.println("erreur modelProto");
		return -1;
	}
}
}

