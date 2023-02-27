package com.serbatic.lucasmoy.tpraga.sbfullstack.dao;

import com.serbatic.lucasmoy.tpraga.sbfullstack.models.User;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getUsers() {
        String query = "FROM User";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void deleteUser(Long id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public void registerUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public User obtainUserById(User user) {
        String query = "FROM User where email = :email";
        List<User> userList = entityManager.createQuery(query)
                .setParameter("email", user.getEmail())
                .getResultList();
        // Si email no existe en la BBDD
        // La lista está vacía
        // Y al tratar de obtener el primer valor devolverá null
        // si intentamos conseguir password de null, nos dará nullPointerException
        if (userList.isEmpty()) {
            System.out.println("is empty");
            return null;
        }
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hashedPassword = userList.get(0).getPassword();
        if (argon2.verify(hashedPassword, user.getPassword().toCharArray())) {
            return userList.get(0);
        }
        System.out.println("argon couldn't verify");
        return null;
    }
}
