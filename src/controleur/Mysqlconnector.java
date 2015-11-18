package controleur;

import java.sql.*;

import com.mysql.jdbc.Connection;

public class Mysqlconnector {
	
	private String url = "jdbc:mysql://eliumtesbapmf.mysql.db:3306/eliumtesbapmf";
	private String utilisateur = "eliumtesbapmf";
	private String motDePasse = "Algerie123";
	private Connection connexion = null;
	
	public Mysqlconnector() throws SQLException
	{
		
		try {
		    Class.forName( "com.mysql.jdbc.Driver" );
		} catch ( ClassNotFoundException e ) {
		    /* Gérer les éventuelles erreurs ici. */
		}
		
		
		try {
			connexion = (Connection) DriverManager.getConnection( url, utilisateur, motDePasse );
		} finally {
		    if ( connexion != null )
		        try {
		            /* Fermeture de la connexion */
		            connexion.close();
		        } catch ( SQLException ignore ) {
		            /* Si une erreur survient lors de la fermeture, il suffit de l'ignorer. */
		        }
		}
	}
	
	public void ecrireDonnees() throws SQLException {
		Statement statement = connexion.createStatement();
		int statut = statement.executeUpdate( "INSERT INTO `eliumtesbapmf`.`frigo` (`id`, `temp`, `humidite`, `etat`) VALUES (NULL, '25', '45', '3');" );
	}
}
