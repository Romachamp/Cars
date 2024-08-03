package com.example.cars.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public CompletableFuture<ResponseEntity<List<User>>> getAllUsers() {
        return userService.getAll()
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<User>> getUserById(@PathVariable String id) {
        return userService.getById(id)
                .thenApply(user -> user.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build()));
    }

    @PostMapping("/add")
    public CompletableFuture<ResponseEntity<Void>> addUser(@RequestBody User user) {
        return userService.add(user)
                .thenApply(result -> ResponseEntity.status(HttpStatus.CREATED).build());
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteUserById(@PathVariable String id) {
        return userService.deleteById(id)
                .thenApply(result -> ResponseEntity.noContent().build());
    }
}
