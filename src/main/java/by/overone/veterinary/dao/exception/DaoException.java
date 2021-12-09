package by.overone.veterinary.dao.exception;

import java.sql.SQLException;

public class DaoException extends Exception{

    public DaoException(String user_not_added, SQLException e) {
    }
}