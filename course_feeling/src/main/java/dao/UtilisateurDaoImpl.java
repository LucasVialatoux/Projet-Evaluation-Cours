package dao;

public class UtilisateurDaoImpl implements UtilisateurDao {

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
    public String getToken() {

        return "eyJhbGciOiJIUzI1NiJ9.e"
                + "yJpc3MiOiIvc2lnbmluIiwiZW1haWwiOiJ0ZXN0QHRlc3QudGVz"
                + "dCIsImlhdCI6MTU3NDUzNzM3NSwiZXhwIjoxNTc0NTM5MTc1fQ.z-YhVaJTDPT"
                + "wj1cURo39ztzVSJccVnmngC-8k34oMjQ";
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
    public boolean supprimerToken(String nomUtilisateur) {
        // met le token de l'utilisateur à null en base
        return true;
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

    public String getEmail() {
        return "test@test.test";
    }

    public String getPassword(String email) {
        return  "";
    }
}
