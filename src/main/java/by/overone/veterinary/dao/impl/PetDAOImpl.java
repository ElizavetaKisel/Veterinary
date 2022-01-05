package by.overone.veterinary.dao.impl;

import by.overone.veterinary.dao.PetDAO;
import by.overone.veterinary.dto.PetDataDTO;
import by.overone.veterinary.model.Pet;
import by.overone.veterinary.model.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PetDAOImpl implements PetDAO {

    private final JdbcTemplate jdbcTemplate;

    private static final String GET_PETS_QUERY = "SELECT * FROM pets WHERE status= 'ACTIVE'";
    private final static String GET_PET_BY_ID_QUERY = "SELECT * FROM pets WHERE pet_id=? and status = 'ACTIVE'";
    private final static String ADD_PET_QUERY = "INSERT INTO pets VALUE (0, ?, ?, ?, ?, ?)";
    private final static String ADD_PETS_HAS_USERS_ID_QUERY = "INSERT INTO pets_has_users (users_user_id, pets_pet_id) VALUE (?, ?)";
    private final static String DELETE_PET_QUERY = "UPDATE pets SET status=? WHERE pet_id=? and status = 'ACTIVE'";
    private final static String UPDATE_PET_QUERY = "UPDATE pets SET name=?, type=?, breed=?, age=? WHERE pet_id=?";
    private static final String GET_PETS_BY_USER_ID_QUERY = "SELECT * FROM pets join pets_has_users" +
            " ON pet_id = pets_pet_id WHERE pets_pet_id=? and status= 'ACTIVE'";
    private final static String DELETE_PET_BY_USER_ID_QUERY = "UPDATE pets join pets_has_users " +
            "ON pet_id = pets_pet_id SET status=? WHERE users_user_id=? and status = 'ACTIVE'";


    @Override
    public List<Pet> getPets() {
        return jdbcTemplate.query(GET_PETS_QUERY, new BeanPropertyRowMapper<>(Pet.class));
    }

    @Override
    public List<Pet> getPetsByUserId(long user_id) {
        return jdbcTemplate.query(GET_PETS_BY_USER_ID_QUERY, new Object[]{user_id}, new BeanPropertyRowMapper<>(Pet.class));
    }

    @Override
    public Pet getPetById(long id) {
        return jdbcTemplate.queryForObject(GET_PET_BY_ID_QUERY, new Object[]{id}, new BeanPropertyRowMapper<>(Pet.class));
    }

    @Transactional
    @Override
    public Pet addPet(long user_id, Pet pet) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(ADD_PET_QUERY);
            ps.setString(1, pet.getName());
            ps.setString(2, pet.getType());
            ps.setString(3, pet.getBreed());
            ps.setLong(4, pet.getAge());
            ps.setString(5, Status.ACTIVE.toString());
            return ps;
        }, keyHolder);
        pet.setPet_id(keyHolder.getKey().longValue());
        jdbcTemplate.update(ADD_PETS_HAS_USERS_ID_QUERY,pet.getPet_id());
        return pet;
    }


    @Override
    public boolean deletePet(long id) {
        jdbcTemplate.update(DELETE_PET_QUERY, Status.DELETED.toString(), id);
        return true;
    }

    @Override
    public boolean deletePetByUserId(long user_id) {
        jdbcTemplate.update(DELETE_PET_QUERY, Status.DELETED.toString(), user_id);
        return true;
    }


    private final static String START_UPDATE_PET_QUERY = "UPDATE pets SET ";
    private final static String END_UPDATE_PET_QUERY = " WHERE pet_id=?";

    @Override
    public PetDataDTO updatePet(long id, PetDataDTO pet) {
            if (pet.getName() != null) {
                jdbcTemplate.update(START_UPDATE_PET_QUERY + "name=?" + END_UPDATE_PET_QUERY, pet.getName(), id);
            }
            if (pet.getType() != null) {
                jdbcTemplate.update(START_UPDATE_PET_QUERY + "type=?" + END_UPDATE_PET_QUERY, pet.getType(), id);
            }
            if (pet.getBreed() != null) {
                jdbcTemplate.update(START_UPDATE_PET_QUERY + "breed=?" + END_UPDATE_PET_QUERY, pet.getBreed(), id);
            }
            if (pet.getAge() != 0) {
                jdbcTemplate.update(START_UPDATE_PET_QUERY + "age=?" + END_UPDATE_PET_QUERY, pet.getAge(), id);
            }

            return jdbcTemplate.queryForObject(GET_PET_BY_ID_QUERY, new Object[]{id}, new BeanPropertyRowMapper<>(PetDataDTO.class));
    }
}
