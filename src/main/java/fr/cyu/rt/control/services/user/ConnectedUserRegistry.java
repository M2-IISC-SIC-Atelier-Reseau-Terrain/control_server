package fr.cyu.rt.control.services.user;

import fr.cyu.rt.control.business.user.User;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Handle all users connected, permit checking whenever
 * a user is connected or not.
 *
 * @author Aldric Vitali Silvestre
 */
@Component
public class ConnectedUserRegistry {

    // TODO is useful ?

    private Map<Long, UserState> userById;

    public void registerUser(User user) {
        if (isUserPresent(user.getId())) {
            throw new IllegalArgumentException("User id is already registered");
        }

    }

    public boolean isUserPresent(Long id) {
        return userById.containsKey(id);
    }

    public record UserState(

    ) {
    }
}
