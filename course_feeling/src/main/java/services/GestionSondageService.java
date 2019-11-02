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
        int idSondage = Integer.parseInt(parametres[3]);
        boolean resultats = (parametres.length == 5
                && parametres[4].equals("results"));
        JsonObject jsonResponse = null;
        if (resultats) {
            jsonResponse = getResultSondages(idSondage);
        } else {
            jsonResponse = getCodeSondage(idSondage);
        }
        resp.setStatus(200);
        out.print(jsonResponse.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String matiere = req.getParameter("id");
        String idProf = null;
        JsonObject jsonResponse = null;
        if (matiere != null && !matiere.equals("")) {
            jsonResponse = addSondage(idProf, matiere);
        } else {
            jsonResponse = generateErrorStatus();
        }
        resp.setStatus(200);
        out.print(jsonResponse.toString());
    }

    public void setSondageDao(SondageDao sondageDao) {
        this.sondageDao = sondageDao;
    }

    private JsonObject getResultSondages(int idSondage) {
        JsonObject response = new JsonObject();
        try {
            ResultatSondage resultats = sondageDao
                    .getResultat(idSondage);
            response.addProperty("statut", "ok");
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
            e.printStackTrace();
            response = generateErrorStatus();
        }
        return response;
    }

    private JsonObject getCodeSondage(int idSondage) {
        JsonObject response = null;
        String codeSondage = null;
        try {
            codeSondage = sondageDao.getCode(idSondage);
            response = new JsonObject();
            response.addProperty("statut", "ok");
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
            jsonResponse = new JsonObject();
            jsonResponse.addProperty("statut", "ok");
        } catch (SondageDaoException e) {
            jsonResponse = generateErrorStatus();
        } 
        return jsonResponse;
    }
}
