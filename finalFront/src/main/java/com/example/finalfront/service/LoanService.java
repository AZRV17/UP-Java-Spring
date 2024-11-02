package com.example.finalfront.service;

import com.example.finalfront.models.Loan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class LoanService {
    private final WebClient webClient;
    private final String baseUrl;
    private final TokenService tokenService;

    public LoanService(WebClient webClient, @Value("${spring.api.base-url}") String baseUrl, TokenService tokenService) {
        this.webClient = webClient;
        this.baseUrl = baseUrl;
        this.tokenService = tokenService;
    }

    public List<Loan> getLoans() {
        return webClient
                .get()
                .uri(baseUrl + "/loans")
                .header("Authorization", "Bearer " + tokenService.getToken())
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToFlux(Loan.class)
                .collectList()
                .block();
    }

    public Loan getLoan(Long id) {
        return webClient
                .get()
                .uri(baseUrl + "/loans/" + id)
                .header("Authorization", "Bearer " + tokenService.getToken())
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToMono(Loan.class)
                .block();
    }

    public Loan createLoan(Loan loan) {
        return webClient
                .post()
                .uri(baseUrl + "/loans")
                .header("Authorization", "Bearer " + tokenService.getToken())
                .bodyValue(loan)
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToMono(Loan.class)
                .block();
    }

    public Loan updateLoan(Long id, Loan loan) {
        return webClient
                .put()
                .uri(baseUrl + "/loans/" + id)
                .header("Authorization", "Bearer " + tokenService.getToken())
                .bodyValue(loan)
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToMono(Loan.class)
                .block();
    }

    public void deleteLoan(Long id) {
        webClient
                .delete()
                .uri(baseUrl + "/loans/" + id)
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
