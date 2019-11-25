package dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.sql.Connection;
import java.sql.Statement;

import javax.activation.DataSource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import dao.MatiereDaoImpl;
import dao.UtilisateurDaoMockImpl;
import services.AuthenticationService;

public class MatiereDaoImplTest extends Mockito {
	
	// AuthenticationService service;
	
	// @Mock
	// HttpServletRequest req;
	
	// @Mock
	// HttpServletResponse resp;
	
	// @Before
	// public void setUp() throws IOException, ServletException {
	// 	MockitoAnnotations.initMocks(this);
    //     service = new AuthenticationService();
    //     service.init();
	// 	service.setUtilisateurDao(new UtilisateurDaoMockImpl());
    // }

    @InjectMocks private MatiereDaoImpl matDaoImpl;

    @Mock private Connection mockConnection;

    @Mock private Statement mockStatement;

    @Mock private MatiereDaoImpl myDao;

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
	public void TestConstructeur() throws IOException, ServletException {
		try {
            MatiereDaoImpl mdi = new MatiereDaoImpl();
        } catch (Exception e) {
            assertNull(e);
        }
    }

    // @Test
    // public void testMockConnection() throws Exception {
    //     when(mockConnection.createStatement()).thenReturn(mockStatement);
    //     when(mockConnection.createStatement().executeUpdate(Mockito.any())).thenReturn(1);
    //     //when(matDaoImpl.ajoutMatiere("test@test.com", "LIFAP3")).thenReturn(true);
    //     Mockito.verify(mockConnection.createStatement(), Mockito.times(1));
    // }

    // @Test
    // public void TestajoutMatiere() throws IOException, ServletException {
	// 	try {
    //         System.out.println("====>1");
    //         MatiereDaoImpl mdi = new MatiereDaoImpl();
    //         System.out.println("====>2");
    //         mdi.ajoutMatiere("test@test.com", "LIFAP3");
    //         System.out.println("====>10");
    //     } catch (Exception e) {
    //         assertNull(e);
    //     }
    // }
    
}
