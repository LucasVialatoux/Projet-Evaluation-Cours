package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class UtilisateurDaoImpl implements UtilisateurDao {
    private DataSource ds;
    private Properties sqlCodeProp;
    static final Logger logger = 
            Logger.getLogger(MatiereDaoImpl.class.getName());

    public void setDatasource(DataSource ds) {
        this.ds = ds;
    }

    
    /**
     * Charge le code SQL depuis un fichier.
     */
    public UtilisateurDaoImpl() {
        try (InputStream input = UtilisateurDaoImpl.class.getClassLoader()
                .getResourceAsStream(
                        "resources/utilisateurDAOSQL.properties")) {

            sqlCodeProp = new Properties();

            if (input == null) {
                logger.log("Sorry, unable to find file");
                return;
            }

            // load a properties file from class path, inside static method
            sqlCodeProp.load(input);

        } catch (IOException ex) {
            logger.severe("Error while loading SQL code : " + ex.getMessage());
        }
    }

    @Override
    public String getMdpHash(String email) throws UtilisateurDaoException {
        String mdpHash = null;
        String getMdpHashString = sqlCodeProp.getProperty("getMdpHash");
        try (Connection con = ds.getConnection();
                PreparedStatement stat = con
                        .prepareStatement(getMdpHashString);) {
            stat.setString(1, email);
            try (ResultSet set = stat.executeQuery()) {
                if (set.next()) {
                    mdpHash = set.getString("mdphash");
                }
            } catch (SQLException e) {
                throw new UtilisateurDaoException("ERROR SQL : ", e);
            }
        } catch (SQLException e) {
            throw new UtilisateurDaoException("ERROR SQL : ", e);
        }
        return mdpHash;
    }

    @Override
    public String getToken(String email) throws UtilisateurDaoException {
        String token = null;
        String getTokenString = sqlCodeProp.getProperty("getToken");
        try (Connection con = ds.getConnection();
                PreparedStatement stat = con
                        .prepareStatement(getTokenString);) {
            stat.setString(1, email);
            try (ResultSet set = stat.executeQuery()) {
                if (set.next()) {
                    token = set.getString("token");
                }
            } catch (SQLException e) {
                throw new UtilisateurDaoException("ERROR SQL : ", e);
            }
        } catch (SQLException e) {
            throw new UtilisateurDaoException("ERROR SQL : ", e);
        }
        return token;
    }

    @Override
    public void stockerToken(String email, String token)
            throws UtilisateurDaoException {

        String stockerTokenString = sqlCodeProp.getProperty("updateToken");
        try (Connection con = ds.getConnection();
                PreparedStatement stat = con
                        .prepareStatement(stockerTokenString);) {
            stat.setString(1, token);
            stat.setString(2, email);
            stat.executeUpdate();

        } catch (SQLException e) {
            throw new UtilisateurDaoException("ERROR SQL : ", e);
        }

    }

    @Override
    public void ajouterUtilisateur(String email, String mdpHash, String token)
            throws UtilisateurDaoException {
        String ajouterUtilisateurString = sqlCodeProp
                .getProperty("addUtilisateur");
        try (Connection con = ds.getConnection();
                PreparedStatement stat = con
                        .prepareStatement(ajouterUtilisateurString);) {
            stat.setString(1, email);
            stat.setString(2, mdpHash);
            stat.setString(3, token);
            stat.executeUpdate();

        } catch (SQLException e) {
            throw new UtilisateurDaoException("ERROR SQL : ", e);
        }
    }

    @Override
    public String getEmail(String token) throws UtilisateurDaoException {
        String email = null;
        String getEmailString = sqlCodeProp.getProperty("getEmail");
        try (Connection con = ds.getConnection();
                PreparedStatement stat = con
                        .prepareStatement(getEmailString);) {
            stat.setString(1, token);
            try (ResultSet set = stat.executeQuery()) {
                if (set.next()) {
                    email = set.getString("token");
                }
            } catch (SQLException e) {
                throw new UtilisateurDaoException("ERROR SQL : ", e);
            }
        } catch (SQLException e) {
            throw new UtilisateurDaoException("ERROR SQL : ", e);
        }
        return email;
    }

    @Override
    public boolean isExist(String email) throws UtilisateurDaoException {
        String emailSql = null;
        String getEmailString = sqlCodeProp.getProperty("isExists");
        try (Connection con = ds.getConnection();
                PreparedStatement stat = con
                        .prepareStatement(getEmailString);) {
            stat.setString(1, email);
            try (ResultSet set = stat.executeQuery()) {
                if (set.next()) {
                    emailSql = set.getString("email");
                }
            } catch (SQLException e) {
                throw new UtilisateurDaoException("ERROR SQL : ", e);
            }
        } catch (SQLException e) {
            throw new UtilisateurDaoException("ERROR SQL : ", e);
        }
        return emailSql != null;
    }

    @Override
    public void supprimerToken(String email) throws UtilisateurDaoException {
        stockerToken(email, null);
    }

}
