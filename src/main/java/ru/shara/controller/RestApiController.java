package ru.shara.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.shara.model.User;
import ru.shara.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rest/")
public class RestApiController {

    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public RestApiController(UserService userService, BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @GetMapping("test")
    public String test() {
        return "{greeting: 'Hello}";
    }


    @GetMapping("users")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @PostMapping("users")
    public User createUser(@Valid @RequestBody User user) {
        userService.save(user);
        return user;
    }

    @GetMapping("users/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long id) {
        User user =  userService.getById(id);
        if(user == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(user);
    }

    @GetMapping("users/name/{name}")
    public ResponseEntity<User> getUserByUsername(@PathVariable(value = "name") String name) {
        User user =  userService.getUserByName(name);
        if(user == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(user);
    }

    @PutMapping("users/id/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id, @Valid @RequestBody User userData) {
        User user = userService.getById(id);
        if(user == null) return ResponseEntity.notFound().build();

        user.setUsername(userData.getUsername());
        user.setRoles(userData.getRoles());
        if (userData.getPassword() != null && !userData.getPassword().equals("") && !userData.getPassword().equals(" ")) {
            user.setPassword(encoder.encode(userData.getPassword()));
        }
        userService.save(user);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("users/id/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable(value = "id") Long id) {
        User user = userService.getById(id);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
