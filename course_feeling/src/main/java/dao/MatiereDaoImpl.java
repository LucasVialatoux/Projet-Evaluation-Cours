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
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.sql.DataSource;

import business.Matiere;
import business.MatiereResultat;
import business.Ressenti;
import business.ResultatSondage;
import business.Sondage;

public class MatiereDaoImpl implements MatiereDao {
    private DataSource ds;

    public void setDatasource(DataSource ds) {
        this.ds = ds;
    }

    private Properties sqlCodeProp;
    static final Logger logger = Logger
            .getLogger(MatiereDaoImpl.class.getName());

    /**
     * Charge le code SQL depuis un fichier.
     * 
     * @throws SondageDaoException .
     */
    public MatiereDaoImpl() {
        try (InputStream input = MatiereDaoImpl.class.getClassLoader()
                .getResourceAsStream("resources/matiereDAOSQL.properties")) {

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
    public void ajoutMatiere(String idProf, String nomMat)
            throws MatiereDaoException {
        String ajouterMatiereString = sqlCodeProp.getProperty("addMatiere");
        try (Connection con = ds.getConnection();
                PreparedStatement stat = con
                        .prepareStatement(ajouterMatiereString);) {
            stat.setString(1, idProf);
            stat.setString(2, nomMat);
            stat.setLong(3, System.currentTimeMillis());
            stat.executeUpdate();

        } catch (SQLException e) {
            throw new MatiereDaoException("ERROR SQL : ", e);
        }

    }

    @Override
    public void supprimerMatiere(String idProf, String nomMat)
            throws MatiereDaoException {
        String supprimerMatiereString = sqlCodeProp
                .getProperty("deleteMatiere");
        try (Connection con = ds.getConnection();
                PreparedStatement stat = con
                        .prepareStatement(supprimerMatiereString);) {
            stat.setString(1, idProf);
            stat.setString(2, nomMat);
            stat.setLong(3, System.currentTimeMillis());
            stat.executeUpdate();

        } catch (SQLException e) {
            throw new MatiereDaoException("ERROR SQL : ", e);
        }

    }

    @Override
    public List<Matiere> getMatieres(String idProf) throws MatiereDaoException {
        String getMatieresString = sqlCodeProp.getProperty("getMatieres");

        Map<String, List<Sondage>> matMap = new HashMap<>();

        // Request SQL
        try (Connection con = ds.getConnection();
                PreparedStatement stat = con
                        .prepareStatement(getMatieresString);) {
            stat.setString(1, idProf);

            try (ResultSet set = stat.executeQuery()) {
                while (set.next()) {
                    String matNom = set.getString("idmatiere");

                    if (matMap.get(matNom) == null) {
                        matMap.put(matNom, new ArrayList<>());
                    }

                    List<Sondage> mat = matMap.get(matNom);

                    Sondage sondage = new Sondage();
                    sondage.setId(set.getInt("idsondage"));
                    sondage.setDate(set.getLong("datesondage"));

                    mat.add(sondage);
                }
            } catch (SQLException e) {
                throw new MatiereDaoException("ERROR SQL : ", e);
            }
        } catch (SQLException e) {
            throw new MatiereDaoException("ERROR SQL : ", e);
        }

        List<Matiere> matieres = new ArrayList<>();

        for (Entry<String, List<Sondage>> it : matMap.entrySet()) {
            Matiere mat = new Matiere();
            mat.setName(it.getKey());
            mat.setSondages(it.getValue());
        }

        return matieres;
    }

    private ResultatSondage getResultatSondage(List<ResultatSondage> resultats,
            int id) {
        for (ResultatSondage res : resultats) {
            if (id == res.getIdSondage()) {
                return res;
            }
        }

        return null;
    }

    @Override
    public MatiereResultat getResultat(String idProf, String nomMat)
            throws MatiereDaoException {
        String getResultatString = sqlCodeProp.getProperty("getMatieres");

        MatiereResultat matRes = new MatiereResultat();
        matRes.setNom(nomMat);
        matRes.setResultatsSondage(new ArrayList<ResultatSondage>());

        // Request SQL
        try (Connection con = ds.getConnection();
                PreparedStatement stat = con
                        .prepareStatement(getResultatString);) {
            stat.setString(1, nomMat);
            stat.setString(2, idProf);

            try (ResultSet set = stat.executeQuery()) {
                while (set.next()) {
                    int idSondage = set.getInt("idsondage");
                    long dateSondage = set.getLong("datesondage");

                    ResultatSondage res = getResultatSondage(
                            matRes.getResultatsSondage(), idSondage);
                    if (res == null) {
                        res = new ResultatSondage();
                        res.setIdSondage(idSondage);
                        res.setDateSondage(dateSondage);
                        for (Ressenti ress : Ressenti.values()) {
                            res.getResultats().put(ress, 0);
                        }

                        matRes.getResultatsSondage().add(res);
                    }
                    res.getResultats().put(
                            Ressenti.valueOf(set.getString("ress")),
                            (Integer) set.getInt("count"));
                }
            } catch (SQLException e) {
                throw new MatiereDaoException("ERROR SQL : ", e);
            }
        } catch (SQLException e) {
            throw new MatiereDaoException("ERROR SQL : ", e);
        }

        return matRes;
    }

}
