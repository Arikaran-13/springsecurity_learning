package com.zensar.commonConfig;

import com.zensar.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.naming.NameNotFoundException;

@Configuration
public class ApplicationConfig {

    private  UserRepository repository;

    @Bean
    public UserDetailsService userDetailsService(){
        return (username)->{
           return repository.findByEmail(username)
                    .orElseThrow(()->new UsernameNotFoundException("UsernameNotfound"));
        };
    }
}
