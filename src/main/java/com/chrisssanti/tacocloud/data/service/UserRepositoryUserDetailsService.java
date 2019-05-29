package com.chrisssanti.tacocloud.data.service;

import com.chrisssanti.tacocloud.data.jpa.UserRepository;
import com.chrisssanti.tacocloud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    UserRepositoryUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetailsService loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if(user != null){
            return (UserDetailsService) user;
        }
        throw new UsernameNotFoundException ("User " + username + " not found" );
    }
}
