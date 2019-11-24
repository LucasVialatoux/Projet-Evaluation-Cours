package services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.JsonObject;

import business.Ressenti;
import dao.SondageDao;
import dao.SondageDaoException;

public class ResponseService extends HttpServlet {

    /**
     * Generated serialVersionUID.
     */
    private static final long serialVersionUID = 6012769876508579988L;

    private static SondageDao sondageDao;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        setResponseHeaders(resp);
        String codeSondage = req.getPathInfo().split("/")[1];
        JsonObject jsonResponse = new JsonObject();
        if (req.getParameter("ressenti") != null) {
            try {
                sondageDao.ajouterRessenti(codeSondage,
                        Ressenti.values()[Integer
                                .parseInt(req.getParameter("ressenti"))]);
                resp.setStatus(200);
                jsonResponse.addProperty("statut", "ok");
            } catch (NumberFormatException | SondageDaoException e) {
                e.printStackTrace();
                jsonResponse.addProperty("statut", "erreur");
            }
        } else {
            jsonResponse.addProperty("statut", "erreur");
        }
        out.print(jsonResponse.toString());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        setResponseHeaders(resp);
        String codeSondage = req.getPathInfo().split("/")[1];
        String matiere = null;
        try {
            matiere = sondageDao.getMatiereOfSondage(codeSondage);
        } catch (SondageDaoException e) {
            e.printStackTrace();
        }
        JsonObject infosSondage = new JsonObject();
        if (matiere != null && !matiere.equals("")) {
            resp.setStatus(200);
            infosSondage.addProperty("statut", "ok");
            infosSondage.addProperty("matiere", matiere);
        } else {
            infosSondage.addProperty("statut", "erreur");
        }
        PrintWriter out = resp.getWriter();
        out.print(infosSondage.toString());
    }

    private void setResponseHeaders(HttpServletResponse resp) {
        //resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods",
                "GET, PUT, POST, DELETE, OPTIONS");
        resp.setContentType("text/plain");
    }

    public void setSondageDao(SondageDao sondageDao) {
        ResponseService.sondageDao = sondageDao;
    }
}
