package by.overone.veterinary.dao.impl;

import by.overone.veterinary.dao.DBConnect;
import by.overone.veterinary.dao.UserDAO;
import by.overone.veterinary.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static Connection connection;

    private final static String GET_USERS_QUERY ="SELECT * FROM user";

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try {
            connection = DBConnect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USERS_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("user_id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                users.add(user);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public User getUserById(long id) {
        return null;
    }

    @Override
    public User addUser(User user) {
       return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public User deleteUser(long id) {
        return null;
    }
}
