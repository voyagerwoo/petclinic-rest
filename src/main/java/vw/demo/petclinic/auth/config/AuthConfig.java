package vw.demo.petclinic.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import vw.demo.petclinic.domains.user.UserRepository;

import java.util.stream.Collectors;

@Configuration
public class AuthConfig {

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManager(WebSecurityConfigurerAdapter webSecurityConfig) throws Exception {
        return webSecurityConfig.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(15);
    }

    @Bean
    UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository
                .findByUsername(username)
                .map(
                        user -> {
                            boolean active = user.isActive();
                            return new User(user.getUsername(), user.getPassword(), active,
                                    active, active, active, user.getRoles().stream()
                                    .map(r -> new SimpleGrantedAuthority(r.name())).collect(Collectors.toList()));
                        })
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("username %s not found!",
                                username)));
    }

}
