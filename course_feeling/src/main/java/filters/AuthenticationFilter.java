package filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import dao.UtilisateurDao;
import dao.UtilisateurDaoException;

@WebFilter(filterName = "AuthenticationFilter", urlPatterns = "/ens/*")
public class AuthenticationFilter implements Filter {

    private static UtilisateurDao utilisateurDao;

    private static final Logger logger = Logger.getLogger(AuthenticationFilter.class.getName());
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String token = req.getHeader("Authorization");
        if (token != null) {
            try {
                String idProf = utilisateurDao.getEmail(token);
                req.setAttribute("ensId", idProf);
                chain.doFilter(request, response);
            } catch (UtilisateurDaoException e) {
                logger.severe("Authentication failed : error in UtilisateurDAO");
                sendError(resp);
            }
        } else {
            logger.severe("Authentication failed : no token found");
            sendError(resp);
        }

    }

    public static void setUtilisateurDao(UtilisateurDao utilisateurDao) {
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
