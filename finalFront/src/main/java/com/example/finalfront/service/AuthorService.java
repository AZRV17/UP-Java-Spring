package com.example.finalfront.service;

import com.example.finalfront.models.Author;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class AuthorService {
    private final WebClient webClient;
    private final String baseUrl;
    private final TokenService tokenService;

    public AuthorService(WebClient webClient, @Value("${spring.api.base-url}") String baseUrl, TokenService tokenService) {
        this.webClient = webClient;
        this.baseUrl = baseUrl;
        this.tokenService = tokenService;
    }

    public List<Author> getAuthors() {
        return webClient
                .get()
                .uri(baseUrl + "/authors")
                .header("Authorization", "Bearer " + tokenService.getToken())
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToFlux(Author.class)
                .collectList()
                .block();
    }

    public Author getAuthor(Long id) {
        return webClient
                .get()
                .uri(baseUrl + "/authors/" + id)
                .header("Authorization", "Bearer " + tokenService.getToken())
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToMono(Author.class)
                .block();
    }

    public void createAuthor(Author author) {
        webClient
                .post()
                .uri(baseUrl + "/authors")
                .header("Authorization", "Bearer " + tokenService.getToken())
                .bodyValue(author)
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToMono(Author.class)
                .block();
    }

    public Author updateAuthor(Long id, Author author) {
        return webClient
                .put()
                .uri(baseUrl + "/authors/" + id)
                .header("Authorization", "Bearer " + tokenService.getToken())
                .bodyValue(author)
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToMono(Author.class)
                .block();
    }

    public void deleteAuthor(Long id) {
        webClient
                .delete()
                .uri(baseUrl + "/authors/" + id)
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
