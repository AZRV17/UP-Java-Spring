package com.example.finalproject.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class ModelUser {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    @Email
    private String email;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number")
    private String phoneNumber;

    @Setter
    @Getter
    @ManyToOne
    private Role role;

    @Setter
    @Getter
    @OneToMany(mappedBy = "modelUser")
    @JsonBackReference
    private List<Loan> loans = new ArrayList<>();

    @Setter
    @Getter
    @OneToMany(mappedBy = "modelUser")
    @JsonBackReference
    private List<Review> reviews = new ArrayList<>();

    public @NotBlank String getLogin() {
        return login;
    }

    public void setLogin(@NotBlank String login) {
        this.login = login;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    public @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number") String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number") String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}

