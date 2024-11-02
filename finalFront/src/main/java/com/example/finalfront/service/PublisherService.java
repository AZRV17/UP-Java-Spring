package com.example.finalfront.service;

import com.example.finalfront.models.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PublisherService {
    private final WebClient webClient;
    private final String baseUrl;
    private final TokenService tokenService;

    public PublisherService(WebClient webClient, @Value("${spring.api.base-url}") String baseUrl, TokenService tokenService) {
        this.webClient = webClient;
        this.baseUrl = baseUrl;
        this.tokenService = tokenService;
    }

    public List<Publisher> getPublishers() {
        return webClient
                .get()
                .uri(baseUrl + "/publishers")
                .header("Authorization", "Bearer " + tokenService.getToken())
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToFlux(Publisher.class)
                .collectList()
                .block();
    }

    public Publisher getPublisher(Long id) {
        return webClient
                .get()
                .uri(baseUrl + "/publishers/" + id)
                .header("Authorization", "Bearer " + tokenService.getToken())
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToMono(Publisher.class)
                .block();
    }

    public Publisher createPublisher(Publisher publisher) {
        return webClient
                .post()
                .uri(baseUrl + "/publishers")
                .bodyValue(publisher)
                .header("Authorization", "Bearer " + tokenService.getToken())
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToMono(Publisher.class)
                .block();
    }

    public Publisher updatePublisher(Long id, Publisher publisher) {
        return webClient
                .put()
                .uri(baseUrl + "/publishers/" + id)
                .header("Authorization", "Bearer " + tokenService.getToken())
                .bodyValue(publisher)
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToMono(Publisher.class)
                .block();
    }

    public void deletePublisher(Long id) {
        webClient
                .delete()
                .uri(baseUrl + "/publishers/" + id)
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
