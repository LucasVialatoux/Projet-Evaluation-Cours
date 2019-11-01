package dao;

import java.util.Map;

import business.Ressenti;

public interface SondageDao {
    public String getMatiereOfSondage(String codeSondage) throws SondageDaoException;
    
    public void ajouterRessenti(String sondage, Ressenti ressenti) throws SondageDaoException;
    
    public void ajouterSondage(String idProf, String idMatiere) throws SondageDaoException;
    
    public String getCode(int id) throws SondageDaoException;
    
    public Map<Ressenti, Integer> getResultat(int id) throws SondageDaoException;
}
