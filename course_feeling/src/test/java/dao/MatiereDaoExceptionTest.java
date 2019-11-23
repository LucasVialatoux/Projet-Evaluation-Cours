package dao;

import org.junit.Test;

import dao.MatiereDaoException;;


public class MatiereDaoExceptionTest {
    @Test
    public void LanceException() {
        Throwable t = new Throwable();
        MatiereDaoException uDE = new MatiereDaoException("error", t);
    }
}