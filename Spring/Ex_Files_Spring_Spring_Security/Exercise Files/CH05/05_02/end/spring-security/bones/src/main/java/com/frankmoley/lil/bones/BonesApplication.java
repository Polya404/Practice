package com.frankmoley.lil.bones;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

@SpringBootApplication
@EnableWebFluxSecurity
public class BonesApplication {

    public static void main(String[] args) {
        SpringApplication.run(BonesApplication.class, args);
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService(){
        List<UserDetails> userDetails = new ArrayList<>();
        userDetails.add(User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                .build()
        );
        userDetails.add(User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password")
                .roles("USER", "ADMIN")
                .build()
        );
        return new MapReactiveUserDetailsService(userDetails);
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http){
        http.authorizeExchange()
                .pathMatchers("/hello").permitAll()
                .anyExchange().hasRole("ADMIN")
                .and().httpBasic();
        return http.build();
    }



}
