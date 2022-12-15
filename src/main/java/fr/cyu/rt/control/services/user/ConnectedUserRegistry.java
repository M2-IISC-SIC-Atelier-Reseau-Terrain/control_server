package fr.cyu.rt.control.services.user;

import fr.cyu.rt.control.business.user.User;
import fr.cyu.rt.control.business.user.UserRole;
import fr.cyu.rt.control.dao.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Handle all users connected, permit checking whenever
 * a user is connected or not.
 *
 * @author Aldric Vitali Silvestre
 */
@Component
public class ConnectedUserRegistry {

    @Autowired
    private UserDao userDao;

    private Map<Long, UserState> userById;

    private UserState houseUserState;

//    @PostConstruct
//    TODO init must be post constructed after
    public void init() {
        List<User> users = userDao.findAll();
        var houseUser = users.stream()
                .filter(u -> u.getRole() == UserRole.COM)
                .findFirst()
                .orElseThrow();
        userById = users.stream()
                .collect(Collectors.toMap(
                        User::getId,
                        u -> new UserState(u.getLastLoginTime())
                ));
        houseUserState = Optional.ofNullable(userById.get(houseUser.getId()))
                .orElseThrow();
    }

    public boolean isUserConnected(long userId) {
        return userById.get(userId).isConnected();
    }

    public UserState getHouseUserState() {
        return houseUserState;
    }

    public boolean isHouseAlive() {
        return houseUserState.isConnected();
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
        private LocalDateTime lostConnectionDate;

        public UserState(LocalDateTime lostConnectionDate) {
            this.lostConnectionDate = lostConnectionDate;
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
