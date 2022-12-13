package fr.cyu.rt.control.persistence.user;

import fr.cyu.rt.control.business.user.QUser;
import fr.cyu.rt.control.business.user.User;
import fr.cyu.rt.control.dao.jpa.BaseJpaRepository;
import fr.cyu.rt.control.dao.user.UserDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author Aldric Vitali Silvestre
 */
@Repository
public class UserJpaPersistence extends BaseJpaRepository<User, Long> implements UserDao {

    public UserJpaPersistence(EntityManager em) {
        super(User.class, em);
    }

    private static final QUser USER = QUser.user;

    @Override
    public Optional<User> findById(Long id) {
        return queryDslFactory()
                .selectFrom(USER)
                .where(USER.id.eq(id))
                .stream().findFirst();
    }

    @Override
    public User findByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cannot find user by id"));
    }

    @Override
    public Optional<User> findByName(String name) {
        return queryDslFactory()
                .selectFrom(USER)
                .where(USER.name.eq(name))
                .stream().findFirst();
    }

    @Override
    public User findByNameOrThrow(String name) {
        return findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Cannot find user by name"));
    }

    @Override
    public User save(User user) {
        return saveEntity(user);
    }
}
