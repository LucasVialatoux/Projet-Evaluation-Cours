package dao;

public interface UtilisateurDao {

    public boolean getMdpHash(String nomUtilisateur);

    public boolean getToken(String token);

    public void stockerToken(String nomUtilisateur, String token);

    public void ajouterUtilisateur(String nomUtilisateur, String mdpHash, String token);

}
