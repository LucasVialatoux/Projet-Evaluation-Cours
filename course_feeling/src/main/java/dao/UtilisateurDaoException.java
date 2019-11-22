package dao;

public class UtilisateurDaoException extends Exception {
    private static final long serialVersionUID = -7404944173405914772L;

    public UtilisateurDaoException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}