package services;

import dao.UtilisateurDao;
import dao.UtilisateurDaoException;
import dao.UtilisateurDaoImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.security.SignatureException;

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

public class AuthenticationService extends AbstractServlet {

    private UtilisateurDao utilisateurDao;

    private TokenProvider tokenProvider;

    @Override
    public void init() throws ServletException {
        tokenProvider = new TokenProvider();
    }

    public void setUtilisateurDao(UtilisateurDao utilisateurDao) {
        this.utilisateurDao = utilisateurDao;
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String[] path = request.getRequestURI().split("/");
        JsonObject jsonResponse = null;

        if (path.length > 2) {
            if (path[2].equals("signin")) {
                String passwordHash = DigestUtils
                        .sha256Hex(request.getParameter("inputMdp"));
                String email = request.getParameter("inputID");
                jsonResponse = signin(email, passwordHash, response);
            } else if (path[2].equals("signup")) {
                String email = request.getParameter("inputID");
                String password = request.getParameter("inputMdp1");
                jsonResponse = signup(email, password, response);
            } else {
                jsonResponse = generateErrorStatus();
            }
        } else {
            jsonResponse = generateErrorStatus();
        }
        writeResponse(response, jsonResponse);
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String[] path = request.getRequestURI().split("/");
        JsonObject jsonResponse = null;
        if (path.length == 2 && path[1].equals("signout")) {
            String token = request.getHeader("Authorization");
            jsonResponse = signout(token);
        } else {
            jsonResponse = generateErrorStatus();
        }
        writeResponse(response, jsonResponse);
    }

    private JsonObject signout(String token) {
        JsonObject jsonResponse = null;
        if (token != null) {
            try {
                Jws<Claims> claimsJws = tokenProvider.validateJwtToken(token);
                String email = (String) claimsJws.getBody().get("email");
                utilisateurDao.supprimerToken(email);
                jsonResponse = generateSuccessStatus();
            } catch (ServletException | UtilisateurDaoException e) {
                jsonResponse = generateErrorStatus();
            }
        } else {
            jsonResponse = generateErrorStatus();
        }
        return jsonResponse;
    }

    private JsonObject signin(String email, String passwordHash,
            HttpServletResponse response) {
        JsonObject jsonResponse = null;
        try {
            if (utilisateurDao.isExist(email)
                    && passwordHash.equals(utilisateurDao.getMdpHash(email))) {
                String token = tokenProvider.createToken(email);
                if (token != null) {
                    utilisateurDao.stockerToken(email, token);
                    response.setHeader("Authorization", token);
                    jsonResponse = generateSuccessStatus();
                    jsonResponse.addProperty("token", token);
                } else {
                    jsonResponse = generateErrorStatus();
                }
            } else {
                jsonResponse = generateErrorStatus();
            }
        } catch (UtilisateurDaoException e) {
            jsonResponse = generateErrorStatus();
        }
        return jsonResponse;
    }

    private JsonObject signup(String email, String password,
            HttpServletResponse response) {
        JsonObject jsonResponse = null;
        try {
            if (!utilisateurDao.isExist(email)) {
                String passwordHash = DigestUtils.sha256Hex(password);
                String token = tokenProvider.createToken(email);
                try {
                    utilisateurDao.ajouterUtilisateur(email, passwordHash,
                            token);
                    jsonResponse = generateSuccessStatus();
                    jsonResponse.addProperty("token", token);
                    response.setHeader("Authorization", token);
                } catch (UtilisateurDaoException e) {
                    jsonResponse = generateErrorStatus();
                }
            } else {
                jsonResponse = generateErrorStatus();
            }
        } catch (UtilisateurDaoException e) {
            jsonResponse = generateErrorStatus();
        }
        return jsonResponse;
    }
}
