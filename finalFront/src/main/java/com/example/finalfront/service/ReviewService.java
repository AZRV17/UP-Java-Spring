package com.example.finalfront.service;

import com.example.finalfront.models.Review;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ReviewService {
    private final WebClient webClient;
    private final String baseUrl;
    private final TokenService tokenService;

    public ReviewService(WebClient webClient, @Value("${spring.api.base-url}") String baseUrl, TokenService tokenService) {
        this.webClient = webClient;
        this.baseUrl = baseUrl;
        this.tokenService = tokenService;
    }

    public List<Review> getReviews() {
        return webClient
                .get()
                .uri(baseUrl + "/reviews")
                .header("Authorization", "Bearer " + tokenService.getToken())
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToFlux(Review.class)
                .collectList()
                .block();
    }

    public Review getReview(Long id) {
        return webClient
                .get()
                .uri(baseUrl + "/reviews/" + id)
                .header("Authorization", "Bearer " + tokenService.getToken())
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToMono(Review.class)
                .block();
    }

    public Review createReview(Review review) {
        return webClient
                .post()
                .uri(baseUrl + "/reviews")
                .header("Authorization", "Bearer " + tokenService.getToken())
                .bodyValue(review)
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToMono(Review.class)
                .block();
    }

    public Review updateReview(Long id, Review review) {
        return webClient
                .put()
                .uri(baseUrl + "/reviews/" + id)
                .header("Authorization", "Bearer " + tokenService.getToken())
                .bodyValue(review)
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToMono(Review.class)
                .block();
    }

    public void deleteReview(Long id) {
        webClient
                .delete()
                .uri(baseUrl + "/reviews/" + id)
                .header("Authorization", "Bearer " + tokenService.getToken())
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToMono(Void.class)
                .block();
    }
}
