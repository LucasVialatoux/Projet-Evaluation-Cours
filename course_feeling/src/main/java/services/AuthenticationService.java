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


@WebServlet(name = "AuthenticationService", urlPatterns = "/login")
public class AuthenticationService extends HttpServlet {

    private UtilisateurDaoImpl utilisateurDao;
    private String token = "";
    private TokenClass tokenClass;
    private String url = "";
    Jws<Claims> claimsJws;

    @Override
    public void init() throws ServletException {
        super.init();
        utilisateurDao = new UtilisateurDaoImpl();
        tokenClass = new TokenClass();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String password = DigestUtils.sha256Hex(request.getParameter("inputMdp"));
        //String password = request.getParameter("inputMdp");


        if (request.getParameter("inputID").equals(utilisateurDao.getEmail())
                && password.equals(utilisateurDao.getMdpHash(request.getParameter("inputID")))) {
            this.token = utilisateurDao.getToken();

            JsonObject jsonResponse = new JsonObject();
            if ((this.token.equals("")) || !tokenClass.isValidToken(this.token)) {

                this.token =  tokenClass.createToken(request.getParameter("email"));
                utilisateurDao.stockerToken(request.getParameter("email"), this.token);
                this.claimsJws = tokenClass.validateJwtToken(this.token);

                Cookie cookieToken = new Cookie("token", this.token);
                cookieToken.setMaxAge(60 * 60);
                //add the cookie to the  response
                response.addCookie(cookieToken);

                jsonResponse.addProperty("token", this.token);
                jsonResponse.addProperty("statut", "ok");
                setResponseHeaders(response);

               // url = "/result.html?signature=" + token;

                this.getServletContext().getRequestDispatcher(url).forward(request, response);

            } else {
                this.claimsJws = tokenClass.validateJwtToken(token);

                jsonResponse.addProperty("token", this.token);
                jsonResponse.addProperty("statut", "ok");
                setResponseHeaders(response);

                this.getServletContext().getRequestDispatcher(url).forward(request, response);
            }
        } else {
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("statut", "erreur");

        //    url = "/result.html?signature=" + token;
            this.getServletContext().getRequestDispatcher(url).forward(request, response);
            setResponseHeaders(response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       // url = "/result.html?signature=" + token;
        response.sendRedirect(url);
    }

    private void setResponseHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");
    }
}



