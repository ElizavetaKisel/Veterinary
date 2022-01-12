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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PetDAOImpl implements PetDAO {

    private final JdbcTemplate jdbcTemplate;

    private static final String GET_PETS_QUERY = "SELECT * FROM pets WHERE status= 'ACTIVE'";
    private final static String GET_PET_BY_ID_QUERY = "SELECT * FROM pets WHERE pet_id=? and status = 'ACTIVE'";
    private final static String ADD_PET_QUERY = "INSERT INTO pets VALUE (0, ?, ?, ?, ?, ?)";
    private final static String ADD_PETS_HAS_USERS_ID_QUERY = "INSERT INTO pets_has_users (users_user_id, pets_pet_id) VALUE (?, ?)";
    private final static String DELETE_PET_QUERY = "UPDATE pets SET status=? WHERE pet_id=? and status = 'ACTIVE'";
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
        jdbcTemplate.update(DELETE_PET_BY_USER_ID_QUERY, Status.DELETED.toString(), user_id);
        return true;
    }


    @Override
    public PetDataDTO updatePet(PetDataDTO pet) {
        List <String> sql = new ArrayList<>();
        if (pet.getName() != null) {
            sql.add("name=" + pet.getName() + "'");
        }
        if (pet.getType() != null) {
            sql.add("type='" + pet.getType() + "'");
        }
        if (pet.getBreed() != null) {
            sql.add("breed=" + pet.getPet_id() + "'");
        }
        if (pet.getAge() != 0) {
            sql.add("age='" + pet.getAge());
        }
        String UPDATE_PET_QUERY = "UPDATE pets SET " + sql.stream().collect(Collectors.joining(", "))
                + " WHERE pet_id=" + pet.getPet_id();
        jdbcTemplate.update(UPDATE_PET_QUERY);
        return jdbcTemplate.queryForObject(GET_PET_BY_ID_QUERY, new Object[]{pet.getPet_id()},
                new BeanPropertyRowMapper<>(PetDataDTO.class));
    }
}
