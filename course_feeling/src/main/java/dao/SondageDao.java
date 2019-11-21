package dao;

import business.Ressenti;
import business.ResultatSondage;

public interface SondageDao {
    
    /**
     * Récupère le nom d'une matière d'un sondage.
     * @param codeSondage : Le code du sondage.
     * @return Nom de sondage.
     * @throws SondageDaoException .
     */
    public String getMatiereOfSondage(String codeSondage) throws SondageDaoException;
    
    /**
     * Ajoute un ressenti dans un sondage.
     * @param sondage : identifiant du sondage.
     * @param ressenti : ressenti à ajouter.
     * @throws SondageDaoException .
     */
    public void ajouterRessenti(String sondage, Ressenti ressenti) throws SondageDaoException;
    
    /**
     * Ajoute un sondage dans la matière d'un prof.
     * @param idProf : identifiant d'un prof.
     * @param idMatiere : identifiant d'une matière.
     * @throws SondageDaoException .
     */
    public void ajouterSondage(String idProf, String idMatiere) throws SondageDaoException;
    
    /**
     * Supprime un sondage.
     * @param idProf : identifiant d'un prof.
     * @param idSondage : identifiant du sondage.
     * @throws SondageDaoException .
     */
    public void supprimerSondage(String idProf, int idSondage) throws SondageDaoException;
    
    /**
     * Récupère le code d'un sondage s'il existe.
     * @param idProf : identifiant d'un prof.
     * @param idSondage : identifiant du sondage.
     * @return Code du sondage ou null.
     * @throws SondageDaoException .
     */
    public String getCode(String idProf, int idSondage) throws SondageDaoException;
    
    /**
     * Génère le code d'un sondage.
     * @param idProf : identifiant d'un prof.
     * @param idSondage : identifiant du sondage.
     * @return le code créer.
     * @throws SondageDaoException .
     */
    public String addCode(String idProf, int idSondage) throws SondageDaoException;

    /**
     * Récupère le résultat d'un sondage.
     * @param idProf : identifiant d'un prof.
     * @param idSondage : identifiant du sondage.
     * @return le résultat du sondage.
     * @throws SondageDaoException .
     */
    public ResultatSondage getResultat(String idProf, int idSondage) throws SondageDaoException;
}
