package services;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
		when(req.getPathInfo()).thenReturn("");
		when(req.getAttribute("ensId")).thenReturn("test@test.com");
		when(resp.getWriter()).thenReturn(out);	
		
		service.doGet(req, resp);
		String response = sw.getBuffer().toString().trim();
		
		assert(response.contains("\"statut\":\"ok\""));
	}

	@Test
	public void getMatiereSondageEnseignantPathNull() throws IOException, ServletException {
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
		when(req.getPathInfo()).thenReturn(null);
		when(req.getAttribute("ensId")).thenReturn("test@test.com");
		when(resp.getWriter()).thenReturn(out);	
		
		service.doGet(req, resp);
		String response = sw.getBuffer().toString().trim();
		
		assert(response.contains("\"statut\":\"erreur\""));
	}

	@Test
	public void getResultatMatiere() throws IOException, ServletException {
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
		when(req.getPathInfo()).thenReturn("/WEB/results");
		when(req.getAttribute("ensId")).thenReturn("test@test2.com");
		when(resp.getWriter()).thenReturn(out);	
		
		service.doGet(req, resp);
		String response = sw.getBuffer().toString().trim();
		
		assert(response.contains("\"statut\":\"ok\""));
		assert(response.contains("\"name\":\"WEB\""));
		assert(response.contains("\"id\":666"));
		assert(response.contains("\"date\":24112019"));
	}

	@Test
	public void getResultatMatiereMauvaiseURI() throws IOException, ServletException {
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
		when(req.getPathInfo()).thenReturn("/WEB/relts");
		when(req.getAttribute("ensId")).thenReturn("test@test2.com");
		when(resp.getWriter()).thenReturn(out);	
		
		service.doGet(req, resp);
		String response = sw.getBuffer().toString().trim();
		
		assert(response.contains("\"statut\":\"erreur\""));
	}

	// @Test
	// public void getResultatMatiereIdMatiereVide() throws IOException, ServletException {
	// 	StringWriter sw = new StringWriter();
	// 	PrintWriter out = new PrintWriter(sw);
	// 	when(req.getPathInfo()).thenReturn("//results");
	// 	when(req.getAttribute("ensId")).thenReturn("test@test2.com");
	// 	when(resp.getWriter()).thenReturn(out);	
		
	// 	service.doGet(req, resp);
	// 	String response = sw.getBuffer().toString().trim();
		
	// 	System.out.println("====>");
	// 	System.out.println(response);
	// 	assert(response.contains("\"statut\":\"erreur\""));
	// }

	@Test
	public void postAjoutDUneMatiere() throws IOException, ServletException {
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
		when(req.getPathInfo()).thenReturn("");
		when(req.getAttribute("ensId")).thenReturn("test@test.com");
		when(req.getParameter("subject")).thenReturn("LIFAP2");
		when(resp.getWriter()).thenReturn(out);	
		
		service.doPost(req, resp);
		String response = sw.getBuffer().toString().trim();
		
		assert(response.contains("\"statut\":\"ok\""));
	}

	@Test
	public void postAjoutDUneMatiereVide() throws IOException, ServletException {
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
		when(req.getPathInfo()).thenReturn("");
		when(req.getAttribute("ensId")).thenReturn("test@test.com");
		when(req.getParameter("subject")).thenReturn("");
		when(resp.getWriter()).thenReturn(out);	
		
		service.doPost(req, resp);
		String response = sw.getBuffer().toString().trim();
		
		assert(response.contains("\"statut\":\"erreur\""));
	}

	@Test
	public void postAjoutDUneMatiereNull() throws IOException, ServletException {
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
		when(req.getPathInfo()).thenReturn("");
		when(req.getAttribute("ensId")).thenReturn("test@test.com");
		when(req.getParameter("subject")).thenReturn(null);
		when(resp.getWriter()).thenReturn(out);	
		
		service.doPost(req, resp);
		String response = sw.getBuffer().toString().trim();
		
		assert(response.contains("\"statut\":\"erreur\""));
	}

	@Test
	public void deleteUneMatiere() throws IOException, ServletException {
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
		when(req.getPathInfo()).thenReturn("/WEB");
		when(req.getAttribute("ensId")).thenReturn("test@test.com");
		when(req.getParameter("subject")).thenReturn(null);
		when(resp.getWriter()).thenReturn(out);	
		
		service.doDelete(req, resp);
		String response = sw.getBuffer().toString().trim();
		
		assert(response.contains("\"statut\":\"ok\""));
	}

	// @Test
	// public void deleteUneMatierePathVide() throws IOException, ServletException {
	// 	StringWriter sw = new StringWriter();
	// 	PrintWriter out = new PrintWriter(sw);
	// 	when(req.getPathInfo()).thenReturn("");
	// 	when(req.getAttribute("ensId")).thenReturn("test@test.com");
	// 	when(req.getParameter("subject")).thenReturn(null);
	// 	when(resp.getWriter()).thenReturn(out);	
		
	// 	service.doDelete(req, resp);
	// 	String response = sw.getBuffer().toString().trim();
		
	// 	assert(response.contains("\"statut\":\"erreur\""));
	// }

	// @Test
	// public void deleteUneMatierePathNull() throws IOException, ServletException {
	// 	StringWriter sw = new StringWriter();
	// 	PrintWriter out = new PrintWriter(sw);
	// 	when(req.getPathInfo()).thenReturn(null);
	// 	when(req.getAttribute("ensId")).thenReturn("test@test.com");
	// 	when(req.getParameter("subject")).thenReturn(null);
	// 	when(resp.getWriter()).thenReturn(out);	
		
	// 	service.doDelete(req, resp);
	// 	String response = sw.getBuffer().toString().trim();
		
	// 	assert(response.contains("\"statut\":\"erreur\""));
	// }
}
