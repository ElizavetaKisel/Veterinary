package by.overone.veterinary.dao.exception;

import java.sql.SQLException;

public class DaoException extends Exception{

    public DaoException(String message, SQLException e) {
        super(message, e);
    }
}