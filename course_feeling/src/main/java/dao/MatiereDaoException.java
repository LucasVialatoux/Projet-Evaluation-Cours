package dao;

public class MatiereDaoException extends Exception {
    private static final long serialVersionUID = -8502445994599416820L;

    public MatiereDaoException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
