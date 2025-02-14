package com.example.finalproject.controllers;

import com.example.finalproject.models.ModelUser;
import com.example.finalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserApiController {
    @Qualifier("userService")
    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<List<ModelUser>> getUsers() {
        List<ModelUser> modelUsers = userService.getUsers();
        return ResponseEntity.ok(modelUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModelUser> getUserById(@PathVariable Long id) {
        ModelUser modelUser = userService.getUserById(id);
        return ResponseEntity.ok(modelUser);
    }

    @PostMapping
    public ResponseEntity<ModelUser> createUser(@RequestBody ModelUser modelUser) {
        ModelUser createdModelUser = userService.createUser(modelUser);
        return ResponseEntity.ok(createdModelUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModelUser> updateUser(@PathVariable Long id, @RequestBody ModelUser modelUser) {
        ModelUser updatedModelUser = userService.updateUser(id, modelUser);
        return ResponseEntity.ok(updatedModelUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
