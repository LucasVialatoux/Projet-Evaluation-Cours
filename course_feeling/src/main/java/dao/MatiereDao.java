package dao;

import java.util.List;

import business.Matiere;
import business.MatiereResultat;

public interface MatiereDao {
    
    /**
     * Ajout d'une matière pour un prof.
     * @param idProf : identifiant du prof.
     * @param nomMat : nom de la matière.
     * @throws MatiereDaoException .
     */
    public void ajoutMatiere(String idProf, String nomMat) throws MatiereDaoException;
    
    /**
     * Suppression d'une matière d'un prof.
     * @param idProf : identifiant du prof.
     * @param nomMat : nom de la matière.
     * @throws MatiereDaoException .
     */
    public void supprimerMatiere(String idProf, String nomMat) throws MatiereDaoException;
    
    /**
     * Récupère les resultats de sondage des matières d'un prof.
     * @param idProf : identifiant du prof.
     * @return Une liste des matières.
     * @throws MatiereDaoException .
     */
    public List<Matiere> getMatieres(String idProf) throws MatiereDaoException;
    
    /**
     * Récupère les résultats de sondage d'une matière.
     * @param idProf : identifiant du prof.
     * @param nomMat : nom de la matière.
     * @return Resultat de la matière.
     * @throws MatiereDaoException .
     */
    public MatiereResultat getResultat(String idProf, String nomMat) throws MatiereDaoException;
    

}
