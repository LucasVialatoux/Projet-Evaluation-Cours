package filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import dao.UtilisateurDao;
import dao.UtilisateurDaoException;
import services.TokenProvider;

public class AuthenticationFilter implements Filter {

    private static UtilisateurDao utilisateurDao;

    private static final Logger logger = Logger
            .getLogger(AuthenticationFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String token = req.getHeader("Authorization");
        TokenProvider tp = new TokenProvider();
        if (token != null) {
            try {
                String idProf = utilisateurDao.getEmail(token);
                if (idProf != null && !idProf.equals("")
                        && !tp.expiredToken(token)) {
                    req.setAttribute("ensId", idProf);
                    chain.doFilter(request, response);
                } else if (idProf == null || idProf.equals("")) {
                    logger.severe("Null or empty email");
                } else {
                    utilisateurDao.supprimerToken(idProf);
                    logger.severe("Expired token");
                }
            } catch (UtilisateurDaoException e) {
                logger.severe("Authentication failed : error in UtilisateurDAO"
                        + e.getMessage());
                sendError(resp);
            }

        } else {
            logger.severe("Authentication failed : no token found");
            sendError(resp);
        }

    }

    public void setUtilisateurDao(UtilisateurDao utilisateurDao) {
        AuthenticationFilter.utilisateurDao = utilisateurDao;
    }

    private void sendError(HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        JsonObject jsonResponse = generateInvalidTokenStatus();
        out.print(jsonResponse.toString());
    }

    private JsonObject generateInvalidTokenStatus() {
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("statut", "invalidToken");
        return jsonResponse;
    }
}
