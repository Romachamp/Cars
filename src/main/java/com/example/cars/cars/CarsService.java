package com.example.cars.cars;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CarsService {

    private final WebClient webClient;

    @Value("${api.api-ninjas.key}")
    private String apiKey;

    public CarsService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.api-ninjas.com/v1").build();
    }

    @Async
    public CompletableFuture<List<Car>> getCars(String model) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/cars")
                        .queryParam("limit", 2)
                        .queryParam("model", model)
                        .build())
                .header("X-Api-Key", apiKey)
                .retrieve()
                .bodyToFlux(Car.class)
                .collectList()
                .toFuture();
    }
}

