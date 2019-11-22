package services;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import dao.SondageDaoMockImpl;

import services.AbstractServlet;

public class AbstractServletTest extends Mockito {
	
	ResponseService service;
	
	@Mock
	HttpServletRequest req;
	
	@Mock
	HttpServletResponse resp;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new ResponseService();
		service.setSondageDao(new SondageDaoMockImpl());
	}
	
	@Test
	public void generateErrorStatusTest() throws IOException, ServletException {
		AbstractServlet absErreur = new AbstractServlet() {};
		JsonObject jErreur = new JsonObject();
		jErreur.addProperty("statut", "erreur");
		assertEquals(jErreur, absErreur.generateErrorStatus());
	}

	public void generateSuccessStatusTest() throws IOException, ServletException {
		AbstractServlet absSucces = new AbstractServlet() {};
		JsonObject jSuccess = new JsonObject();
		jSuccess.addProperty("statut", "ok");
		assertEquals(jSuccess, absSucces.generateErrorStatus());
	}
}
