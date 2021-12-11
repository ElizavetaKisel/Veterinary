package by.overone.veterinary.dao.exception;

import java.sql.SQLException;

public class DaoNotFoundException extends Exception {
    public DaoNotFoundException(String message, SQLException e) {
        super(message,e);
    }

    public DaoNotFoundException(String message) {
        super(message);
    }
}
