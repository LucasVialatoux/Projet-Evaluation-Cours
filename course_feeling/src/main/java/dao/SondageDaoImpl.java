package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.management.RuntimeErrorException;
import javax.sql.DataSource;

import business.Ressenti;

public class SondageDaoImpl implements SondageDao {

    private DataSource ds;
    private Properties sqlCodeProp;
    
    /**
     * .
     * @throws SondageDaoException .
     */
    public SondageDaoImpl() {
        try (InputStream input = 
                SondageDaoImpl.class.getClassLoader()
                .getResourceAsStream("resources/sondageDAOSQL.properties")) {

            sqlCodeProp = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find file");
                return;
            }

            //load a properties file from class path, inside static method
            sqlCodeProp.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String getMatiereOfSondage(String codeSondage)
            throws SondageDaoException {
        String getMatiereString = sqlCodeProp.getProperty("getMatiere");
        String matiere = null;
        try (Connection con = ds.getConnection();
             PreparedStatement stat = con.prepareStatement(getMatiereString);) {
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
        String addRessentiString = sqlCodeProp.getProperty("addRessenti");
        try (Connection con = ds.getConnection();
             PreparedStatement stat = con.prepareStatement(addRessentiString);) {
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
        String ajouterSondageString = sqlCodeProp.getProperty("addSondage");
        try (Connection con = ds.getConnection();
             PreparedStatement stat = con.prepareStatement(ajouterSondageString);) {
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
        String getCodeString = sqlCodeProp.getProperty("getCode");
        try (Connection con = ds.getConnection();
             PreparedStatement stat = con.prepareStatement(getCodeString);) {
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
        String getResultatString = sqlCodeProp.getProperty("getResultat");
        Map<Ressenti, Integer> resultat = new HashMap<Ressenti, Integer>();
        // Init resultat
        for (Ressenti res : Ressenti.values()) {
            resultat.put(res, 0);
        }
        
        // Request SQL
        try (Connection con = ds.getConnection();
             PreparedStatement stat = con.prepareStatement(getResultatString);) {
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
