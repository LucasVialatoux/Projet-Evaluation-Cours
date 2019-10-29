package contextlisteners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;

import dao.SondageDAO;
import dao.SondageDAOImpl;
import services.ResponseService;


@WebListener
public class AppContextListener implements ServletContextListener {

    SondageDAO sondageDao;
    ResponseService responseService;
    DataSource ds;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);
        setupDatasource();
        setupServicesAndDAOs();
        ServletContext ctx = sce.getServletContext();
        ServletRegistration regist = ctx.addServlet("Response", responseService);
        regist.addMapping("/sondage/*");
    }

    private void setupServicesAndDAOs() {
        SondageDAOImpl dao = new SondageDAOImpl();
        dao.setDatasource(ds);
        sondageDao = dao;

        responseService = new ResponseService();
        responseService.setSondageDao(sondageDao);
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
