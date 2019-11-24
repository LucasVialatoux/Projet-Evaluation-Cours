package services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

public abstract class AbstractServlet extends HttpServlet {

    private static final long serialVersionUID = 2453774818147500647L;

    private static final Logger logger = Logger
            .getLogger(AbstractServlet.class.getName());

    protected JsonObject generateErrorStatus() {
        JsonObject j = new JsonObject();
        j.addProperty("statut", "erreur");
        return j;
    }

    protected JsonObject generateSuccessStatus() {
        JsonObject j = new JsonObject();
        j.addProperty("statut", "ok");
        return j;
    }

    protected void writeResponse(HttpServletResponse resp,
            JsonObject jsonResponse) {
        setResponseHeaders(resp);
        resp.setStatus(200);
        PrintWriter out;
        try {
            out = resp.getWriter();
            out.print(jsonResponse.toString());
        } catch (IOException e) {
            logger.severe("Couldn't write response");
        }

    }

    private void setResponseHeaders(HttpServletResponse response) {
        // response.addHeader("Access-Control-Allow-Origin", "*");
        // response.addHeader("Access-Control-Allow-Headers","*");
        response.addHeader("Access-Control-Allow-Methods",
                "GET, PUT, POST, DELETE, OPTIONS");
        response.setContentType("text/plain");
    }
}
