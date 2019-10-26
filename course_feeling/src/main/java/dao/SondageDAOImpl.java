package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import business.Ressenti;

public class SondageDAOImpl implements SondageDAO {

	private String database = "jdbc:postgresql://localhost:5432/course_feeling";
	private String getMatiere = "SELECT nomMatiere FROM miseadispo Mi "
			+ "JOIN Sondage S on Mi.idSondage=S.idSondage JOIN Matiere M on S.idMatiere=M.nomMatiere "
			+ "WHERE Mi.code=?;";
	
	@Override
	public String getMatiereOfSondage(String codeSondage) {
		String matiere = null;
		try {
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection(database,"course_feeling","");
			PreparedStatement stat = con.prepareStatement(getMatiere);
			stat.setString(1, codeSondage);
			//System.out.println(getMatiere + codeSondage);
			ResultSet set = stat.executeQuery();
			if(set.next()) {
				matiere = set.getString("nomMatiere");
			}
		} catch (SQLException | ClassNotFoundException e) { 
			e.printStackTrace();
		}
		return matiere;
	}

	@Override
	public boolean ajouterRessenti(String sondage, Ressenti ressenti) {
		return false;
	}

}
