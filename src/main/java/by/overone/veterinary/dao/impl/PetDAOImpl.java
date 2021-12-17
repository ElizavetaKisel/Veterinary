package by.overone.veterinary.dao.impl;

import by.overone.veterinary.dao.PetDAO;
import by.overone.veterinary.dao.conection.DBConnect;
import by.overone.veterinary.dao.exception.DaoException;
import by.overone.veterinary.dao.exception.DaoNotFoundException;
import by.overone.veterinary.model.Pet;
import by.overone.veterinary.util.constant.PetConstant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetDAOImpl implements PetDAO {

    private Connection connection;
    private static final String GET_PETS_QUERY = "SELECT * FROM pets";
    private final static String GET_PET_BY_ID_QUERY = "SELECT * FROM pets WHERE pet_id=?";

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
    public Pet addPet(Pet pet) {
      return null;
    }

    @Override
    public boolean deletePet(long id) {
        return false;
    }
}
