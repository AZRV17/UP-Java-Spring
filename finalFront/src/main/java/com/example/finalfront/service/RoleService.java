package com.example.finalfront.service;

import com.example.finalfront.models.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class RoleService {
    private final WebClient webClient;
    private final String baseUrl;
    private final TokenService tokenService;

    public RoleService(WebClient webClient, @Value("${spring.api.base-url}") String baseUrl, TokenService tokenService) {
        this.webClient = webClient;
        this.baseUrl = baseUrl;
        this.tokenService = tokenService;
    }

    public List<Role> getRoles() {
        return webClient
                .get()
                .uri(baseUrl + "/roles")
                .header("Authorization", "Bearer " + tokenService.getToken())
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToFlux(Role.class)
                .collectList()
                .block();
    }

    public Role getRole(Long id) {
        return webClient
                .get()
                .uri(baseUrl + "/roles/" + id)
                .header("Authorization", "Bearer " + tokenService.getToken())
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToMono(Role.class)
                .block();
    }

    public Role createRole(Role role) {
        return webClient
                .post()
                .uri(baseUrl + "/roles")
                .header("Authorization", "Bearer " + tokenService.getToken())
                .bodyValue(role)
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToMono(Role.class)
                .block();
    }

    public Role updateRole(Long id, Role role) {
        return webClient
                .put()
                .uri(baseUrl + "/roles/" + id)
                .header("Authorization", "Bearer " + tokenService.getToken())
                .bodyValue(role)
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToMono(Role.class)
                .block();
    }

    public void deleteRole(Long id) {
        webClient
                .delete()
                .uri(baseUrl + "/roles/" + id)
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
