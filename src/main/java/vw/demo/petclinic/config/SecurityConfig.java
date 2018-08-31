package vw.demo.petclinic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import vw.demo.petclinic.auth.config.JwtTokenFilterConfigurer;
import vw.demo.petclinic.auth.config.JwtTokenProvider;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    WebSecurityConfigurerAdapter webSecurityConfig(JwtTokenProvider jwtTokenProvider) {
        return new WebSecurityConfigurerAdapter() {
            @Override
            protected void configure(HttpSecurity http) throws Exception {
                // Disable CSRF (cross site request forgery)
                http.csrf().disable();

                // No session will be created or used by spring security
                http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

                // Entry points
                http.authorizeRequests()//
                        .antMatchers("/actuator/**").permitAll()
                        .antMatchers("/auth/**").permitAll()
                        .antMatchers("/open/**").permitAll()
                        .antMatchers("/h2-console/**/**").permitAll()
                        // Disallow everything else..
                        .anyRequest().authenticated();

                // If a user try to access a resource without having enough permissions
                http.exceptionHandling().accessDeniedPage("/signin");

                // Apply JWT
                http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

                // Optional, if you want to test the API from a browser
                // http.httpBasic();
            }
        };
    }
}
