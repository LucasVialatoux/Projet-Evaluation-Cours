package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import business.Ressenti;

public class SondageDaoImpl implements SondageDao {

    private DataSource ds;
    private String getMatiere = "SELECT nomMatiere FROM miseadispo Mi "
            + "JOIN Sondage S on Mi.idSondage=S.idSondage" 
            +  "JOIN Matiere M on S.idMatiere=M.nomMatiere "
            + "WHERE Mi.code=?;";
    private String addRessenti = "INSERT INTO ressentis(idSondage,ress) VALUES ("
            + "(SELECT idSondage FROM miseadispo M WHERE code=?)"
            + ",CAST(? AS ressenti_t));";
    
    @Override
    public String getMatiereOfSondage(String codeSondage) {
        String matiere = null;
        try {
            Connection con = ds.getConnection();
            PreparedStatement stat = con.prepareStatement(getMatiere);
            stat.setString(1, codeSondage);
            ResultSet set = stat.executeQuery();
            if (set.next()) {
                matiere = set.getString("nomMatiere");
            }
        } catch (SQLException e) { 
            e.printStackTrace();
        }
        return matiere;
    }

    @Override
    public boolean ajouterRessenti(String sondage, Ressenti ressenti) {
        boolean success = false;
        try {
            Connection con = ds.getConnection();
            PreparedStatement stat = con.prepareStatement(addRessenti);
            stat.setString(1, sondage);
            stat.setString(2, ressenti.toString());
            stat.executeUpdate();
            success = true;
            
        } catch (SQLException e) { 
            e.printStackTrace();
        }
        return success;
    }

    public void setDatasource(DataSource ds) {
        this.ds = ds;
    }
}
