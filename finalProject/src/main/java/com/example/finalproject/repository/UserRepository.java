package com.example.finalproject.repository;

import com.example.finalproject.models.ModelUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<ModelUser, Long> {
    ModelUser findByLogin(String login);
}
