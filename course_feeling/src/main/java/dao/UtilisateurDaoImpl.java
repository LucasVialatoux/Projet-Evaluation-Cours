package dao;

public class UtilisateurDaoImpl implements UtilisateurDao {

    @Override
    public String getMdpHash(String nomUtilisateur) {
        String motdepasseHacheAvecSha256 = "123";
        return motdepasseHacheAvecSha256;
    }

    @Override
    public String getToken() {
        return "";
    }

    @Override
    public void stockerToken(String nomUtilisateur, String token) {

    }

    @Override
    public void ajouterUtilisateur(String nomUtilisateur, String mdpHash, String token) {

    }

    // Pour mes test en local

    public String getEmail() {
        return "test@test.test";
    }

    public String getPassword() {
        return "123";
    }
}
