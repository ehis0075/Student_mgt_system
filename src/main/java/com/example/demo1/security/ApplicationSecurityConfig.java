package com.example.demo1.security;

import com.example.demo1.models.ApplicationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // authorize all request
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())
                .anyRequest()   // any requests
                .authenticated()    // must be authenticated i.e client must specify username and password
                .and()  // and the mechanism to enforce the authenticity of a client
                .httpBasic(); // is by using basic auth
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {  // how you return users from database
        UserDetails jamesBondUser = User.builder()
                .username("jamesbond")
                .password(passwordEncoder.encode("password"))
                .roles(ApplicationUserRole.STUDENT.name())  // ROLE_STUDENT
                .build();

        UserDetails sarahaliUser = User.builder()
                .username("sarahali")
                .password(passwordEncoder.encode("password123"))
                .roles(ApplicationUserRole.ADMIN.name())
                .build();

        

        return new InMemoryUserDetailsManager(
                jamesBondUser,
                sarahaliUser
        );
    }

}
