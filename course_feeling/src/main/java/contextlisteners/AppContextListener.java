package contextlisteners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;

import dao.SondageDao;
import dao.SondageDaoImpl;
import services.GestionSondageService;
import services.ResponseService;

@WebListener
public class AppContextListener implements ServletContextListener {

    SondageDao sondageDao;
    ResponseService responseService;
    GestionSondageService gestionSondageService;
    DataSource ds;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);
        setupDatasource();
        setupServicesAndDaos();
        ServletContext ctx = sce.getServletContext();

        ServletRegistration registResponseService = ctx.addServlet("Response",
                responseService);
        registResponseService.addMapping("/sondage/*");

        ServletRegistration registGestionSondageService = ctx
                .addServlet("GestionSondage", gestionSondageService);
        registGestionSondageService.addMapping("/ens/poll/*");
    }

    private void setupServicesAndDaos() {
        SondageDaoImpl dao = new SondageDaoImpl();
        dao.setDatasource(ds);
        sondageDao = dao;

        responseService = new ResponseService();
        responseService.setSondageDao(sondageDao);

        gestionSondageService = new GestionSondageService();
        gestionSondageService.setSondageDao(sondageDao);
    }

    private void setupDatasource() {
        PGSimpleDataSource datasource = new PGSimpleDataSource();
        datasource.setServerName("localhost");
        datasource.setDatabaseName("course_feeling");
        datasource.setUser("course_feeling");
        datasource.setPassword("");
        ds = datasource;
    }
}
