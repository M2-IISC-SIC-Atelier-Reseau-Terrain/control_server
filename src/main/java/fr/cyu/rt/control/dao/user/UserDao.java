package fr.cyu.rt.control.dao.user;

import fr.cyu.rt.control.business.user.User;

import java.util.Optional;

/**
 * @author Aldric Vitali Silvestre
 */
public interface UserDao {

    Optional<User> findById(Long id);
    User findByIdOrThrow(Long id);

    Optional<User> findByName(String name);
    User findByNameOrThrow(String name);

    User save(User user);
}
