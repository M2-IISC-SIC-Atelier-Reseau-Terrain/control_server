package fr.cyu.rt.control.business.user;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Aldric Vitali Silvestre
 */
@Entity
@Table(name = "user_entity")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @Column(nullable = false)
    private LocalDateTime creationTime = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime lastLoginTime = LocalDateTime.now();

    public User() {
    }

    public User(String name, String password, String email, UserRole role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public User(String name,
                String password,
                UserRole role,
                LocalDateTime creationTime,
                LocalDateTime lastLoginTime) {
        this.name = name;
        this.password = password;
        this.role = role;
        this.creationTime = creationTime;
        this.lastLoginTime = lastLoginTime;
    }

    public void updateLastLoginTime() {
        lastLoginTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name,
                                                             user.name
        ) && Objects.equals(password,
                            user.password
        ) && role == user.role && Objects.equals(creationTime, user.creationTime) && Objects.equals(
                lastLoginTime,
                user.lastLoginTime
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, role, creationTime, lastLoginTime);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role=" + role +
                ", creationTime=" + creationTime +
                ", lastLoginTime=" + lastLoginTime +
                '}';
    }
}
