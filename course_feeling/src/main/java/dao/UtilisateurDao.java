package dao;

public interface UtilisateurDao {

    public String getMdpHash(String email) throws UtilisateurDaoException;

    public String getToken(String email) throws UtilisateurDaoException;

    public void stockerToken(String email, String token)
            throws UtilisateurDaoException;

    public void ajouterUtilisateur(String email, String mdpHash, String token)
            throws UtilisateurDaoException;

    public String getEmail(String token) throws UtilisateurDaoException;

}
