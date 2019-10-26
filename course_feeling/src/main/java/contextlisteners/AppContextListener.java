package contextlisteners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

import business.Ressenti;
import dao.SondageDAO;
import dao.SondageDAOImpl;
import services.ResponseService;


@WebListener
public class AppContextListener implements ServletContextListener {
	
	SondageDAO sondageDao;
	ResponseService responseService;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContextListener.super.contextInitialized(sce);
		setupServicesAndDAOs();
		ServletContext ctx = sce.getServletContext();
		ServletRegistration regist = ctx.addServlet("Response", responseService);
		regist.addMapping("/sondage/*");
	}
	
	private void setupServicesAndDAOs() {
		responseService = new ResponseService();
		sondageDao = new SondageDAOImpl();
		responseService.setSondageDao(sondageDao);
	}
}
