package com.example.jwtdemo.service;

import com.example.jwtdemo.entity.User;
import com.example.jwtdemo.exceptions.UsernameAlreadyExistsException;
import com.example.jwtdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User saveUser(User newUser) {

        try{
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            // Username has to be unique (Exception)
            newUser.setUsername(newUser.getUsername());

            // Make sure that password and confirmPassword match
            // we don't persist or show the confirmPassword
            return userRepository.save(newUser);
        }catch(Exception e){
            throw new UsernameAlreadyExistsException("Username " + newUser.getUsername() + " already exists!");
        }



    }
}
