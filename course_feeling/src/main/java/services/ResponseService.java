package services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.JsonObject;

import business.Ressenti;
import dao.SondageDAO;

public class ResponseService extends HttpServlet {

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = 6012769876508579988L;
	
	private SondageDAO sondageDao;
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		setResponseHeaders(resp);
		String codeSondage = req.getPathInfo().split("/")[1];
		JsonObject jsonResponse = new JsonObject();
		if(req.getParameter("ressenti") != null && 
				sondageDao.ajouterRessenti(codeSondage, Ressenti.valueOf(req.getParameter("ressenti")))) {
			resp.setStatus(200);
			jsonResponse.addProperty("statut", "ok");
			
		} else {
			jsonResponse.addProperty("statut", "erreur");
		}
		out.print(jsonResponse.toString());
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		setResponseHeaders(resp);
		String codeSondage = req.getPathInfo().split("/")[1];
		String matiere = sondageDao.getMatiereOfSondage(codeSondage);
		JsonObject infosSondage = new JsonObject();
		if(matiere != null && !matiere.equals("")) {
			resp.setStatus(200);
			infosSondage.addProperty("statut", "ok");
			infosSondage.addProperty("matiere", matiere);
		} else {
			infosSondage.addProperty("statut", "erreur");
			
		}
		out.print(infosSondage.toString());
	}
	
	private void setResponseHeaders(HttpServletResponse resp) {
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");
	}
	
	public void setSondageDao(SondageDAO sondageDao) {
		this.sondageDao = sondageDao;
	}
}
