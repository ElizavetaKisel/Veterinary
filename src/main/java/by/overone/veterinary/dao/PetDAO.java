package by.overone.veterinary.dao;


import by.overone.veterinary.dao.exception.DaoException;
import by.overone.veterinary.dao.exception.DaoExistException;
import by.overone.veterinary.dao.exception.DaoNotFoundException;
import by.overone.veterinary.model.Pet;

import java.util.List;

public interface PetDAO {

    List<Pet> getPets() throws DaoException;

    Pet getPetById(long id) throws DaoNotFoundException, DaoException;

    Pet addPet(long user_id, Pet pet) throws DaoExistException, DaoException;

    Pet updatePet(long id, Pet pet);

    boolean deletePet(long id) throws DaoException;
}
