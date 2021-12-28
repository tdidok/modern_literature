package com.tdidok.literature.identityservice.service;

import com.tdidok.literature.identityservice.repository.UserRepository;
import com.tdidok.literature.identityservice.repository.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id) throws IllegalArgumentException {
        final Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) throw new IllegalArgumentException("No such user found");
        return user.get();
    }

    public User getUserByUsername(String username) throws IllegalArgumentException {
        final Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) throw new IllegalArgumentException("No such user found");
        return user.get();
    }

    public Long createUser(String username, String password){
        User newUser = new User(username, password);
        User savedUser =  userRepository.save(newUser);
        return savedUser.getId();
    }

    public void updateUser(Long id, String username, String password) throws IllegalArgumentException {
        Optional<User> userData = userRepository.findById(id);
        if (userData.isEmpty()) throw new IllegalArgumentException("Invalid user id");
        User user = userData.get();
        if (username!=null && !username.isBlank()) user.setUsername(username);
        if (password!=null && !password.isBlank()) user.setPassword(password);
        userRepository.save(user);
    }

    public void deleteUser (long id){
        userRepository.deleteById(id);
    }
}
