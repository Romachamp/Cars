package com.example.cars.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Async
    public CompletableFuture<List<User>> getAll() {
        try {
            List<User> users = repository.findAll();
            return CompletableFuture.completedFuture(users);
        } catch (Exception e) {
            // Handle exceptions or rethrow if necessary
            throw new RuntimeException("Failed to fetch all users", e);
        }
    }

    @Async
    public CompletableFuture<Optional<User>> getById(String id) {
        try {
            Optional<User> user = repository.findById(id);
            return CompletableFuture.completedFuture(user);
        } catch (Exception e) {
            // Handle exceptions or rethrow if necessary
            throw new RuntimeException("Failed to find user with id: " + id, e);
        }
    }

    @Async
    public CompletableFuture<Void> add(User user) {
        try {
            repository.save(user);
            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
            // Handle exceptions or rethrow if necessary
            throw new RuntimeException("Failed to add user", e);
        }
    }

    @Async
    public CompletableFuture<Void> deleteById(String id) {
        try {
            repository.deleteById(id);
            return CompletableFuture.completedFuture(null);
        } catch (EmptyResultDataAccessException ex) {
            // Handle case where entity with given id is not found
            throw new IllegalArgumentException("User not found with id: " + id);
        } catch (Exception e) {
            // Handle other exceptions or rethrow if necessary
            throw new RuntimeException("Failed to delete user with id: " + id, e);
        }
    }
}
