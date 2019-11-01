package dao;

import java.util.Map;

import business.Ressenti;

public interface SondageDao {
    public String getMatiereOfSondage(String codeSondage);
    
    public void ajouterRessenti(String sondage, Ressenti ressenti);
    
    public void ajouterSondage(String idProf, String idMatiere);
    
    public String getCode(int id);
    
    public Map<Ressenti, Integer> getResultat(int id);
}
