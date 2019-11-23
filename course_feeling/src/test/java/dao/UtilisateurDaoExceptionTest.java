package dao;

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


public class UtilisateurDaoExceptionTest {
    @Test
    public void LanceException() {
        Throwable t = new Throwable();
        UtilisateurDaoException uDE = new UtilisateurDaoException("error", t);
    }
}