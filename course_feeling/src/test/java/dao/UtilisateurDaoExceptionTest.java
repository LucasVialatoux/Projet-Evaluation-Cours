package dao;

import org.junit.Test;

import dao.SondageDaoMockImpl;


public class UtilisateurDaoExceptionTest {
    @Test
    public void LanceException() {
        Throwable t = new Throwable();
        UtilisateurDaoException uDE = new UtilisateurDaoException("error", t);
    }
}