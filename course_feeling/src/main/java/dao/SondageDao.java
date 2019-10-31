package dao;

import business.Ressenti;

public interface SondageDao {
    public String getMatiereOfSondage(String codeSondage);
    
    public boolean ajouterRessenti(String sondage, Ressenti ressenti);
}
