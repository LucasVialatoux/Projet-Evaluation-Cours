package dao;

import java.util.Map;
import java.util.HashMap;

import business.Ressenti;
import business.ResultatSondage;

public class SondageDaoMockImpl implements SondageDao {

    @Override
    public String getMatiereOfSondage(String codeSondage) throws SondageDaoException {
        if (codeSondage.contentEquals("00000")) {
			return "Sciences";	
		} else if (codeSondage.equals("abcde")) {
			throw new SondageDaoException("abc", null);
		}
		else {
			return "";
		}
	}

    @Override
    public void ajouterRessenti(String sondage, Ressenti ressenti)
            throws SondageDaoException {
        // TODO Auto-generated method stub
    }

    @Override
    public void ajouterSondage(String idProf, String idMatiere)
            throws SondageDaoException {
        // TODO Auto-generated method stub
    }

    @Override
    public String getCode(String idProf, int idSondage)
            throws SondageDaoException {
		// TODO Auto-generated method stub
		if(idProf == "test@test.com" && idSondage == 00000) {
			return "AA999";
		}
		else return null;
    }

    @Override
    public String addCode(String idProf, int idSondage)
            throws SondageDaoException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void supprimerSondage(String idProf, int idSondage)
            throws SondageDaoException {
        // TODO Auto-generated method stub
    }

    @Override
    public ResultatSondage getResultat(String idProf, int idSondage)
            throws SondageDaoException {
		if(idProf == "test@test.com" && idSondage == 00000) {
			ResultatSondage rS = new ResultatSondage();
			rS.setIdSondage(idSondage);
			rS.setDateSondage(22112019);

			Map<Ressenti,Integer> monSondage = new HashMap<>();
        	monSondage.put(Ressenti.Complique, 125);
			monSondage.put(Ressenti.Accessible, 134);
			rS.setResultats(monSondage);

			return rS;
		}
        return null;
    }
}
