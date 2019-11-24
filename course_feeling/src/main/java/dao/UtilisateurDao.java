package dao;

public interface UtilisateurDao {

    /**
     * Récupère le mot de passe de l'utilisateur.
     * @param email Email de l'utilisateur.
     * @return Le mot de passe utilisateur hashé.
     * @throws UtilisateurDaoException .
     */
    public String getMdpHash(String email) throws UtilisateurDaoException;

    /**
     * Récupère le token d'un utilisateur.
     * @param email Email de l'utilisateur.
     * @return Le token de l'utilisateur.
     * @throws UtilisateurDaoException .
     */
    public String getToken(String email) throws UtilisateurDaoException;

    /**
     * Modifie le token d'un utilisateur.
     * @param email Email de l'utilisateur.
     * @param token Token de l'utilisateur.
     * @throws UtilisateurDaoException .
     */
    public void stockerToken(String email, String token)
            throws UtilisateurDaoException;

    /**
     * Ajoute un utilisateur à la base de données.
     * @param email Email de l'utilisateur.
     * @param mdpHash Mot de passe hashé de l'utilisateur.
     * @param token Token de l'utilisateur.
     * @throws UtilisateurDaoException .
     */
    public void ajouterUtilisateur(String email, String mdpHash, String token)
            throws UtilisateurDaoException;

    /**
     * Récupère l'email d'un utilisateur depuis le token.
     * @param token Token de l'utilisateur.
     * @return Email de l'utilisateur.
     * @throws UtilisateurDaoException .
     */
    public String getEmail(String token) throws UtilisateurDaoException;
    
    /**
     * Vérifie qu'un email existe.
     * @param email Email que l'on vérifie.
     * @return si l'email existe.
     * @throws UtilisateurDaoException .
     */
    public boolean isExist(String email) throws UtilisateurDaoException;
    
    /**
     * Supprime le token d'un utilisateur.
     * @param email Email de l'utilisateur dont on supprime le token.
     * @throws UtilisateurDaoException .
     */
    public void supprimerToken(String email) throws UtilisateurDaoException;

}
