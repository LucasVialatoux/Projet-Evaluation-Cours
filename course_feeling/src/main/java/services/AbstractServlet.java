package services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

public abstract class AbstractServlet extends HttpServlet {

    private static final long serialVersionUID = 2453774818147500647L;

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

        }

    }

    private void setResponseHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods",
                "GET, PUT, POST, DELETE, OPTIONS");
        response.setContentType("application/json");
    }
}
