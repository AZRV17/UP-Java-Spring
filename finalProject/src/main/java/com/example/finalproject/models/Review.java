package com.example.finalproject.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    @Max(5)
    private int rating;

    @Size(max = 500)
    private String comment;

    @ManyToOne
    @JsonManagedReference
    private ModelUser modelUser;

    @ManyToOne
    @JsonManagedReference
    private Book book;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Min(1)
    @Max(5)
    public int getRating() {
        return rating;
    }

    public void setRating(@Min(1) @Max(5) int rating) {
        this.rating = rating;
    }

    public @Size(max = 500) String getComment() {
        return comment;
    }

    public void setComment(@Size(max = 500) String comment) {
        this.comment = comment;
    }

    public ModelUser getUser() {
        return modelUser;
    }

    public void setUser(ModelUser modelUser) {
        this.modelUser = modelUser;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}

