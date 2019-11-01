package dao;

public class SondageDaoException extends Exception {
    private static final long serialVersionUID = 6778756221491035135L;
    
    public SondageDaoException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
