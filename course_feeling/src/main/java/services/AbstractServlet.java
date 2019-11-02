package services;

import javax.servlet.http.HttpServlet;

import com.google.gson.JsonObject;

public abstract class AbstractServlet extends HttpServlet {

    private static final long serialVersionUID = 2453774818147500647L;

    protected JsonObject generateErrorStatus() {
        JsonObject j = new JsonObject();
        j.addProperty("statut", "erreur");
        return j;
    }
}
