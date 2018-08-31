package vw.demo.petclinic.domains.user;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table
@Data
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Size(min = 4, message = "Minimum password length: 4 characters")
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Role> roles = new ArrayList<>();

    private boolean active = true;

    public void addRole(Role role) {
        roles.add(role);
    }

    public User(@Size(min = 4, max = 255, message = "Minimum username length: 4 characters") String username,
                String email,
                @Size(min = 4, message = "Minimum password length: 4 characters") String password,
                Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.addRole(role);
    }

    public List<String> getStringRoles() {
        return roles.stream().map(Enum::name).collect(Collectors.toList());
    }

    @Data
    public static class UserResponse {
        private Integer id;
        private String username;
        private String email;
        private List<Role> roles;
    }

    @Data
    public static class UserRequest {
        private String username;
        private String email;
        private String password;
        private Role role;
    }

    @Data
    public static class SigninRequest {
        private String username;
        private String password;
    }
}
