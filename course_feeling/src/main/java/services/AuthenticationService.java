package services;


import dao.UtilisateurDaoImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.JsonObject;
import org.apache.commons.codec.digest.DigestUtils;


@WebServlet(name = "AuthenticationService", urlPatterns = {"/signin","/signup","/signout"})
public class AuthenticationService extends HttpServlet {

    private UtilisateurDaoImpl utilisateurDao;
    private String token = "";
    private TokenProvider tokenProvider;
    Jws<Claims> claimsJws;

    @Override
    public void init() throws ServletException {
        super.init();
        utilisateurDao = new UtilisateurDaoImpl();
        tokenProvider = new TokenProvider();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] path = request.getRequestURI().split("/");
        PrintWriter out = response.getWriter();

        if (path.length > 2) {

            if (path[2].equals("signin")) {
                String password = DigestUtils.sha256Hex(request.getParameter("inputMdp"));
                String email = request.getParameter("inputID");
                if (utilisateurDao.isExist(email) && password.equals(
                                utilisateurDao.getMdpHash(email))) {
                    this.token = tokenProvider.createToken(
                            request.getParameter("inputID"));
                    JsonObject jsonResponse = new JsonObject();
                    if (token != null) {
                        utilisateurDao.stockerToken(email, token);
                        jsonResponse.addProperty("token", token);
                        jsonResponse.addProperty("statut", "ok");
                        response.setHeader("Authorization", token);
                        setResponseHeaders(response);
                        out.print(jsonResponse.toString());
                    } else {
                        jsonResponse.addProperty("statut", "error");
                        jsonResponse.addProperty("error", "Token : impossible de créer le token");
                        out.print(jsonResponse.toString());
                        setResponseHeaders(response);
                        out.print(jsonResponse.toString());
                    }
                } else {
                    JsonObject jsonResponse = new JsonObject();
                    jsonResponse.addProperty("statut", "error");
                    jsonResponse.addProperty("error", "identifiant incorrect");
                    setResponseHeaders(response);
                    out.print(jsonResponse.toString());
                }
            }
            if (path[2].equals("signup")) {
                String email = request.getParameter("inputID");
                String password = request.getParameter("inputMdp1");
                if (!utilisateurDao.isExist(email)) {
                    String passwordHash = DigestUtils.sha256Hex(password);
                    this.token =  tokenProvider.createToken(email);
                    utilisateurDao.ajouterUtilisateur(email,passwordHash,token);
                    JsonObject jsonResponse = new JsonObject();
                    jsonResponse.addProperty("statut", "ok");
                    jsonResponse.addProperty("token", token);
                    response.setHeader("Authorization", token);
                    setResponseHeaders(response);
                    out.print(jsonResponse.toString());
                } else {
                    JsonObject jsonResponse = new JsonObject();
                    jsonResponse.addProperty("statut", "emailAlreadyUsed");
                    jsonResponse.addProperty("error", "email déja utilisé");
                    setResponseHeaders(response);
                    out.print(jsonResponse.toString());
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] path = request.getRequestURI().split("/");
        PrintWriter out = response.getWriter();
        if (path.length > 2) {
            if (path[2].equals("signout")) {
                String tokenFromAutorization = request.getHeader("Authorization");
                JsonObject jsonResponse = new JsonObject();
                if (tokenFromAutorization != null) {
                    Jws<Claims> claimsJws =
                            tokenProvider.validateJwtToken(tokenFromAutorization);
                    String email = (String) claimsJws.getBody().get("email");
                    utilisateurDao.supprimerToken(email);
                    jsonResponse.addProperty("statut", "ok");
                    setResponseHeaders(response);
                    out.print(jsonResponse.toString());
                } else {
                    jsonResponse.addProperty("statut", "error");
                    jsonResponse.addProperty("error", "error autorization");
                    setResponseHeaders(response);
                    out.print(jsonResponse.toString());
                }
            }
        }
    }

    private void setResponseHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");
        response.setContentType("application/json");
    }
}



