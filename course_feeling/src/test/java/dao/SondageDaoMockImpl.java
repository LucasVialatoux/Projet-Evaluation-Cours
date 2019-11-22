package dao;

import java.util.Map;

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
        return null;
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
        // TODO Auto-generated method stub
        return null;
    }

}
