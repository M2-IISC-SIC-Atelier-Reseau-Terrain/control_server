package fr.cyu.rt.control.services.user;

import fr.cyu.rt.control.business.user.User;
import fr.cyu.rt.control.dao.user.UserDao;
import fr.cyu.rt.control.security.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * @author Aldric Vitali Silvestre
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void onLogin(String userName) {
        User user = userDao.findByNameOrThrow(userName);
        user.updateLastLoginTime();
        userDao.save(user);
    }

    public User createUser(String name, String password) {
        User user = new User(name, passwordEncoder.encode(password));
        return userDao.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findByName(username)
                .map(JwtUserDetails::new)
                .orElseThrow(() -> new NoSuchElementException("No user with this username found"));
    }
}
