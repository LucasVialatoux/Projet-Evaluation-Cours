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
import java.util.logging.Logger;

import com.google.gson.JsonObject;
import org.apache.commons.codec.digest.DigestUtils;

public class AuthenticationService extends AbstractServlet {

    private UtilisateurDao utilisateurDao;

    private TokenProvider tokenProvider;

    private static final Logger logger = Logger
            .getLogger(AuthenticationService.class.getName());

    @Override
    public void init() throws ServletException {
        tokenProvider = new TokenProvider();
    }

    public void setUtilisateurDao(UtilisateurDao utilisateurDao) {
        this.utilisateurDao = utilisateurDao;
    }

    /**
     * Gère le signIn (connexion) ou le SignUp (inscription).
     * 
     * @return Un JSON contenant le statut
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String[] path = request.getRequestURI().split("/");
        JsonObject jsonResponse = null;

        if (path.length == 2) {
            if (path[1].equals("signin")) {
                String passwordHash = DigestUtils
                        .sha256Hex(request.getParameter("password"));
                String email = request.getParameter("email");
                jsonResponse = signin(email, passwordHash, response);
            } else if (path[1].equals("signup")) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                jsonResponse = signup(email, password, response);
            } else {
                logger.severe("Error : doPost invalid : invalid attribute");
                jsonResponse = generateErrorStatus();
            }
        } else {
            logger.severe(
                    "Error : doPost invalid : path with unexpected length");
            jsonResponse = generateErrorStatus();
        }
        writeResponse(response, jsonResponse);
    }

    /**
     * Gère la deconnexion.
     * 
     * @return Un JSON contenant le statut
     */
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
                logger.severe("Can't signout user : Exception in token "
                        + " validation or DAO");
                jsonResponse = generateErrorStatus();
            }
        } else {
            logger.severe("Can't signout user : token not found");
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
                    logger.severe("Can't signin user : token null");
                    jsonResponse = generateErrorStatus();
                }
            } else {
                logger.severe("Can't signin user : user not found");
                jsonResponse = generateErrorStatus();
            }
        } catch (UtilisateurDaoException e) {
            logger.severe("Can't signin user : exception in DAO");
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
                    logger.severe("Can't signup user : Exception in DAO");
                    jsonResponse = generateErrorStatus();
                }
            } else {
                logger.severe("Can't signup user : user exists");
                jsonResponse = generateEmailAlreadyUsedStatus();
            }
        } catch (UtilisateurDaoException e) {
            logger.severe("Can't signup user : Exception in DAO");
            jsonResponse = generateErrorStatus();
        }
        return jsonResponse;
    }
    
    private JsonObject generateEmailAlreadyUsedStatus() {
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("statut", "emailAlreadyUsed");
        return jsonResponse;
    }
}
