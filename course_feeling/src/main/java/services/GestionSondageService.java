package services;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import business.Ressenti;
import business.ResultatSondage;
import dao.SondageDao;
import dao.SondageDaoException;

public class GestionSondageService extends AbstractServlet {

    // urlPatterns = "/ens/poll"
    private static SondageDao sondageDao;

    private static final long serialVersionUID = -7794936188718493591L;

    private static final Logger logger = Logger
            .getLogger(GestionSondageService.class.getName());

    /**
     * /ens/poll/{idSondage} : Récupération d'un code de sondage existant.
     * 
     * /ens/poll/{idSondage]/results : Récupération des résultats d'un sondage.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Récupération des paramètres nécessaires dans l'URI
        String[] parametres = req.getPathInfo().split("/");
        String idProf = getIdProf(req);
        int idSondage = Integer.parseInt(parametres[1]);

        // Vérification de l'URI afin de différencier les cas
        boolean resultats = (parametres.length == 3
                && parametres[2].equals("results"));
        JsonObject jsonResponse = null;
        if (resultats) {
            jsonResponse = getResultSondages(idProf, idSondage);
        } else if (parametres.length == 2) {
            jsonResponse = getCodeSondage(idProf, idSondage);
        } else {
            logger.severe("Malformed request in doGet.");
            jsonResponse = generateErrorStatus();
        }

        writeResponse(resp, jsonResponse);
    }

    /**
     * /ens/poll : Ajout d'un sondage.
     * 
     * /ens/poll/{idSondage} : Création d'un code de sondage
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Récupération des paramètres nécessaires dans l'URI
        String matiere = req.getParameter("id");
        String idProf = getIdProf(req);
        JsonObject jsonResponse = null;

        // Vérification de l'URI afin de différencier les cas
        String idSondageString = null;
        if (req.getPathInfo() != null
                && req.getPathInfo().split("/").length == 2) {
            idSondageString = req.getPathInfo().split("/")[1];
        }
        if (idSondageString != null) { // To create a code
            /*
             * try { int idSondage = Integer.parseInt(idSondageString);
             * jsonResponse = createCodeSondage(idProf, idSondage); } catch
             * (NullPointerException e) {
             * logger.severe("NullPointerException in doPost"); jsonResponse =
             * generateErrorStatus(); }
             */
            int idSondage = Integer.parseInt(idSondageString);
            jsonResponse = createCodeSondage(idProf, idSondage);
        } else if (matiere != null && !matiere.equals("")) { // Add a new form
            jsonResponse = addSondage(idProf, matiere);
        } else {
            logger.severe("Malformed request in doPost");
            jsonResponse = generateErrorStatus();
        }

        writeResponse(resp, jsonResponse);
    }

    /**
     * /ens/poll/{idSondage} : Supprimer un sondage.
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Récupération des paramètres nécessaires dans l'URI
        String idProf = getIdProf(req);
        String pathInfo = req.getPathInfo();
        String idSondageString = null;
        if (pathInfo != null && pathInfo.split("/").length == 2) {
            idSondageString = req.getPathInfo().split("/")[1];
        }

        // Vérification de l'URI et traitement.
        JsonObject jsonResponse = null;
        if (idSondageString != null) {
            try {
                int idSondage = Integer.parseInt(idSondageString);
                jsonResponse = deleteSondage(idProf, idSondage);
            } catch (NullPointerException | NumberFormatException e) {
                logger.severe("Exception in doDelete " + e.getMessage());
                jsonResponse = generateErrorStatus();
            }
        } else {
            logger.severe("Malformed request in doDelete");
            jsonResponse = generateErrorStatus();
        }

        writeResponse(resp, jsonResponse);
    }

    public void setSondageDao(SondageDao sondageDao) {
        GestionSondageService.sondageDao = sondageDao;
    }

    /**
     * Récupère les résultats d'un sondage.
     * 
     * @param idProf    L'ID du professeur.
     * @param idSondage Le code du sondage.
     * @return Le JSON contenant les résultats ou le JSON d'erreur
     */
    private JsonObject getResultSondages(String idProf, int idSondage) {
        JsonObject response = null;
        try {
            ResultatSondage resultats = sondageDao.getResultat(idProf,
                    idSondage);
            if (resultats != null) {
                response = serializeFromResults(resultats);
            } else {
                logger.severe("Null results in getResultsSondage");
                response = generateErrorStatus();
            }
        } catch (SondageDaoException e) {
            logger.severe("Exception in getResultSondage from DAO : "
                    + e.getMessage());
            response = generateErrorStatus();
        }
        return response;
    }

    /**
     * Sérialize un objet de type ResultatSondage en réponse JSON.
     * 
     * @param resultats Les résultats communiqués par le DAO.
     * @return L'équivalent JSON de resultats
     */
    private JsonObject serializeFromResults(ResultatSondage resultats) {
        JsonObject poll = new JsonObject();
        poll.addProperty("id", resultats.getIdSondage());
        poll.addProperty("date", resultats.getDateSondage());
        JsonArray jsonResults = new JsonArray();
        for (Ressenti r : Ressenti.values()) {
            JsonObject result = new JsonObject();
            result.addProperty("num", r.ordinal());
            result.addProperty("result", resultats.getResultats().get(r));
            jsonResults.add(result);
        }
        poll.add("results", jsonResults);
        JsonObject response = generateSuccessStatus();
        response.add("poll", poll);
        return response;
    }

    /**
     * Récupère le code d'un sondage s'il existe.
     * 
     * @param idProf    L'ID du professeur.
     * @param idSondage Le code du sondage.
     * @return Le JSON contenant le code ou un JSON d'erreur
     */
    private JsonObject getCodeSondage(String idProf, int idSondage) {
        JsonObject response = null;
        String codeSondage = null;
        try {
            codeSondage = sondageDao.getCode(idProf, idSondage);
            if (codeSondage != null) {
                response = generateSuccessStatus();
                response.addProperty("code", codeSondage);
            } else {
                logger.severe("Poll code null in getCodeSondage");
                response = generateErrorStatus();
            }
        } catch (SondageDaoException e) {
            logger.severe(
                    "Exception in getCodeSondage from DAO : " + e.getMessage());
            response = generateErrorStatus();
        }
        return response;
    }

    /**
     * Supprime le sondage.
     * 
     * @param idProf    L'ID du professeur.
     * @param idSondage Le code du sondage.
     * @return Un JSON de validation ou un JSON d'erreur
     */
    private JsonObject deleteSondage(String idProf, int idSondage) {
        JsonObject response = null;
        try {
            sondageDao.supprimerSondage(idProf, idSondage);
            response = generateSuccessStatus();
        } catch (SondageDaoException e) {
            logger.severe(
                    "Exception in deleteSondage from DAO : " + e.getMessage());
            response = generateErrorStatus();
        }
        return response;
    }

    /**
     * Crée un code de sondage.
     * 
     * @param idProf    L'ID du professeur.
     * @param idSondage Le code du sondage.
     * @return Un JSON contenant le code créé ou un JSON d'erreur
     */
    private JsonObject createCodeSondage(String idProf, int idSondage) {
        JsonObject response = null;
        String codeSondage = null;
        try {
            codeSondage = sondageDao.addCode(idProf, idSondage);
            if (codeSondage != null) {
                response = generateSuccessStatus();
                response.addProperty("code", codeSondage);
            } else {
                logger.severe("Poll code null in createCodeSondage");
                response = generateErrorStatus();
            }
        } catch (SondageDaoException e) {
            logger.severe("Exception in createCodeSondage from DAO : "
                    + e.getMessage());
            response = generateErrorStatus();
        }
        return response;
    }

    /**
     * Ajoute un sondage.
     * 
     * @param idProf  L'ID du professeur.
     * @param matiere Le nom de la matière du sondage.
     * @return Un JSON de validation ou un JSON d'erreur
     */
    private JsonObject addSondage(String idProf, String matiere) {
        JsonObject jsonResponse = null;
        try {
            sondageDao.ajouterSondage(idProf, matiere);
            jsonResponse = generateSuccessStatus();
        } catch (SondageDaoException e) {
            logger.severe(
                    "Exception in addSondage from DAO : " + e.getMessage());
            jsonResponse = generateErrorStatus();
        }
        return jsonResponse;
    }

    /**
     * Récupère l'ID du professeur.
     * 
     * @param req La requête HTTP contenant l'ID.
     * @return L'ID du professeur.
     */
    private String getIdProf(HttpServletRequest req) {
        return (String) req.getAttribute("ensId");
    }
}
