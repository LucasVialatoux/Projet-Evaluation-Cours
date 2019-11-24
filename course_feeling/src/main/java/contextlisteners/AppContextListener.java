package contextlisteners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;

import dao.MatiereDao;
import dao.MatiereDaoImpl;
import dao.SondageDao;
import dao.SondageDaoImpl;
import dao.UtilisateurDao;
import dao.UtilisateurDaoImpl;
import services.AuthenticationService;
import services.GestionMatiereService;
import services.GestionSondageService;
import services.ResponseService;

/**
 * Initialise les servlets et les DAOs.
 * 
 * @author ajanperd
 *
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    SondageDao sondageDao;
    MatiereDao matiereDao;
    UtilisateurDao utilisateurDao;

    AuthenticationService authenticationService;
    ResponseService responseService;
    GestionSondageService gestionSondageService;
    GestionMatiereService gestionMatiereService;
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

        ServletRegistration registGestionMatiereService = ctx
                .addServlet("GestionMatiere", gestionMatiereService);
        registGestionMatiereService.addMapping("/ens/subjects/*");

        ServletRegistration registAuthenticationService = ctx
                .addServlet("AuthenticationService", authenticationService);
        registAuthenticationService.addMapping("/signup");
        registAuthenticationService.addMapping("/signin");
        registAuthenticationService.addMapping("/signout");
    }

    private void setupServicesAndDaos() {
        SondageDaoImpl sondageDaoImpl = new SondageDaoImpl();
        sondageDaoImpl.setDatasource(ds);
        sondageDao = sondageDaoImpl;

        UtilisateurDaoImpl utilisateurDaoImpl = new UtilisateurDaoImpl();
        utilisateurDaoImpl.setDatasource(ds);
        utilisateurDao = utilisateurDaoImpl;

        MatiereDaoImpl matiereDaoImpl = new MatiereDaoImpl();
        matiereDaoImpl.setDatasource(ds);
        matiereDao = matiereDaoImpl;

        responseService = new ResponseService();
        responseService.setSondageDao(sondageDao);

        gestionSondageService = new GestionSondageService();
        gestionSondageService.setSondageDao(sondageDao);

        gestionMatiereService = new GestionMatiereService();
        gestionMatiereService.setMatiereDao(matiereDao);

        authenticationService = new AuthenticationService();
        authenticationService.setUtilisateurDao(utilisateurDao);

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
