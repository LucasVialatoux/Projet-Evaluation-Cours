package dao;

public interface UtilisateurDao {

    public String getMdpHash(String nomUtilisateur);

    public String getToken();

    public void stockerToken(String nomUtilisateur, String token);

    public void ajouterUtilisateur(String nomUtilisateur, String mdpHash, String token);

}
