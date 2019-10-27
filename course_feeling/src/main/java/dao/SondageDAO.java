package dao;

import business.Ressenti;

public interface SondageDAO {
	public String getMatiereOfSondage(String codeSondage);
	
	public boolean ajouterRessenti(String sondage, Ressenti ressenti);
}
