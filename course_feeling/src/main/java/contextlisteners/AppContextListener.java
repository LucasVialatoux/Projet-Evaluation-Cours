package contextlisteners;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
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
import filters.AuthenticationFilter;
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

    AuthenticationFilter authenticationFilter;

    AuthenticationService authenticationService;
    ResponseService responseService;
    GestionSondageService gestionSondageService;
    GestionMatiereService gestionMatiereService;
    DataSource ds;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);
        setupDatasource();
        setupDaos();
        setupFilters();
        setupServices();
        setupRegistrations(sce);
    }

    private void setupDaos() {
        SondageDaoImpl sondageDaoImpl = new SondageDaoImpl();
        sondageDaoImpl.setDatasource(ds);
        sondageDao = sondageDaoImpl;

        UtilisateurDaoImpl utilisateurDaoImpl = new UtilisateurDaoImpl();
        utilisateurDaoImpl.setDatasource(ds);
        utilisateurDao = utilisateurDaoImpl;

        MatiereDaoImpl matiereDaoImpl = new MatiereDaoImpl();
        matiereDaoImpl.setDatasource(ds);
        matiereDao = matiereDaoImpl;
    }

    private void setupServices() {
        responseService = new ResponseService();
        responseService.setSondageDao(sondageDao);

        gestionSondageService = new GestionSondageService();
        gestionSondageService.setSondageDao(sondageDao);

        gestionMatiereService = new GestionMatiereService();
        gestionMatiereService.setMatiereDao(matiereDao);

        authenticationService = new AuthenticationService();
        authenticationService.setUtilisateurDao(utilisateurDao);

    }

    private void setupFilters() {
        authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setUtilisateurDao(utilisateurDao);
    }

    private void setupDatasource() {
        PGSimpleDataSource datasource = new PGSimpleDataSource();
        datasource.setServerName("localhost");
        datasource.setDatabaseName("course_feeling");
        datasource.setUser("course_feeling");
        datasource.setPassword("");
        ds = datasource;
    }

    private void setupRegistrations(ServletContextEvent sce) {
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

        FilterRegistration registAuthenticationFilter = ctx
                .addFilter("AuthenticationFilter", authenticationFilter);
        registAuthenticationFilter.addMappingForUrlPatterns(
                EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD),
                true, "/ens/*");
    }
}
