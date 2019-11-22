package services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

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

    public void setMatiereDao(MatiereDao matiereDao) {
        GestionMatiereService.matiereDao = matiereDao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String[] parametres = req.getRequestURI().split("/");
        String idProf = getIdProf(req);
        String idMatiere = parametres[3];
        boolean resultats = (parametres.length == 5
                && parametres[4].equals("results"));
        JsonObject jsonResponse = null;
        if (resultats) {
            jsonResponse = getResultsMatiere(idProf, idMatiere);
        } else {
            jsonResponse = getMatieres(idProf);
        }
        resp.setStatus(200);
        out.print(jsonResponse.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String matiere = req.getParameter("subject");
        String idProf = getIdProf(req);
        JsonObject jsonResponse = null;
        if (matiere != null && !matiere.equals("")) { // To create a code
            jsonResponse = addMatiere(idProf, matiere);
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
        String[] parametres = req.getRequestURI().split("/");
        String matiere = parametres[3];
        String idProf = getIdProf(req);
        JsonObject jsonResponse = null;
        if (matiere != null && !matiere.equals("")) { // To create a code
            jsonResponse = deleteMatiere(idProf, matiere);
        } else {
            jsonResponse = generateErrorStatus();
        }
        resp.setStatus(200);
        out.print(jsonResponse.toString());
    }

    private JsonObject deleteMatiere(String idProf, String matiere) {
        JsonObject response = null;
        try {
            matiereDao.supprimerMatiere(idProf, matiere);
            response = generateSuccessStatus();
            response.addProperty("id", matiere);
        } catch (MatiereDaoException e) {
            response = generateErrorStatus();
        }
        return response;
    }

    private String getIdProf(HttpServletRequest req) {
        return req.getParameter("id");
    }

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
            response = generateErrorStatus();
        }
        return response;
    }

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
            response = generateErrorStatus();
        }
        return response;
    }

    private JsonObject addMatiere(String idProf, String nomMatiere) {
        JsonObject response = null;
        try {
            matiereDao.ajoutMatiere(idProf, nomMatiere);
            response = generateSuccessStatus();
            response.addProperty("id", nomMatiere);
        } catch (MatiereDaoException e) {
            response = generateErrorStatus();
        }
        return response;
    }
}
