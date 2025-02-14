package com.example.finalfront.service;

import com.example.finalfront.dto.LoginRequest;
import com.example.finalfront.dto.RegisterRequest;
import com.example.finalfront.dto.ResponseMessage;
import com.example.finalfront.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final WebClient webClient;
    private final String baseUrl;
    private final TokenService tokenService;

    public UserService(WebClient webClient, @Value("${spring.api.base-url}") String baseUrl, TokenService tokenService) {
        this.webClient = webClient;
        this.baseUrl = baseUrl;
        this.tokenService = tokenService;
    }

    public List<User> getUsers() {
        List<User> users = webClient
                .get()
                .uri(baseUrl + "/users")
                .header("Authorization", "Bearer " + tokenService.getToken())
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToFlux(User.class)
                .collectList()
                .block();

        log.info("Получено {} пользователей", users.get(0).getLogin());

        return users;
    }

    public User getUser(Long id) {
        return webClient
                .get()
                .uri(baseUrl + "/users/" + id)
                .header("Authorization", "Bearer " + tokenService.getToken())
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToMono(User.class)
                .block();
    }

    public User createUser(User user) {
        return webClient
                .post()
                .uri(baseUrl + "/users")
                .header("Authorization", "Bearer " + tokenService.getToken())
                .bodyValue(user)
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToMono(User.class)
                .block();
    }

    public User updateUser(Long id, User user) {
        return webClient
                .put()
                .uri(baseUrl + "/users/" + id)
                .header("Authorization", "Bearer " + tokenService.getToken())
                .bodyValue(user)
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToMono(User.class)
                .block();
    }

    public void deleteUser(Long id) {
        webClient
                .delete()
                .uri(baseUrl + "/users/" + id)
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

    public User getUserByUsername(String username) {
        return webClient
                .get()
                .uri(baseUrl + "/users/username/" + username)
                .header("Authorization", "Bearer " + tokenService.getToken())
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToMono(User.class)
                .block();
    }

    public String registerUser(RegisterRequest registerRequest) {
        return webClient
                .post()
                .uri(baseUrl + "/auth/register")
                .bodyValue(registerRequest)
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> clientResponse.createException()
                                .flatMap(ex -> {
                                    return Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"));
                                })
                )
                .bodyToMono(String.class)
                .block();
    }

    public String loginUser(LoginRequest loginRequest) {
        String token = webClient
                .post()
                .uri(baseUrl + "/auth/login")
                .bodyValue(loginRequest)
                .retrieve()
                .onStatus(
                        HttpStatus.FORBIDDEN::equals,
                        clientResponse -> Mono.error(new RuntimeException("Доступ запрещен: недостаточно прав"))
                )
                .bodyToMono(ResponseMessage.class)  // Десериализуем ответ в AuthResponse
                .map(authResponse -> {
                    String token1 = authResponse.getToken();
                    return token1;
                })
                .block();

        tokenService.saveToken(token);

        return token;
    }

    public void logout() {
        tokenService.removeToken();
    }

}
