package dao;

public class UtilisateurDaoMockImpl implements UtilisateurDao {

    /**
     * Fonction qui renvoi le mdp de l'utilisateur.
     * @param nomUtilisateur .
     * @return le mot de passe chiffré de l'utilisateur.
     */
    @Override
    public String getMdpHash(String nomUtilisateur) {
        String motdepasseHasheAvecSha256 = "a665a45920422f9d417e486"
                + "7efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3"; // hash de 123 avec sha256
        return motdepasseHasheAvecSha256;
    }

    /**
     * accesseur du token.
     * @return le token de l'utilisaeur stocké dans la base de données.
     */
    @Override
    public String getToken(String nomUtilisateur) {
        return " ";
    }

    /**
     *  la fonction qui stoke le token de l'utilisateur dans la base de données.
     * @param nomUtilisateur .
     * @param token .
     */
    @Override
    public void stockerToken(String nomUtilisateur, String token) {

    }

    /**
     * Supprime de token de l'utilisateur.
     * @param nomUtilisateur .
     * @return un boolean , vrai si on met le token a null, faux sinon
     */
    @Override
    public void supprimerToken(String nomUtilisateur) {
        // met le token de l'utilisateur à null en base
    }

    /**
     * Fonction qui rajoute un utilisateur dans la base de données.
     * @param nomUtilisateur .
     * @param mdpHash .
     * @param token .
     */
    @Override
    public void ajouterUtilisateur(String nomUtilisateur, String mdpHash, String token) {
        // Ajoute l'utilisateur dans la base de données
    }

    @Override
    public boolean isExist(String email) {
        // verifie la presence d'un email en base et renvoie vrai ou faux
        if (email.equals("test@test.test")) {
            return true;
        }
        return false;
    }

    @Override
    public String getEmail(String token) throws UtilisateurDaoException {
        // TODO Auto-generated method stub
        return null;
    }

}
