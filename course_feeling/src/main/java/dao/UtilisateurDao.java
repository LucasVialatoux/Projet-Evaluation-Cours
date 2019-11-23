package dao;

public interface UtilisateurDao {

    public String getMdpHash(String nomUtilisateur);

    public String getToken();

    public void stockerToken(String nomUtilisateur, String token);

    public boolean supprimerToken(String nomUtilisateur);

    public void ajouterUtilisateur(String nomUtilisateur, String mdpHash, String token);

    public boolean isExist(String email);

}
