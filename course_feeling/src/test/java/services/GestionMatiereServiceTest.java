package services;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import dao.MatiereDaoMockImpl;
import services.GestionMatiereService;

public class GestionMatiereServiceTest extends Mockito {
	
	GestionMatiereService service;
	
	@Mock
	HttpServletRequest req;
	
	@Mock
	HttpServletResponse resp;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new GestionMatiereService();
		service.setMatiereDao(new MatiereDaoMockImpl());
	}
	
	@Test
	public void getMatiereSondageEnseignant() throws IOException, ServletException {
		// StringWriter sw = new StringWriter();
		// PrintWriter out = new PrintWriter(sw);
		// when(req.getPathInfo()).thenReturn("");
		// when(req.getAttribute("ensId")).thenReturn("test@test.com");
		// when(resp.getWriter()).thenReturn(out);	
		
		// service.doGet(req, resp);
		// String response = sw.getBuffer().toString().trim();
		
		// System.out.println("=======>");
		// System.out.println(response);
		// //assert(response.contains("\"statut\":\"ok\""));
	}
}
