package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import business.Ressenti;

public class SondageDaoImpl implements SondageDao {

    private DataSource ds;
    private String getMatiere = "SELECT nomMatiere FROM miseadispo Mi "
            + "JOIN Sondage S on Mi.idSondage=S.idSondage"
            + " JOIN Matiere M on S.idMatiere=M.nomMatiere "
            + "WHERE Mi.code=?;";
    private String addRessenti = "INSERT INTO ressentis(idSondage,ress) VALUES ("
            + "(SELECT idSondage FROM miseadispo M WHERE code=?)"
            + ",CAST(? AS ressenti_t));";
    private String addSondage = "INSERT INTO sondage(idprof, idmatiere) VALUES (?, ?);";
    private String getCode = "SELECT code FROM miseadispo M WHERE idSondage=?;";
    private String getResultat = "SELECT ress, COUNT(*) FROM ressentis "
                               + " WHERE idsondage = ? GROUP BY ress;";

    @Override
    public String getMatiereOfSondage(String codeSondage)
            throws SondageDaoException {
        String matiere = null;
        try (Connection con = ds.getConnection();
             PreparedStatement stat = con.prepareStatement(getMatiere);) {
            stat.setString(1, codeSondage);
            ResultSet set = stat.executeQuery();
            if (set.next()) {
                matiere = set.getString("nomMatiere");
            }
        } catch (SQLException e) {
            throw new SondageDaoException("ERROR SQL : ", e);
        }
        return matiere;
    }

    public void setDatasource(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public void ajouterRessenti(String sondage, Ressenti ressenti) throws SondageDaoException {
        try (Connection con = ds.getConnection();
             PreparedStatement stat = con.prepareStatement(addRessenti);) {
            stat.setString(1, sondage);
            stat.setString(2, ressenti.toString());
            stat.executeUpdate();

        } catch (SQLException e) {
            throw new SondageDaoException("ERROR SQL : ", e);
        }
    }

    @Override
    public void ajouterSondage(String idProf, String idMatiere)
            throws SondageDaoException {
        try (Connection con = ds.getConnection();
             PreparedStatement stat = con.prepareStatement(addSondage);) {
            stat.setString(1, idProf);
            stat.setString(2, idMatiere);
            stat.executeUpdate();

        } catch (SQLException e) {
            throw new SondageDaoException("ERROR SQL : ", e);
        }
    }

    @Override
    public String getCode(int id) throws SondageDaoException {
        String code = null;
        try (Connection con = ds.getConnection();
             PreparedStatement stat = con.prepareStatement(getCode);) {
            stat.setInt(1, id);
            ResultSet set = stat.executeQuery();
            if (set.next()) {
                code = set.getString("code");
            }
        } catch (SQLException e) {
            throw new SondageDaoException("ERROR SQL : ", e);
        }
        return code;
    }

    @Override
    public Map<Ressenti, Integer> getResultat(int id) throws SondageDaoException {
        Map<Ressenti, Integer> resultat = new HashMap<Ressenti, Integer>();
        // Init resultat
        for (Ressenti res : Ressenti.values()) {
            resultat.put(res, 0);
        }
        
        // Request SQL
        try (Connection con = ds.getConnection();
             PreparedStatement stat = con.prepareStatement(getResultat);) {
            stat.setInt(1, id);
            ResultSet set = stat.executeQuery();
            while (set.next()) {
                resultat.put(Ressenti.valueOf(set.getString("ress")), (
                        Integer) set.getInt("count"));
            }
        } catch (SQLException e) {
            throw new SondageDaoException("ERROR SQL : ", e);
        }
        return resultat;
    }
}
