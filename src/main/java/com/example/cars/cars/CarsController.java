package com.example.cars.cars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/cars")
public class CarsController {

    @Autowired
    private CarsService carsService;

    @GetMapping("/all")
    public CompletableFuture<ResponseEntity<List<Car>>> getCars(@RequestParam String model) {
        return carsService.getCars(model)
                .thenApply(ResponseEntity::ok);
    }
}

