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

import dao.UtilisateurDaoMockImpl;
import services.AuthenticationService;

public class AuthentificationServiceTest extends Mockito {
	
	AuthenticationService service;
	
	@Mock
	HttpServletRequest req;
	
	@Mock
	HttpServletResponse resp;
	
	@Before
	public void setUp() throws IOException, ServletException {
		MockitoAnnotations.initMocks(this);
        service = new AuthenticationService();
        service.init();
		service.setUtilisateurDao(new UtilisateurDaoMockImpl());
    }

    @Test
	public void postSignIn() throws IOException, ServletException {
		StringWriter sw = new StringWriter();
        PrintWriter out = new PrintWriter(sw);
        when(req.getRequestURI()).thenReturn("/signin");
        when(req.getParameter("password")).thenReturn("123");
		when(req.getParameter("email")).thenReturn("test@test.test");
		when(resp.getWriter()).thenReturn(out);	
		
		service.doPost(req, resp);
		String response = sw.getBuffer().toString().trim();
        
		assert(response.contains("\"statut\":\"ok\""));
    }

    @Test
	public void postSignInSansPassword() throws IOException, ServletException {
		StringWriter sw = new StringWriter();
        PrintWriter out = new PrintWriter(sw);
        when(req.getRequestURI()).thenReturn("/signin");
        when(req.getParameter("password")).thenReturn("");
		when(req.getParameter("email")).thenReturn("test@test.test");
		when(resp.getWriter()).thenReturn(out);	
		
		service.doPost(req, resp);
		String response = sw.getBuffer().toString().trim();
        
		assert(response.contains("\"statut\":\"erreur\""));
    }

    @Test
	public void postSignInSansEmail() throws IOException, ServletException {
		StringWriter sw = new StringWriter();
        PrintWriter out = new PrintWriter(sw);
        when(req.getRequestURI()).thenReturn("/signin");
        when(req.getParameter("password")).thenReturn("123");
		when(req.getParameter("email")).thenReturn("");
		when(resp.getWriter()).thenReturn(out);	
		
		service.doPost(req, resp);
		String response = sw.getBuffer().toString().trim();
        
		assert(response.contains("\"statut\":\"erreur\""));
    }

    @Test
	public void postPathLengthSuperieurDeux() throws IOException, ServletException {
		StringWriter sw = new StringWriter();
        PrintWriter out = new PrintWriter(sw);
        when(req.getRequestURI()).thenReturn("/signin/signErreur");
        when(req.getParameter("password")).thenReturn("123");
		when(req.getParameter("email")).thenReturn("test@test.test");
		when(resp.getWriter()).thenReturn(out);	
		
		service.doPost(req, resp);
		String response = sw.getBuffer().toString().trim();
        
		assert(response.contains("\"statut\":\"erreur\""));
    }
      
    @Test
	public void postSignUp() throws IOException, ServletException {
		StringWriter sw = new StringWriter();
        PrintWriter out = new PrintWriter(sw);
        when(req.getRequestURI()).thenReturn("/signup");
        when(req.getParameter("password")).thenReturn("123");
		when(req.getParameter("email")).thenReturn("test@test2.test");
		when(resp.getWriter()).thenReturn(out);	
		
		service.doPost(req, resp);
		String response = sw.getBuffer().toString().trim();
        
		assert(response.contains("\"statut\":\"ok\""));
    }

    @Test
	public void postSignUpDejaInscrit() throws IOException, ServletException {
		StringWriter sw = new StringWriter();
        PrintWriter out = new PrintWriter(sw);
        when(req.getRequestURI()).thenReturn("/signup");
        when(req.getParameter("password")).thenReturn("123");
		when(req.getParameter("email")).thenReturn("test@test.test");
		when(resp.getWriter()).thenReturn(out);	
		
		service.doPost(req, resp);
		String response = sw.getBuffer().toString().trim();
        
		assert(response.contains("\"statut\":\"emailAlreadyUsed\""));
    }

    @Test
	public void postMauvaispath1() throws IOException, ServletException {
		StringWriter sw = new StringWriter();
        PrintWriter out = new PrintWriter(sw);
        when(req.getRequestURI()).thenReturn("/glouglouglou");
        when(req.getParameter("password")).thenReturn("123");
		when(req.getParameter("email")).thenReturn("test@test.test");
		when(resp.getWriter()).thenReturn(out);	
		
		service.doPost(req, resp);
		String response = sw.getBuffer().toString().trim();
        
		assert(response.contains("\"statut\":\"erreur\""));
    }

	@Test
	public void getSignOut() throws IOException, ServletException {
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
		when(req.getRequestURI()).thenReturn("/signout");
        when(req.getHeader("Authorization"))
            .thenReturn("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" 
                + ".eyJlbWFpbCI6InRlc3RAdGVzdC5jb20ifQ" 
                + ".QSl7ijFZ06CAPSRQrUfa6sHxcNLTcr9Kxc6j44sZp20");
		when(resp.getWriter()).thenReturn(out);	
		
		service.doGet(req, resp);
        String response = sw.getBuffer().toString().trim();
        
		assert(response.contains("\"statut\":\"ok\""));
    }
    
    @Test
	public void getSignOutMauvaisToken() throws IOException, ServletException {
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
		when(req.getRequestURI()).thenReturn("/signout");
        when(req.getHeader("Authorization"))
            .thenReturn("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" 
            + ".eyJlbWFpbCI6InRlc3RAdGVzdC5jb20ifQ" 
            + ".QSl7ijFZ06CAPSRQrUfa6sHxcNLTcr9Kxc6j44sZp2");
		when(resp.getWriter()).thenReturn(out);	
		
		service.doGet(req, resp);
		String response = sw.getBuffer().toString().trim();

		assert(response.contains("\"statut\":\"erreur\""));
    }
    
    @Test
	public void getSignOutTokenNul() throws IOException, ServletException {
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
		when(req.getRequestURI()).thenReturn("/signout");
        when(req.getHeader("Authorization"))
            .thenReturn(null);
		when(resp.getWriter()).thenReturn(out);	
		
		service.doGet(req, resp);
		String response = sw.getBuffer().toString().trim();

		assert(response.contains("\"statut\":\"erreur\""));
	}

    @Test
	public void getPasSignout() throws IOException, ServletException {
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
		when(req.getRequestURI()).thenReturn("/glouglouglou");
		when(resp.getWriter()).thenReturn(out);	
		
		service.doGet(req, resp);
		String response = sw.getBuffer().toString().trim();
        
		assert(response.contains("\"statut\":\"erreur\""));
    }
    
    @Test
	public void getPasSignoutSuperieur2() throws IOException, ServletException {
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
		when(req.getRequestURI()).thenReturn("/glouglouglou/bipbip");
		when(resp.getWriter()).thenReturn(out);	
		
		service.doGet(req, resp);
		String response = sw.getBuffer().toString().trim();
        
		assert(response.contains("\"statut\":\"erreur\""));
	}

}
