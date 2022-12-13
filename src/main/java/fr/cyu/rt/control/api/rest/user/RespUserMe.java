package fr.cyu.rt.control.api.rest.user;

import fr.cyu.rt.control.business.user.User;
import fr.cyu.rt.control.business.user.UserRole;

import java.time.LocalDateTime;

/**
 * @author Aldric Vitali Silvestre
 */
public record RespUserMe(
        Long id,
        String name,
        String email,
        LocalDateTime creationDate,
        LocalDateTime lastLoginDate,
        UserRole role
) {
    public RespUserMe(User user) {
        this(user.getId(),
             user.getName(),
             user.getEmail(),
             user.getCreationTime(),
             user.getLastLoginTime(),
             user.getRole()
        );
    }
}
