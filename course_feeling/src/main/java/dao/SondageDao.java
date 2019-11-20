package dao;

import business.Ressenti;
import business.ResultatSondage;

public interface SondageDao {
    public String getMatiereOfSondage(String codeSondage) throws SondageDaoException;
    
    public void ajouterRessenti(String sondage, Ressenti ressenti) throws SondageDaoException;
    
    public void ajouterSondage(String idProf, String idMatiere) throws SondageDaoException;
    
    public String getCode(int id) throws SondageDaoException;
    
    public String addCode(int id) throws SondageDaoException;
    
    public ResultatSondage getResultat(int id) throws SondageDaoException;
}
