package by.overone.veterinary.dao.impl;

import by.overone.veterinary.dao.PetDAO;
import by.overone.veterinary.dao.conection.DBConnect;
import by.overone.veterinary.dao.exception.DaoException;
import by.overone.veterinary.dao.exception.DaoExistException;
import by.overone.veterinary.dao.exception.DaoNotFoundException;
import by.overone.veterinary.model.Pet;
import by.overone.veterinary.model.Status;
import by.overone.veterinary.util.constant.PetConstant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetDAOImpl implements PetDAO {

    private Connection connection;
    private static final String GET_PETS_QUERY = "SELECT * FROM pets WHERE status= 'ACTIVE'";
    private final static String GET_PET_BY_ID_QUERY = "SELECT * FROM pets WHERE pet_id=? and status = 'ACTIVE'";
    private final static String ADD_PET_QUERY = "INSERT INTO pets VALUE (0, ?, ?, ?, ?, ?)";
    private final static String ADD_PETS_HAS_USERS_ID_QUERY = "INSERT INTO pets_has_users (pets_pet_id, users_user_id) VALUE (?, ?)";
    private final static String DELETE_PET_QUERY = "UPDATE pets SET status=? WHERE pet_id=? and status = 'ACTIVE'";
    private final static String UPDATE_PET_QUERY = "UPDATE pets SET name=?, type=?, breed=?, age=? WHERE pet_id=?";
    private static final String GET_PETS_BY_USER_ID_QUERY = "SELECT * FROM pets join pets_has_users WHERE users_user_id=? and status= 'ACTIVE'";

    @Override
    public List<Pet> getPetsByUserId(long user_id) throws DaoException {
        List<Pet> pets = new ArrayList<>();
        try {
            connection = DBConnect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_PETS_BY_USER_ID_QUERY);
            preparedStatement.setLong(1,user_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Pet pet = new Pet();
                pet.setId(resultSet.getLong(PetConstant.PET_ID));
                pet.setName(resultSet.getString(PetConstant.NAME));
                pet.setType(resultSet.getString(PetConstant.TYPE));
                pet.setBreed(resultSet.getString(PetConstant.BREED));
                pet.setAge(resultSet.getInt(PetConstant.AGE));
                pets.add(pet);
            }
        }catch (SQLException e) {
            throw new DaoException("Dao error", e);
        }finally {
            try {
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return pets;
    }

    @Override
    public Pet addPet(long user_id, Pet pet) throws DaoExistException, DaoException {
        try {
            connection = DBConnect.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_PET_QUERY, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, pet.getName());
            preparedStatement.setString(2, pet.getType());
            preparedStatement.setString(3, pet.getBreed());
            preparedStatement.setInt(4, pet.getAge());
            preparedStatement.setString(3, Status.ACTIVE.toString());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                pet.setId(resultSet.getLong(1));
            }

            preparedStatement = connection.prepareStatement(ADD_PETS_HAS_USERS_ID_QUERY);
            preparedStatement.setLong(1, pet.getId());
            preparedStatement.setLong(2,user_id);
            preparedStatement.executeUpdate();

            connection.commit();

        }catch (SQLIntegrityConstraintViolationException ex){
            throw new DaoExistException("Duplicate pet", ex);
        }catch (SQLException e){
            try {
                connection.rollback();
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new DaoException("dao error", e);
        }finally {
            try {
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return pet;
    }

    @Override
    public Pet updatePet(long id, Pet pet) throws DaoException {
        try {
            connection = DBConnect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PET_QUERY);

            if (pet.getName() != null) {
                preparedStatement.setString(1, pet.getName());
            }
            if (pet.getType() != null) {
                preparedStatement.setString(2, pet.getType());
            }
            if (pet.getBreed() != null) {
                preparedStatement.setString(3, pet.getBreed());
            }
            if (pet.getAge() != 0) {
                preparedStatement.setInt(4, pet.getAge());
            }
            preparedStatement.setLong(5, id);

            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new DaoException("dao error", e);
        }finally {
            try {
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return pet;
    }

    @Override
    public List<Pet> getPets() throws DaoException {
        List<Pet> pets = new ArrayList<>();
        try {
            connection = DBConnect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_PETS_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Pet pet = new Pet();
                pet.setId(resultSet.getLong(PetConstant.PET_ID));
                pet.setName(resultSet.getString(PetConstant.NAME));
                pet.setType(resultSet.getString(PetConstant.TYPE));
                pet.setBreed(resultSet.getString(PetConstant.BREED));
                pet.setAge(resultSet.getInt(PetConstant.AGE));
                pets.add(pet);
            }
        }catch (SQLException e) {
            throw new DaoException("Dao error", e);
        }finally {
            try {
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return pets;
    }

    @Override
    public Pet getPetById(long id) throws DaoNotFoundException, DaoException {
        Pet pet = new Pet();
        try{
            connection = DBConnect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_PET_BY_ID_QUERY);
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                pet.setId(resultSet.getLong(PetConstant.PET_ID));
                pet.setName(resultSet.getString(PetConstant.NAME));
                pet.setType(resultSet.getString(PetConstant.TYPE));
                pet.setBreed(resultSet.getString(PetConstant.BREED));
                pet.setAge(resultSet.getInt(PetConstant.AGE));
            }else {
                throw new DaoNotFoundException("Pet not found");
            }
        }catch (SQLException e) {
            throw new DaoException("error",e);
        }finally {
            try {
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return pet;
    }

    @Override
    public boolean deletePet(long id) throws DaoException {
        try{
            connection = DBConnect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PET_QUERY);
            preparedStatement.setString(1,Status.DELETED.toString());
            preparedStatement.setLong(2,id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("dao error", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
