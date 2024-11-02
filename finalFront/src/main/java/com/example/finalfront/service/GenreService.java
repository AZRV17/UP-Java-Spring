package com.example.finalfront.service;

import com.example.finalfront.models.Genre;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GenreService {
    private final WebClient webClient;
    private final String baseUrl;
    private final TokenService tokenService;

    public GenreService(WebClient webClient, @Value("${spring.api.base-url}") String baseUrl, TokenService tokenService) {
        this.webClient = webClient;
        this.baseUrl = baseUrl;
        this.tokenService = tokenService;
    }

    public List<Genre> getGenres() {
        return webClient
                .get()
                .uri(baseUrl + "/genres")
                .header("Authorization", "Bearer " + tokenService.getToken())
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToFlux(Genre.class)
                .collectList()
                .block();
    }

    public Genre getGenre(Long id) {
        return webClient
                .get()
                .uri(baseUrl + "/genres/" + id)
                .header("Authorization", "Bearer " + tokenService.getToken())
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToMono(Genre.class)
                .block();
    }

    public Genre createGenre(Genre genre) {
        return webClient
                .post()
                .uri(baseUrl + "/genres")
                .header("Authorization", "Bearer " + tokenService.getToken())
                .bodyValue(genre)
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToMono(Genre.class)
                .block();
    }

    public Genre updateGenre(Long id, Genre genre) {
        return webClient
                .put()
                .uri(baseUrl + "/genres/" + id)
                .header("Authorization", "Bearer " + tokenService.getToken())
                .bodyValue(genre)
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToMono(Genre.class)
                .block();
    }

    public void deleteGenre(Long id) {
        webClient
                .delete()
                .uri(baseUrl + "/genres/" + id)
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
