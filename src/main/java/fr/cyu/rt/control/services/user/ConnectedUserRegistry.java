package fr.cyu.rt.control.services.user;

import fr.cyu.rt.control.business.user.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Handle all users connected, permit checking whenever
 * a user is connected or not.
 *
 * @author Aldric Vitali Silvestre
 */
@Component
public class ConnectedUserRegistry {

    private Map<Long, UserState> userById;

    public boolean isUserConnected(long userId) {
        return userById.get(userId).isConnected();
    }

    public LocalDateTime getLostConnectionDateOf(long userId) {
        return userById.get(userId).getLostConnectionDate();
    }

    public void onUserConnected(long userId) {
        userById.get(userId).setConnected(true);
    }

    public void onUserDisconnected(long userId) {
        UserState userState = userById.get(userId);
        userState.setConnected(false);
        userState.setLostConnectionDate(LocalDateTime.now());
    }

    public class UserState {
        private boolean isConnected = false;
        private LocalDateTime lostConnectionDate = LocalDateTime.now();

        public UserState() {
        }

        public boolean isConnected() {
            return isConnected;
        }

        public void setConnected(boolean connected) {
            isConnected = connected;
        }

        public LocalDateTime getLostConnectionDate() {
            return lostConnectionDate;
        }

        public void setLostConnectionDate(LocalDateTime lostConnectionDate) {
            this.lostConnectionDate = lostConnectionDate;
        }
    }
}
