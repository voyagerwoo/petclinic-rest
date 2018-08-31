package vw.demo.petclinic.interfaces;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vw.demo.petclinic.auth.config.JwtSecurityException;
import vw.demo.petclinic.domains.user.User;
import vw.demo.petclinic.domains.user.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody User.SigninRequest signinRequest) {
        return ResponseEntity.ok(userService.signin(signinRequest.getUsername(), signinRequest.getPassword()));
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody User.UserRequest userRequest) {
        userService.signup(userRequest);
        return ResponseEntity.status(201).build();
    }

    @GetMapping(value = "/me")
    public ResponseEntity whoami(Principal principal) {
        if (principal == null)
            return ResponseEntity.badRequest().build();

        User user = userService.findByUsername(principal.getName());
        return ResponseEntity.ok(modelMapper.map(user, User.UserResponse.class));
    }

    @ExceptionHandler(JwtSecurityException.class)
    public ResponseEntity jwtSecurityExceptionHandler() {
        return ResponseEntity.badRequest().build();
    }
}
