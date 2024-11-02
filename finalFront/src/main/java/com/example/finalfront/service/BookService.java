package com.example.finalfront.service;

import com.example.finalfront.dto.BookDTO;
import com.example.finalfront.models.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class BookService {
    private final WebClient webClient;
    private final String baseUrl;
    private final TokenService tokenService;

    public BookService(WebClient webClient, @Value("${spring.api.base-url}") String baseUrl, TokenService tokenService) {
        this.webClient = webClient;
        this.baseUrl = baseUrl;
        this.tokenService = tokenService;
    }

    public List<Book> getBooks() {
        List<Book> books = webClient
                .get()
                .uri(baseUrl + "/books")
                .header("Authorization", "Bearer " + tokenService.getToken())
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToFlux(Book.class)
                .doOnError(e -> System.err.println("Ошибка при получении книг: " + e.getMessage()))
                .collectList()
                .block();


        System.out.printf("Количество книг: %d%n", books.size());

        return books;
    }

    public Book getBook(Long id) {
        return webClient
                .get()
                .uri(baseUrl + "/books/" + id)
                .header("Authorization", "Bearer " + tokenService.getToken())
                .retrieve()
              .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToMono(Book.class)
                .block();
    }

    public Book createBook(BookDTO book) {
        return webClient
                .post()
                .uri(baseUrl + "/books")
                .bodyValue(book)
                .header("Authorization", "Bearer " + tokenService.getToken())
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToMono(Book.class)
                .block();
    }

    public Book updateBook(Long id, Book book) {
        return webClient
                .put()
                .uri(baseUrl + "/books/" + id)
                .header("Authorization", "Bearer " + tokenService.getToken())
                .bodyValue(book)
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToMono(Book.class)
                .block();
    }

    public void deleteBook(Long id) {
        webClient
                .delete()
                .uri(baseUrl + "/books/" + id)
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
