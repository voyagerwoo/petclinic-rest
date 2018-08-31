package vw.demo.petclinic.domains.user;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vw.demo.petclinic.auth.config.JwtSecurityException   ;
import vw.demo.petclinic.auth.services.AuthService;

import java.util.List;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthService authService;

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthService authService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }

    public String signin(String username, String password) {
        User user = findByUsername(username);
        try {
            return authService.authenticateAndCreateToken(username, password, user.getStringRoles());
        } catch (AuthenticationException e) {
            throw new JwtSecurityException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public void signup(User.UserRequest userRequest) {
        User user = modelMapper.map(userRequest, User.class);
        user.addRole(userRequest.getRole());

        if (!userRepository.existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            userRepository.save(user);
        } else {
            throw new JwtSecurityException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public void delete(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new JwtSecurityException("The user doesn't exist", HttpStatus.NOT_FOUND));
        user.setActive(false);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new JwtSecurityException("The user doesn't exist", HttpStatus.NOT_FOUND));
    }

    public List<User> findAllByIds(List<Long> userIds) {
        return userRepository.findAllById(userIds);
    }
}
