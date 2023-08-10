package com.DeepanshuPanwar.learnspringsecurity.basic;


import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class BasicAuthSecurityConfiguration {

    @Bean
    SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
                    auth.anyRequest().authenticated();
                }
        );
        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
//        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());

        http.csrf(csrf -> csrf.disable());
        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService () {

        var user = User.withUsername("DeepanshuPanwar")
                .password("{noop}password")
                .roles("USER")
                .build();

        var admin = User.withUsername("admin")
                .password("{noop}password")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
