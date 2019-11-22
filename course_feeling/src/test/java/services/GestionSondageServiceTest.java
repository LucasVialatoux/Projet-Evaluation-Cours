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
import services.GestionMatiereService;

public class GestionSondageServiceTest extends Mockito {
	
	GestionSondageService service;
	
	@Mock
	HttpServletRequest req;
	
	@Mock
	HttpServletResponse resp;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new GestionSondageService();
		service.setSondageDao(new SondageDaoMockImpl());
	}
	
	@Test
	public void getCodeSondageCorrect() throws IOException, ServletException {
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
        when(req.getPathInfo()).thenReturn("/00000");
        when(req.getAttribute("ensId")).thenReturn("test@test.com");
        when(resp.getWriter()).thenReturn(out);	
		
		service.doGet(req, resp);
        String response = sw.getBuffer().toString().trim();
        
		assert(response.contains("\"statut\":\"ok\""));
        assert(response.contains("\"code\":\"AA999\""));
    }

    @Test
    public void getCodeSondageIncorrect() throws IOException, ServletException {
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
        when(req.getPathInfo()).thenReturn("/00000");
        when(req.getAttribute("id")).thenReturn("");
        when(resp.getWriter()).thenReturn(out);	
		
		service.doGet(req, resp);
		String response = sw.getBuffer().toString().trim();
        
		assert(response.contains("\"statut\":\"erreur\""));
    }

    @Test
	public void getResultatCodeSondageCorrect() throws IOException, ServletException {
        StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
        when(req.getPathInfo()).thenReturn("/00000/results");
        when(req.getAttribute("ensId")).thenReturn("test@test.com");
        when(resp.getWriter()).thenReturn(out);	

		service.doGet(req, resp);
        String response = sw.getBuffer().toString().trim();
    
        assert(response.contains("\"statut\":\"ok\""));
        assert(response.contains("\"result\":125"));
        assert(response.contains("\"result\":134"));
    }

    @Test
	public void getResultatCodeSondageIncorrect() throws IOException, ServletException {
        StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
        when(req.getPathInfo()).thenReturn("/00000/pasrslt");
        when(req.getAttribute("ensId")).thenReturn("test@test.com");
        when(resp.getWriter()).thenReturn(out);	

		service.doGet(req, resp);
        String response = sw.getBuffer().toString().trim();

        assert(response.contains("\"statut\":\"erreur\""));
    }

    @Test 
    public void postAjoutUnSondage() throws IOException, ServletException {
        StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
        when(req.getPathInfo()).thenReturn(null);
        when(req.getAttribute("id")).thenReturn("CPP");
        when(req.getParameter("id")).thenReturn("CPP");
        when(req.getAttribute("ensId")).thenReturn("test@test.com");
        when(resp.getWriter()).thenReturn(out);	

		service.doPost(req, resp);
        String response = sw.getBuffer().toString().trim();

        assert(response.contains("\"statut\":\"ok\""));
    }

    @Test 
    public void postAddSondage() throws IOException, ServletException {
        StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
        when(req.getPathInfo()).thenReturn("/12345");
        when(req.getAttribute("id")).thenReturn("CPP");
        when(req.getParameter("id")).thenReturn("CPP");
        when(req.getAttribute("ensId")).thenReturn("test@test.com");
        when(resp.getWriter()).thenReturn(out);	

		service.doPost(req, resp);
        String response = sw.getBuffer().toString().trim();

        assert(response.contains("\"statut\":\"ok\""));
        assert(response.contains("\"code\":\"98765\""));
    }

    @Test 
    public void postCreateCodeSondage() throws IOException, ServletException {
        StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
        when(req.getPathInfo()).thenReturn(null);
        when(req.getAttribute("id")).thenReturn("CPP");
        when(req.getParameter("id")).thenReturn("CPP");
        when(req.getAttribute("ensId")).thenReturn("test@test.com");
        when(resp.getWriter()).thenReturn(out);	

		service.doPost(req, resp);
        String response = sw.getBuffer().toString().trim();
        
        assert(response.contains("\"statut\":\"ok\""));
    }

    @Test
    public void postCreateMauvaisCodeSondage() throws IOException, ServletException {

    }
}
