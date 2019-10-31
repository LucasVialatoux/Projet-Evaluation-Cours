package dao;

import business.Ressenti;

public class SondageDaoMockImpl implements SondageDao {

	@Override
	public String getMatiereOfSondage(String codeSondage) {
		if(codeSondage.contentEquals("00000")) {
			return "Sciences";
		} else {
			return "";
		}
	}

	@Override
	public boolean ajouterRessenti(String codeSondage, Ressenti ressenti) {
		return (codeSondage.contentEquals("00000"));
	}

}
