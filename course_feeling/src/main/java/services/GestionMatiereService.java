package services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import business.Matiere;
import business.MatiereResultat;
import business.Ressenti;
import business.ResultatSondage;
import business.Sondage;
import dao.MatiereDao;
import dao.MatiereDaoException;

public class GestionMatiereService extends AbstractServlet {

    private static MatiereDao matiereDao;

    private static final Logger logger = Logger
            .getLogger(GestionMatiereService.class.getName());

    public void setMatiereDao(MatiereDao matiereDao) {
        GestionMatiereService.matiereDao = matiereDao;
    }

    /**
     * /ens/subjects : Récupération des matières et sondages d'un enseignant.
     * 
     * /ens/subjects/{idMatiere}/results : Récupération des résultats d'une
     * matière.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Récupération des paramètres nécessaires dans l'URI
        String idProf = getIdProf(req);
        String[] parametres = null;
        if (req.getPathInfo() != null) {
            parametres = req.getPathInfo().split("/");
        }
        String idMatiere = null;
        if (parametres != null && parametres.length > 1) {
            idMatiere = parametres[1];
        }

        // Vérification de l'URI afin de différencier les cas
        boolean resultats = (parametres != null && parametres.length == 3
                && parametres[2].equals("results") && idMatiere != null);
        JsonObject jsonResponse = null;
        if (resultats) {
            jsonResponse = getResultsMatiere(idProf, idMatiere);
        } else if (parametres != null && (parametres.length == 0
                || (parametres.length == 1 && parametres[0].equals("")))) {
            jsonResponse = getMatieres(idProf);
        } else {
            logger.severe("Malformed request on doGet " + req.getPathInfo());
            jsonResponse = generateErrorStatus();
        }

        // Écriture de la réponse
        writeResponse(resp, jsonResponse);
    }

    /**
     * /ens/subjects : Ajout d'une matière.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Récupération des paramètres nécessaires dans l'URI
        String matiere = req.getParameter("subject");
        String idProf = getIdProf(req);
        logger.info(idProf);
        // Traitement de la requête
        JsonObject jsonResponse = null;
        if (matiere != null && !matiere.equals("")) {
            jsonResponse = addMatiere(idProf, matiere);
        } else {
            logger.severe("Malformed request on doPost");
            jsonResponse = generateErrorStatus();
        }

        // Écriture de la réponse
        writeResponse(resp, jsonResponse);
    }

    /**
     * /ens/subjects/{matiere} : Supprimer une matière.
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Récupération des paramètres nécessaires dans l'URI
        String[] parametres = req.getPathInfo().split("/");
        String matiere = null;
        try {
            matiere = parametres[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.severe("Malformed request on doDelete call : matiere null");
        }
        String idProf = getIdProf(req);

        // Traitement de la requête
        JsonObject jsonResponse = null;
        if (matiere != null && !matiere.equals("")) { // To create a code
            jsonResponse = deleteMatiere(idProf, matiere);
        } else {
            logger.severe("Malformed request on doDelete call");
            jsonResponse = generateErrorStatus();
        }

        // Écriture de la réponse
        writeResponse(resp, jsonResponse);
    }

    /**
     * Supprime une matière.
     * 
     * @param idProf  L'enseignant à qui appartient la matière.
     * @param matiere La matière à supprimer.
     * @return Un JSON de confirmation ou un JSON d'erreur.
     */
    private JsonObject deleteMatiere(String idProf, String matiere) {
        JsonObject response = null;
        try {
            matiereDao.supprimerMatiere(idProf, matiere);
            response = generateSuccessStatus();
            response.addProperty("id", matiere);
        } catch (MatiereDaoException e) {
            logger.severe("Error on deleteMatiere from DAO");
            response = generateErrorStatus();
        }
        return response;
    }

    /***
     * Récupère l'ID de l'enseignant dans la requête.
     * 
     * @param req La requête
     * @return L'ID de l'enseignant
     */
    private String getIdProf(HttpServletRequest req) {
        return (String) req.getAttribute("ensId");
    }

    /**
     * Récupère les résultats d'une matière.
     * 
     * @param idProf    L'enseignant à qui appartient la matière.
     * @param idMatiere La matière dont on récupère les résultats.
     * @return Un JSON contenant les résultats de la matière ou un JSON
     *         d'erreur.
     */
    private JsonObject getResultsMatiere(String idProf, String idMatiere) {
        JsonObject response = null;
        try {
            MatiereResultat resultats = matiereDao.getResultat(idProf,
                    idMatiere);
            JsonObject subject = new JsonObject();
            subject.addProperty("name", idMatiere);
            JsonArray polls = new JsonArray();
            for (ResultatSondage resultatSondage : resultats
                    .getResultatsSondage()) {
                JsonObject jsonSondage = new JsonObject();
                jsonSondage.addProperty("id", resultatSondage.getIdSondage());
                jsonSondage.addProperty("date",
                        resultatSondage.getDateSondage());
                Map<Ressenti, Integer> ressentis = resultatSondage
                        .getResultats();
                JsonArray jsonResults = new JsonArray();
                for (Ressenti r : ressentis.keySet()) {
                    JsonObject result = new JsonObject();
                    result.addProperty("num", r.ordinal());
                    result.addProperty("result",
                            resultatSondage.getResultats().get(r));
                    jsonResults.add(result);
                }
                jsonSondage.add("results", jsonResults);
                polls.add(jsonSondage);
            }
            subject.add("polls", polls);
            response = generateSuccessStatus();
            response.add("subject", subject);
        } catch (MatiereDaoException e) {
            logger.severe("Error on getResultsMatiere from DAO");
            response = generateErrorStatus();
        }
        return response;
    }

    /**
     * Récupère les matières et sondages des matières d'un enseignant.
     * 
     * @param idProf L'enseignant.
     * @return Un JSON contenant les matières et sondages.
     */
    private JsonObject getMatieres(String idProf) {
        JsonObject response = null;
        try {
            List<Matiere> matieres = matiereDao.getMatieres(idProf);
            JsonArray subjects = new JsonArray();
            for (Matiere m : matieres) {
                JsonObject jsonMatiere = new JsonObject();
                jsonMatiere.addProperty("id", m.getName());
                jsonMatiere.addProperty("name", m.getName());
                JsonArray polls = new JsonArray();
                for (Sondage s : m.getSondages()) {
                    JsonObject jsonSondage = new JsonObject();
                    jsonSondage.addProperty("id", s.getId());
                    jsonSondage.addProperty("date", s.getDate());
                    polls.add(jsonSondage);
                }
                jsonMatiere.add("polls", polls);
                subjects.add(jsonMatiere);
            }
            response = generateSuccessStatus();
            response.add("subjects", subjects);
        } catch (MatiereDaoException e) {
            logger.severe("Error on getMatieres from DAO" + e.getMessage());
            response = generateErrorStatus();
        }
        return response;
    }

    /**
     * Crée une nouvelle matière.
     * 
     * @param idProf     L'enseignant à qui appartient la nouvelle matière.
     * @param nomMatiere Le nom de la nouvelle matière.
     * @return Un JSON contenant l'ID de la matière créée ou un JSON d'erreur.
     */
    private JsonObject addMatiere(String idProf, String nomMatiere) {
        JsonObject response = null;
        try {
            matiereDao.ajoutMatiere(idProf, nomMatiere);
            response = generateSuccessStatus();
            response.addProperty("id", nomMatiere);
        } catch (MatiereDaoException e) {
            logger.severe("Error on addMatiere from DAO" + e.getMessage());
            response = generateErrorStatus();
        }
        return response;
    }
}
