package com.tdidok.literature.identityservice.api;

import com.tdidok.literature.identityservice.api.dto.UserDto;
import com.tdidok.literature.identityservice.repository.model.User;
import com.tdidok.literature.identityservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public final class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id){
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getByUsername(@PathVariable String username){
        try{
            User user = userService.getUserByUsername(username);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody UserDto userDto){
        try{
            String username = userDto.getUsername();
            String password = userDto.getPassword();
            Long userId = userService.createUser(username, password);
            String userUri = String.format("/user/%d", userId);
            return ResponseEntity.created(URI.create(userUri)).build();
        } catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UserDto userDto){
        try{
            String username = userDto.getUsername();
            String password = userDto.getPassword();
            userService.updateUser(id, username, password);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }
}
