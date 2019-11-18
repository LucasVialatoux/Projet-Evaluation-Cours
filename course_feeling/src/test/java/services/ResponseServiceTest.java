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

import dao.SondageDaoMockImpl;

public class ResponseServiceTest extends Mockito {
	
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
	public void getSondageNumeroCorrect() throws IOException, ServletException {
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
		when(req.getPathInfo()).thenReturn("/00000");
		when(resp.getWriter()).thenReturn(out);	
		
		service.doGet(req, resp);
		String response = sw.getBuffer().toString().trim();
		
		assert(response.contains("\"statut\":\"ok\""));
		assert(response.contains("\"matiere\":\"Sciences\""));
	}
	
	@Test
	public void getSondageNumeroIncorrect() throws IOException, ServletException {
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
		when(req.getPathInfo()).thenReturn("/01000");
		when(resp.getWriter()).thenReturn(out);

		service.doGet(req, resp);
		String response = sw.getBuffer().toString().trim();
		
		assert(response.contains("\"statut\":\"erreur\""));
	}
	
	@Test
	public void ajouterRessentiSondageExistant() throws IOException, ServletException {
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
		when(req.getPathInfo()).thenReturn("/00000");
		when(resp.getWriter()).thenReturn(out);
		when(req.getParameter("ressenti")).thenReturn("1");
		service.doPost(req, resp);
		String response = sw.getBuffer().toString().trim();
		
		assert(response.contains("\"statut\":\"ok\""));
	}
	
	@Test
	public void ajouterRessentiSondageNonExistant() throws IOException, ServletException {
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
		when(req.getPathInfo()).thenReturn("/00010");
		when(resp.getWriter()).thenReturn(out);

		service.doPost(req, resp);
		String response = sw.getBuffer().toString().trim();
		
		assert(response.contains("\"statut\":\"erreur\""));
	}
}
