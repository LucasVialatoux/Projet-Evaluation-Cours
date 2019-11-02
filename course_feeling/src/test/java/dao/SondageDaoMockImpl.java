package dao;

import java.util.Map;

import business.Ressenti;
import business.ResultatSondage;

public class SondageDaoMockImpl implements SondageDao {

    @Override
    public String getMatiereOfSondage(String codeSondage) {
        if (codeSondage.contentEquals("00000")) {
            return "Sciences";
        } else {
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
    public String getCode(int id) throws SondageDaoException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultatSondage getResultat(int id)
            throws SondageDaoException {
        // TODO Auto-generated method stub
        return null;
    }

}
