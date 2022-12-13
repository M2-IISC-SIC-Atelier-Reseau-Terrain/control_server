package fr.cyu.rt.control.security;

import fr.cyu.rt.control.business.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author Aldric Vitali Silvestre
 */
public class JwtUserDetails implements UserDetails {

    private final User user;
    private final Long id;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUserDetails(User user,
                          Long id,
                          String username,
                          String password,
                          Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public JwtUserDetails(User user) {
        this(user,
             user.getId(),
             user.getName(),
             user.getPassword(),
             List.of(new SimpleGrantedAuthority(user.getRole().roleName()))
        );
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
