package com.example.finalproject.service;

import com.example.finalproject.dto.LoginRequest;
import com.example.finalproject.dto.RegisterRequest;
import com.example.finalproject.models.ModelUser;

import java.util.List;

public interface UserService {
    List<ModelUser> getUsers();

    ModelUser getUserById(Long id);

    ModelUser getUserByLogin(String login);

    ModelUser createUser(ModelUser modelUser);

    ModelUser updateUser(Long id, ModelUser modelUser);

    void deleteUser(Long id);

    void registerUser(RegisterRequest registerRequest);

    String authenticateUser(LoginRequest loginRequest);
}
