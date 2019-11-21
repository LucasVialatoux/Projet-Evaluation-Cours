package services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
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
    private SondageDao sondageDao;

    private static final long serialVersionUID = -7794936188718493591L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String[] parametres = req.getRequestURI().split("/");
        String idProf = getIdProf(req);
        int idSondage = Integer.parseInt(parametres[3]);
        boolean resultats = (parametres.length == 5
                && parametres[4].equals("results"));
        JsonObject jsonResponse = null;
        if (resultats) {
            jsonResponse = getResultSondages(idProf, idSondage);
        } else {
            jsonResponse = getCodeSondage(idProf, idSondage);
        }
        resp.setStatus(200);
        out.print(jsonResponse.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String matiere = req.getParameter("id");
        String idProf = getIdProf(req);
        JsonObject jsonResponse = null;
        String idSondageString = req.getRequestURI().split("/")[3];
        if (idSondageString != null) { // To create a code
            try {
                int idSondage = Integer.parseInt(idSondageString);
                jsonResponse = createCodeSondage(idProf, idSondage);
            } catch (NullPointerException e) {
                jsonResponse = generateErrorStatus();
            }
        } else if (matiere != null && !matiere.equals("")) { // To add a new
                                                             // form
            jsonResponse = addSondage(idProf, matiere);
        } else {
            jsonResponse = generateErrorStatus();
        }
        resp.setStatus(200);
        out.print(jsonResponse.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String idProf = getIdProf(req);
        JsonObject jsonResponse = null;
        String idSondageString = req.getRequestURI().split("/")[3];
        if (idSondageString != null) { // To create a code
            try {
                int idSondage = Integer.parseInt(idSondageString);
                jsonResponse = deleteSondage(idProf, idSondage);
            } catch (NullPointerException e) {
                jsonResponse = generateErrorStatus();
            }
        }
        resp.setStatus(200);
        out.print(jsonResponse.toString());
    }

    public void setSondageDao(SondageDao sondageDao) {
        this.sondageDao = sondageDao;
    }

    private JsonObject getResultSondages(String idProf, int idSondage) {
        JsonObject response = null;
        try {
            ResultatSondage resultats = sondageDao.getResultat(idProf,
                    idSondage);
            response = generateSuccessStatus();
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
            response.add("poll", poll);
        } catch (SondageDaoException e) {
            response = generateErrorStatus();
        }
        return response;
    }

    private JsonObject getCodeSondage(String idProf, int idSondage) {
        JsonObject response = null;
        String codeSondage = null;
        try {
            codeSondage = sondageDao.getCode(idProf, idSondage);
            response = generateSuccessStatus();
            response.addProperty("code", codeSondage);
        } catch (SondageDaoException e) {
            response = generateErrorStatus();
        }
        return response;
    }

    private JsonObject deleteSondage(String idProf, int idSondage) {
        JsonObject response = null;
        try {
            sondageDao.supprimerSondage(idProf, idSondage);
            response = generateSuccessStatus();
        } catch (SondageDaoException e) {
            response = generateErrorStatus();
        }
        return response;
    }
    
    private JsonObject createCodeSondage(String idProf, int idSondage) {
        JsonObject response = null;
        String codeSondage = null;
        try {
            codeSondage = sondageDao.addCode(idProf, idSondage);
            response = generateSuccessStatus();
            response.addProperty("code", codeSondage);
        } catch (SondageDaoException e) {
            response = generateErrorStatus();
        }
        return response;
    }

    private JsonObject addSondage(String idProf, String matiere) {
        JsonObject jsonResponse = null;
        try {
            sondageDao.ajouterSondage(idProf, matiere);
            jsonResponse = generateSuccessStatus();
        } catch (SondageDaoException e) {
            jsonResponse = generateErrorStatus();
        }
        return jsonResponse;
    }

    private String getIdProf(HttpServletRequest req) {
        return null;
    }
}
