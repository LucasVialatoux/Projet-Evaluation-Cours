package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import business.Ressenti;
import business.ResultatSondage;

public class SondageDaoImpl implements SondageDao {

    private DataSource ds;
    private Properties sqlCodeProp;
    static final Logger logger = 
            Logger.getLogger(SondageDaoImpl.class.getName());
    private Random random = new Random();

    /**
     * Charge le code SQL depuis un fichier.
     */
    public SondageDaoImpl() {
        try (InputStream input = SondageDaoImpl.class.getClassLoader()
                .getResourceAsStream("resources/sondageDAOSQL.properties")) {

            sqlCodeProp = new Properties();

            if (input == null) {
                logger.log(Level.INFO, "Sorry, unable to find file");
                return;
            }

            // load a properties file from class path, inside static method
            sqlCodeProp.load(input);

        } catch (IOException ex) {
            logger.severe("Error while loading SQL code : " + ex.getMessage());
        }
    }

    @Override
    public String getMatiereOfSondage(String codeSondage)
            throws SondageDaoException {
        String getMatiereString = sqlCodeProp.getProperty("getMatiere");
        String matiere = null;
        try (Connection con = ds.getConnection();
                PreparedStatement stat = con
                        .prepareStatement(getMatiereString);) {
            stat.setString(1, codeSondage);

            try (ResultSet set = stat.executeQuery()) {
                if (set.next()) {
                    matiere = set.getString("nomMatiere");
                }
            } catch (SQLException e) {
                throw new SondageDaoException("ERROR SQL : " + e.getMessage(), e);
            }
        } catch (SQLException e) {
            throw new SondageDaoException("ERROR SQL : " + e.getMessage(), e);
        }
        return matiere;
    }

    public void setDatasource(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public void ajouterRessenti(String sondage, Ressenti ressenti)
            throws SondageDaoException {
        String addRessentiString = sqlCodeProp.getProperty("addRessenti");
        try (Connection con = ds.getConnection();
                PreparedStatement stat = con
                        .prepareStatement(addRessentiString);) {
            stat.setString(1, sondage);
            stat.setString(2, ressenti.toString());
            stat.executeUpdate();

        } catch (SQLException e) {
            throw new SondageDaoException("ERROR SQL : "  + e.getMessage(), e);
        }
    }

    @Override
    public void ajouterSondage(String idProf, String idMatiere)
            throws SondageDaoException {
        String ajouterSondageString = sqlCodeProp.getProperty("addSondage");
        try (Connection con = ds.getConnection();
                PreparedStatement stat = con
                        .prepareStatement(ajouterSondageString);) {
            stat.setString(1, idProf);
            stat.setString(2, idMatiere);
            stat.setLong(3, System.currentTimeMillis());
            stat.executeUpdate();

        } catch (SQLException e) {
            throw new SondageDaoException("ERROR SQL : " + e.getMessage(), e);
        }
    }

    @Override
    public String getCode(String idProf, int idSondage)
            throws SondageDaoException {
        String code = null;
        String getCodeString = sqlCodeProp.getProperty("getCode");
        try (Connection con = ds.getConnection();
                PreparedStatement stat = con.prepareStatement(getCodeString);) {
            stat.setInt(1, idSondage);
            stat.setString(2, idProf);
            try (ResultSet set = stat.executeQuery()) {
                if (set.next()) {
                    code = set.getString("code");
                }
            } catch (SQLException e) {
                throw new SondageDaoException("ERROR SQL : " + e.getMessage(), e);
            }
        } catch (SQLException e) {
            throw new SondageDaoException("ERROR SQL : " + e.getMessage(), e);
        }
        return code;
    }

    private List<String> getExistingCode() throws SondageDaoException {
        List<String> codes = new ArrayList<>();

        String getCodeString = sqlCodeProp.getProperty("getCodes");
        try (Connection con = ds.getConnection();
                PreparedStatement stat = con.prepareStatement(getCodeString);
                ResultSet set = stat.executeQuery();) {

            if (set.next()) {
                codes.add(set.getString("code"));
            }
        } catch (SQLException e) {
            throw new SondageDaoException("ERROR SQL : " + e.getMessage(), e);
        }

        return codes;
    }

    @Override
    public String addCode(String idProf, int idSondage)
            throws SondageDaoException {
        String code = getCode(idProf, idSondage);
        if (code != null && !code.equals("")) {
            return code;
        }
        List<String> codes = getExistingCode();

        char[] codeChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                             'A', 'B' };
        do {
            char[] ccode = new char[5];
            for (int i = 0; i < 5; i++) {
                int cid = random.nextInt(codeChars.length);
                ccode[i] = codeChars[cid];
            }
            code = String.valueOf(ccode);
        } while (!codes.contains(code));

        // Adding code to database
        String addCodeString = sqlCodeProp.getProperty("addCode");
        try (Connection con = ds.getConnection();
                PreparedStatement stat = con.prepareStatement(addCodeString);) {
            stat.setInt(1, idSondage);
            stat.setString(2, code);
            stat.setLong(3, System.currentTimeMillis());
            stat.executeUpdate();

        } catch (SQLException e) {
            throw new SondageDaoException("ERROR SQL : " + e.getMessage(), e);
        }
        return code;
    }

    @Override
    public ResultatSondage getResultat(String idProf, int idSondage)
            throws SondageDaoException {
        ResultatSondage resultatSondage = new ResultatSondage();
        resultatSondage.setIdSondage(idSondage);

        String getResultatString = sqlCodeProp.getProperty("getResultat");
        Map<Ressenti, Integer> resultat = new HashMap<>();

        // Init resultat
        for (Ressenti res : Ressenti.values()) {
            resultat.put(res, 0);
        }

        // Request SQL
        try (Connection con = ds.getConnection();
                PreparedStatement stat = con
                        .prepareStatement(getResultatString);) {
            stat.setInt(1, idSondage);
            stat.setString(2, idProf);

            try (ResultSet set = stat.executeQuery()) {
                while (set.next()) {
                    resultat.put(Ressenti.valueOf(set.getString("ress")),
                            (Integer) set.getInt("count"));
                    resultatSondage.setDateSondage(set.getLong("datesondage"));
                }
            } catch (SQLException e) {
                throw new SondageDaoException("ERROR SQL : " + e.getMessage(), e);
            }
        } catch (SQLException e) {
            throw new SondageDaoException("ERROR SQL : " + e.getMessage(), e);
        }

        resultatSondage.setResultats(resultat);

        return resultatSondage;
    }

    @Override
    public void supprimerSondage(String idProf, int idSondage)
            throws SondageDaoException {
        String supprimerSondageString = sqlCodeProp
                .getProperty("deleteSondage");
        try (Connection con = ds.getConnection();
                PreparedStatement stat = con
                        .prepareStatement(supprimerSondageString);) {
            stat.setInt(1, idSondage);
            stat.setString(2, idProf);
            stat.executeUpdate();

        } catch (SQLException e) {
            throw new SondageDaoException("ERROR SQL : " + e.getMessage(), e);
        }

    }
}
